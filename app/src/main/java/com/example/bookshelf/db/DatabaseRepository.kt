package com.example.bookshelf.db

import android.content.Context
import androidx.room.Room
import com.example.bookshelf.FAVOURITES
import javax.inject.Inject

object DatabaseRepository {
    private var database: Database? = null
    private var bookDao: BookDao? = null

    fun initDatabase(context: Context) {
        database = Room.databaseBuilder(
            context,
            Database::class.java, FAVOURITES
        )
            .fallbackToDestructiveMigration()
            .build()
        bookDao = database?.getBookDao()
    }
}