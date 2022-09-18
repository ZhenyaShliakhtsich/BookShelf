package com.example.bookshelf.ui.adapter.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.example.bookshelf.databinding.ItemBookCartBinding
import com.example.bookshelf.model.Book
import com.squareup.picasso.Picasso

class CartViewHolder(private var binding: ItemBookCartBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Book?) {
        binding.root.tag = item
        binding.deleteButton.tag = item
        binding.itemBookName.text = item?.title
        binding.itemBookPrice.text = item?.price
        binding.itemBookDescription.text = item?.desc
        Picasso.get().load(item?.image).into(binding.itemBookImage)
    }
}
