package com.example.bookshelf.ui.adapter.recyclerView

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.bookshelf.databinding.ItemBookBinding
import com.example.bookshelf.model.Book


interface FavouritesClickListener {
    fun onItemClick(book: Book)
}

class FavouritesAdapter(
    private val context: Context,
    private var actionListener: FavouritesClickListener
) : ListAdapter<Book, FavouritesViewHolder>(FavouritesDiffUtil()), View.OnClickListener {

    var favouritesList = ArrayList<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(context), parent, false)
        binding.root.setOnClickListener(this)
        return FavouritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        holder.bind(favouritesList[position])
    }


    override fun onClick(v: View?) {
        val book = v?.tag as Book
        actionListener.onItemClick(book)
    }

    override fun getItemCount(): Int {
        return favouritesList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Book>) {
        favouritesList = list as ArrayList<Book>
        notifyDataSetChanged()
    }
}