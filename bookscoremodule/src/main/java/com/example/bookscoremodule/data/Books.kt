package com.example.bookscoremodule.data

import com.example.bookscoremodule.domain.BookModel
import com.example.bookscoremodule.utils.Utils

data class Books(
    val books:List<BookDataDto>
)
suspend fun Books.toDomainModel():List<BookModel> {
    val bookList:MutableList<BookModel> = mutableListOf()
    this.books.forEach {dto ->
        var isFav = false
        bookList.add(BookModel(
            alias = dto.alias,
            hits = dto.hits,
            id = dto.id?:Utils.generateUniqueId(),
            image = dto.image,
            lastChapterDate = dto.lastChapterDate,
            title = dto.title,
            isFav = isFav
        ))
    }
    return bookList
}