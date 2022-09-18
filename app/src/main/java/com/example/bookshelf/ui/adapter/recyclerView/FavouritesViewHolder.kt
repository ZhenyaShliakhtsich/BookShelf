package com.example.bookshelf.ui.adapter.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.example.bookshelf.databinding.ItemBookBinding
import com.example.bookshelf.model.Book
import com.squareup.picasso.Picasso

class FavouritesViewHolder(private var binding: ItemBookBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Book?) {
        binding.root.tag = item
        binding.itemBookName.text = item?.title
        binding.itemBookPrice.text = item?.price
        binding.itemBookDescription.text = item?.desc
        Picasso.get().load(item?.image).into(binding.itemBookImage)
    }
}