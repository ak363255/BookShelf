package com.example.dbmodule

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_db_model")
data class UserRoomDbModel(
    @PrimaryKey(autoGenerate = false)
    val id:String,
    val userData:String
)
