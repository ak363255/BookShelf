package com.example.bookshelf.domain.utils

sealed class SignUpFieldValidation<out T> {
    data class InvalidEmail(val msg:String):SignUpFieldValidation<String>()
    data class InvalidPassword(val msg:String):SignUpFieldValidation<String>()
    data class InvalidName(val msg:String):SignUpFieldValidation<String>()
    object AllOk:SignUpFieldValidation<Nothing>()
}