/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.michaelDavidis.individualProjectB.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;

/*Here are all the tools that I created in order to make certain functions
  easier and not recreate them inside certain methods and classes
 */
public class Tools {

//  All there are initiated in this class to avoid multiple initiations across the project
    public static Scanner scan = new Scanner(System.in);
    public static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost/school_backup?serverTimezone=UTC";
    public static final String USERNAME = "java_user";
    public static final String PASSWORD = "1234";

//  Casts a String into a LocalDate object
    public static LocalDate stringToLocalDate(String date) throws ParseException {
        Date dateD = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        LocalDate dateLD = dateD.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return dateLD;
    }

    /**
     * Validates that the input beginning with a number form 1-9.
     *
     * @param input The given input string.
     * @return True or False
     */
    public static boolean intValidation(String input) {
//        String inputIndex = input.substring(0);
//        return numList.contains(inputIndex);
        return (!input.matches(".*[a-z].*"));
    }
}
