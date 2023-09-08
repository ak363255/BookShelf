package com.example.bookshelf.domain.utils

import java.util.regex.Pattern

object PasswordValidationUtility {
    fun invalidPasswordReason(password:String):String{
        if(password.length < 8 ){
            return "Length should be atleat 8 character"
        }
        if(password.contains(" ")){
            return "White space are not allowed now allowed"
        }
        val lowerCaseRegex = "(?=.*[a-z])[^\\s]{8,}"
        val upperCaseRegex = "(?=.*[A-Z])[^\\s]{8,}"
        val numericRegex = "(?=.*[0-9])[^\\s]{8,}"
        val specialCharacterRegex = "(?=.*[@$%&])[^\\s]{8,}"
        var pattern: Pattern = Pattern.compile(lowerCaseRegex)
        var matcher =  pattern.matcher(password)
        if(!matcher.matches()){
            return "Password should contain atleast one lower case character"
        }
        pattern = Pattern.compile(upperCaseRegex)
        if(!matcher.matches()){
        matcher = pattern.matcher(password)
            return "Password should contain atleast one upper case character"
        }

        pattern = Pattern.compile(numericRegex)
        matcher = pattern.matcher(password)
        if(!matcher.matches()){
            return "Password should contain atleast one numeric(0-9)"
        }

        pattern = Pattern.compile(specialCharacterRegex)
        matcher = pattern.matcher(password)
        if(!matcher.matches()){
            return " Aleast one special character and Only special character allowed are - $,%,@,& "
        }
            return "Only allowed special character are @,$,%,&"
    }
}