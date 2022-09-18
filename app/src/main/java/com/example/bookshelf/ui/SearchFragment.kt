package com.example.bookshelf.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.example.bookshelf.ID
import com.example.bookshelf.R
import com.example.bookshelf.databinding.FragmentSearchBinding
import com.example.bookshelf.model.Book
import com.example.bookshelf.model.BookShort
import com.example.bookshelf.ui.adapter.BooksViewModel
import com.example.bookshelf.ui.adapter.recyclerView.BookAdapter
import com.example.bookshelf.ui.adapter.recyclerView.BookClickListener
import com.google.gson.internal.bind.TypeAdapters.CHARACTER
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private var viewModel = BooksViewModel()
    lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchEditText.doOnTextChanged { text, _, _, _ ->
            viewLifecycleOwner.lifecycleScope.launch{
                viewModel.searchBooks(text).debounce(500).collectLatest {
                    setList(it)
                }
            }
        }
        super.onCreate(savedInstanceState)
    }

    private suspend fun setList(list: PagingData<BookShort>) {
        binding.searchRecyclerView.run {
            if (adapter == null) {
                adapter = BookAdapter(requireContext(),object : BookClickListener{
                    override fun onItemClick(book: BookShort) {
                        val bookFragment = BookFragment().apply {
                            arguments = bundleOf( ID to book.isbn13)
                        }
                        fragmentManager?.beginTransaction()
                            ?.replace(R.id.container_view,bookFragment)
                            ?.commit()
                    }

                })
            }
            adapter!!.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
            (adapter as? BookAdapter)?.submitData(list)
        }
    }
}