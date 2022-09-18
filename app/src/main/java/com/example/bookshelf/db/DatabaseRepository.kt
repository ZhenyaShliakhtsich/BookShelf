package com.example.bookshelf.db

import android.content.Context
import androidx.room.Room
import com.example.bookshelf.model.Book

object DatabaseRepository {
    private var database : Database? = null
    internal var bookDao : BookDao? = null

    fun initDatabase(context: Context){
        database = Room.databaseBuilder(
            context,
            Database::class.java, "favourites"
        )
            .fallbackToDestructiveMigration()
            .build()
        bookDao = database?.getBookDao()
    }
}