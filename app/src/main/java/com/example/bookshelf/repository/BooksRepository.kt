package com.example.bookshelf.repository

import com.example.bookshelf.model.NewBook
import com.example.bookshelf.model.SearchBook
import com.example.bookshelf.network.NetworkController
import retrofit2.Response

class BooksRepository {

    suspend fun getNewBooks(): Response<NewBook> {
        return NetworkController.getITBookStoreApi().getNewBooks()
    }

    suspend fun getSearchedBooks(string: CharSequence?, page: Int): Response<SearchBook> {
        return NetworkController.getITBookStoreApi().getSearchedBooks(string, page)
    }


}