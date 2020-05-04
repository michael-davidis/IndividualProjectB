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
public class StudentsCourses {

    private String studentId;
    private String courseId;

    Menu menu = new Menu();

    public void insertIntoStudentsCourses(String studentId, String courseId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            String query = "INSERT INTO STUDENTS_COURSES VALUES (?,?);";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, courseId);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("The student has been successfully assigned to a course.");
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

    public static void selectStudentsPerCourse() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            statement = connection.createStatement();

            String query = "SELECT C. TITLE, S.LAST_NAME, S.FIRST_NAME FROM STUDENTS S, COURSES C, STUDENTS_COURSES SC WHERE S.STUDENT_ID = SC.STUDENT_ID AND C.COURSE_ID = SC.COURSE_ID;";

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String title = resultSet.getString("TITLE");
                String lastName = resultSet.getString("LAST_NAME");
                String firstName = resultSet.getString("FIRST_NAME");
                System.out.println("Course: " + title + ": " + lastName + " " + firstName);
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
    }

}
