package com.example.dbmodule

import com.example.bookscoremodule.domain.BookModel
import com.example.bookscoremodule.domain.User
import com.example.bookscoremodule.utils.Utils
import com.example.network.NetworkHelper

object BookDbHelper {
     lateinit var  bookDb:BookRoomDb

    suspend fun insertUser(user: User){
          val jsonString = NetworkHelper.convertObjectToString(user)
          jsonString?.let {userData ->
              val userDbModel = UserRoomDbModel(userData = userData, id = user.id)
              if(this::bookDb.isInitialized){
                  bookDb.getUserDao().insertUser(userDbModel)
              }
          }
    }

     suspend fun getUserByEmail(email:String):User?{
          var user:User? = null
          if(this::bookDb.isInitialized){
               val userRoomDb = bookDb.getUserDao().getUserById(email)
               userRoomDb.forEach {
                    user = NetworkHelper.convert(it.userData)
               }
          }
          return user
     }

    suspend fun insertBook(bookModel:BookModel){
        if(bookModel.id == null)return
        val jsonString = NetworkHelper.convertObjectToString(bookModel)
        jsonString?.let {bookData ->
            val bookDbModel = BookDbModel(id = bookModel.id!!, bookData = bookData)
            if(this::bookDb.isInitialized){
                bookDb.getBookDao().insertBookItem(bookDbModel)
            }
        }
    }

    suspend fun getBookById(id:String):BookModel?{
        var book:BookModel? = null
        if(this::bookDb.isInitialized){
            val bookDbModel = bookDb.getBookDao().getBook(id)
            bookDbModel.forEach {
                book = NetworkHelper.convert(it.bookData)
            }
        }
        return book
    }
    suspend fun removeBookById(id:String){
            if(this::bookDb.isInitialized){
                bookDb.getBookDao().deleteFavBookById(id)
            }
    }
}