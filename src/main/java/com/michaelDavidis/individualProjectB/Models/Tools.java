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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

/*Here are all the tools that I created in order to make certain functions
  easier and not recreate them inside certain methods and classes
 */
public class Tools {

//  All there are initiated in this class to avoid multiple initiations across the project
    public static Scanner scan = new Scanner(System.in);
    public static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost/school?serverTimezone=UTC";
    public static final String USERNAME = "java_user";
    public static final String PASSWORD = "1234";
    private static ArrayList<String> numList = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));

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
//  Validate that the input is a string with no numbers

    public static boolean strValidation(String input) {
        int x = 0;
        for (int i = 0; i < input.length(); i++) {
            String inputIndex = input.substring(i);
            if (numList.contains(inputIndex)) {
                x++;
                break;
            }
        }
        if (x > 0) {
            return false;
        } else {
            return true;
        }
    }
//  Validate that the input is a string with no numbers, used in first time validations

    public static boolean strFirstTimeValidation(String input) {
        int x = 0;
        for (int i = 0; i < input.length(); i++) {
            String inputIndex = input.substring(i);
            if (numList.contains(inputIndex)) {
                x++;
                break;
            }
        }
        if (x > 0) {
            return false;
        } else if (x < 0 && Tools.strValidation(input) && (input.equalsIgnoreCase(" ") || input.equalsIgnoreCase(""))) {
            return true;
        } else {
            return false;
        }
    }

}
