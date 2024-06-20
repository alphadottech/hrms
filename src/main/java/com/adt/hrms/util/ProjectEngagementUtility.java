package com.adt.hrms.util;

public class ProjectEngagementUtility {
    public static boolean validateEmployee(String str) {
        if (str == null || str.isEmpty() || str.matches(".*\\d.*") || str.matches(".*[!@#$%^&*(){}\\[\\]:;<>,.?/~_+\\-=|\"\\\\].*")) {
            return false;
        }
        return true;
    }

    public static boolean validateDescription(String str) {
        if (str == "" || str == null) {
            return false;
        }
        return true;
    }

    public static boolean validateEmail(String email) {
        if (email == "" || email == null || !email.contains("@") || !email.contains(".")) {
            return false;
        }
        return true;
    }

    public static boolean validatePhoneNo(String mobile) {

        if (mobile == "" || mobile == null || mobile.length() > 10 || mobile.length() < 10 || !mobile.matches("^[^A-Za-z]*$")) {
            return false;
        }
        return true;
    }

    public static boolean validateWorkExp(String workExperience) {
        if (workExperience == null || workExperience.isEmpty() ||
                !workExperience.matches("\\d+(\\.\\d+)?")) {
            return false;
        }
        return true;
    }


    public static boolean validateCTC(double lastCTC) {
        if (Double.isNaN(lastCTC) || lastCTC < 0) {
            return false;
        }
        return true;
    }

    public static boolean validateNoticePeriod(int notice) {
        if (notice < 0) {
            return false;
        }
        return true;
    }

    public static boolean validateInteger(Integer val) {
        if (val == null || val < 0) {
            return false;
        }
        return true;
    }

    public static boolean validateDate(String str){
        if(str == "" || str == null){
            return false;
        }
        return true;
    }

}





