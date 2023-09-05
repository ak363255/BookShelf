package com.example.bookshelf.data

import com.example.bookshelf.domain.repository.EmailMatcherRepo
import java.util.regex.Pattern

class ImplEmailMatcher:EmailMatcherRepo {
    override suspend fun matchEmail(email: String): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
}