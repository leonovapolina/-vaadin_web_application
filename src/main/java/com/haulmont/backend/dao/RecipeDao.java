package com.haulmont.backend.dao;

import com.haulmont.backend.Doctor;
import com.haulmont.backend.Patient;
import com.haulmont.backend.Recipe;
import com.haulmont.backend.RecipePriority;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeDao extends AbstractEntityDAO<Recipe> {

    @Override
    protected Recipe getEntity(ResultSet rs) throws SQLException {
        Patient patient = new PatientDao().getById(rs.getLong("PATIENTID"));
        Doctor doctor = new DoctorDao().getById(rs.getLong("DOCTORID"));
        RecipePriority recipePriority = new RecipePriorityDao().getById(rs.getLong("PRIORITYID"));

        return new Recipe(rs.getLong("ID"),
                          rs.getString("DESCRIPTION"),
                          rs.getDate("CREATIONDATE"),
                          rs.getInt("VALIDITY"),
                          doctor,
                          patient,
                          recipePriority);
    }

    @Override
    protected ResultSet getResultSetForGetAll(Statement statement) throws SQLException {
        return statement.executeQuery("SELECT * FROM RECIPES");
    }

    @Override
    protected PreparedStatement getPreparedStatementForGetEntityById(Connection connection, long id)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM RECIPES WHERE ID = ?");
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    @Override
    protected PreparedStatement getPreparedStatementForUpdate(Connection connection, Recipe recipe)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE RECIPES " +
                "SET DESCRIPTION = ?, PATIENTID = ?, DOCTORID = ?, CREATIONDATE = ?, VALIDITY = ?, PRIORITYID = ? " +
                "WHERE ID = ?");
        setValues(preparedStatement, recipe);
        preparedStatement.setLong(7, recipe.getId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement getPreparedStatementForAdd(Connection connection, Recipe recipe) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO RECIPES " +
                "(DESCRIPTION, PATIENTID, DOCTORID, CREATIONDATE, VALIDITY, PRIORITYID) VALUES (?, ?, ?, ?, ?, ?)");
        setValues(preparedStatement, recipe);
        return preparedStatement;
    }

    @Override
    protected PreparedStatement getPreparedStatementForDelete(Connection connection, Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM RECIPES WHERE ID = ?");
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    public List<Recipe> getAllFiltered(long patientID, long priorityID, String description) {
        List<Recipe> list = null;
        String patientFilter = patientID > -1 ? " AND PATIENTID = " + patientID : "";
        String priorityFilter = priorityID > -1 ? " AND PRIORITYID = " + priorityID : "";
        String decriptionFilter = description.length() > 0 ? " AND DESCRIPTION LIKE '%" + description + "%'" : "";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM RECIPES WHERE TRUE "
                                                    + patientFilter + priorityFilter + decriptionFilter)) {
                list = new ArrayList<>();
                while (rs.next()) {
                    list.add(getEntity(rs));
                }
        } catch (SQLException e) {
                printSQLException(e);
        }
        return list;
    }

    private void setValues(PreparedStatement preparedStatement, Recipe recipe) throws SQLException {
        preparedStatement.setString(1, recipe.getDescription());
        preparedStatement.setLong(2, recipe.getPatient().getId());
        preparedStatement.setLong(3, recipe.getDoctor().getId());
        preparedStatement.setDate(4, recipe.getCreationDate());
        preparedStatement.setInt(5, recipe.getValidity());
        preparedStatement.setLong(6, recipe.getPriority().getId());
    }
}