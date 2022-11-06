package com.example.movieapp.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.API_KEY
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.extentions.gone
import com.example.movieapp.extentions.show
import com.example.movieapp.model.Movie
import com.example.movieapp.ui.main.adapter.MoviesAdapter
import com.example.movieapp.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val moviesAdapter: MoviesAdapter by lazy { MoviesAdapter(::handleItemClicked , listOf()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getMovies(API_KEY ,1)
        initView()
    }

    private fun initView() {
        viewModel.movieLiveData.observe(this) { states ->
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
                layoutManager = GridLayoutManager(this@MainActivity ,2)
            }
        }
    }

    private fun handleItemClicked(id: Int?) {

    }
}