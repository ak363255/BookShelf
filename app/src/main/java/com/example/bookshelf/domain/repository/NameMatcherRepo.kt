package com.example.bookshelf.domain.repository

interface NameMatcherRepo {
    suspend fun matchName(name:String):Boolean
}