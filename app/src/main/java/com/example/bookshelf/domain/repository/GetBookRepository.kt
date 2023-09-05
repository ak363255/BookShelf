package com.example.bookshelf.domain.repository

import com.example.bookscoremodule.domain.BookModel
import com.example.bookshelf.domain.usecase.GetBooksUseCase
import com.example.network.ResultEvent
import kotlinx.coroutines.flow.Flow

interface GetBookRepository {
    suspend  fun getBooks(params: GetBooksUseCase.GetBooksUseCaseParams):Flow<ResultEvent<BookModel>>
}