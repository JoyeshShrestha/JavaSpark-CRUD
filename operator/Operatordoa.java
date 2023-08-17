/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.operator;

import static com.mycompany.db.Connection.getConnection;
import com.mycompany.exceptions.CustomException;
import com.mycompany.exceptions.NotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lenovo
 */
public class Operatordoa {

    public ArrayList displayAll() throws SQLException {
        try (Connection conn = getConnection()) {
            AllOperator alloperator = new AllOperator();

            String query = "SELECT * FROM operator ";
            System.out.println("okoko2");
            try (PreparedStatement stmt = conn.prepareStatement(query)) {

                //        ResultSet rs = stmt.executeQuery();
                try (ResultSet rs = stmt.executeQuery()) {
                    ArrayList listarray = new ArrayList();
                    Map map = new HashMap();
                    while (rs.next()) {
//                        map.put("id", rs.getInt("id"));
//
//                        map.put("name", rs.getString("name"));
//                        map.put("email", rs.getString("email"));
                        System.out.println(map);

                        alloperator.setId(rs.getInt("id"));
                        alloperator.setName(rs.getString("name"));
                        alloperator.setEmail(rs.getString("email"));

                        listarray.add(alloperator.toString());
                    }

                    rs.close();
                    System.out.println(listarray);
                    return listarray;
                } catch (Exception ex) {
                    System.out.println(ex);
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }
            return null;

        }
    }

    void addAnOperator(Operator addOperator) throws SQLException, CustomException {
        try (Connection conn = getConnection()) {

//            String query = "INSERT INTO employees(id,name,address_id,role_id,email,password,login_count,status) LEFT OUTER JOIN address(district, municipality,province) ON employees.address_id = address.id VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            String query = "INSERT INTO operator(name, email, address_id, role_id, password, login_count, status) VALUES (?,?,?,?,?,0,'enable')";

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, addOperator.getName());
            System.out.println(addOperator.getName());
            pstmt.setString(2, addOperator.getEmail());
            pstmt.setString(3, addOperator.getAddress_id());
            pstmt.setString(4, addOperator.getRole_id());
            pstmt.setString(5, addOperator.getPassword());
            System.out.println(pstmt);
            pstmt.execute();
//            System.out.prinln(executeUpdate);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
            System.out.println(e.getErrorCode());
            if (e.getErrorCode() == 1062) {
                throw new CustomException(409, "Dublicate data");
            } else {
                throw new CustomException(500, "Server Side Error");
            }
        }

    }

    Operator displaySpecific(String email) throws SQLException {
        try (Connection conn = getConnection()) {
            Operator operator = new Operator();

            String query = "SELECT * FROM operator  where email = ? ";
            System.out.println("okoko2");
            try (PreparedStatement stmt = conn.prepareStatement(query)) {

                //        ResultSet rs = stmt.executeQuery();
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {
                        operator.setId(rs.getInt("id"));
                        operator.setName(rs.getString("name"));
                        operator.setEmail(rs.getString("email"));
                        operator.setAddress_id(rs.getString("address_id"));
                        operator.setRole_id(rs.getString("role_id"));

                    }

                    rs.close();

                    return operator;
                } catch (Exception ex) {
                    System.out.println(ex);
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }
            return null;

        }
    }

    String deleteSpecific(String email) throws SQLException {
        try (Connection conn = getConnection()) {

            String query = "DELETE FROM operator  where email = ? ";
            System.out.println("okoko2");
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, email);

                //        ResultSet rs = stmt.executeQuery();
                stmt.execute();
                return "Deleted successfully";
            } catch (Exception ex) {
                System.out.println(ex);
            }
            return null;

        }
    }

    Operator updateSpecific(Operator addOperator, String email) throws NotFoundException, CustomException {
        try (Connection conn = getConnection()) {

//            String query = "INSERT INTO employees(id,name,address_id,role_id,email,password,login_count,status) LEFT OUTER JOIN address(district, municipality,province) ON employees.address_id = address.id VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            String query = "UPDATE operator SET name = ?, email = ?, address_id = ?, role_id= ?, password = ?, login_count = 0, status = 'enable' WHERE email = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, addOperator.getName());
                System.out.println(addOperator.getName());
                pstmt.setString(2, email);
                pstmt.setString(3, addOperator.getAddress_id());
                pstmt.setString(4, addOperator.getRole_id());
                pstmt.setString(5, addOperator.getPassword());
                pstmt.setString(6, email);

                System.out.println(pstmt);

                if (pstmt.executeUpdate() < 1) {
                    throw new NotFoundException(404, "User Not Found");
                }

//            System.out.prinln(executeUpdate);
            }
        } catch (SQLException e) {
            e.printStackTrace();

            System.out.println(e.getErrorCode());
            System.out.println(e);
        }
        return null;
    }

}
