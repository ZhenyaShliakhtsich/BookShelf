package com.example.bookshelf.model

data class SearchBook(
    val books: List<BookShort>,
    val page: String?,
    val total: String?
)