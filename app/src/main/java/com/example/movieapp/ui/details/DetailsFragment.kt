package com.example.movieapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.movieapp.MOVIE_ID
import com.example.movieapp.databinding.FragmentDetailsBinding
import com.example.movieapp.extentions.setImage
import com.example.movieapp.ui.details.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        viewModel.getMovieDetails(arguments?.getInt(MOVIE_ID, 1) ?: 1)
        with(binding) {
            viewModel.movieLiveData.observe(viewLifecycleOwner, Observer {
                it?.let {
                    movieBackdrop.setImage(it.backdropPath)
                    moviePoster.setImage(it.posterPath)
                    movieTitle.text = it.title
                    movieOverview.text = it.overview
                    movieRating.rating = it.voteAverage?.toFloat() ?: 0f
                }
            })
        }
    }
}