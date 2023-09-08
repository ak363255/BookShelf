package com.example.bookshelf.data

import com.example.bookshelf.domain.repository.PasswordMatcherRepo
import java.util.regex.Pattern

class ImplPasswordMatcher:PasswordMatcherRepo {
    override suspend fun matchPassword(passowrd: String): Boolean {
        val regx = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$&%])[a-zA-Z0-9@$&%]{8,}"
        //Compile regular expression to get the pattern
        val pattern: Pattern = Pattern.compile(regx)
        val matcher =  pattern.matcher(passowrd)
        return matcher.matches()
    }
}