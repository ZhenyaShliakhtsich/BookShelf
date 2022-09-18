package com.example.bookshelf.ui.adapter.recyclerView

import androidx.recyclerview.widget.DiffUtil
import com.example.bookshelf.model.Book

class FavouritesDiffUtil : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
      return  oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return  oldItem == newItem
    }
}