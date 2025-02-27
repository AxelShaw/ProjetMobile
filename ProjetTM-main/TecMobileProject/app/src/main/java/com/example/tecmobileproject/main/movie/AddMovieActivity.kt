package com.example.tecmobileproject.main.movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.tecmobileproject.R
import com.example.tecmobileproject.databinding.ActivityAddMovieBinding
import com.example.tecmobileproject.databinding.ActivityUpdateBinding
import com.example.tecmobileproject.dtos.DtoOutputCreateMovie
import com.example.tecmobileproject.dtos.DtoOutputUpdateMovie
import com.example.tecmobileproject.main.user.UserViewModel

class AddMovieActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddMovieBinding
    private lateinit var viewModel: MovieViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)
        binding = ActivityAddMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.btnAddActivityAddMovie.setOnClickListener {
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
                    val movieData = DtoOutputCreateMovie(
                        binding.etAddActivityName.text.toString(),
                        binding.etAddActivityRuntimeMinute.text.toString().toInt(),
                        binding.etAddActivityType.text.toString(),
                        binding.etAddActivityDescription.text.toString(),
                        "null",
                        binding.etAddActivityGenre.text.toString(),
                        binding.etAddActivityDirector.text.toString(),
                        binding.etAddActivityRelease.text.toString(),
                    )
                    viewModel.addMovie(movieData)

                    Intent(this, MovieListActivity::class.java).apply {
                        startActivity(this)
                    }
                }
        }
    }
}