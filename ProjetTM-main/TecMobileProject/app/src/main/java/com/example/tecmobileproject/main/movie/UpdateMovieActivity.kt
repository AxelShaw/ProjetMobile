package com.example.tecmobileproject.main.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tecmobileproject.databinding.ActivityUpdateBinding
import com.example.tecmobileproject.dtos.DtoInputMovie
import com.example.tecmobileproject.dtos.DtoOutputUpdateMovie
import com.example.tecmobileproject.main.user.UserViewModel

class UpdateMovieActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateBinding
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = intent.getSerializableExtra("movieData") as? DtoInputMovie
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        binding.btnAddActivityUpdateMovie.setOnClickListener {
            if (movie == null) {
                Toast.makeText(this, "Movie data is null", Toast.LENGTH_SHORT).show()
            } else {
                if (binding.etAddActivityGenre.text.isNullOrBlank() ||
                    binding.etAddActivityName.text.isNullOrBlank() ||
                    binding.etAddActivityRelease.text.isNullOrBlank() ||
                    binding.etAddActivityDirector.text.isNullOrBlank() ||
                    binding.etAddActivityDescription.text.isNullOrBlank() ||
                    binding.etAddActivityType.text.isNullOrBlank() ||
                    binding.etAddActivityRuntimeMinute.text.isNullOrBlank()
                ) {
                    Toast.makeText(this, "Please fill in the fields", Toast.LENGTH_SHORT).show()
                } else {
                    val movieData = DtoOutputUpdateMovie(
                        movie.idMovie,
                        binding.etAddActivityName.text.toString(),
                        binding.etAddActivityRuntimeMinute.text.toString().toInt(),
                        binding.etAddActivityType.text.toString(),
                        binding.etAddActivityDescription.text.toString(),
                        movie.imageMovie,
                        binding.etAddActivityGenre.text.toString(),
                        binding.etAddActivityDirector.text.toString(),
                        binding.etAddActivityRelease.text.toString(),
                    )
                    viewModel.updateMovie(movieData)

                    Intent(this, MovieListActivity::class.java).apply {
                        startActivity(this)
                    }
                }
            }
        }

    }




    override fun onResume() {
        super.onResume()
        val movie = intent.getSerializableExtra("movieData") as? DtoInputMovie

        if(movie is DtoInputMovie){
            Log.i("temp",movie.toString())

            binding.etAddActivityName.setText(movie.nameMovie)
            binding.etAddActivityRuntimeMinute.setText(movie.runtimeMinute.toString())
            binding.etAddActivityType.setText(movie.movieType)
            binding.etAddActivityDescription.setText(movie.descriptionMovie)
            binding.etAddActivityGenre.setText(movie.filmGenre)
            binding.etAddActivityDirector.setText(movie.director)
            binding.etAddActivityRelease.setText(movie.release_movie)


        }else{
            Log.e("ERREUR","No movie found")
        }
    }
}