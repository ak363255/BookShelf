package com.example.dbmodule

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user_db_model")
    suspend fun getRecipes():List<UserRoomDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserRoomDbModel)

    @Query("SELECT * FROM user_db_model WHERE :id = id")
    suspend fun getUserById(id:String):List<UserRoomDbModel>

    @Query("DELETE FROM user_db_model WHERE :id = id")
    suspend fun deleteUserById(id:String)

}