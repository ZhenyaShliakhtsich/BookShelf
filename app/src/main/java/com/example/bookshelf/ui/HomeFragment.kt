package com.example.bookshelf.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bookshelf.BACK
import com.example.bookshelf.ID
import com.example.bookshelf.R
import com.example.bookshelf.databinding.FragmentHomeBinding
import com.example.bookshelf.model.Book
import com.example.bookshelf.model.BookShort
import com.example.bookshelf.model.NewBook
import com.example.bookshelf.repository.BooksRepository
import com.example.bookshelf.ui.adapter.BooksViewModel
import com.example.bookshelf.ui.adapter.recyclerView.NewBookAdapter
import com.example.bookshelf.ui.adapter.recyclerView.NewBookClickListener


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = NewBookAdapter(requireContext(), object : NewBookClickListener {
            override fun onItemClick(book: BookShort) {
                val bookFragment = BookFragment().apply {
                    arguments = bundleOf( ID to book.isbn13)
                }
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.container_view,bookFragment)
                    ?.addToBackStack(BACK)
                    ?.commit()
            }
        }
        )
        binding.homeRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        binding.homeRecyclerView.adapter = adapter
        lifecycleScope.launchWhenCreated {
            val newBooksList = BooksRepository().getNewBooks().body()?.books
            adapter.setList(newBooksList!!)
        }

    }
}