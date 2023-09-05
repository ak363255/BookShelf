package com.example.bookshelf.data

import com.example.bookscoremodule.domain.SignUpDataModel
import com.example.bookscoremodule.domain.User
import com.example.bookshelf.domain.repository.RegisterUserRepo
import com.example.bookshelf.domain.utils.BookDataStorePreference
import com.example.dbmodule.BookDbHelper
import com.example.foodrecipes.domain.utils.Utility
import com.example.network.ResultEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ImplSignRegisterUserRepo : RegisterUserRepo {
    override suspend fun registerUser(signUpDataModel: SignUpDataModel): Flow<ResultEvent<User>> =
        flow {
            emit(ResultEvent.OnLoading)
            /*
                    Register User--> Call API here
                    For simplicity --> for this project create new user
                */
            val user = User(
                name = signUpDataModel.fullName,
                email = signUpDataModel.email,
                password = signUpDataModel.password,
                id = signUpDataModel.email
            )
            /*
              if register suucessfully
              save user to data store preference && local db
             */
            BookDataStorePreference.saveLoginObject(user)
            BookDbHelper.insertUser(user)
            emit(ResultEvent.OnSuccess(user))
        }
}