package com.example.bookshelf.domain.usecase

import com.example.bookscoremodule.domain.SignUpDataModel
import com.example.bookshelf.domain.repository.RegisterUserRepo

class RegisterUseCase(private val registerUserRepo: RegisterUserRepo) {
    suspend operator fun invoke(signUpDataModel: SignUpDataModel) = registerUserRepo.registerUser(signUpDataModel)
}