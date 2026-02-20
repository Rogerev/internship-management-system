package com.internship.ui;

import com.internship.dao.DepartmentDAO;
import com.internship.model.Department;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DepartmentViewUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    DepartmentDAO dao = new DepartmentDAO(); // IMPORTANT

    public DepartmentViewUI() {

        setTitle("Departments");
        setSize(600, 350);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(
                new String[]{"ID", "Code", "Name"}, 0);

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadDepartments(); // MUST be called

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
}
