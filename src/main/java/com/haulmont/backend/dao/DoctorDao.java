package com.haulmont.backend.dao;

import com.haulmont.backend.Doctor;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DoctorDao extends AbstractEntityDAO<Doctor> {

    @Override
    protected Doctor getEntity(ResultSet rs) throws SQLException {
        return new Doctor(rs.getLong("ID"),
                          rs.getString("NAME"),
                          rs.getString("LASTNAME"),
                          rs.getString("PATRONYMIC"),
                          rs.getString("SPECIALIZATION"));
    }

    @Override
    protected ResultSet getResultSetForGetAll(Statement statement) throws SQLException {
        return statement.executeQuery("SELECT * FROM DOCTORS");
    }

    @Override
    protected PreparedStatement getPreparedStatementForGetEntityById(Connection connection, long id)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM DOCTORS WHERE ID = ?");
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    @Override
    protected PreparedStatement getPreparedStatementForUpdate(Connection connection, Doctor doctor)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE DOCTORS SET NAME = ?, LASTNAME = ?, PATRONYMIC = ?, SPECIALIZATION = ? WHERE ID = ?");
        setValues(preparedStatement, doctor);
        preparedStatement.setLong(5, doctor.getId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement getPreparedStatementForAdd(Connection connection, Doctor doctor) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO DOCTORS (NAME, LASTNAME, PATRONYMIC, SPECIALIZATION) VALUES (?, ?, ?, ?)");
        setValues(preparedStatement, doctor);
        return preparedStatement;
    }

    @Override
    protected PreparedStatement getPreparedStatementForDelete(Connection connection, Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM DOCTORS WHERE ID = ?");
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    public Map<Long, Integer> getQuantityRecipes() {
        Map<Long, Integer> map = null;
        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT D.ID, COUNT(*) QTY FROM DOCTORS D " +
                                                       "JOIN RECIPES R ON D.ID = R.DOCTORID GROUP BY D.ID")) {
                map = new HashMap<>();
                while (rs.next()) {
                    Long id = rs.getLong("ID");
                    Integer quantity = rs.getInt("QTY");
                    map.put(id, quantity);
                }
        } catch (SQLException e) {
                printSQLException(e);
        }
        return map;
    }

    private void setValues(PreparedStatement preparedStatement, Doctor doctor) throws SQLException {
        preparedStatement.setString(1, doctor.getName());
        preparedStatement.setString(2, doctor.getLastName());
        preparedStatement.setString(3, doctor.getPatronymic());
        preparedStatement.setString(4, doctor.getSpecialization());
    }
}
