package com.example.dbmodule.di

import android.content.Context
import androidx.room.Room
import com.example.dbmodule.BookRoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Singleton
    @Provides
    fun provideRoomDb(@ApplicationContext context: Context):BookRoomDb{
        return Room.databaseBuilder(context,BookRoomDb::class.java,"RECIPE_DB").build()
    }
}