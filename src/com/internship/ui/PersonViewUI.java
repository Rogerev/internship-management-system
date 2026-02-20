package com.internship.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import com.internship.dao.PersonDAO;
import com.internship.model.Person;

public class PersonViewUI extends JFrame {

    JTable table;
    DefaultTableModel model;
    PersonDAO dao;   // ✅ DAO reference

    public PersonViewUI() {

        dao = new PersonDAO();  // ✅ initialize DAO

        setTitle("View Persons");
        setSize(650, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(
                new String[]{"ID", "Name", "Email", "Dept ID"}, 0
        );

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadPersons();

        setVisible(true);
    }

    private void loadPersons() {
        model.setRowCount(0);

        List<Person> list = dao.getAllPersons();  // ✅ FIXED

        for (Person p : list) {
            model.addRow(new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getEmail(),
                    p.getDeptId()
            });
        }
    }
}
