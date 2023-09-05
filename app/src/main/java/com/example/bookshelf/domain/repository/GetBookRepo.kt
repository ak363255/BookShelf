package com.example.bookshelf.domain.repository

import com.example.bookscoremodule.data.Books
import com.example.bookscoremodule.domain.BookModel
import com.example.network.ResultEvent
import kotlinx.coroutines.flow.Flow

interface GetBookRepo {
    suspend fun getBooks():Flow<ResultEvent<List<BookModel>>>
}