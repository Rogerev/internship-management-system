package com.internship.ui;

import com.internship.dao.DepartmentDAO;
import com.internship.model.Department;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DepartmentUI extends JFrame {

    JTextField txtId, txtCode, txtName;
    JTable table;
    DefaultTableModel model;

    DepartmentDAO dao = new DepartmentDAO();

    public DepartmentUI() {

        setTitle("Department Management");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));

        txtId = new JTextField();
        txtId.setEditable(false);
        txtCode = new JTextField();
        txtName = new JTextField();

        form.add(new JLabel("Department ID"));
        form.add(txtId);
        form.add(new JLabel("Department Code"));
        form.add(txtCode);
        form.add(new JLabel("Department Name"));
        form.add(txtName);

        add(form, BorderLayout.NORTH);

        JPanel buttons = new JPanel();

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");

        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);

        add(buttons, BorderLayout.SOUTH);

        model = new DefaultTableModel(
                new String[]{"ID", "Code", "Name"}, 0
        );

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadDepartments();

        btnAdd.addActionListener(e -> addDepartment());
        btnUpdate.addActionListener(e -> updateDepartment());
        btnDelete.addActionListener(e -> deleteDepartment());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = table.getSelectedRow();
            if (row != -1) {
                txtId.setText(model.getValueAt(row, 0).toString());
                txtCode.setText(model.getValueAt(row, 1).toString());
                txtName.setText(model.getValueAt(row, 2).toString());
            }
        });

        setVisible(true);
    }

    private void loadDepartments() {
        model.setRowCount(0);
        for (Department d : dao.getAllDepartments()) {
            model.addRow(new Object[]{
                    d.getId(),
                    d.getCode(),
                    d.getName()
            });
        }
    }

    private void addDepartment() {

        if (!validateDepartmentInput()) return;

        dao.addDepartment(
                txtCode.getText().trim(),
                txtName.getText().trim()
        );

        loadDepartments();
        clear();
    }

    private void updateDepartment() {

        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a department to update");
            return;
        }

        if (!validateDepartmentInput()) return;

        dao.updateDepartment(
                Integer.parseInt(txtId.getText()),
                txtCode.getText().trim(),
                txtName.getText().trim()
        );

        loadDepartments();
        clear();
    }

    private void deleteDepartment() {

        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a department to delete");
            return;
        }

        dao.deleteDepartment(Integer.parseInt(txtId.getText()));
        loadDepartments();
        clear();
    }

    private void clear() {
        txtId.setText("");
        txtCode.setText("");
        txtName.setText("");
    }

    private boolean validateDepartmentInput() {

        String code = txtCode.getText().trim();
        String name = txtName.getText().trim();

        if (code.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Department code cannot be empty");
            return false;
        }

        if (!code.matches("^[A-Za-z0-9]+$")) {
            JOptionPane.showMessageDialog(this,
                    "Department code must contain only letters and numbers");
            return false;
        }

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Department name cannot be empty");
            return false;
        }

        if (!name.matches("^[A-Za-z ]+$")) {
            JOptionPane.showMessageDialog(this,
                    "Department name must contain only alphabets and spaces");
            return false;
        }

        return true;
    }
}
