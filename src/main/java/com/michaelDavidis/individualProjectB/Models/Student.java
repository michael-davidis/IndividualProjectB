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
public class Student {

    private String id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private float tuitionFees;

    public Student(String id, String firstName, String lastName, String dateOfBirth, float tuitionFees) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.tuitionFees = tuitionFees;
    }

    public Student() {
    }
    /**
     * Retrieve all the students from the SQL table "students"
     * @return A list of all students as objects Student
     */
    public ArrayList<Student> selectAllStudents() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<Student> studentList = new ArrayList<>();
        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            statement = connection.createStatement();

            String query = "SELECT * FROM STUDENTS;";

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString("STUDENT_ID");
                String lastName = resultSet.getString("LAST_NAME");
                String firstName = resultSet.getString("FIRST_NAME");
                String dob = resultSet.getString("DATE_OF_BIRTH");
                float fees = resultSet.getFloat("TUITION_FEES");
                Student student = new Student(id, firstName, lastName, dob, fees);

                studentList.add(student);

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
        return studentList;
    }
    /**
     * Retrieve all the IDs of the table "students_courses".
     * @return ArrayList of String objects
     */
    public ArrayList<String> selectAllIdsStudentCourse() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<String> pkList = new ArrayList<>();

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            statement = connection.createStatement();

            String query = "SELECT CONCAT(STUDENT_ID, ',', COURSE_ID) AS PK FROM STUDENTS_COURSES;";

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

    public String getStudentId(String lastName, String firstName, String dob) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String studentId = null;

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            String getId = "SELECT STUDENT_ID FROM STUDENTS WHERE LAST_NAME = ? AND FIRST_NAME = ? AND DATE_OF_BIRTH = ? ;";

            preparedStatement = connection.prepareStatement(getId);

            preparedStatement.setString(1, lastName);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, dob);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                studentId = resultSet.getString("STUDENT_ID");
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
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return studentId;
    }
    
    /**
     * Returns the ID of the last student, so it can be compared against an input.
     * @return int of the ID of the last student
     */
    public static int getMaxNumOfStudents() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int pk = 0;

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            statement = connection.createStatement();

            String query = "SELECT STUDENT_ID AS MAX_NUM FROM STUDENTS ORDER BY STUDENT_ID DESC LIMIT 1;";

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                pk = resultSet.getInt("MAX_NUM");
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
        return pk;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public float getTuitionFees() {
        return tuitionFees;
    }

    public void setTuitionFees(float tuitionFees) {
        this.tuitionFees = tuitionFees;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
