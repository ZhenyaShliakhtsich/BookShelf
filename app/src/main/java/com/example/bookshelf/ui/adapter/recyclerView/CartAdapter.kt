package com.example.bookshelf.ui.adapter.recyclerView

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookshelf.R
import com.example.bookshelf.databinding.ItemBookCartBinding
import com.example.bookshelf.model.Book


interface CartClickListener {
    fun onItemClick(book: Book)
    fun onDeleteClick(book: Book)
}

class CartAdapter(private val context: Context, private var actionListener: CartClickListener) :
    RecyclerView.Adapter<CartViewHolder>(), View.OnClickListener {

    var cartList = ArrayList<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemBookCartBinding.inflate(LayoutInflater.from(context), parent, false)
        binding.root.setOnClickListener(this)
        binding.deleteButton.setOnClickListener(this)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartList[position])
    }

    override fun getItemCount(): Int {
        return cartList.size
    }


    override fun onClick(v: View?) {
        val book = v?.tag as Book
        if (v.id == R.id.delete_button) {
            actionListener.onDeleteClick(book)
        } else actionListener.onItemClick(book)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Book>) {
        cartList = list as ArrayList<Book>
        notifyDataSetChanged()
    }
}