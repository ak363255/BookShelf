package com.example.bookshelf.domain.repository

interface EmailMatcherRepo {
    suspend fun matchEmail(email:String):Boolean
}