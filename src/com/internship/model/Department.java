package com.internship.model;

public class Department {

    private int id;
    private String code;
    private String name;

    public Department() {
    }

    public Department(int id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ⭐ REQUIRED for JComboBox display
    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}
