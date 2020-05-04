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
public class StudentsAssignments {

    private int studentId;
    private int assignmentId;
    private int oralMark;
    private int totalMark;

    public StudentsAssignments(int studentId, int assignmentId, int oralMark, int totalMark) {
        this.studentId = studentId;
        this.assignmentId = assignmentId;
        this.oralMark = oralMark;
        this.totalMark = totalMark;
    }

    public StudentsAssignments() {
    }
    
    /**
     * Selects all IDs from junction table students_assignments
     * @return Returns ArrayList of IDs.
     */
    public static ArrayList<String> selectAllIdsStudentsAssignment() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<String> pkList = new ArrayList<>();

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            statement = connection.createStatement();

            String query = "SELECT CONCAT(STUDENT_ID, ',', ASSIGNMENT_ID) AS PK FROM STUDENTS_ASSIGNMENTS;";

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String pk = resultSet.getString("PK");
                pkList.add(pk);
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
        return pkList;
    }
    
    /**
     * Inserts values into course_assignments
     * @param courseId Given course ID;
     * @param assignmentId Given assignment ID;
     * @param oMark Given Oral Mark;
     * @param tMark Given Total Mark;
     */
    public void insertIntoCoursesAssignments(String courseId, String assignmentId, String oMark, String tMark) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            String query = "INSERT STUDENTS_ASSIGNMENTS VALUES (?,?,?,?);";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, courseId);
            preparedStatement.setString(2, assignmentId);
            preparedStatement.setString(3, oMark);
            preparedStatement.setString(4, tMark);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("The assignment has been successfully assigned to the course.");
            } else {
                System.out.println("Something went wrong.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
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
    }
}
