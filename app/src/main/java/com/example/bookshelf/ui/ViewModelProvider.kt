package com.example.bookshelf.ui

import com.example.bookshelf.network.ITBookStoreAPI
import com.example.bookshelf.repository.BookDataSource
import com.example.bookshelf.repository.FavouritesRepository
import com.example.bookshelf.ui.adapter.BooksViewModel
import com.example.bookshelf.ui.base.BaseViewModelFactory
import javax.inject.Inject

class ViewModelProvider @Inject constructor(
    private val dataSource: BookDataSource,
    private val repository : FavouritesRepository,
    private val api: ITBookStoreAPI
)
    : BaseViewModelFactory<BooksViewModel>(BooksViewModel::class.java) {

    override fun createViewModel(): BooksViewModel {
        return BooksViewModel(dataSource,repository,api)
    }
}