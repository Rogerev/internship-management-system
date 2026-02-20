package com.internship.ui;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {

        setTitle("Admin Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnDept = new JButton("Manage Departments");
        JButton btnPerson = new JButton("Manage Persons");
        JButton btnSalary = new JButton("Manage Salary");
        JButton btnLogout = new JButton("Logout");

        add(btnDept);
        add(btnPerson);
        add(btnSalary);
        add(btnLogout);

        // ACTIONS
        btnDept.addActionListener(e -> new DepartmentUI());
        btnPerson.addActionListener(e -> new PersonUI());
        btnSalary.addActionListener(e -> new SalaryUI());

        btnLogout.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }
}
