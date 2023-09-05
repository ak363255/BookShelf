package com.example.bookshelf.domain.usecase

import com.example.bookshelf.domain.repository.NameMatcherRepo

class NameValidationUseCase(private val nameMatcherRepo: NameMatcherRepo) {
    suspend operator fun invoke(name:String) = nameMatcherRepo.matchName(name)
}