package com.internship.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import com.internship.db.DBConnection;

public class Person {

    public static void addPerson() {
        Scanner sc = new Scanner(System.in);

        System.out.print("PIN: ");
        String pin = sc.next();

        System.out.print("Name: ");
        String name = sc.next();

        System.out.print("Gender (M/F): ");
        String gender = sc.next();

        System.out.print("Department Code: ");
        String dept = sc.next();

        System.out.print("Card Number: ");
        String card = sc.next();

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO persons VALUES (NULL,?,?,?,?,?)"
            );
            ps.setString(1, pin);
            ps.setString(2, name);
            ps.setString(3, gender);
            ps.setString(4, dept);
            ps.setString(5, card);

            ps.executeUpdate();
            System.out.println("Person added successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void viewPerson() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter PIN: ");
        String pin = sc.next();

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM persons WHERE pin=?"
            );
            ps.setString(1, pin);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Gender: " + rs.getString("gender"));
                System.out.println("Department: " + rs.getString("dept_code"));
                System.out.println("Card No: " + rs.getString("card_no"));
            } else {
                System.out.println("Person not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
