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
public class CoursesAssignments {

    private String courseId;
    private String assignmentId;
    
    Menu menu = new Menu();

    public void insertIntoCoursesAssignments(String courseId, String assignmentId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            String query = "INSERT COURSES_ASSIGNMENTS VALUES (?,?);";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, courseId);
            preparedStatement.setString(2, assignmentId);

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

    public static ArrayList<String> selectAllIdsCourseAssignment() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<String> pkList = new ArrayList<>();

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            statement = connection.createStatement();

            String query = "SELECT CONCAT(COURSE_ID, ',', ASSIGNMENT_ID) AS PK FROM COURSES_ASSIGNMENTS;";

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
     * Inserts a row in courses_assignments in the schema that is unique.
     * @param pkList A list of all existing rows in courses_assignments
     * @param assignment The given assignment
     */
    public void assignCourseToAssignment(ArrayList<String> pkList, Assignment assignment) {
        System.out.println("Which course is the trainer teaching?");
        menu.showAllCourses();
        String courseChoice = Tools.scan.next();
        String assignmentId = assignment.getId();
        String courseId = courseChoice;
        String placeHolder = courseId + "," + assignmentId;
        if (pkList.contains(placeHolder)) {
            System.out.println("This trainer is already teaching another class.");
        } else {
            insertIntoCoursesAssignments(courseId, assignmentId);
            pkList.add(placeHolder);
        }
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

}
