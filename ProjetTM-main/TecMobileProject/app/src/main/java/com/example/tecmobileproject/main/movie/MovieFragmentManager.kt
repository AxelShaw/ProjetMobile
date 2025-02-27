package com.example.tecmobileproject



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
import androidx.appcompat.app.AppCompatActivity
import com.example.tecmobileproject.databinding.FragmentMovieManagerBinding
import com.example.tecmobileproject.dtos.DtoInputMovie
import com.example.tecmobileproject.main.movie.*


class MovieManagerFragment : Fragment() {
    lateinit var binding: FragmentMovieManagerBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieListFragment: MovieListFragment

    companion object {
        fun newInstance() = MovieManagerFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieManagerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        movieListFragment = childFragmentManager.findFragmentById(R.id.fragmentContainerView_movieManager_list) as MovieListFragment

        viewModel.mutableCreateMovieLiveData.observe(viewLifecycleOwner) {
            movieListFragment.addMovieToUI(it)
        }

        viewModel.mutableMovieLiveData.observe(viewLifecycleOwner) {
            movieListFragment.initUIWithMovies(it)
        }

        viewModel.mutableMovieLiveData.observe(viewLifecycleOwner) { updatedMovieList ->
            movieListFragment.initUIWithMovies(updatedMovieList)
        }

        movieListFragment.setOnItemClickListener(object : MovieRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(movie: DtoInputMovie) {
                // Afficher le MovieDetailFragment lorsque l'utilisateur clique sur un élément
                val movieDetailFragment = MovieDetailFragment.newInstance(movie)
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainer, movieDetailFragment) // Remplacez le conteneur principal
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })

        movieListFragment.setOnItemLongClickListener(object : MovieRecyclerViewAdapter.OnItemLongClickListener {
            override fun onItemLongClick(movie: DtoInputMovie) {
                ShowClickMenu(movie)
            }
        })

        viewModel.startGetAllMovies()
        viewModel.deletedMovieLiveData.observe(viewLifecycleOwner) { isDeleted ->
            if (isDeleted) {
                viewModel.startGetAllMovies()
            }
        }

    }

    private fun ShowClickMenu(movie: DtoInputMovie){

        val preferences = requireContext().getSharedPreferences("app", AppCompatActivity.MODE_PRIVATE)
        val token = preferences.getString("jwtToken", "???")

        viewModel.getDataTokenRole(token.toString()) { userRole ->
            if (userRole != null) {
                val oui = userRole
                Log.i("ouioui",userRole.toString())

                val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog, null)

                val OpenButton = dialogView.findViewById<Button>(R.id.buttonOpen)
                val DeleteButton = dialogView.findViewById<Button>(R.id.buttonDelete)
                val UpdateButton = dialogView.findViewById<Button>(R.id.buttonUpdate)
                val CloseButton = dialogView.findViewById<Button>(R.id.buttonClose)

                if (userRole != "user") {
                    DeleteButton.visibility = View.VISIBLE
                    UpdateButton.visibility = View.VISIBLE
                } else {
                    DeleteButton.visibility = View.GONE
                    UpdateButton.visibility = View.GONE
                }

                val alertDialogBuilder = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
                    .setView(dialogView)

                val alertDialog = alertDialogBuilder.create()

                OpenButton.setOnClickListener {
                    val movieDetailFragment = MovieDetailFragment.newInstance(movie)
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragmentContainer, movieDetailFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    alertDialog.dismiss()
                }

                DeleteButton.setOnClickListener {
                    showDeleteButton(movie)
                    alertDialog.dismiss()
                }

                UpdateButton.setOnClickListener {
                    val intent = Intent(requireContext(), UpdateMovieActivity::class.java)
                    intent.putExtra("movieData", movie)
                    startActivity(intent)
                    alertDialog.dismiss()
                }


                CloseButton.setOnClickListener {
                    alertDialog.dismiss()
                }

                alertDialog.show()
            }
        }
    }



    private fun showDeleteButton(movie: DtoInputMovie) {

        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.delete_alert_dialog, null)

        val positiveButton = dialogView.findViewById<Button>(R.id.buttonPositive)
        val negativeButton = dialogView.findViewById<Button>(R.id.buttonNegative)

        positiveButton.setOnClickListener {
            viewModel.deleteMovieById(movie.idMovie)
        }

        val alertDialogBuilder = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
            .setView(dialogView)
            .setTitle("Confirmation")
            .setMessage("Voulez-vous supprimer ce film?")


        val alertDialog = alertDialogBuilder.create()
        negativeButton.setOnClickListener {
            alertDialog.dismiss()
        }

        viewModel.deletedMovieLiveData.observe(viewLifecycleOwner) {
            if (it) {
                alertDialog.dismiss()
            }
        }

        alertDialog.show()
    }
}