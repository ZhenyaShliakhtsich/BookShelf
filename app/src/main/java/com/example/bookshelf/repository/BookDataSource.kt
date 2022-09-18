package com.example.bookshelf.repository


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bookshelf.PAGE_SIZE
import com.example.bookshelf.model.BookShort
import javax.inject.Inject


class BookDataSource @Inject constructor(
    private val repository : BooksRepository
    ) : PagingSource<Int, BookShort>() {

    private var searchedString: CharSequence? = ""

    fun setString(string: CharSequence?){
        searchedString = string
    }


    override fun getRefreshKey(state: PagingState<Int, BookShort>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookShort> {
        try {
            val key = params.key ?: 1
            val response = repository.getSearchedBooks(searchedString, key)
            return LoadResult.Page(
                data = response.body()?.books ?: ArrayList<BookShort>(),
                prevKey = if (key == 1) null else key - 1,
                nextKey = if (params.loadSize == PAGE_SIZE) key + 1 else null
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


}