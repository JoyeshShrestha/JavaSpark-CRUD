/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.db;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author lenovo
 */
public class Connection {

    static java.sql.Connection conn;
    static String url = "jdbc:mysql://localhost:3305/intern_task_afterdesign";
    static String username = "root";
    static String password = "root123";

    public static java.sql.Connection getConnection() throws SQLException {

        if (conn == null || conn.isClosed()) {

            conn = DriverManager.getConnection(url, username, password);
        }
        return conn;
    }

    public void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
