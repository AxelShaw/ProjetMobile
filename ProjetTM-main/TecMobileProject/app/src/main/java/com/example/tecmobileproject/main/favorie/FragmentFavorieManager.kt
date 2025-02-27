package com.example.tecmobileproject.main.favorie

import android.app.AlertDialog
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.AppCompatImageButton
import com.example.tecmobileproject.R
import com.example.tecmobileproject.databinding.FragmentFavorieManagerBinding
import com.example.tecmobileproject.dtos.DtoInputMovie
import com.example.tecmobileproject.main.movie.MovieListActivity
import com.example.tecmobileproject.main.movie.MovieRecyclerViewAdapter

class FragmentFavorieManager : Fragment() {
    lateinit var binding: FragmentFavorieManagerBinding
    private lateinit var viewModel: FavorieViewModel
    private lateinit var favorieListFragment: FavorieListFragment

    companion object {
        fun newInstance() = FragmentFavorieManager()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavorieManagerBinding.inflate(layoutInflater, container, false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavorieViewModel::class.java)

        favorieListFragment = (childFragmentManager.findFragmentById(R.id.fragmentContainerView_favorieList) as FavorieListFragment)


        val backButton = view.findViewById<AppCompatImageButton>(R.id.btn_fragmentFavManager_backFav)
        backButton.setOnClickListener {
            val intent = Intent(requireContext(), MovieListActivity::class.java)
            startActivity(intent)
        }

        favorieListFragment.setOnItemLongClickListener(object : MovieRecyclerViewAdapter.OnItemLongClickListener {
            override fun onItemLongClick(movie: DtoInputMovie) {
                showDeleteButton(movie)
            }
        })
        if (favorieListFragment != null) {
            viewModel.mutableMovieLiveData.observe(viewLifecycleOwner) {
                favorieListFragment.initUIWithFavories(it)
            }

            viewModel.getListFavorie(requireContext())
        } else {
            Log.e("FragmentFavorieManager", "FavorieListFragment not found in the fragment container.")
        }
    }
    private fun showDeleteButton(movie: DtoInputMovie) {

        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.delete_alert_dialog, null)

        val positiveButton = dialogView.findViewById<Button>(R.id.buttonPositive)
        val negativeButton = dialogView.findViewById<Button>(R.id.buttonNegative)

        positiveButton.setOnClickListener {
            //viewModel.deleteMovieById(movie.idMovie)
        }

        val alertDialogBuilder = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
            .setView(dialogView)
            .setTitle("Confirmation")
            .setMessage("Voulez-vous supprimer ce film?")


        val alertDialog = alertDialogBuilder.create()
        negativeButton.setOnClickListener {
            alertDialog.dismiss()
        }



        alertDialog.show()
    }

}




