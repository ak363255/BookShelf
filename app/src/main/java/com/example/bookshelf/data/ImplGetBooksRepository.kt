package com.example.bookshelf.data

import com.example.bookscoremodule.domain.BookModel
import com.example.bookshelf.domain.repository.GetBookRepository
import com.example.bookshelf.domain.usecase.GetBooksUseCase
import com.example.network.ResultEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ImplGetBooksRepository :GetBookRepository {
    override suspend fun getBooks(params: GetBooksUseCase.GetBooksUseCaseParams): Flow<ResultEvent<BookModel>>  = flow{

    }

}