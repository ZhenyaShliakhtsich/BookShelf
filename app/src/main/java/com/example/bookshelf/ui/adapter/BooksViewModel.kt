package com.example.bookshelf.ui.adapter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.bookshelf.model.Book
import com.example.bookshelf.model.BookShort
import com.example.bookshelf.network.ITBookStoreAPI
import com.example.bookshelf.network.NetworkController
import com.example.bookshelf.repository.BookDataSource
import com.example.bookshelf.repository.FavouritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class BooksViewModel(
    private val dataSource: BookDataSource,
    private val repository: FavouritesRepository,
    private val api: ITBookStoreAPI
) : ViewModel() {


    val foundBook = MutableLiveData<Book>()

    val cart = MutableLiveData<ArrayList<Book>?>()

    val favourites = MutableLiveData<List<Book>?>()


    fun searchBooks(string: CharSequence?): Flow<PagingData<BookShort>> {
        dataSource.setString(string)
        return Pager(
            PagingConfig(
                pageSize = com.example.bookshelf.PAGE_SIZE,
                initialLoadSize = com.example.bookshelf.PAGE_SIZE
            ),
            pagingSourceFactory = { dataSource }
        )
            .flow
            .cachedIn(viewModelScope)
    }

    fun findBook(isbn13: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = api.getBook(isbn13)
            if (response.isSuccessful) {
                foundBook.postValue(response.body())
            }
        }
    }

    suspend fun getNewBooks() = api.getNewBooks().body()?.books


    suspend fun addToFavourites(book: Book) {
        repository.insertBook(book)
    }

    suspend fun deleteFromFavourites(book: Book) {
        repository.deleteBook(book)
    }

    suspend fun checkBook(id: String): Boolean {
        return repository.findBookById(id)
    }

    suspend fun getFavourites() {
        favourites.postValue(repository.getFavourites())
    }

    fun addToCart(book: Book) {
        var newCart = cart.value
        if (newCart.isNullOrEmpty()) {
            newCart = ArrayList()
        }
        newCart.add(book)
        cart.value = newCart
    }

    fun deleteFromCart(book: Book) {
        val newList = cart.value
        newList?.remove(book)
        cart.value = newList
    }

}