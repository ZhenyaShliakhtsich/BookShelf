package com.example.bookshelf.ui.adapter.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.example.bookshelf.databinding.ItemBookBinding
import com.example.bookshelf.model.Book
import com.example.bookshelf.model.BookShort
import com.squareup.picasso.Picasso

class BookViewHolder(private var binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item : BookShort?){
        binding.root.tag = item
        binding.itemBookName.text = item?.title
        binding.itemBookPrice.text = item?.price
        Picasso.get().load(item?.image).into(binding.itemBookImage)
    }
}