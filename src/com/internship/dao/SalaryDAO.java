package com.internship.dao;

import com.internship.db.DBConnection;
import com.internship.model.Salary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaryDAO {

    public boolean addSalary(Salary s) {

        String sql = """
        INSERT INTO salary
        (person_id, basic_salary, hra, allowance, deductions, net_salary)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, s.getPersonId());
            ps.setDouble(2, s.getBasic());
            ps.setDouble(3, s.getHra());
            ps.setDouble(4, s.getAllowance());
            ps.setDouble(5, s.getDeductions());
            ps.setDouble(6, s.getNet());

            ps.executeUpdate();
            return true;

        } catch (SQLIntegrityConstraintViolationException e) {
            // Salary already exists
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSalary(Salary s) {

        String sql = """
        UPDATE salary SET
        basic_salary=?,
        hra=?,
        allowance=?,
        deductions=?,
        net_salary=?
        WHERE person_id=?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, s.getBasic());
            ps.setDouble(2, s.getHra());
            ps.setDouble(3, s.getAllowance());
            ps.setDouble(4, s.getDeductions());
            ps.setDouble(5, s.getNet());
            ps.setInt(6, s.getPersonId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Salary> getAllSalary() {

        List<Salary> list = new ArrayList<>();
        String sql = "SELECT * FROM salary";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Salary s = new Salary();
                s.setSalaryId(rs.getInt("salary_id"));
                s.setPersonId(rs.getInt("person_id"));
                s.setBasic(rs.getDouble("basic_salary"));
                s.setHra(rs.getDouble("hra"));
                s.setAllowance(rs.getDouble("allowance"));
                s.setDeductions(rs.getDouble("deductions"));
                s.setNet(rs.getDouble("net_salary"));
                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
