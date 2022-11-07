package com.example.movieapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.API_KEY
import com.example.movieapp.MOVIE_ID
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.extentions.gone
import com.example.movieapp.extentions.show
import com.example.movieapp.model.Movie
import com.example.movieapp.ui.main.adapter.MoviesAdapter
import com.example.movieapp.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by viewModels()
    private val moviesAdapter: MoviesAdapter by lazy {
        MoviesAdapter(
            ::handleItemClicked
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMovies(API_KEY, 1)
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.items.collectLatest {
                it.let {
                    handleSuccessState(it)
                    //moviesAdapter.submitData(it)
                }
            }
        }

    }

    private fun initView() {
        viewModel.movieLiveData.observe(viewLifecycleOwner) { states ->
            run {
                when (states) {
                    is ScreenState.Loading -> showLoadingState()
                    is ScreenState.Error -> showErrorState()
                }
            }
        }
    }

    private fun showLoadingState() {
        with(binding) {
            progress.show()
            movieRecycler.gone()
            emptyText.gone()
        }
    }

    private fun showErrorState() {
        with(binding) {
            progress.gone()
            movieRecycler.gone()
            emptyText.show()
        }
    }

    private suspend fun handleSuccessState(movies: PagingData<Movie>) {
        with(binding) {
            progress.gone()
            movieRecycler.show()
            emptyText.gone()

            movieRecycler.apply {
                val manager = GridLayoutManager(context, 2)
                adapter = moviesAdapter
                layoutManager = manager

                addOnScrollListener(object : ScrollingPagination(manager) {
                    override fun loadMoreDate() {
                        viewModel.getScrollingMovies()
                    }
                })
            }
            moviesAdapter.submitData(movies)
            if (moviesAdapter.snapshot().items.isEmpty()) {
                showErrorState()
            }

        }
    }

    private fun handleItemClicked(id: Int?) {
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID, id ?: 0)
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
    }
}