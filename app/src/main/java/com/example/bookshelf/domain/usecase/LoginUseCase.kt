package com.example.bookshelf.domain.usecase

import com.example.bookscoremodule.domain.LoginDataModel

class LoginUseCase(private val loginRepo: LoginRepo) {
    suspend operator fun invoke(loginDataModel: LoginDataModel) = loginRepo.loginUser(loginDataModel)
}