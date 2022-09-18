package com.example.bookshelf.ui.adapter.recyclerView

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.bookshelf.databinding.ItemNewBookBinding
import com.example.bookshelf.model.BookShort

interface NewBookClickListener {
    fun onItemClick(book: BookShort)
}

class NewBookAdapter(
    private val context: Context,
    private var actionListener: NewBookClickListener
) : ListAdapter<BookShort, NewBookViewHolder>(BookDiffUtil()), View.OnClickListener {

    var newBooksList = ArrayList<BookShort>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewBookViewHolder {
        val binding = ItemNewBookBinding.inflate(LayoutInflater.from(context), parent, false)
        binding.root.setOnClickListener(this)
        return NewBookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewBookViewHolder, position: Int) {
        holder.bind(newBooksList[position])
    }

    override fun getItemCount(): Int = newBooksList.size

    override fun onClick(v: View?) {
        val book = v?.tag as BookShort
        actionListener.onItemClick(book)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<BookShort>) {
        newBooksList = list as ArrayList<BookShort>
        notifyDataSetChanged()
    }
}