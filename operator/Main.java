/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.operator;

import com.mycompany.login.Controller;
import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.put;

/**
 *
 * @author lenovo
 */
public class Main {

    public static void main(String[] args) {
        Controller control = new Controller();
        OperatorController operatorcontrol = new OperatorController();
        path("/operator/aunthenticate", () -> {

            post("", (request, response) -> {

                return control.login(request, response);

            });
            delete("", (request, response) -> {

                return control.logout(request, response);

            });

        });
        path("/operator", () -> {

            before("", (request, response) -> {
                if (control.checkSession(request, response) == null) {
                    halt(401, "There is no session, please login");
                }

            });

            post("", (request, response) -> {

                return operatorcontrol.addOperator(request, response);

            });
            get("", (request, response) -> {

                return operatorcontrol.displayallOperator(request, response);

            });

//            path("operator/:email", () -> {
//                before("", (request, response) -> {
//
//                    if (control.checkSession(request, response) == null) {
//                        halt("There is no session, please login");
//                    }
//
//                });
            before("/:email", (request, response) -> {
//                System.out.println(request.pathInfo());
                if (!request.pathInfo().equals("/operator/aunthenticate")) {

                    if (control.checkSession(request, response) == null) {

                        halt(401, "There is no session, please login");
                    }
                }
            });
            get("/:email", (request, response) -> {
                response.type("application/json");
                String email = request.params(":email");
                return operatorcontrol.displaySpecificOperator(request, response, email);

            });
            put("/:email", (request, response) -> {
                response.type("application/json");
                String email = request.params(":email");
                return operatorcontrol.updateSpecificOperator(request, response, email);

            });
            delete("/:email", (request, response) -> {
                response.type("application/json");
                String email = request.params(":email");
                return operatorcontrol.deleteSpecificOperator(request, response, email);

            });
//            });
        });
    }
}
