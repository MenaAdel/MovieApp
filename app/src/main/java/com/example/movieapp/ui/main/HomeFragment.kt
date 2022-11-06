package com.example.movieapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by viewModels()
    private val moviesAdapter: MoviesAdapter by lazy { MoviesAdapter(::handleItemClicked , listOf()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMovies(API_KEY ,1)
        initView()
    }

    private fun initView() {
        viewModel.movieLiveData.observe(viewLifecycleOwner) { states ->
            run {
                when (states) {
                    is ScreenState.Loading -> showLoadingState()
                    is ScreenState.Error -> showErrorState()
                    is ScreenState.Success -> handleSuccessState(states.list)
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

    private fun handleSuccessState(movies: List<Movie>){
        moviesAdapter.updateMoviesList(movies)
        with(binding) {
            progress.gone()
            movieRecycler.show()
            emptyText.gone()

            movieRecycler.apply {
                adapter = moviesAdapter
                layoutManager = GridLayoutManager(context ,2)
            }
        }
    }

    private fun handleItemClicked(id: Int?) {
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID ,id ?: 0)
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment ,bundle)
    }
}