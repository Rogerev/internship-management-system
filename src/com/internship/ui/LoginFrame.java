package com.internship.ui;

import javax.swing.*;
import java.awt.*;
import com.internship.dao.UserDAO;

public class LoginFrame extends JFrame {

    JTextField txtUser;
    JPasswordField txtPass;

    public LoginFrame() {
        setTitle("Internship Management System - Login");
        setSize(400, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblTitle = new JLabel("Login", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel lblUser = new JLabel("Username:");
        JLabel lblPass = new JLabel("Password:");

        txtUser = new JTextField();
        txtPass = new JPasswordField();

        JButton btnLogin = new JButton("Login");

        JPanel panel = new JPanel(null);

        lblTitle.setBounds(0, 15, 400, 30);

        lblUser.setBounds(50, 70, 100, 25);
        txtUser.setBounds(150, 70, 180, 25);

        lblPass.setBounds(50, 110, 100, 25);
        txtPass.setBounds(150, 110, 180, 25);

        btnLogin.setBounds(150, 160, 100, 30);

        panel.add(lblTitle);
        panel.add(lblUser);
        panel.add(txtUser);
        panel.add(lblPass);
        panel.add(txtPass);
        panel.add(btnLogin);

        add(panel);

        // LOGIN BUTTON ACTION
        btnLogin.addActionListener(e -> {

            String username = txtUser.getText();
            String password = new String(txtPass.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter username and password");
                return;
            }

            String role = UserDAO.authenticate(username, password);

            if (role == null) {
                JOptionPane.showMessageDialog(this, "Invalid Login");
            } else if (role.equalsIgnoreCase("ADMIN")) {
                new AdminDashboard();
                dispose();
            } else {
                new UserDashboard();
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
