package com.example.bookshelf.domain.usecase

import com.example.bookshelf.domain.repository.GetBookRepo

class GetBooksUseCase(
    private val getBookRepo: GetBookRepo
) {
    suspend operator fun invoke() = getBookRepo.getBooks()
    data class GetBooksUseCaseParams(
        val type: String = ""
    )
}