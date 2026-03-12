package com.fastfood.util;
public class Validator {
    public static boolean isPositive(int number){
        return number > 0;
    }
    public static boolean isValidString(String value){
        return value != null && !value.trim().isEmpty();
    }
}