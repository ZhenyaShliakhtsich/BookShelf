package com.example.bookshelf.network

import com.example.bookshelf.model.Book
import com.example.bookshelf.model.NewBook
import com.example.bookshelf.model.SearchBook
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ITBookStoreAPI  {

    @GET("/1.0/new")
    suspend fun getNewBooks(): Response<NewBook>

    @GET("/1.0/search/{string}/{page}")
    suspend fun getSearchedBooks(
        @Path("string") string: CharSequence?,
        @Path("page") page: Int
    ): Response<SearchBook>

    @GET("/1.0/books/{string}")
    suspend fun getBook(@Path("string") isbn13: String): Response<Book>


}