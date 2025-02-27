package com.example.tecmobileproject.main.favorie


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

import com.example.tecmobileproject.databinding.ActivityFavorieBinding


class FavorieActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavorieBinding
    private lateinit var viewModel: FavorieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavorieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(FavorieViewModel::class.java)


    }



    private fun setUpListeners() {

    }
}