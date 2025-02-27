package com.example.tecmobileproject.main.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tecmobileproject.R
import com.example.tecmobileproject.dtos.DtoInputMovie


class MovieListFragment : Fragment() {
    private val moviesUI: ArrayList<DtoInputMovie> = arrayListOf()
    val movieRecyclerViewAdapter = MovieRecyclerViewAdapter(moviesUI)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.movie_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = movieRecyclerViewAdapter
            }
        }


        return view
    }

    fun initUIWithMovies(movies: List<DtoInputMovie>?) {
        moviesUI.clear()
        movies?.let {
            moviesUI.addAll(it)
        }
        movieRecyclerViewAdapter.notifyDataSetChanged()
    }


    fun addMovieToUI(movie: DtoInputMovie) {
        moviesUI.add(movie)
        movieRecyclerViewAdapter.notifyDataSetChanged()
    }


    fun setOnItemClickListener(listener: MovieRecyclerViewAdapter.OnItemClickListener) {
        movieRecyclerViewAdapter.setOnItemClickListener(listener)
    }
    fun setOnItemLongClickListener(longClickListener: MovieRecyclerViewAdapter.OnItemLongClickListener) {
        movieRecyclerViewAdapter.setOnItemLongClickListener(longClickListener)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MovieListFragment()
    }

}