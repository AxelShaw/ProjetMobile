package com.example.tecmobileproject.main.favorie

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tecmobileproject.R
import com.example.tecmobileproject.dtos.DtoInputMovie
import com.example.tecmobileproject.main.movie.MovieListFragment
import com.example.tecmobileproject.main.movie.MovieRecyclerViewAdapter


class FavorieListFragment : Fragment() {
    private val favoriesUI: ArrayList<DtoInputMovie> = arrayListOf()
    val favorieRecyclerViewAdapter = FavorieRecyclerViewAdapter(favoriesUI)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.favorie_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = favorieRecyclerViewAdapter
            }
        }


        return view
    }

    fun initUIWithFavories(favories: List<DtoInputMovie>?) {
        favoriesUI.clear()
        favories?.let {
            favoriesUI.addAll(it)
        }
        favorieRecyclerViewAdapter.notifyDataSetChanged()
    }
    fun setOnItemLongClickListener(longClickListener: MovieRecyclerViewAdapter.OnItemLongClickListener) {
        favorieRecyclerViewAdapter.setOnItemLongClickListener(longClickListener)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavorieListFragment()
    }
}