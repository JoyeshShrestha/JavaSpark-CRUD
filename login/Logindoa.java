/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.login;

import static com.mycompany.db.Connection.getConnection;
import com.mycompany.operator.Operator;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author lenovo
 */
public class Logindoa {

    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
     */
    public boolean authenticate(String emailEntered, String passwordEntered) throws SQLException {
        try (Connection conn = getConnection()) {
            boolean ismatch = false;
            try (CallableStatement statement = conn.prepareCall("{call check_password(?, ?,?)}")) {
                statement.setString(1, emailEntered);
                statement.setString(2, passwordEntered);
                statement.registerOutParameter(3, Types.BOOLEAN);

                statement.execute();
                ismatch = statement.getBoolean(3);

                ResultSet resultSet = statement.getResultSet();
                System.out.println(ismatch);
                System.out.println(resultSet);
                return ismatch;

            } catch (Exception e) {
                System.out.println(e);
            }
            return ismatch;

//            String query = "SELECT password FROM operator where email = ?";
//            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//                pstmt.setString(1, emailEntered);
//
//                ResultSet rs = pstmt.executeQuery();
//                while (rs.next()) {
//                    String actualPassword = rs.getString("password");
//                    return actualPassword;
//
//                }
//            } catch (Exception ex) {
//                System.out.println(ex);
//            }
//
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }
//        return null;
        }

    }

    public Operator createObject(String emailEntered) throws SQLException {
        try (Connection conn = getConnection()) {
            Operator operator = new Operator();

            String query = "SELECT * FROM operator LEFT OUTER JOIN address ON operator.address_id = address.id where operator.email = ?";
            System.out.println("okoko");
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, emailEntered);
                //        ResultSet rs = stmt.executeQuery();
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        operator.setId(rs.getInt("operator.id"));
                        operator.setName(rs.getString("operator.name"));
                        operator.setEmail(rs.getString("operator.email"));
//                        operator.setPassword(rs.getString("employees.password"));
                        operator.setDistrict(rs.getString("address.district"));
                        operator.setMunicipality(rs.getString("address.municipality"));
                        operator.setProvince(rs.getString("address.province"));
//                        operator.setRole_name(rs.getString("role.name"));
                        operator.setLogin_count(rs.getInt("operator.login_count"));
                        operator.setStatus(rs.getString("operator.status"));

//                        System.out.println(operator.getMunicipality());
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

    public void increaseCounter(String emailEntered, int loginCount, String status) {
        if (loginCount >= 3) {
            disableStatus(emailEntered, loginCount, status);
        } else {
            addCounter(emailEntered, loginCount);
        }
    }

    private void disableStatus(String emailEntered, int loginCount, String status) { //To change body of generated methods, choose Tools | Templates.
        try (Connection conn = getConnection()) {
//            employees employee = new employees();
            String query = "UPDATE operator SET status=? WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(2, emailEntered);
                if (status.equals("enable")) {

                    stmt.setString(1, "disable");
                    stmt.executeUpdate();
                    addCounter(emailEntered, loginCount);

                } else {
                    stmt.setString(1, "disable");
                    stmt.executeUpdate();
                    addCounter(emailEntered, loginCount);

                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void addCounter(String emailEntered, int loginCount) {
        try (Connection conn = getConnection()) {

            int increase = loginCount + 1;
            String query = "UPDATE operator SET login_count=? WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, increase);
//            System.out.println(emailEntered);
            stmt.setString(2, emailEntered);
            stmt.executeUpdate();
            System.out.println(increase);

        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public void changeZero(String emailEntered) {
        try (Connection conn = getConnection()) {

            String query = "UPDATE operator SET login_count=0 WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, emailEntered);
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);

        }
    }
}
