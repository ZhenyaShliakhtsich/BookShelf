package com.example.bookshelf.ui.adapter.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.example.bookshelf.databinding.ItemNewBookBinding
import com.example.bookshelf.model.Book
import com.example.bookshelf.model.BookShort
import com.example.bookshelf.model.NewBook
import com.squareup.picasso.Picasso

class NewBookViewHolder(private var binding: ItemNewBookBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(item : BookShort){
        binding.root.tag = item
        binding.newBookName.text = item.title
        binding.newBookPrice.text = item.price
        Picasso.get().load(item.image).into(binding.newBookImage)
    }

}