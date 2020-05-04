/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.michaelDavidis.individualProjectB.Models;

import com.michaelDavidis.individualProjectB.Main;
import java.sql.*;

/**
 *
 * @author Mike
 */
public class Menu {

    Student student = new Student();
    Trainer trainer = new Trainer();

    public Menu() {
    }

    public static void showAllStudents() {
        Student student = new Student();
        student.selectAllStudents().forEach((s) -> {
            System.out.println(s.getId() + ". " + s.getLastName() + " " + s.getFirstName());
        });
    }

    public static void showAllTrainers() {
        Trainer trainer = new Trainer();
        trainer.selectAllTrainers().forEach((t) -> {
            System.out.println(t.getId() + ". " + t.getLastName() + " " + t.getFirstName() + " with subject " + t.getSubject());
        });
    }

    public static void showAllCourses() {
        Course course = new Course();

        course.selectAllCourses().forEach((c) -> {
            System.out.println(c.getId() + ". " + c.getTitle() + ", Stream: " + c.getStream() + ", Type: " + c.getType() + ", Start Date: " + c.getStartDate() + ", End Date: " + c.getEndDate());
        });
    }

    public static void showAllAssignments() {
        Assignment assignment = new Assignment();
        assignment.selectAllAssignments().forEach((a) -> {
            System.out.println(a.getId() + ". " + a.getTitle() + ", Description: " + a.getDescription() + ", Submission Date: " + a.getSubDateTime() + ", Oral Mark: " + a.getoMark() + ", Total Mark: " + a.gettMark());
        });
    }

    public void selectAssignmentsPerCourse() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            statement = connection.createStatement();

            String query = "SELECT C.TITLE AS COURSE , A.TITLE, A.DESCRIPTION FROM COURSES C, ASSIGNMENTS A, COURSES_ASSIGNMENTS CA WHERE C.COURSE_ID = CA.COURSE_ID AND A.ASSIGNMENT_ID = CA.ASSIGNMENT_ID ORDER BY C.COURSE_ID;";

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String title = resultSet.getString("COURSE");
                String aTitle = resultSet.getString("TITLE");

                System.out.println("Course: " + title + ", Title: " + aTitle);

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

    public void selectTrainersPerCourse() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            statement = connection.createStatement();

            String query = "SELECT C.TITLE, T.LAST_NAME, T.FIRST_NAME FROM COURSES C, TRAINERS T, TRAINERS_COURSES TC WHERE C.COURSE_ID = TC.COURSE_ID AND T.TRAINER_ID = TC.TRAINER_ID;";

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String title = resultSet.getString("TITLE");
                String lastName = resultSet.getString("LAST_NAME");
                String firstName = resultSet.getString("FIRST_NAME");
                System.out.println("Course: " + title + ", " + lastName + " " + firstName);

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

    public void selectStudentsWithMoreThan1Courses() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            statement = connection.createStatement();

            String query = "SELECT S.STUDENT_ID, S.LAST_NAME, S.FIRST_NAME FROM STUDENTS S, STUDENTS_COURSES SC GROUP BY S.STUDENT_ID HAVING COUNT(SC.COURSE_ID) > 1;";

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("STUDENT_ID");
                String lastName = resultSet.getString("LAST_NAME");
                String firstName = resultSet.getString("FIRST_NAME");
                System.out.println(id + ". " + lastName + " " + firstName);

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

    public void selectAssignmentsPerCoursePerStudent() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            statement = connection.createStatement();

            String query = "SELECT S.STUDENT_ID, S.LAST_NAME, S.FIRST_NAME, A.TITLE, C.TITLE AS COURSE FROM STUDENTS S, ASSIGNMENTS A, COURSES C, COURSES_ASSIGNMENTS CA, STUDENTS_COURSES SC WHERE A.ASSIGNMENT_ID = CA.ASSIGNMENT_ID AND S.STUDENT_ID = SC.STUDENT_ID AND C.COURSE_ID = CA.COURSE_ID AND C.COURSE_ID = SC.COURSE_ID ORDER BY S.STUDENT_ID;";

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("STUDENT_ID");
                String lastName = resultSet.getString("LAST_NAME");
                String firstName = resultSet.getString("FIRST_NAME");
                String title = resultSet.getString("TITLE");
                String course = resultSet.getString("COURSE");
                System.out.println(id + ". " + lastName + " " + firstName + " with assignment " + title + ", Course: " + course);

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

    public void innerMenuShow(String innerChoice) {
        if (innerChoice.equalsIgnoreCase("1")) {
            System.out.println("\n \n");
            System.out.println("*____----====STUDENTS====----____*");
            showAllStudents();
            System.out.println("\n \n");
        } else if (innerChoice.equalsIgnoreCase("2")) {
            System.out.println("\n \n");
            System.out.println("*____----====TRAINERS====----____*");
            showAllTrainers();
            System.out.println("\n \n");
        } else if (innerChoice.equalsIgnoreCase("3")) {
            System.out.println("\n \n");
            System.out.println("*____----====ASSIGNMENTS====----____*");
            showAllAssignments();
            System.out.println("\n \n");
        } else if (innerChoice.equalsIgnoreCase("4")) {
            System.out.println("\n \n");
            System.out.println("*____----====COURSES====----____*");
            showAllCourses();
            System.out.println("\n \n");
        } else if (innerChoice.equalsIgnoreCase("5")) {
            System.out.println("\n \n");
            System.out.println("*____----====STUDENTS PER COURSE====----____*");
            StudentsCourses.selectStudentsPerCourse();
            System.out.println("\n \n");
        } else if (innerChoice.equalsIgnoreCase("6")) {
            System.out.println("\n \n");
            System.out.println("*____----====ASSIGNMENTS PER COURSE====----____*");
            selectAssignmentsPerCourse();
            System.out.println("\n \n");
        } else if (innerChoice.equalsIgnoreCase("7")) {
            System.out.println("\n \n");
            System.out.println("*____----====TRAINERS PER COURSE====----____*");
            selectTrainersPerCourse();
            System.out.println("\n \n");
        } else if (innerChoice.equalsIgnoreCase("8")) {
            System.out.println("\n \n");
            System.out.println("*__--==STUDENTS WITH MORE THAN ON COURSES==--__*");
            selectStudentsWithMoreThan1Courses();
            System.out.println("\n \n");
        } else if (innerChoice.equalsIgnoreCase("9")) {
            System.out.println("\n \n");
            System.out.println("*__--==ASSIGNMENTS PER COURSE PER STUDENT==--__*");
            selectAssignmentsPerCoursePerStudent();
            System.out.println("\n \n");
        }
    }

    public void innerMenuInsert(String innerChoice) {
        if (innerChoice.equalsIgnoreCase("1")) {
            Main.createStudent(student.selectAllIdsStudentCourse());
        } else if (innerChoice.equalsIgnoreCase("2")) {
            Main.createTrainer(trainer.selectAllIdsTrainerCourse());
        } else if (innerChoice.equalsIgnoreCase("3")) {
            Main.createAssignment(CoursesAssignments.selectAllIdsCourseAssignment());
        } else if (innerChoice.equalsIgnoreCase("4")) {
            Main.createCourse();
        } else if (innerChoice.equalsIgnoreCase("5")){
            Main.createConnectionStudentCourse();
        } else if (innerChoice.equalsIgnoreCase("6")){
            Main.createConnectionTrainersCourses();
        } else if (innerChoice.equalsIgnoreCase("7")){
            Main.createConnectionAssignmentCourse();
        }
    }

    public static void intro() {
        System.out.println("*******************************************");
        System.out.println("*                                         *");
        System.out.println("*          Welcome to our school's        *");
        System.out.println("*                database                 *");
        System.out.println("*                                         *");
        System.out.println("*******************************************\n");
    }

    public static void outro() {
        System.out.println("\n*******************************************");
        System.out.println("*             Thank you for               *");
        System.out.println("*             using our app!              *");
        System.out.println("*******************************************");
    }

    public static void createOrSee() {
        System.out.println("\nDo you wish to make a new entry\nor to see what you have?");
        System.out.println("During this menu, you can type \"Exit\" and exit our app.");
        System.out.print("Press 1 to make a new entry or 2 to access the database: ");
    }

    public static void chooseToCreateFromList() {
        System.out.println("\nYou can choose to create anything you want from this list.");
        showInputChoices();
        System.out.print("Just type the number that you want: ");

    }

    public static void chooseToSelect() {
        System.out.println("\nYou can choose to see anything you want from this list.");
        showSelectChoices();
        System.out.print("Just type the number that you want: ");

    }

    public static void showInputChoices() {
//      This is the menu for object creation
        System.out.println("\nPress 1 for a student.");
        System.out.println("Press 2 for a trainer.");
        System.out.println("Press 3 for an assignment.");
        System.out.println("Press 4 for a course.");
        System.out.println("Press 5 to assign a student to a course.");
        System.out.println("Press 6 to assign a trainer to a course.");
        System.out.println("Press 7 to assign an assignment to a course.");
    }

    public static void showSelectChoices() {
//      This is the menu for printing objects
        System.out.println("\nPress 1 for a course.");
        System.out.println("Press 2 for a trainer.");
        System.out.println("Press 3 for an assignment.");
        System.out.println("Press 4 for a student.");
        System.out.println("Press 5 for a list of all the students per course.");
        System.out.println("Press 6 for a list of all the assignments per course.");
        System.out.println("Press 7 for a list of all trainers per course.");
        System.out.println("Press 8 for a list of all students with 2+ courses.");
        System.out.println("Press 9 for a list of all assignments per course per student");
        System.out.println("one or more assignments in the same week as the date that you want.");
    }

    public void mainMenu() {
        Tools tools = new Tools();
        String choice = "y";

        while (!choice.equalsIgnoreCase("exit")) {
            Menu.createOrSee();
            choice = tools.scan.next();
            if (choice.equalsIgnoreCase("1")) {
                chooseToCreateFromList();
                String innerChoice = tools.scan.next();
                innerMenuInsert(innerChoice);
            } else if (choice.equalsIgnoreCase("2")) {
                chooseToSelect();
                String innerChoice = tools.scan.next();
                innerMenuShow(innerChoice);
            }
        }

    }
}
