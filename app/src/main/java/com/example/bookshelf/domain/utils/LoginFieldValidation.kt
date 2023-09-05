package com.example.bookshelf.domain.utils

sealed class LoginFieldValidation<out T> {
    data class InvalidEmail(val msg:String):LoginFieldValidation<String>()
    data class InvalidPassword(val msg:String):LoginFieldValidation<String>()
    object AllOk:LoginFieldValidation<Nothing>()
}