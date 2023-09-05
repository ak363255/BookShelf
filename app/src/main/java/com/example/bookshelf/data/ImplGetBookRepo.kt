package com.example.bookshelf.data

import com.example.bookscoremodule.data.Books
import com.example.bookscoremodule.domain.BookModel
import com.example.bookshelf.domain.repository.GetBookRepo
import com.example.foodrecipes.domain.utils.BookResourceUtils
import com.example.network.ResultEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ImplGetBookRepo:GetBookRepo {
    override suspend fun getBooks(): Flow<ResultEvent<List<BookModel>>>  = flow{
        val data = BookResourceUtils.loadBooks()
        if(data != null){
            emit(ResultEvent.OnSuccess(data))
        }else{
            emit(ResultEvent.OnFailure("Something went wrong"))
        }
    }
}