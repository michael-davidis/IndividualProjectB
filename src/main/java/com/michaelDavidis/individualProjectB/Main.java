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
    static StudentsAssignments sa = new StudentsAssignments();
    static TrainersCourses tc = new TrainersCourses();
    
    /**
     * Creates a student.
     * @param studentPkList ArrayList of all primary keys for duplicate checking.
     */
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
    /**
     * Creates a trainer
     *  @param trainerPkList ArrayList of all primary keys for duplicate checking.
     */
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
    /**
     * Creates an assignment.
     * @param assignmentPkList ArrayList of all primary keys for duplicate checking.
     */
    public static void createAssignment(ArrayList<String> assignmentPkList) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Assignment assignment = new Assignment();
        CoursesAssignments ca = new CoursesAssignments();
        String placeHolder = "0,0";

        try {
            Class.forName(Tools.MYSQL_JDBC_DRIVER);

            connection = DriverManager.getConnection(Tools.DB_URL, Tools.USERNAME, Tools.PASSWORD);

            String query = "INSERT INTO ASSIGNMENTS VALUES (NULL, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(query);

            System.out.println("Enter the assignment's title: ");
            String deathOfAScanner = Tools.scan.nextLine();
            String title = Tools.scan.nextLine();
            System.out.println("Enter the assignment's description: ");
            String desc = Tools.scan.nextLine();
            System.out.println("Enter the assignment's submission date in the following format (YYYY-MM-DD): ");
            String subDate = Tools.scan.next();

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, desc);
            preparedStatement.setString(3, subDate);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Update successful!");
                assignment = new Assignment(assignment.getAssignmentId(title, desc, subDate), title, desc, subDate);
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
    /**
     * Creates a course.
     */
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
            deathOfAScanner = Tools.scan.next();
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
    
    /**
     * Creates an entry in the junction table students_courses.
     */
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
    /**
     * Creates an entry in the junction table trainers_courses.
     */
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
            while (Trainer.getMaxNumOfTrainers() < trainerChoiceV) {
                System.out.println("Incorrect number.");
                Menu.showAllTrainers();
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
                    tc.insertIntoTrainersCourses(trainerChoice, courseChoice);
                    pkList.add(pk);
                }
                System.out.print("Do you want to add this trainer to another course? (Y/N)");
                addAnotherCourse = Tools.scan.next();
            }
            System.out.print("Do you want to repeat this for another trainer? (Y/N)");
            addAnotherStudent = Tools.scan.next();
        }
    }
    /**
     * Creates an entry in the junction table students_assignments by referring to student_courses and courses_assignments.
     */
    public static void createConnectionCourseStudentAssignment() {
        ArrayList<String> pkList = StudentsAssignments.selectAllIdsStudentsAssignment();
        System.out.println("Which course do you want to access?");
        Menu.showAllCourses();
        System.out.println("Type the desired number:");
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
        System.out.println("Which student do you want to choose?");
        StudentsCourses.selectStudentsFromCourseId(courseChoice);
        System.out.print("Type the desired number: ");
        String studentChoice = Tools.scan.next();
        while (!Tools.intValidation(studentChoice)) {
            System.out.println("Please enter a valid option: ");
            courseChoice = Tools.scan.next();
        }
        while (!StudentsCourses.createArrayStudentsFromCourseId(courseChoice).contains(studentChoice)) {
            StudentsCourses.selectStudentsFromCourseId(courseChoice);
            System.out.println("Please choose a student that attends this class from the above:");
            studentChoice = Tools.scan.next();
        }
        System.out.println("Choose the assignment you want the student to have");
        ca.getAssignmentsPerCourse(courseChoice);
        System.out.print("Type the desired number: ");
        String assignmentChoice = Tools.scan.next();
        while (!Tools.intValidation(assignmentChoice)) {
            System.out.println("Please enter a valid option: ");
            assignmentChoice = Tools.scan.next();
        }
        int assignmentChoiceV = Integer.valueOf(assignmentChoice);
        while (Assignment.getMaxNumOfAssignments() < assignmentChoiceV) {
            System.out.println("Incorrect number.");
            Menu.showAllAssignments();
            System.out.print("Please enter an option from one of the above: ");
            assignmentChoice = Tools.scan.next();
            while (!Tools.intValidation(assignmentChoice)) {
                System.out.println("Please enter a valid option: ");
                assignmentChoice = Tools.scan.next();
            }
            assignmentChoiceV = Integer.valueOf(assignmentChoice);
        }
        while (!CoursesAssignments.createArrayAssignmentFromCourseId(courseChoice).contains(assignmentChoice)) {
            ca.getAssignmentsPerCourse(courseChoice);
            System.out.println("Please choose an assignment that is in this class from the above:");
            assignmentChoice = Tools.scan.next();
            while (!Tools.intValidation(assignmentChoice)) {
                System.out.println("Please enter a valid option: ");
                assignmentChoice = Tools.scan.next();
            }
            assignmentChoiceV = Integer.valueOf(assignmentChoice);
            while (Assignment.getMaxNumOfAssignments() < assignmentChoiceV) {
                System.out.println("Incorrect number.");
                ca.getAssignmentsPerCourse(courseChoice);
                System.out.print("Please enter an option from one of the above: ");
                assignmentChoice = Tools.scan.next();
                while (!Tools.intValidation(assignmentChoice)) {
                    System.out.println("Please enter a valid option: ");
                    assignmentChoice = Tools.scan.next();
                }
                assignmentChoiceV = Integer.valueOf(assignmentChoice);
            }
        }
        String placeHolder = studentChoice + "," + assignmentChoice;
        if (pkList.contains(placeHolder)) {
            System.out.println("This student already has this assignment.");
        } else {
            System.out.println("Please grade the oral Mark (max 100): ");
            String oMark = Tools.scan.next();
            while (!Tools.intValidation(oMark)) {
                System.out.println("Please enter a valid number: ");
                oMark = Tools.scan.next();
            }
            float oMarkV = Integer.valueOf(oMark);
            while (oMarkV > 100) {
                System.out.println("The oral mark cannot exceed 100");
                System.out.println("Please enter a mark between 0 and 100: ");
                oMark = Tools.scan.next();
                while (!Tools.intValidation(oMark)) {
                    System.out.println("Please enter a valid number: ");
                    assignmentChoice = Tools.scan.next();
                    oMark = Tools.scan.next();
                }
                oMarkV = Integer.valueOf(oMark);
            }
            sa.insertIntoCoursesAssignments(studentChoice, assignmentChoice, oMark, "100");
            pkList.add(placeHolder);
        }

    }

    public static void main(String[] args) {
        menu.mainMenu();
    }

}
