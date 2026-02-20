package com.internship.ui;

import com.internship.dao.PersonDAO;
import com.internship.dao.SalaryDAO;
import com.internship.model.Person;
import com.internship.model.Salary;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SalaryUI extends JFrame {

    JComboBox<Person> cmbPerson;
    JTextField txtBasic, txtHra, txtAllowance, txtDeductions;

    JTable table;
    JButton btnAdd;

    DefaultTableModel model;

    SalaryDAO salaryDao = new SalaryDAO();
    PersonDAO personDao = new PersonDAO();

    public SalaryUI() {

        setTitle("Salary Management");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ================= FORM =================
        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));

        cmbPerson = new JComboBox<>();
        txtBasic = new JTextField();
        txtHra = new JTextField();
        txtAllowance = new JTextField();
        txtDeductions = new JTextField();

        form.add(new JLabel("Person"));
        form.add(cmbPerson);
        form.add(new JLabel("Basic Salary"));
        form.add(txtBasic);
        form.add(new JLabel("HRA"));
        form.add(txtHra);
        form.add(new JLabel("Allowance"));
        form.add(txtAllowance);
        form.add(new JLabel("Deductions"));
        form.add(txtDeductions);

        // ================= BUTTONS =================
        btnAdd = new JButton("Add Salary");
        JButton btnUpdate = new JButton("Update Salary");

        JPanel buttons = new JPanel();
        buttons.add(btnAdd);
        buttons.add(btnUpdate);

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.SOUTH);

        // ================= TABLE =================
        model = new DefaultTableModel(
                new String[]{
                        "Salary ID",
                        "Person ID",
                        "Basic",
                        "HRA",
                        "Allowance",
                        "Deductions",
                        "Net Salary"
                }, 0
        );

        table = new JTable(model);
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                top,
                new JScrollPane(table)
        );
        splitPane.setDividerLocation(220);
        splitPane.setResizeWeight(0.3);

        add(splitPane, BorderLayout.CENTER);

        // ================= LOAD DATA =================
        loadPersons();
        loadSalary();
        cmbPerson.addActionListener(e -> {
            Person p = (Person) cmbPerson.getSelectedItem();
            filterSalaryByPerson(p);
            updateAddButtonState(p);
        });


        // ================= EVENTS =================
        btnAdd.addActionListener(e -> addSalary());
        btnUpdate.addActionListener(e -> updateSalary());
        // ================= TABLE ROW CLICK =================
        table.getSelectionModel().addListSelectionListener(e ->
        {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1)
            {

                int row = table.getSelectedRow();
                int personId =
                        Integer.parseInt(model.getValueAt(row, 1).toString());

                // Select correct person in combo box
                for (int i = 0; i < cmbPerson.getItemCount(); i++) {
                    Person p = cmbPerson.getItemAt(i);
                    if (p.getId() == personId) {
                        cmbPerson.setSelectedIndex(i);
                        break;
                    }
                }

                txtBasic.setText(model.getValueAt(row, 2).toString());
                txtHra.setText(model.getValueAt(row, 3).toString());
                txtAllowance.setText(model.getValueAt(row, 4).toString());
                txtDeductions.setText(model.getValueAt(row, 5).toString());
            }
        });

        setVisible(true);
    }


    // ================= METHODS =================

    private void loadPersons() {
        cmbPerson.removeAllItems();
        for (Person p : personDao.getAllPersons()) {
            cmbPerson.addItem(p); // uses toString()
        }
    }

    private void loadSalary() {
        model.setRowCount(0);
        for (Salary s : salaryDao.getAllSalary()) {
            model.addRow(new Object[]{
                    s.getSalaryId(),
                    s.getPersonId(),
                    s.getBasic(),
                    s.getHra(),
                    s.getAllowance(),
                    s.getDeductions(),
                    s.getNet()
            });
        }
    }

    private void addSalary() {
        if (!validateSalaryInput()) return;
        try {
            Person p = (Person) cmbPerson.getSelectedItem();
            if (p == null) {
                JOptionPane.showMessageDialog(this, "Select a person");
                return;
            }

            double basic = Double.parseDouble(txtBasic.getText());
            double hra = Double.parseDouble(txtHra.getText());
            double allowance = Double.parseDouble(txtAllowance.getText());
            double deductions = Double.parseDouble(txtDeductions.getText());
            double net = basic + hra + allowance - deductions;

            Salary s = new Salary();
            s.setPersonId(p.getId());
            s.setBasic(basic);
            s.setHra(hra);
            s.setAllowance(allowance);
            s.setDeductions(deductions);
            s.setNet(net);

            if (salaryDao.addSalary(s)) {
                JOptionPane.showMessageDialog(this, "Salary Added");
                loadSalary();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Salary already exists. You can update it.");
                selectSalaryByPersonId(p.getId());
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid numbers");
        }
    }

    private void updateSalary() {
        if (!validateSalaryInput()) return;
        try {
            Person p = (Person) cmbPerson.getSelectedItem();
            if (p == null) {
                JOptionPane.showMessageDialog(this, "Select a person");
                return;
            }

            double basic = Double.parseDouble(txtBasic.getText());
            double hra = Double.parseDouble(txtHra.getText());
            double allowance = Double.parseDouble(txtAllowance.getText());
            double deductions = Double.parseDouble(txtDeductions.getText());
            double net = basic + hra + allowance - deductions;

            Salary s = new Salary();
            s.setPersonId(p.getId());
            s.setBasic(basic);
            s.setHra(hra);
            s.setAllowance(allowance);
            s.setDeductions(deductions);
            s.setNet(net);

            if (salaryDao.updateSalary(s)) {
                JOptionPane.showMessageDialog(this, "Salary Updated");
                loadSalary();
            } else {
                JOptionPane.showMessageDialog(this, "No salary found to update");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input");
        }
    }

    private void clearFields() {
        txtBasic.setText("");
        txtHra.setText("");
        txtAllowance.setText("");
        txtDeductions.setText("");
    }

    private void selectSalaryByPersonId(int personId) {
        for (int i = 0; i < model.getRowCount(); i++) {
            int pid = Integer.parseInt(model.getValueAt(i, 1).toString());
            if (pid == personId) {
                table.setRowSelectionInterval(i, i);
                table.scrollRectToVisible(
                        table.getCellRect(i, 0, true)
                );
                break;
            }
        }
    }


    private boolean validateSalaryInput() {

        if (cmbPerson.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a person");
            return false;
        }

        if (txtBasic.getText().trim().isEmpty()
                || txtHra.getText().trim().isEmpty()
                || txtAllowance.getText().trim().isEmpty()
                || txtDeductions.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "All salary fields are required");
            return false;
        }

        try {
            double basic = Double.parseDouble(txtBasic.getText());
            double hra = Double.parseDouble(txtHra.getText());
            double allowance = Double.parseDouble(txtAllowance.getText());
            double deductions = Double.parseDouble(txtDeductions.getText());

            if (basic < 0 || hra < 0 || allowance < 0 || deductions < 0) {
                JOptionPane.showMessageDialog(this,
                        "Salary values cannot be negative");
                return false;
            }

            double net = basic + hra + allowance - deductions;
            if (net < 0) {
                JOptionPane.showMessageDialog(this,
                        "Net salary cannot be negative");
                return false;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Salary fields must be numeric");
            return false;
        }

        return true;
    }
    private void filterSalaryByPerson(Person p) {

        // reload all salary first
        loadSalary();

        if (p == null) return;

        // filter rows
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            int personIdInRow =
                    Integer.parseInt(model.getValueAt(i, 1).toString());

            if (personIdInRow != p.getId()) {
                model.removeRow(i);
            }
        }
    }
    private void updateAddButtonState(Person p) {

        btnAdd.setEnabled(true); // default allow

        if (p == null) return;

        for (Salary s : salaryDao.getAllSalary()) {
            if (s.getPersonId() == p.getId()) {
                btnAdd.setEnabled(false); // salary exists → disable add
                return;
            }
        }
    }
}
