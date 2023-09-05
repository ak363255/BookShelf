package com.example.bookshelf.data

import com.example.bookshelf.domain.repository.NameMatcherRepo
import java.util.regex.Pattern

class ImplNameMatcher:NameMatcherRepo {
    override suspend fun matchName(name: String): Boolean {
        val regexUserName = "^[A-Za-z\\s]+$"
        //Compile regular expression to get the pattern
        val pattern: Pattern = Pattern.compile(regexUserName)
        val matcher =  pattern.matcher(name)
        return matcher.matches() && name.length > 3
    }
}