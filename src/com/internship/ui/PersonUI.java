package com.internship.ui;

import com.internship.dao.DepartmentDAO;
import com.internship.dao.PersonDAO;
import com.internship.model.Department;
import com.internship.model.Person;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PersonUI extends JFrame {

    JTextField txtId, txtName, txtEmail;
    JComboBox<Department> cmbDepartment;

    JTable table;
    DefaultTableModel model;

    PersonDAO personDao = new PersonDAO();
    DepartmentDAO deptDao = new DepartmentDAO();

    public PersonUI() {

        setTitle("Person Management");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));

        txtId = new JTextField();
        txtId.setEditable(false);
        txtName = new JTextField();
        txtEmail = new JTextField();
        cmbDepartment = new JComboBox<>();

        form.add(new JLabel("Person ID"));
        form.add(txtId);
        form.add(new JLabel("Name"));
        form.add(txtName);
        form.add(new JLabel("Email"));
        form.add(txtEmail);
        form.add(new JLabel("Department"));
        form.add(cmbDepartment);

        JPanel buttons = new JPanel(new FlowLayout());

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");

        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);

        topPanel.add(form, BorderLayout.CENTER);
        topPanel.add(buttons, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new String[]{"ID", "Name", "Email", "Dept ID"}, 0
        );

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadDepartments();
        loadPersons();

        btnAdd.addActionListener(e -> addPerson());
        btnUpdate.addActionListener(e -> updatePerson());
        btnDelete.addActionListener(e -> deletePerson());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = table.getSelectedRow();
            if (row != -1) {

                txtId.setText(model.getValueAt(row, 0).toString());
                txtName.setText(model.getValueAt(row, 1).toString());
                txtEmail.setText(model.getValueAt(row, 2).toString());

                int deptId = Integer.parseInt(model.getValueAt(row, 3).toString());
                for (int i = 0; i < cmbDepartment.getItemCount(); i++) {
                    Department d = cmbDepartment.getItemAt(i);
                    if (d.getId() == deptId) {
                        cmbDepartment.setSelectedIndex(i);
                        break;
                    }
                }
            }
        });

        setVisible(true);
    }

    private void loadDepartments() {
        cmbDepartment.removeAllItems();
        for (Department d : deptDao.getAllDepartments()) {
            cmbDepartment.addItem(d);
        }
    }

    private void loadPersons() {
        model.setRowCount(0);
        for (Person p : personDao.getAllPersons()) {
            model.addRow(new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getEmail(),
                    p.getDeptId()
            });
        }
    }

    private void addPerson() {

        if (!validatePersonInput()) return;

        Department d = (Department) cmbDepartment.getSelectedItem();

        boolean ok = personDao.addPerson(
                txtName.getText().trim(),
                txtEmail.getText().trim(),
                d.getId()
        );

        if (ok) {
            JOptionPane.showMessageDialog(this, "Person Added Successfully");
            loadPersons();
            clearFields();
        }
    }

    private void updatePerson() {

        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a person to update");
            return;
        }

        if (!validatePersonInput()) return;

        Department d = (Department) cmbDepartment.getSelectedItem();

        boolean ok = personDao.updatePerson(
                Integer.parseInt(txtId.getText()),
                txtName.getText().trim(),
                txtEmail.getText().trim(),
                d.getId()
        );

        if (ok) {
            JOptionPane.showMessageDialog(this, "Person Updated Successfully");
            loadPersons();
            clearFields();
        }
    }

    private void deletePerson() {

        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a person to delete");
            return;
        }

        boolean ok = personDao.deletePerson(
                Integer.parseInt(txtId.getText())
        );

        if (ok) {
            JOptionPane.showMessageDialog(this, "Person Deleted Successfully");
            loadPersons();
            clearFields();
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        cmbDepartment.setSelectedIndex(-1);
    }

    private boolean validatePersonInput() {

        String name = txtName.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty");
            return false;
        }

        if (!name.matches("^[A-Za-z ]+$")) {
            JOptionPane.showMessageDialog(this,
                    "Name must contain only alphabets and spaces");
            return false;
        }

        String email = txtEmail.getText().trim();

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email cannot be empty");
            return false;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format");
            return false;
        }

        if (cmbDepartment.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a department");
            return false;
        }

        return true;
    }
}
