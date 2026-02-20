package com.internship.model;

public class Person {

    private int id;
    private String name;
    private String email;
    private int deptId;

    public Person() {
    }

    public Person(int id, String name, String email, int deptId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.deptId = deptId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return name + " (ID: " + id + ")";
    }

}
