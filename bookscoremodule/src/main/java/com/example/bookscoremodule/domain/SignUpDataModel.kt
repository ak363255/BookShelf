package com.example.bookscoremodule.domain

data class SignUpDataModel(
    val fullName:String,
    val email:String,
    val password:String,
    val country: Country?
)