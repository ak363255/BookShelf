package com.example.bookshelf

import android.app.Application
import com.example.dbmodule.BookDbHelper
import com.example.dbmodule.BookRoomDb
import com.example.network.NetworkHelper
import com.example.network.RetrofitClient
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BookShelfAppllication:Application() {
    @Inject
    lateinit var retrofitClient: RetrofitClient

    @Inject
    lateinit var bookRoomDb: BookRoomDb

    override fun onCreate() {
        super.onCreate()
        applicationContext_ = this
        initializeRetrofit()
        initReicpeRoomDb()
    }

    private fun initReicpeRoomDb() {
        BookDbHelper.bookDb = bookRoomDb
    }


    private fun initializeRetrofit() {
        NetworkHelper.retrofitClient = retrofitClient
    }

    companion object{
        private lateinit var applicationContext_:BookShelfAppllication
        fun getApplicationContext() = applicationContext_
    }
}