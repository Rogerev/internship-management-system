package com.internship.app;

import java.sql.*;
import java.util.Scanner;
import com.internship.db.DBConnection;

public class Login {

    public static boolean authenticate() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Username: ");
        String username = sc.next();

        System.out.print("Password: ");
        String password = sc.next();

        try {
            Connection con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("Login failed: Database not connected");
                return false;
            }

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE username=? AND password=?"
            );
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return false;
    }
}
