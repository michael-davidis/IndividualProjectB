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
    /**
     * Prints all students.
     */
    public static void showAllStudents() {
        Student student = new Student();
        student.selectAllStudents().forEach((s) -> {
            System.out.println(s.getId() + ". " + s.getLastName() + " " + s.getFirstName());
        });
    }
    /**
     * Prints all trainers.
     */
    public static void showAllTrainers() {
        Trainer trainer = new Trainer();
        trainer.selectAllTrainers().forEach((t) -> {
            System.out.println(t.getId() + ". " + t.getLastName() + " " + t.getFirstName() + " with subject " + t.getSubject());
        });
    }
    /**
     * Prints all courses.
     */
    public static void showAllCourses() {
        Course course = new Course();

        course.selectAllCourses().forEach((c) -> {
            System.out.println(c.getId() + ". " + c.getTitle() + ", Stream: " + c.getStream() + ", Type: " + c.getType() + ", Start Date: " + c.getStartDate() + ", End Date: " + c.getEndDate());
        });
    }
    /**
     * Prints all assignments.
     */
    public static void showAllAssignments() {
        Assignment assignment = new Assignment();
        assignment.selectAllAssignments().forEach((a) -> {
            System.out.println(a.getId() + ". " + a.getTitle() + ", Description: " + a.getDescription());
        });
    }
    
    /**
     * Prints all assignments per course.
     */
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
    
    /**
     * Prints all trainers per course.
     */
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
    
    /**
     * Prints all students with more than 1 courses.
     */
    public void selectStudentsWithMoreThan1Courses() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            statement = connection.createStatement();

            String query = "SELECT S.STUDENT_ID, S.LAST_NAME, S.FIRST_NAME,COUNT(SC.STUDENT_ID) FROM STUDENTS S, STUDENTS_COURSES SC WHERE S.STUDENT_ID = SC.STUDENT_ID GROUP BY SC.STUDENT_ID HAVING COUNT(SC.STUDENT_ID) > 1;";

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString("STUDENT_ID");
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
    
    /**
     * Prints all assignments per student per course
     */
    public void selectAssignmentsPerCoursePerStudent() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            statement = connection.createStatement();

            String query = "SELECT S.STUDENT_ID, S.LAST_NAME, S.FIRST_NAME, A.TITLE, SA.ORAL_MARK, SA.TOTAL_MARK FROM STUDENTS S, STUDENTS_ASSIGNMENTS SA, ASSIGNMENTS A WHERE S.STUDENT_ID = SA.STUDENT_ID AND A.ASSIGNMENT_ID = SA.ASSIGNMENT_ID;";

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("STUDENT_ID");
                String lastName = resultSet.getString("LAST_NAME");
                String firstName = resultSet.getString("FIRST_NAME");
                String title = resultSet.getString("TITLE");
                String oMark = resultSet.getString("ORAL_MARK");
                String tMark = resultSet.getString("TOTAL_MARK");
                System.out.println(id + ". " + lastName + " " + firstName + " with assignment " + title + ", Oral Mark: " + oMark + "/" + tMark + ".");

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
    
    /**
     * The choices of the inner menu that prints results.
     * @param innerChoice String that acts as a choice to get to the specific method.
     */
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
    
    /**
     * The choices of the inner menu that inserts values in the database.
     * @param innerChoice String that acts as a choice to get to the specific method.
     */
    public void innerMenuInsert(String innerChoice) {
        if (innerChoice.equalsIgnoreCase("1")) {
            Main.createStudent(student.selectAllIdsStudentCourse());
        } else if (innerChoice.equalsIgnoreCase("2")) {
            Main.createTrainer(trainer.selectAllIdsTrainerCourse());
        } else if (innerChoice.equalsIgnoreCase("3")) {
            Main.createAssignment(CoursesAssignments.selectAllIdsCourseAssignment());
        } else if (innerChoice.equalsIgnoreCase("4")) {
            Main.createCourse();
        } else if (innerChoice.equalsIgnoreCase("5")) {
            Main.createConnectionStudentCourse();
        } else if (innerChoice.equalsIgnoreCase("6")) {
            Main.createConnectionTrainersCourses();
        } else if (innerChoice.equalsIgnoreCase("7")) {
            Main.createConnectionCourseStudentAssignment();
        }
    }
    
    /**
     * The programs intro.
     */
    public static void intro() {
        System.out.println("*******************************************");
        System.out.println("*                                         *");
        System.out.println("*          Welcome to our school's        *");
        System.out.println("*                database                 *");
        System.out.println("*                                         *");
        System.out.println("*******************************************\n");
    }
    
    /**
     * The programs outro.
     */
    public static void outro() {
        System.out.println("\n*******************************************");
        System.out.println("*             Thank you for               *");
        System.out.println("*             using our app!              *");
        System.out.println("*******************************************");
    }
    
    /**
     * Choosing whether to create an object or show objects.
     */
    public static void createOrSee() {
        System.out.println("\nDo you wish to make a new entry\nor to see what you have?");
        System.out.println("During this menu, you can type \"Exit\" and exit our app.");
        System.out.print("Press 1 to make a new entry or 2 to access the database: ");
    }
    
    /**
     * Choose what to create from the list of options
     */
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
    
    /**
     * Prints all the choices that you can input.
     */
    public static void showInputChoices() {
//      This is the menu for object creation
        System.out.println("\nPress 1 for a student.");
        System.out.println("Press 2 for a trainer.");
        System.out.println("Press 3 for an assignment.");
        System.out.println("Press 4 for a course.");
        System.out.println("Press 5 to assign a student to a course.");
        System.out.println("Press 6 to assign a trainer to a course.");
        System.out.println("Press 7 to assign a student to an assignment that is in his class.");
    }
    
    /**
     * Prints all the choices that can be shown.
     */
    public static void showSelectChoices() {
//      This is the menu for printing objects
        System.out.println("\nPress 1 for a student.");
        System.out.println("Press 2 for a trainer.");
        System.out.println("Press 3 for an assignment.");
        System.out.println("Press 4 for a course.");
        System.out.println("Press 5 for a list of all the students per course.");
        System.out.println("Press 6 for a list of all the assignments per course.");
        System.out.println("Press 7 for a list of all trainers per course.");
        System.out.println("Press 8 for a list of all students with 2+ courses.");
        System.out.println("Press 9 for a list of all assignments per course per student");
    }
    
    /**
     * Main menu.
     */
    public void mainMenu() {
        Tools tools = new Tools();
        String choice = "y";
        intro();
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
        outro();

    }
}
