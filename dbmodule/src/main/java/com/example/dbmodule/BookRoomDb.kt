package com.example.dbmodule

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BookDbModel::class,UserRoomDbModel::class],
    version = 1,
    exportSchema = false
)
abstract class BookRoomDb :RoomDatabase(){
    abstract fun getBookDao():BookDao
    abstract fun getUserDao():UserDao
}