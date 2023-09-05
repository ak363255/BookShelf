package com.example.bookshelf.domain.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.bookscoremodule.domain.User
import com.example.bookshelf.BookShelfAppllication
import com.example.network.NetworkHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object BookDataStorePreference {

    private val BOOK_DATASTORE = "BOOK_DATASTORE"
    private val Context.bookDataStore :DataStore<Preferences> by preferencesDataStore(BOOK_DATASTORE)

    private object BOOK_DATASTORE_KEY{
        val loginObject = stringPreferencesKey("loginObject")
    }


    suspend fun getLoginObject():Flow<User?> = BookShelfAppllication.getApplicationContext().bookDataStore.data.map {
        val user = it[BOOK_DATASTORE_KEY.loginObject]
        if(user != null){
            NetworkHelper.convert(user)
        }else null
    }

    suspend fun removeLoginObject() = BookShelfAppllication.getApplicationContext().bookDataStore.edit {
        it.remove(BOOK_DATASTORE_KEY.loginObject)
    }




    suspend fun saveLoginObject(user:User) = BookShelfAppllication.getApplicationContext().bookDataStore.edit {
             val user = NetworkHelper.convertObjectToString(user)
        if(user != null){
            it[BOOK_DATASTORE_KEY.loginObject] = user
        }
    }

}
