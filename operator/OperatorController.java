/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.operator;

import com.google.gson.Gson;
import com.mycompany.exceptions.CustomException;
import com.mycompany.exceptions.NotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import spark.Request;
import spark.Response;

/**
 *
 * @author lenovo
 */
class OperatorController {

    Operatordoa operatorDoa = new Operatordoa();

    public String addOperator(Request request, Response response) throws SQLException, CustomException {
        response.type("application/json");

        Operator operator = new Gson().fromJson(request.body(), Operator.class);

        try {
            operatorDoa.addAnOperator(operator);
        } catch (CustomException e) {
            if (e.getStatusCode() == 409) {
                response.status(e.getStatusCode());
                return "Dublicate Data";
            } else {
                response.status(e.getStatusCode());
                return "Server Side Error";
            }

        }
        return "Successfully Added";
    }

    public String displayallOperator(Request request, Response response) throws SQLException {
        response.type("text/plain");

        ArrayList allOperator = operatorDoa.displayAll();
        String allOperatorString = allOperator.toString();

        response.type("application/json");
        return new Gson().toJson(allOperator);
    }

    public String displaySpecificOperator(Request request, Response response, String email) throws SQLException {
        System.out.println(email);
        Operator operator = operatorDoa.displaySpecific(email);
        response.type("application/json");

        return new Gson().toJson(operator.toString());

    }

    String deleteSpecificOperator(Request request, Response response, String email) throws SQLException {
        String operator = operatorDoa.deleteSpecific(email);
        return operator;

//        return null;
    }

    String updateSpecificOperator(Request request, Response response, String email) throws CustomException {
        response.type("application/json");
        if (email.isEmpty()) {
            response.status(400);
            return "Incomplete Data Sent";
        }
        Operator operator;
        int count = 0;
        ArrayList<String> emptyList = new ArrayList<String>();
        ArrayList<String> invalidList = new ArrayList<String>();

        try {
            operator = new Gson().fromJson(request.body(), Operator.class);
        } catch (Exception e) {

            response.status(400);

            return "Bad Request";
        }
        if (operator.getName().isEmpty()) {
            response.status(400);

            emptyList.add("\n Name is empty \n");
            count++;
        }
        if (operator.getEmail().isEmpty()) {
            response.status(400);
            emptyList.add("Email is empty \n");
            count++;

        }
        if (operator.getAddress_id().isEmpty()) {
            response.status(400);
            emptyList.add(" Address Id is empty \n");
            count++;

        }
        if (operator.getRole_id().isEmpty()) {

            response.status(400);
            emptyList.add(" Role ID is empty \n");
            count++;

        }
        if (count > 0) {
            return emptyList.toString();
        }
        count = 0;
        if (!(Pattern.matches("[a-zA-Z]+", operator.getName()))) {
            response.status(422);
            invalidList.add("Invalid Data on Name Field \n");
            count++;

        }
        if (!(Pattern.matches("[0-9]{1}+", operator.getAddress_id()))) {
            response.status(422);
            invalidList.add("Invalid Data on Address Id Field \n");
            count++;

        }
        if (!(Pattern.matches("[0-9]{1}+", operator.getRole_id()))) {

            response.status(422);

            invalidList.add("Invalid Data on Role Id Field \n");
            count++;

        }
        String emailChecker = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        System.out.println("Email Check: " + (Pattern.matches(emailChecker, operator.getEmail())));
        if (!(Pattern.matches(emailChecker, operator.getEmail()))) {
            response.status(422);
            invalidList.add("Invalid Data on Email Field \n");
            count++;

        }

        if (count > 0) {
            return invalidList.toString();
        }
//        if(operator.getName())
        try {
            operatorDoa.updateSpecific(operator, email);
        } catch (NotFoundException ex) {

            response.status(ex.getStatusCode());
            return "User Not Found";

        }
//        return new Gson().toJson(operator.toString());
        return "Successfully Updated " + email; //To change body of generated methods, choose Tools | Templates.
    }

}
