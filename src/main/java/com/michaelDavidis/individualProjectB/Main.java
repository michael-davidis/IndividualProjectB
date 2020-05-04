package com.michaelDavidis.individualProjectB;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.michaelDavidis.individualProjectB.Models.*;
import java.sql.*;
import java.util.ArrayList;

public class Main {

    static Menu menu = new Menu();
    static StudentsCourses sc = new StudentsCourses();
    static Student student = new Student();
    static Trainer trainer = new Trainer();
    static Assignment assignment = new Assignment();
    static CoursesAssignments ca = new CoursesAssignments();

    public static void createStudent(ArrayList<String> studentPkList) {
        Connection connection = null;
        Student student = new Student();
        PreparedStatement preparedStatement = null;

        String placeHolder = "0,0";

        try {

            Class.forName(Tools.MYSQL_JDBC_DRIVER);
            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            String query = "INSERT INTO STUDENTS VALUES(NULL, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(query);

            System.out.println("Enter the student's last name: ");
            String lastName = Tools.scan.next();
            System.out.println("Enter the student's first name: ");
            String firstName = Tools.scan.next();
            System.out.println("Enter the student's birth date using the following format (YYYY-MM-DD): ");
            String dob = Tools.scan.next();
            System.out.println("Enter the student's tuition fees: ");
            float tuitionFees = Tools.scan.nextFloat();

            preparedStatement.setString(1, lastName);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, dob);
            preparedStatement.setFloat(4, tuitionFees);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                student = new Student(student.getStudentId(lastName, firstName, dob), lastName, firstName, dob, tuitionFees);
                System.out.println("Update successful!");
                System.out.println("Would you  like to assign the student to a course? (Y/N)");
                String addAnother = Tools.scan.next();
                while (addAnother.equalsIgnoreCase("y")) {
                    do {
                        System.out.println("Which course is the student attending?");
                        Menu.showAllCourses();
                        System.out.print("Choose the course by typing the appropriate number: ");
                        String courseChoice = Tools.scan.next();
                        while (!Tools.intValidation(courseChoice)) {
                            System.out.println("Please enter a valid option: ");
                            courseChoice = Tools.scan.next();
                        }
                        int courseChoiceV = Integer.valueOf(courseChoice);
                        while (Course.getMaxNumOfCourses() < courseChoiceV) {
                            System.out.println("Incorrect number.");
                            Menu.showAllCourses();
                            System.out.print("Please enter an option from one of the above: ");
                            courseChoice = Tools.scan.next();
                            while (!Tools.intValidation(courseChoice)) {
                                System.out.println("Please enter a valid option: ");
                                courseChoice = Tools.scan.next();
                            }
                            courseChoiceV = Integer.valueOf(courseChoice);
                        }
                        String studentId = student.getId();
                        String courseId = courseChoice;
                        placeHolder = studentId + "," + courseId;
                        if (studentPkList.contains(placeHolder)) {
                            System.out.println("This student is already enrolled in this class.");
                        } else {
                            sc.insertIntoStudentsCourses(studentId, courseId);
                            studentPkList.add(placeHolder);
                        }
                    } while (!studentPkList.contains(placeHolder));
                    System.out.println("Would you like to enroll this student in another course? (Y/N)");
                    addAnother = Tools.scan.next();
                }
            } else {
                System.out.println("Something Went Wrong. The student was not inserted in the database. Please check you input.");
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

    public static void createTrainer(ArrayList<String> trainerPkList) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Trainer trainer = new Trainer();
        TrainersCourses tc = new TrainersCourses();

        String placeHolder = "0,0";

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            String query = "INSERT INTO TRAINERS VALUES (NULL, ?, ?, ?);";

            preparedStatement = connection.prepareStatement(query);

            System.out.print("Enter the trainer's last name: ");
            String lastName = Tools.scan.next();
            System.out.print("Enter the trainer's first name: ");
            String firstName = Tools.scan.next();
            System.out.print("Enter the subject that the trainer is teaching: ");
            String deathOfAScanner = Tools.scan.nextLine();
            String subject = Tools.scan.nextLine();

            preparedStatement.setString(1, lastName);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, subject);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Update successful!");
                trainer = new Trainer(trainer.getTrainerId(lastName, firstName, subject), lastName, firstName, subject);
                System.out.println("Would you  like to assign the trainer to a course? (Y/N)");
                String addAnother = Tools.scan.next();
                while (addAnother.equalsIgnoreCase("y")) {
                    do {
                        System.out.println("Which course is the trainer teaching?");
                        Menu.showAllCourses();
                        String courseChoice = Tools.scan.next();
                        while (!Tools.intValidation(courseChoice)) {
                            System.out.println("Please enter a valid option: ");
                            courseChoice = Tools.scan.next();
                        }
                        int courseChoiceV = Integer.valueOf(courseChoice);
                        while (Course.getMaxNumOfCourses() < courseChoiceV) {
                            System.out.println("Incorrect number.");
                            Menu.showAllCourses();
                            System.out.print("Please enter an option from one of the above: ");
                            courseChoice = Tools.scan.next();
                            while (!Tools.intValidation(courseChoice)) {
                                System.out.println("Please enter a valid option: ");
                                courseChoice = Tools.scan.next();
                            }
                            courseChoiceV = Integer.valueOf(courseChoice);
                        }
                        String trainerId = trainer.getId();
                        String courseId = courseChoice;
                        placeHolder = trainerId + "," + courseId;
                        if (trainerPkList.contains(placeHolder)) {
                            System.out.println("This trainer is already teaching this class.");
                        } else {
                            tc.insertIntoTrainersCourses(trainerId, courseId);
                            trainerPkList.add(placeHolder);
                        }
                    } while (!trainerPkList.contains(placeHolder));
                    System.out.println("Would you like to assign this trainer to another course? (Y/N)");
                    addAnother = Tools.scan.next();
                }
            } else {
                System.out.println("Something Went Wrong. The trainer was not inserted in the database. Please check you input.");
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

    public static void createAssignment(ArrayList<String> assignmentPkList) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Assignment assignment = new Assignment();
        CoursesAssignments ca = new CoursesAssignments();
        String placeHolder = "0,0";

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            String query = "INSERT INTO ASSIGNMENTS VALUES (NULL, ?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(query);

            System.out.println("Enter the assignment's title: ");
            String deathOfAScanner = Tools.scan.nextLine();
            String title = Tools.scan.nextLine();
            System.out.println("Enter the assignment's description: ");
            String desc = Tools.scan.nextLine();
            System.out.println("Enter the assignment's submission date in the following format (YYYY-MM-DD): ");
            String subDate = Tools.scan.next();
            System.out.println("Enter the assignment's oral mark: ");
            int oMark = Tools.scan.nextInt();
            System.out.println("Enter the assignment's total mark: ");
            int tMark = Tools.scan.nextInt();

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, desc);
            preparedStatement.setString(3, subDate);
            preparedStatement.setInt(4, oMark);
            preparedStatement.setInt(5, tMark);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Update successful!");
                assignment = new Assignment(assignment.getAssignmentId(title, desc, subDate), title, desc, subDate, oMark, tMark);
                System.out.println("Would you  like to assign the asssignment to a course? (Y/N)");
                String addAnother = Tools.scan.next();
                while (addAnother.equalsIgnoreCase("y")) {
                    do {
                        System.out.println("In which course is the assignment located?");
                        Menu.showAllCourses();
                        String courseChoice = Tools.scan.next();
                        while (!Tools.intValidation(courseChoice)) {
                            System.out.println("Please enter a valid option: ");
                            courseChoice = Tools.scan.next();
                        }
                        int courseChoiceV = Integer.valueOf(courseChoice);
                        while (Course.getMaxNumOfCourses() < courseChoiceV) {
                            System.out.println("Incorrect number.");
                            Menu.showAllCourses();
                            System.out.print("Please enter an option from one of the above: ");
                            courseChoice = Tools.scan.next();
                            while (!Tools.intValidation(courseChoice)) {
                                System.out.println("Please enter a valid option: ");
                                courseChoice = Tools.scan.next();
                            }
                            courseChoiceV = Integer.valueOf(courseChoice);
                        }
                        String assignmentId = assignment.getId();
                        String courseId = courseChoice;
                        placeHolder = courseId + "," + assignmentId;
                        if (assignmentPkList.contains(placeHolder)) {
                            System.out.println("This assignent is already in this class.");
                        } else {
                            ca.insertIntoCoursesAssignments(courseId, assignmentId);
                            assignmentPkList.add(placeHolder);
                        }
                    } while (!assignmentPkList.contains(placeHolder));
                    System.out.println("Would you like to assign this assignment to another course? (Y/N)");
                    addAnother = Tools.scan.next();
                }
            } else {
                System.out.println("Something Went Wrong. The assignment was not inserted in the database. Please check you input.");
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

    public static void createCourse() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            String query = "INSERT INTO COURSES VALUES (NULL, ?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(query);

            System.out.println("Enter the course's title: ");
            String title = Tools.scan.next();
            System.out.println("Enter the course's stream: ");
            String deathOfAScanner = Tools.scan.nextLine();
            String stream = Tools.scan.nextLine();
            System.out.println("Enter the course's type: ");
            String type = Tools.scan.next();
            System.out.println("Enter the course's start date in the following format (YYYY-MM-DD): ");
            String sDate = Tools.scan.next();
            System.out.println("Enter the course's end date in the following format (YYYY-MM-DD): ");
            String eDate = Tools.scan.next();

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, stream);
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, sDate);
            preparedStatement.setString(5, eDate);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Update successful!");
            } else {
                System.out.println("Something Went Wrong. The course was not inserted in the database. Please check you input.");
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

    public static void createConnectionStudentCourse() {
        ArrayList<String> pkList = student.selectAllIdsStudentCourse();
        String addAnotherStudent = "y";

        while (addAnotherStudent.equalsIgnoreCase("y")) {
            System.out.println("Which student would you like to assign to a course?");
            Menu.showAllStudents();
            System.out.print("Choose the appropriate number: ");
            String studentChoice = Tools.scan.next();
            while (!Tools.intValidation(studentChoice)) {
                System.out.println("Please enter a valid option: ");
                studentChoice = Tools.scan.next();
            }
            int studentChoiceV = Integer.valueOf(studentChoice);
            while (Student.getMaxNumOfStudents() < studentChoiceV) {
                System.out.println("Incorrect number.");
                Menu.showAllStudents();
                System.out.print("Please enter an option from one of the above: ");
                studentChoice = Tools.scan.next();
                while (!Tools.intValidation(studentChoice)) {
                    System.out.println("Please enter a valid option: ");
                    studentChoice = Tools.scan.next();
                }
                studentChoiceV = Integer.valueOf(studentChoice);
            }
            String addAnotherCourse = "y";
            while (addAnotherCourse.equalsIgnoreCase("y")) {
                System.out.println("Which course do you want to assign him to?");
                Menu.showAllCourses();
                System.out.print("Choose the appropriate number: ");
                String courseChoice = Tools.scan.next();
                while (!Tools.intValidation(courseChoice)) {
                    System.out.println("Please enter a valid option: ");
                    courseChoice = Tools.scan.next();
                }
                int courseChoiceV = Integer.valueOf(courseChoice);
                while (Course.getMaxNumOfCourses() < courseChoiceV) {
                    System.out.println("Incorrect number.");
                    Menu.showAllCourses();
                    System.out.print("Please enter an option from one of the above: ");
                    studentChoice = Tools.scan.next();
                    while (!Tools.intValidation(studentChoice)) {
                        System.out.println("Please enter a valid option: ");
                        studentChoice = Tools.scan.next();
                    }
                    courseChoiceV = Integer.valueOf(courseChoice);
                }
                String pk = studentChoice + "," + courseChoice;
                if (pkList.contains(pk)) {
                    System.out.println("This student is already enrolled in this class.");
                } else {
                    sc.insertIntoStudentsCourses(studentChoice, courseChoice);
                    pkList.add(pk);
                }
                System.out.print("Do you want to add this student to another course? (Y/N)");
                addAnotherCourse = Tools.scan.next();
            }
            System.out.print("Do you want to repeat this for another student? (Y/N)");
            addAnotherStudent = Tools.scan.next();
        }
    }

    public static void createConnectionTrainersCourses() {
        ArrayList<String> pkList = trainer.selectAllIdsTrainerCourse();
        String addAnotherStudent = "y";

        while (addAnotherStudent.equalsIgnoreCase("y")) {
            System.out.println("Which trainer would you like to assign to a course?");
            Menu.showAllTrainers();
            System.out.print("Choose the appropriate number: ");
            String trainerChoice = Tools.scan.next();
            while (!Tools.intValidation(trainerChoice)) {
                System.out.println("Please enter a valid option: ");
                trainerChoice = Tools.scan.next();
            }
            int trainerChoiceV = Integer.valueOf(trainerChoice);
            while (Student.getMaxNumOfStudents() < trainerChoiceV) {
                System.out.println("Incorrect number.");
                Menu.showAllStudents();
                System.out.print("Please enter an option from one of the above: ");
                trainerChoice = Tools.scan.next();
                while (!Tools.intValidation(trainerChoice)) {
                    System.out.println("Please enter a valid option: ");
                    trainerChoice = Tools.scan.next();
                }
                trainerChoiceV = Integer.valueOf(trainerChoice);
            }
            String addAnotherCourse = "y";
            while (addAnotherCourse.equalsIgnoreCase("y")) {
                System.out.println("Which course do you want to assign the trainer to?");
                Menu.showAllCourses();
                System.out.print("Choose the appropriate number: ");
                String courseChoice = Tools.scan.next();
                while (!Tools.intValidation(courseChoice)) {
                    System.out.println("Please enter a valid option: ");
                    courseChoice = Tools.scan.next();
                }
                int courseChoiceV = Integer.valueOf(courseChoice);
                while (Course.getMaxNumOfCourses() < courseChoiceV) {
                    System.out.println("Incorrect number.");
                    Menu.showAllCourses();
                    System.out.print("Please enter an option from one of the above: ");
                    courseChoice = Tools.scan.next();
                    while (!Tools.intValidation(courseChoice)) {
                        System.out.println("Please enter a valid option: ");
                        courseChoice = Tools.scan.next();
                    }
                    courseChoiceV = Integer.valueOf(courseChoice);
                }
                String pk = trainerChoice + "," + courseChoice;
                if (pkList.contains(pk)) {
                    System.out.println("This trainer is already teaching in this class.");
                } else {
                    sc.insertIntoStudentsCourses(trainerChoice, courseChoice);
                    pkList.add(pk);
                }
                System.out.print("Do you want to add this trainer to another course? (Y/N)");
                addAnotherCourse = Tools.scan.next();
            }
            System.out.print("Do you want to repeat this for another trainer? (Y/N)");
            addAnotherStudent = Tools.scan.next();
        }
    }

    public static void createConnectionAssignmentCourse() {
        ArrayList<String> pkList = CoursesAssignments.selectAllIdsCourseAssignment();
        String addAnotherStudent = "y";

        while (addAnotherStudent.equalsIgnoreCase("y")) {
            System.out.println("Which assignment would you like to assign to a course?");
            Menu.showAllAssignments();
            System.out.print("Choose the appropriate number: ");
            String assignmentChoice = Tools.scan.next();
            while (!Tools.intValidation(assignmentChoice)) {
                System.out.println("Please enter a valid option: ");
                assignmentChoice = Tools.scan.next();
            }
            int trainerChoiceV = Integer.valueOf(assignmentChoice);
            while (Student.getMaxNumOfStudents() < trainerChoiceV) {
                System.out.println("Incorrect number.");
                Menu.showAllStudents();
                System.out.print("Please enter an option from one of the above: ");
                assignmentChoice = Tools.scan.next();
                while (!Tools.intValidation(assignmentChoice)) {
                    System.out.println("Please enter a valid option: ");
                    assignmentChoice = Tools.scan.next();
                }
                trainerChoiceV = Integer.valueOf(assignmentChoice);
            }
            String addAnotherCourse = "y";
            while (addAnotherCourse.equalsIgnoreCase("y")) {
                System.out.println("Which course do you want to assign it to?");
                Menu.showAllCourses();
                System.out.print("Choose the appropriate number: ");
                String courseChoice = Tools.scan.next();
                while (!Tools.intValidation(courseChoice)) {
                    System.out.println("Please enter a valid option: ");
                    courseChoice = Tools.scan.next();
                }
                int courseChoiceV = Integer.valueOf(courseChoice);
                while (Course.getMaxNumOfCourses() < courseChoiceV) {
                    System.out.println("Incorrect number.");
                    Menu.showAllCourses();
                    System.out.print("Please enter an option from one of the above: ");
                    courseChoice = Tools.scan.next();
                    while (!Tools.intValidation(courseChoice)) {
                        System.out.println("Please enter a valid option: ");
                        courseChoice = Tools.scan.next();
                    }
                    courseChoiceV = Integer.valueOf(courseChoice);
                }
                String pk = courseChoice + "," + assignmentChoice;
                if (pkList.contains(pk)) {
                    System.out.println("This assignment is already in this class.");
                } else {
                    ca.insertIntoCoursesAssignments(courseChoice, assignmentChoice);
                    pkList.add(pk);
                }
                System.out.print("Do you want to add this assignment to another course? (Y/N)");
                addAnotherCourse = Tools.scan.next();
            }
            System.out.print("Do you want to repeat this for another assignment? (Y/N)");
            addAnotherStudent = Tools.scan.next();
        }
    }

    public static void main(String[] args) {
        menu.mainMenu();
    }

}
