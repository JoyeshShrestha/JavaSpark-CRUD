/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.login;

import com.google.gson.Gson;
import com.mycompany.operator.Operator;
import java.sql.SQLException;
import spark.Request;
import spark.Response;

/**
 *
 * @author lenovo
 */
public class Controller {

    Logindoa login = new Logindoa();
    Operator operator = new Operator();

    public String login(Request request, Response response) throws SQLException {

//        response.type("application/json");
        //Take inputs from the operator
        String emailEntered = request.queryParams("email");
        String passwordEntered = request.queryParams("password");

        if (login.authenticate(emailEntered, passwordEntered)) {
            Operator operatorObj = login.createObject(emailEntered);

//            Operator operator = operator.createObject(String emailEntered);
            System.out.println(operatorObj.getStatus());
            if (operatorObj.getStatus().equals("disable")) {

                return "Account Disabled";

            } else {
//                login.changeZero(emailEntered);
                login.changeZero(emailEntered);
                request.session(true);
//
                request.session().attribute("operatorObject", new Gson().toJson(operatorObj));
                response.status(200);
                response.header("Access-Control-Allow-Origin", "*");
                response.header("Access-Control-Allow-Methods", "*");

                return "Login Successful";

            }
        } else {
            Operator operatorObj = login.createObject(emailEntered);

            login.increaseCounter(emailEntered, operatorObj.getLogin_count(), operatorObj.getStatus());
            response.status(401);
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "*");
            return "Invalid";
        }
    }

    public String logout(Request request, Response response) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        request.session(true);
//        request.session().attribute("operatorObject");
        String req = request.session().attribute("operatorObject");
        System.out.println(req);
        if (req == null) {
            return "There is nothing to logout";
        } else {
            request.session().removeAttribute("operatorObject");
            return "Logged out successfully";

        }
    }

    public String checkSession(Request request, Response response) {
        String req = request.session(true).attribute("operatorObject");

        return req;
    }
}
