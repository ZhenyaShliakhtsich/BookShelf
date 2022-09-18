package com.example.bookshelf.repository

import com.example.bookshelf.db.DatabaseRepository
import com.example.bookshelf.model.Book

class FavouritesRepository {

    suspend fun insertBook(book: Book){
        DatabaseRepository.bookDao?.insert(book)
    }
    suspend fun deleteBook(book : Book){
        DatabaseRepository.bookDao?.delete(book)
    }
    suspend fun getFavourites(): List<Book>? {
       return DatabaseRepository.bookDao?.getBooks()
    }
    suspend fun findBookById(id : String):Boolean{
        return DatabaseRepository.bookDao?.findBookById(id) != null
    }
}