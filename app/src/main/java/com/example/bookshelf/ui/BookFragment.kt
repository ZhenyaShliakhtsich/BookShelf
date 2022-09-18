package com.example.bookshelf.ui

import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.bookshelf.App
import com.example.bookshelf.ID
import com.example.bookshelf.R
import com.example.bookshelf.databinding.FragmentBookBinding
import com.example.bookshelf.model.Book
import com.example.bookshelf.ui.adapter.BooksViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class BookFragment : Fragment() {


    lateinit var binding: FragmentBookBinding

    @Inject
    lateinit var viewModelProvider: ViewModelProvider

    private lateinit var  viewModel: BooksViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        App.appComponent.inject(this)
        viewModel = androidx.lifecycle.ViewModelProvider(requireActivity(), viewModelProvider)[BooksViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.findBook(requireArguments().getString(ID)!!)
        var book = Book()
        viewModel.foundBook.observe(viewLifecycleOwner) {
            binding.bookAuthor.text = it.authors
            binding.bookName.text = it.title
            binding.bookRate.text = it.rating
            binding.bookDescription.text = it.desc
            binding.buyButton.text = it.price
            binding.bookDescription.movementMethod = ScrollingMovementMethod()
            Picasso.get().load(it.image).into(binding.bookImage)
            book = it
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            checkIfInFavourites(book)
        }
        binding.likeButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                if (viewModel.checkBook(book.isbn13)) {
                    viewModel.deleteFromFavourites(book)
                    Picasso.get().load(R.drawable.icon_heart).into(binding.likeButton)
                } else {
                    viewModel.addToFavourites(book)
                    Picasso.get().load(R.drawable.icon_heart_filled).into(binding.likeButton)
                }
            }
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                viewModel.getFavourites()
            }
        }

        binding.buyButton.setOnClickListener {
            viewModel.addToCart(book)
            Toast.makeText(requireContext(), R.string.added_to_cart.toString(), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private suspend fun checkIfInFavourites(book: Book): Boolean {
        return if (!viewModel.checkBook(book.isbn13)) {
            Picasso.get().load(R.drawable.icon_heart_filled).into(binding.likeButton)
            false
        } else {
            Picasso.get().load(R.drawable.icon_heart).into(binding.likeButton)
            true
        }

    }


}

