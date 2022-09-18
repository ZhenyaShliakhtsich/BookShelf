package com.example.bookshelf.ui

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.bookshelf.ID
import com.example.bookshelf.R
import com.example.bookshelf.databinding.FragmentBookBinding
import com.example.bookshelf.model.Book
import com.example.bookshelf.ui.adapter.BooksViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BookFragment : Fragment() {

    private val viewModel : BooksViewModel by activityViewModels()
    lateinit var binding: FragmentBookBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.findBook(this.arguments?.getString(ID)!!)
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
        var isInFavourites: Boolean = false
        viewLifecycleOwner.lifecycleScope.launch {
            isInFavourites = checkIfInFavourites(book)
        }
        binding.likeButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                if (viewModel.checkBook(book.isbn13)) {
                    viewModel.deleteFromFavourites(book)
                } else {
                    viewModel.addToFavourites(book)
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
        return if (viewModel.checkBook(book.isbn13)) {
            Picasso.get().load(R.drawable.icon_heart_filled).into(binding.likeButton)
            false
        } else {
            Picasso.get().load(R.drawable.icon_heart).into(binding.likeButton)
            true
        }

    }


}

