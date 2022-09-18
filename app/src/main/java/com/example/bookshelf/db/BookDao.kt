package com.example.bookshelf.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.bookshelf.model.Book

@Dao
interface BookDao {

    @Insert
    suspend fun insert(book : Book)

    @Delete
    suspend fun delete(book: Book)

    @Query("SELECT * FROM book")
     fun getBooks(): List<Book>

    @Query("SELECT * FROM book WHERE isbn13 LIKE :id  LIMIT 1")
    suspend fun findBookById(id: String): Book
}