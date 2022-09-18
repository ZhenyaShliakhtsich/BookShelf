package com.example.bookshelf.ui.adapter.recyclerView

import androidx.recyclerview.widget.DiffUtil
import com.example.bookshelf.model.BookShort

class BookDiffUtil : DiffUtil.ItemCallback<BookShort>() {
    override fun areItemsTheSame(oldItem: BookShort, newItem: BookShort): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: BookShort, newItem: BookShort): Boolean {
        return oldItem == newItem
    }
}