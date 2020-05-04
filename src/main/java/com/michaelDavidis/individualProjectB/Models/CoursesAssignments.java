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
    
    /**
     * Inserts values into courses_assignments.
     * @param courseId ID of the course.
     * @param assignmentId ID of the assignment.
     */
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
    /**
     * Selects all IDs in courses_assignments.
     * @return 
     */
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
     * Retrieves assignments per course.
     * @param courseChoice ID for course.
     */
    public void getAssignmentsPerCourse(String courseChoice) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            String query = "SELECT A.ASSIGNMENT_ID, A.TITLE, A.DESCRIPTION FROM ASSIGNMENTS A, COURSES C, COURSES_ASSIGNMENTS CA WHERE A.ASSIGNMENT_ID = CA.ASSIGNMENT_ID AND C.COURSE_ID = CA.COURSE_ID AND C.COURSE_ID = ?;";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, courseChoice);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String assignmentId = resultSet.getString("ASSIGNMENT_ID");
                String title = resultSet.getString("TITLE");
                String desc = resultSet.getString("DESCRIPTION");
                System.out.println(assignmentId + ". " + title + "/// " + desc);
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
    }
    
    /**
     * Creates an array of assignments based on the course ID.
     * @param courseId The given course ID.
     * @return Returns an ArrayList of IDs.
     */
    public static ArrayList<String> createArrayAssignmentFromCourseId(String courseId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<String> assignmentIDs = new ArrayList<>();

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            String query = "SELECT A.ASSIGNMENT_ID FROM ASSIGNMENTS A, COURSES C, COURSES_ASSIGNMENTS CA WHERE A.ASSIGNMENT_ID = CA.ASSIGNMENT_ID AND C.COURSE_ID = CA.COURSE_ID AND C.COURSE_ID = ?;";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, courseId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String assignmentId = resultSet.getString("ASSIGNMENT_ID");
                assignmentIDs.add(assignmentId);
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
        return assignmentIDs;
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
