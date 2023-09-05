package com.example.bookshelf.domain.repository

import com.example.bookscoremodule.domain.Country
import com.example.network.ResultEvent
import kotlinx.coroutines.flow.Flow

interface GetCountryRepo {
    suspend fun getCountryList():Flow<ResultEvent<List<Country>>>
}