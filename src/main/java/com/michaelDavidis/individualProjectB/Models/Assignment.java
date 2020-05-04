/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.michaelDavidis.individualProjectB.Models;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Mike
 */
public class Assignment {

    private String id;
    private String title;
    private String description;
    private String subDateTime;
    private int oMark;
    private int tMark;

    public Assignment(String id, String title, String description, String subDateTime, int oralMark, int totalMark) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.subDateTime = subDateTime;
        this.oMark = oralMark;
        this.tMark = totalMark;
    }

    public Assignment() {
    }

    public ArrayList<Assignment> selectAllAssignments() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<Assignment> assignmentList = new ArrayList<>();
        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            statement = connection.createStatement();

            String query = "SELECT * FROM ASSIGNMENTS;";

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString("ASSIGNMENT_ID");
                String title = resultSet.getString("TITLE");
                String description = resultSet.getString("DESCRIPTION");
                String subDate = resultSet.getString("SUB_DATE");
                int oMark = resultSet.getInt("ORAL_MARK");
                int tMark = resultSet.getInt("TOTAL_MARK");
                Assignment assignment = new Assignment(id, title, description, subDate, oMark, tMark);
                assignmentList.add(assignment);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return assignmentList;
    }
    

    public String getAssignmentId(String title, String desc, String subDate) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String assignmentId = "";

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            String getId = "SELECT ASSIGNMENT_ID FROM ASSIGNMENTS WHERE TITLE = ? AND DESCRIPTION = ? AND SUB_DATE = ? ;";

            preparedStatement = connection.prepareStatement(getId);

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, desc);
            preparedStatement.setString(3, subDate);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                assignmentId = resultSet.getString("ASSIGNMENT_ID");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return assignmentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubDateTime() {
        return subDateTime;
    }

    public void setSubDateTime(String subDateTime) {
        this.subDateTime = subDateTime;
    }

    public int getoMark() {
        return oMark;
    }

    public void setoMark(int oMark) {
        this.oMark = oMark;
    }

    public int gettMark() {
        return tMark;
    }

    public void settMark(int tMark) {
        this.tMark = tMark;
    }

}
