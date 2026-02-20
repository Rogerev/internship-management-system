package com.internship.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import com.internship.db.DBConnection;

public class Department {

    public static void addDepartment() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Department Code: ");
        String code = sc.next();

        System.out.print("Department Name: ");
        String name = sc.next();

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO departments (dept_code, dept_name) VALUES (?, ?)"
            );
            ps.setString(1, code);
            ps.setString(2, name);
            ps.executeUpdate();

            System.out.println("Department added successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void viewDepartments() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM departments");

            System.out.println("\nDepartment List:");
            while (rs.next()) {
                System.out.println(
                        rs.getString("dept_code") + " - " +
                                rs.getString("dept_name")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
