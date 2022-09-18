package com.example.bookshelf.ui.adapter.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.bookshelf.databinding.ItemBookBinding
import com.example.bookshelf.model.BookShort


interface BookClickListener {
    fun onItemClick(book: BookShort)
}

class BookAdapter(private val context: Context, private var actionListener: BookClickListener) :
    PagingDataAdapter<BookShort, BookViewHolder>(BookDiffUtil()), View.OnClickListener {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(context), parent, false)
        binding.root.setOnClickListener(this)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        getItem(position).let {
            holder.bind(it)
        }
        holder.itemView.tag = getItem(position)
    }


    override fun onClick(v: View?) {
        val book = v?.tag as BookShort
        actionListener.onItemClick(book)
    }

}