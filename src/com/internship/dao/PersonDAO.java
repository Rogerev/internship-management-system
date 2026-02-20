package com.internship.dao;

import com.internship.db.DBConnection;
import com.internship.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    public boolean addPerson(String name, String email, int deptId) {

        String sql = "INSERT INTO persons (name, email, dept_id) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setInt(3, deptId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePerson(int id, String name, String email, int deptId) {

        String sql = "UPDATE persons SET name=?, email=?, dept_id=? WHERE person_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setInt(3, deptId);
            ps.setInt(4, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePerson(int id) {

        String sql = "DELETE FROM persons WHERE person_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Person> getAllPersons() {

        List<Person> list = new ArrayList<>();
        String sql = "SELECT * FROM persons";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Person p = new Person();
                p.setId(rs.getInt("person_id"));
                p.setName(rs.getString("name"));
                p.setEmail(rs.getString("email"));
                p.setDeptId(rs.getInt("dept_id"));
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
