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
import com.example.bookshelf.databinding.FragmentFavouriteBinding
import com.example.bookshelf.model.Book
import com.example.bookshelf.ui.adapter.BooksViewModel
import com.example.bookshelf.ui.adapter.recyclerView.FavouritesAdapter
import com.example.bookshelf.ui.adapter.recyclerView.FavouritesClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteFragment : Fragment() {

    lateinit var binding: FragmentFavouriteBinding
    private val viewModel: BooksViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getFavourites()
        }
        val adapter = FavouritesAdapter(requireContext(), object : FavouritesClickListener {
            override fun onItemClick(book: Book) {
                val bookFragment = BookFragment().apply {
                    arguments = bundleOf(ID to book.isbn13)
                }
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.container_view, bookFragment)
                    ?.addToBackStack(BACK)
                    ?.commit()
            }
        })
        binding.favouriteRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.favourites.observe(viewLifecycleOwner) {
            adapter.setList(it as ArrayList<Book>) /* = java.util.ArrayList<com.example.bookshelf.model.Book> */
        }
        binding.favouriteRecyclerView.adapter = adapter


    }
}