package com.example.bookshelf.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookshelf.App
import com.example.bookshelf.BACK
import com.example.bookshelf.ID
import com.example.bookshelf.R
import com.example.bookshelf.databinding.FragmentCartBinding
import com.example.bookshelf.model.Book
import com.example.bookshelf.ui.adapter.BooksViewModel
import com.example.bookshelf.ui.adapter.recyclerView.CartAdapter
import com.example.bookshelf.ui.adapter.recyclerView.CartClickListener
import java.math.RoundingMode
import javax.inject.Inject


class CartFragment : Fragment() {

    lateinit var binding: FragmentCartBinding

    @Inject
    lateinit var viewModelProvider: ViewModelProvider

    private lateinit var  viewModel: BooksViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        App.appComponent.inject(this)
        viewModel = androidx.lifecycle.ViewModelProvider(requireActivity(), viewModelProvider)[BooksViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var totalPrice: Double
        var list: ArrayList<Book>?

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
            list?.forEach { book ->
                totalPrice = totalPrice.plus(book.price.substringAfter("$").toDoubleOrNull()!!)
            }
            val totalPriceBD = totalPrice.toBigDecimal()
            binding.totalPrice.text = "${totalPriceBD.setScale(2, RoundingMode.HALF_UP)}$"
        }

    }
}