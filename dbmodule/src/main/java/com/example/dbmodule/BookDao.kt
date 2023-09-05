package com.example.dbmodule

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookItem(bookDbModel: BookDbModel)

    @Query("SELECT * FROM book_db_model WHERE :id = id")
    suspend fun getBook(id:String):List<BookDbModel>

    @Query("DELETE FROM book_db_model WHERE :id = id")
    suspend fun deleteFavBookById(id:String)
}