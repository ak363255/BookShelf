package com.example.bookshelf.domain.usecase

import com.example.bookshelf.domain.repository.NameMatcherRepo
import com.example.bookshelf.domain.repository.PasswordMatcherRepo

class PasswordValidationUseCase(private val passwordMatcher: PasswordMatcherRepo) {
    suspend operator fun invoke(password:String) = passwordMatcher.matchPassword(password)
}