package com.example.bookshelf.data

import com.example.bookscoremodule.domain.LoginDataModel
import com.example.bookscoremodule.domain.User
import com.example.bookshelf.domain.usecase.LoginRepo
import com.example.bookshelf.domain.utils.BookDataStorePreference
import com.example.dbmodule.BookDbHelper
import com.example.network.ResultEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class ImplLoginRepo:LoginRepo {
    override suspend fun loginUser(loginDataModel: LoginDataModel): Flow<ResultEvent<User>> = flow {
        emit(ResultEvent.OnLoading)
        val user = BookDbHelper.getUserByEmail(loginDataModel.email)
        if(user != null){
            emit(ResultEvent.OnSuccess(user))
            withContext<Unit>(Dispatchers.IO+ Job()) {
                BookDataStorePreference.saveLoginObject(user)
            }

        }else{
            emit(ResultEvent.OnFailure("Invalid User"))
        }
    }
}