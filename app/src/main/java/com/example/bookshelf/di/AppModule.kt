package com.example.bookshelf.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.bookshelf.App
import com.example.bookshelf.FAVOURITES
import com.example.bookshelf.db.BookDao
import com.example.bookshelf.db.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton



@Module
class AppModule(private val app : App) {

    @Provides
    @Singleton
    fun provideApplication() : App = app

    @Provides
    @Singleton
    fun provideApplicationContext(app : Application): Context = app

    @Provides
    @Singleton
    fun getDatabase() = Room
        .databaseBuilder(app, Database::class.java, FAVOURITES)
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Provides
    @Singleton
    fun provideBookDao() : BookDao = Room
        .databaseBuilder(app, Database::class.java, FAVOURITES)
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build().getBookDao()



}