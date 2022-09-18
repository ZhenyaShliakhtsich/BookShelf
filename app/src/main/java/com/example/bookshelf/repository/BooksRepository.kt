package com.example.bookshelf.repository

import com.example.bookshelf.model.NewBook
import com.example.bookshelf.model.SearchBook
import com.example.bookshelf.network.ITBookStoreAPI
import com.example.bookshelf.network.NetworkController
import retrofit2.Response
import javax.inject.Inject

class BooksRepository @Inject constructor(
  private val api : ITBookStoreAPI
) {

    suspend fun getNewBooks(): Response<NewBook> {
        return api.getNewBooks()
    }

    suspend fun getSearchedBooks(string: CharSequence?, page: Int): Response<SearchBook> {
        return api.getSearchedBooks(string, page)
    }


}