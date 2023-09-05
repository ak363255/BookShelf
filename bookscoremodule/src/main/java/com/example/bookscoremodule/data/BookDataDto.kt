package com.example.bookscoremodule.data

import androidx.annotation.Keep

@Keep
data class BookDataDto(
    var alias: String?,
    var hits: Int?,
    var id: String?,
    var image: String?,
    var lastChapterDate: Int?,
    var title: String?
)