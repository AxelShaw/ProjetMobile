package com.example.tecmobileproject.main.movie

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tecmobileproject.R
import com.example.tecmobileproject.databinding.FragmentMovieDetailBinding
import com.example.tecmobileproject.dtos.DtoInputMovie
import com.example.tecmobileproject.dtos.DtoOutputFav
import java.io.Serializable

private const val ARG_MOVIE = "movie"

class MovieDetailFragment : Fragment() {
    private var movie: DtoInputMovie? = null
    private lateinit var binding: FragmentMovieDetailBinding
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getSerializable(ARG_MOVIE) as? DtoInputMovie
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        displayMovieDetails()

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        val preferences = requireContext().getSharedPreferences("app", AppCompatActivity.MODE_PRIVATE)
        val token = preferences.getString("jwtToken", "???")

        binding.btnFav.setOnClickListener{
            viewModel.getDataTokenId(token.toString()) { userId ->
                val favData = DtoOutputFav(
                    movie?.idMovie,
                    userId
                )
                viewModel.addFav(favData)

                Toast.makeText(requireContext(), "Film ajouté aux favoris !", Toast.LENGTH_SHORT).show()

                }
            }
        }

    private fun displayMovieDetails() {
        movie?.let { movie ->
            binding.tvMovieDetailName.text = movie.nameMovie
            binding.tvMovieDetailRuntime.text = "Durée: ${movie.runtimeMinute} minutes"
            binding.tvMovieDetailType.text = "Type: ${movie.movieType}"
            binding.tvMovieDetailDescription.text = "Description: ${movie.descriptionMovie}"

            if (movie.imageMovie.isNotEmpty()) {
                val decodedBytes: ByteArray = Base64.decode(movie.imageMovie, Base64.DEFAULT)
                val decodedBitmap: Bitmap? = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

                if (decodedBitmap != null) {
                    binding.ivMovieDetail.setImageBitmap(decodedBitmap)
                } else {
                    binding.ivMovieDetail.setImageResource(R.drawable.logo)
                }
            } else {
                binding.ivMovieDetail.setImageResource(R.drawable.logo)
            }

            binding.tvMovieDirector.text = "Réalisateur: ${movie.director}"
            binding.tvMovieGenre.text = "Genre: ${movie.filmGenre}"
            binding.tvMovieRelease.text = "Date de sortie: ${movie.release_movie}"


            displayRatings(movie.idMovie)
        }
    }

    private fun displayRatings(movieId: Int) {
        viewModel.getRatingById(movieId)
        viewModel.mutableRatingLiveData.observe(viewLifecycleOwner) { ratings ->

            if (ratings.isNotEmpty()) {
                val averageRating = ratings[0].average_rating
                val formattedRating = "$averageRating/100"
                binding.tvMovieDetailRatings.text = "Note : $formattedRating"
            } else {
                binding.tvMovieDetailRatings.text = "Pas d'évaluation disponible"
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(movie: DtoInputMovie) =
            MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_MOVIE, movie as Serializable)
                }
            }
    }
}