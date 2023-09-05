package com.example.bookshelf.domain.usecase

import com.example.bookscoremodule.domain.LoginDataModel
import com.example.bookscoremodule.domain.User
import com.example.network.ResultEvent
import kotlinx.coroutines.flow.Flow

interface LoginRepo {
    suspend fun loginUser(loginDataModel: LoginDataModel):Flow<ResultEvent<User>>
}