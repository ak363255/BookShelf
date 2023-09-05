package com.example.bookscoremodule.utils

object  Utils {
    fun generateUniqueId():String{
        return java.util.UUID.randomUUID().toString()
    }
}