/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.operator;

/**
 *
 * @author lenovo
 */
public class Operator {

    int id;
    String name;
    String address_id;
    String role_id;
    String permission_id;
    String email;
    String password;
    int login_count;
    String status;
    String district;
    String municipality;
    String province;
    String role_name;
    String permissiom_name;

    public Operator() {
    }

    public Operator(int id, String name, String address_id, String role_id, String permission_id, String email, String password, int login_count, String status, String district, String municipality, String province, String role_name, String permissiom_name) {
        this.id = id;
        this.name = name;
        this.address_id = address_id;
        this.role_id = role_id;
        this.permission_id = permission_id;
        this.email = email;
        this.password = password;
        this.login_count = login_count;
        this.status = status;
        this.district = district;
        this.municipality = municipality;
        this.province = province;
        this.role_name = role_name;
        this.permissiom_name = permissiom_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getPermission_id() {
        return permission_id;
    }

    public void setPermission_id(String permission_id) {
        this.permission_id = permission_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLogin_count() {
        return login_count;
    }

    public void setLogin_count(int login_count) {
        this.login_count = login_count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getPermissiom_name() {
        return permissiom_name;
    }

    public void setPermissiom_name(String permissiom_name) {
        this.permissiom_name = permissiom_name;
    }

    @Override
    public String toString() {
        return ""
                + "{" + "id=" + id + ", name=" + name + ", address_id=" + address_id + ", role_id=" + role_id + ", permission_id=" + permission_id + ", email=" + email + '}';
    }

}
