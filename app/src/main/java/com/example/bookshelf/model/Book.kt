package com.example.bookshelf.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Book(
    @ColumnInfo(name = "authors")
    var authors: String = "",
    @ColumnInfo(name = "desc")
    var desc: String= "",
    @Ignore
    var error: String= "",
    @ColumnInfo(name = "image")
    var image: String= "",
    @Ignore
    var isbn10: String= "",
    @PrimaryKey
    var isbn13: String= "",
    @Ignore
    var language: String= "",
    @Ignore
    var pages: String= "",
    @Ignore
    var pdf: Pdf = Pdf(),
    @ColumnInfo(name = "price")
    var price: String= "",
    @Ignore
    var publisher: String= "",
    @ColumnInfo(name = "rating")
    var rating: String= "",
    @Ignore
    var subtitle: String= "",
    @ColumnInfo(name = "title")
    var title: String= "",
    @Ignore
    var url: String= "",
    @Ignore
    var year: String= ""
)