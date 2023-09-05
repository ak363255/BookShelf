package com.example.foodrecipes.domain.utils

import com.example.bookscoremodule.data.Books
import com.example.bookscoremodule.data.CountryListDto
import com.example.bookscoremodule.data.toCountry
import com.example.bookscoremodule.data.toDomainModel
import com.example.bookscoremodule.domain.BookModel
import com.example.bookscoremodule.domain.Country
import com.example.bookshelf.domain.utils.LocalResourceLoaderUtil
import com.example.dbmodule.BookDbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object BookResourceUtils {
    val BOOKS_JSON = "books.json"
    val COUNTRY_JSON = "countries.json"

    suspend fun loadBooks(): List<BookModel>? = withContext(Dispatchers.IO + Job()) {
        try {
            val fileName = BOOKS_JSON
            val data: Books? =
                LocalResourceLoaderUtil.loadJSONFromAsset(fileName, Books::class.java) as Books?
            val list = data?.toDomainModel()?.subList(0,20)
            list?.forEach {
                var isFav = false
                if(it.id != null){
                    isFav = BookDbHelper.getBookById(it.id!!) != null
                }
                it.isFav = isFav
            }
            list
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }

    suspend fun loadCountryList(): List<Country> = withContext(Dispatchers.IO + Job()) {
        val fileName = COUNTRY_JSON
        val data: CountryListDto? = LocalResourceLoaderUtil.loadJSONFromAsset(
            fileName,
            CountryListDto::class.java
        ) as CountryListDto?
        if (data != null) {
            data.toCountry()
        } else {
            mutableListOf()
        }
    }
}