package com.example.pcmovies.ui.moviedetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.pcmovies.R
import com.example.pcmovies.databinding.FragmentMovieDetailBinding
import com.example.pcmovies.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieDetailViewModel by viewModels()
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeMovieDetail()
        viewModel.fetchMovieDetail(args.movieId)
    }

    private fun observeMovieDetail() {
        Log.e("test", "observeMovieDetail")
        lifecycleScope.launch {
            viewModel.movieDetail.collect { movie ->
                if (movie.poster_path.isNullOrBlank()) return@collect

                Log.e("test", "observeMovieDetail2: ${movie.poster_path}")
                binding.textTitle.text = movie.title
                binding.textReleaseDate.text = "Release Date: ${movie.release_date}"
                binding.textDescription.text = movie.overview
                Glide.with(requireContext())
                    .load("${Constants.TMDB_IMAGE_URL}${movie.poster_path}")
                    .placeholder(R.drawable.ic_avatar_placeholder)
                    .error(R.drawable.ic_avatar_placeholder)
                    .transform(RoundedCorners(24))
                    .into(binding.imagePoster)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}