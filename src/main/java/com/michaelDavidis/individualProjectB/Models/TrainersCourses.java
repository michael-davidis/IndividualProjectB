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
public class TrainersCourses {

    private String trainerId;
    private String courseId;
    
    Menu menu = new Menu();

    public void insertIntoTrainersCourses(String trainerId, String courseId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            String query = "INSERT INTO TRAINERS_COURSES VALUES (?,?);";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, trainerId);
            preparedStatement.setString(2, courseId);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("The trainer has been successfully assigned to a course.");
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

    public void assignTrainersToCourse(ArrayList<String> pkList, Trainer trainer) {
        System.out.println("Which course is the trainer teaching?");
        menu.showAllCourses();
        String courseChoice = Tools.scan.next();
        String trainerId = trainer.getId();
        String courseId = courseChoice;
        String placeHolder = trainerId + "," + courseId;
        if (pkList.contains(placeHolder)) {
            System.out.println("This trainer is already teaching another class.");
        } else {
            insertIntoTrainersCourses(trainerId, courseId);
            pkList.add(placeHolder);
        }
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

}
