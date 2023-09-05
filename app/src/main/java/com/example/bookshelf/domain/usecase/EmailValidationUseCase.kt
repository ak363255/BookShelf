package com.example.bookshelf.domain.usecase

import com.example.bookshelf.domain.repository.EmailMatcherRepo

class EmailValidationUseCase(private val emailMatcherRepo: EmailMatcherRepo) {
    suspend operator fun invoke(email:String):Boolean = emailMatcherRepo.matchEmail(email)
}