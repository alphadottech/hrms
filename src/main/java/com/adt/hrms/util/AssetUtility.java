package com.adt.hrms.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class AssetUtility {
    public static boolean checkValidate(String str) {
        if (str == "" || str == null || str.matches(".*\\d.*")) {
            return false;
        }
        return true;
    }
    public static boolean validateId(String str) {
        if (str == "" || str == null) {
            return false;
        }
        return true;
    }
    public static boolean validateProcessor(String str) {
        if (str == "" || str == null || str.matches("\\d+")) {
            return false;
        }
        return true;
    }

    public static boolean validateName(String str) {
        if (str == "" || str == null) {
            return false;
        }
        return true;
    }
    public static boolean validateRAM(String str) {
        if (str == "" || str == null || str.contains(".")) {
            return false;
        }
        return true;
    }
    public static boolean validateDiskType(String str) {
        if (str == null || str.isEmpty() || !str.matches("^[a-zA-Z0-9]*$")) {
            return false;
        }
        return true;
    }

    public static boolean validateYear(String str) {
        String nonNumericPattern = "\\D";
        if (Pattern.compile(nonNumericPattern).matcher(str).find()) {
            return false;
        }
        if (str.length() != 4) {
            return false;
        }
        try {
            int year = Integer.parseInt(str);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean isValidateDate(String date) {
        if (date == null || date.isEmpty()) {
            return false;
        }
        LocalDate inputDate;
        try {
            inputDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        } catch (Exception e) {
            return false;
        }
        LocalDate today = LocalDate.now();
        return !inputDate.isAfter(today);
    }
}


