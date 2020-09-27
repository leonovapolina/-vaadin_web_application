package com.haulmont.backend.dao;

import java.io.File;

public class HSQLDBDao implements JDBCDao {
    private static HSQLDBDao jdbc;
    private String url;

    private HSQLDBDao() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Unable to load driver class.");
        }
    }

    public static synchronized JDBCDao getInstance() {
        if (jdbc == null) {
            jdbc = new HSQLDBDao();
            jdbc.initDBPath();
        }
        return jdbc;
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return "SA";
    }

    public String getPass() {
        return "SA";
    }

    private void initDBPath() {
        String pathToProject = new File("").getAbsolutePath();
        pathToProject = pathToProject.replaceAll("[\\\\/]", "/");
        url = "jdbc:hsqldb:file:" + pathToProject + "/src/main/resources/db/maindb";
    }
}
