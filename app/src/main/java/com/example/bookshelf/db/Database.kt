package com.example.bookshelf.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bookshelf.model.Book


@Database(entities = [Book::class], version = 1)
abstract class Database : RoomDatabase(){

    abstract fun getBookDao():BookDao
}