package com.example.bookshelf.repository

import com.example.bookshelf.db.BookDao
import com.example.bookshelf.db.DatabaseRepository
import com.example.bookshelf.model.Book
import javax.inject.Inject

class FavouritesRepository @Inject constructor(
    private val bookDao: BookDao
){

    suspend fun insertBook(book: Book) {
        bookDao.insert(book)
    }

    suspend fun deleteBook(book: Book) {
        bookDao.delete(book)
    }

    suspend fun getFavourites(): List<Book>? {
        return bookDao.getBooks()
    }

    suspend fun findBookById(id: String): Boolean {
        return bookDao.findBookById(id) != null
    }
}