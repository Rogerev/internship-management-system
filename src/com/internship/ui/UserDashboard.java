package com.internship.ui;

import javax.swing.*;
import java.awt.*;

public class UserDashboard extends JFrame {

    public UserDashboard() {


        setTitle("User Dashboard");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("User Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JButton btnViewDept = new JButton("View Departments");
        JButton btnViewPerson = new JButton("View Persons");
        JButton btnLogout = new JButton("Logout");

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.add(btnViewDept);
        panel.add(btnViewPerson);
        panel.add(btnLogout);

        add(title, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        btnViewDept.addActionListener(e -> new DepartmentViewUI());
        btnViewPerson.addActionListener(e -> new PersonViewUI());

        btnLogout.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        setVisible(true);
    }
}
