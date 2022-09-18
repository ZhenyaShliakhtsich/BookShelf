package com.example.bookshelf.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookshelf.BACK
import com.example.bookshelf.ID
import com.example.bookshelf.R
import com.example.bookshelf.databinding.FragmentCartBinding
import com.example.bookshelf.model.Book
import com.example.bookshelf.ui.adapter.BooksViewModel
import com.example.bookshelf.ui.adapter.recyclerView.CartAdapter
import com.example.bookshelf.ui.adapter.recyclerView.CartClickListener
import com.example.bookshelf.ui.adapter.recyclerView.FavouritesAdapter
import com.example.bookshelf.ui.adapter.recyclerView.FavouritesClickListener
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat

class CartFragment : Fragment() {

    lateinit var binding: FragmentCartBinding
    private val viewModel : BooksViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var totalPrice : Double
        var list: ArrayList<Book>? = ArrayList<Book>()

        val adapter = CartAdapter(requireContext(), object : CartClickListener {
            override fun onItemClick(book: Book) {
                val bookFragment = BookFragment().apply {
                    arguments = bundleOf(ID to book.isbn13)
                }
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.container_view, bookFragment)
                    ?.addToBackStack(BACK)
                    ?.commit()
            }
            override fun onDeleteClick(book: Book) {
                viewModel.deleteFromCart(book)
            }
        })
        binding.cartRecyclerView.adapter = adapter
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.cart.observe(viewLifecycleOwner) {
            adapter.setList(it!!)
            list = it
            totalPrice = 0.0
            list?.forEach { book -> totalPrice = totalPrice.plus(book.price.substringAfter("$").toDoubleOrNull()!!) }
            var totalPriceBD = totalPrice.toBigDecimal()
            binding.totalPrice.text = "${totalPriceBD.setScale(2,RoundingMode.HALF_UP)}$"
        }

    }
}