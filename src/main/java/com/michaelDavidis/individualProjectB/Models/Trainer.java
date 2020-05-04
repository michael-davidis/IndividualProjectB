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
public class Trainer {

    private String id;
    private String firstName;
    private String lastName;
    private String subject;

    public Trainer(String id, String firstName, String lastName, String subject) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
    }

    public Trainer() {
    }
    
    /**
     * Selects all trainers.
     * @return An ArrayList of all trainers.
     */
    public ArrayList<Trainer> selectAllTrainers() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<Trainer> trainerList = new ArrayList<>();
        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            statement = connection.createStatement();

            String query = "SELECT * FROM TRAINERS;";

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString("TRAINER_ID");
                String lastName = resultSet.getString("LAST_NAME");
                String firstName = resultSet.getString("FIRST_NAME");
                String subject = resultSet.getString("SUBJECT_TEACHING");

                Trainer trainer = new Trainer(id, firstName, lastName, subject);
                trainerList.add(trainer);

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
        return trainerList;
    }
    
    /**
     * Retrieve all the IDs of the table "trainers_courses".
     * @return ArrayList of String objects
     */
    public ArrayList<String> selectAllIdsTrainerCourse() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<String> pkList = new ArrayList<>();

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            statement = connection.createStatement();

            String query = "SELECT CONCAT(TRAINER_ID, ',', COURSE_ID) AS PK FROM TRAINERS_COURSES;";

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
     * Gets trainers ID based on last name, first name and subject.
     * @param lastName Trainer's last name.
     * @param firstName Trainer's first name.
     * @param subject Trainer's subject.
     * @return A string that is the ID.
     */
    public String getTrainerId(String lastName, String firstName, String subject) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String trainerId = "";

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            String getId = "SELECT TRAINER_ID FROM TRAINERS WHERE LAST_NAME = ? AND FIRST_NAME = ? AND SUBJECT_TEACHING = ? ;";

            preparedStatement = connection.prepareStatement(getId);

            preparedStatement.setString(1, lastName);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, subject);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                trainerId = resultSet.getString("TRAINER_ID");
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
        return trainerId;
    }
    
    /**
     * Finds the maximum number of trainers in the database.
     * @return The ID of the last trainer which is also the number of trainers.
     */
    public static int getMaxNumOfTrainers() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int pk = 0;

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            statement = connection.createStatement();

            String query = "SELECT TRAINER_ID AS MAX_NUM FROM TRAINERS ORDER BY TRAINER_ID DESC LIMIT 1;";

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
