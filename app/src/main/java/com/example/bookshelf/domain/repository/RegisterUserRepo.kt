package com.example.bookshelf.domain.repository

import com.example.bookscoremodule.domain.SignUpDataModel
import com.example.bookscoremodule.domain.User
import com.example.network.ResultEvent
import kotlinx.coroutines.flow.Flow

interface RegisterUserRepo {
    suspend fun registerUser(signUpDataModel: SignUpDataModel):Flow<ResultEvent<User>>
}