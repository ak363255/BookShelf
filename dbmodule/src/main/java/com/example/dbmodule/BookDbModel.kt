package com.example.dbmodule

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_db_model")
data class BookDbModel(
    @PrimaryKey(autoGenerate = false)
    var id:String,
    var bookData:String
)
