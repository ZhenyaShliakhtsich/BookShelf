package com.example.bookshelf.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.bookshelf.R
import com.example.bookshelf.databinding.FragmentMainBinding
import com.example.bookshelf.ui.adapter.BooksViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel : BooksViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            bottomNavigation.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home -> {
                        childFragmentManager.beginTransaction()
                            .replace(R.id.container_view, HomeFragment())
                            .commit()
                        tabName.text = resources.getText(R.string.home)
                        true
                    }
                    R.id.search -> {
                        childFragmentManager.beginTransaction()
                            .replace(R.id.container_view, SearchFragment())
                            .commitNow()
                        tabName.text = resources.getText(R.string.search)
                        true
                    }
                    R.id.favourite -> {
                        childFragmentManager.beginTransaction()
                            .replace(R.id.container_view, FavouriteFragment())
                            .commit()
                        tabName.text = resources.getText(R.string.favourite)
                        true
                    }
                    R.id.cart -> {
                        childFragmentManager.beginTransaction()
                            .replace(R.id.container_view, CartFragment())
                            .commit()
                        tabName.text = resources.getText(R.string.cart)
                        true
                    }
                    else -> false
                }
            }
            bottomNavigation.selectedItemId = R.id.home
            bottomNavigation.setOnItemReselectedListener { item ->
                when (item.itemId) {
                    R.id.home -> {
                        childFragmentManager.beginTransaction()
                            .replace(R.id.container_view, HomeFragment())
                            .commit()
                        tabName.text = resources.getText(R.string.home)
                    }
                    R.id.search -> {
                        childFragmentManager.beginTransaction()
                            .replace(R.id.container_view, SearchFragment())
                            .commit()
                        tabName.text = resources.getText(R.string.search)
                    }
                    R.id.favourite -> {
                        childFragmentManager.beginTransaction()
                            .replace(R.id.container_view, FavouriteFragment())
                            .commit()
                        tabName.text = resources.getText(R.string.favourite)
                    }
                    R.id.cart -> {
                        childFragmentManager.beginTransaction()
                            .replace(R.id.container_view, CartFragment())
                            .commit()
                        tabName.text = resources.getText(R.string.cart)
                    }
                }
            }
        }
        var badge = binding.bottomNavigation.getOrCreateBadge(R.id.cart)
        viewModel.cart.observe(viewLifecycleOwner){
            if(!it.isNullOrEmpty()){
                badge.isVisible = true
                badge.number = it.size
            } else {
                badge.isVisible = true
                badge.number = 0
            }
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getFavourites()
        }

    }
}
