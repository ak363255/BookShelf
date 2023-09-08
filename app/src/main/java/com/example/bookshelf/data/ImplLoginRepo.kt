package com.example.bookshelf.data

import com.example.bookscoremodule.domain.LoginDataModel
import com.example.bookscoremodule.domain.User
import com.example.bookshelf.domain.usecase.LoginRepo
import com.example.bookshelf.domain.utils.BookDataStorePreference
import com.example.dbmodule.BookDbHelper
import com.example.network.ResultEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ImplLoginRepo:LoginRepo {
    override suspend fun loginUser(loginDataModel: LoginDataModel): Flow<ResultEvent<User>> = flow {
        emit(ResultEvent.OnLoading)
        val user = BookDbHelper.getUserByEmail(loginDataModel.email)
        if(user != null){
            BookDataStorePreference.saveLoginObject(user)
            emit(ResultEvent.OnSuccess(user))
        }else{
            emit(ResultEvent.OnFailure("Invalid User"))
        }
    }
}