package com.example.bookshelf.domain.repository

interface PasswordMatcherRepo {
    suspend fun matchPassword(passowrd:String):Boolean
}