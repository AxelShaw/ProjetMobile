package com.example.tecmobileproject.main.favorie

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecmobileproject.dtos.DtoInputFavorie
import com.example.tecmobileproject.dtos.DtoInputMovie

import com.example.tecmobileproject.repository.IFavorieRepository
import com.example.tecmobileproject.repository.IMovieRepository
import com.example.tecmobileproject.repository.IUserRepository
import com.example.tecmobileproject.utils.RetrofitFactory
import kotlinx.coroutines.launch
import retrofit2.HttpException


class FavorieViewModel : ViewModel() {

    val mutableLiveDataLoginUser: MutableLiveData<String?> = MutableLiveData()

    val mutableMovieLiveData: MutableLiveData<List<DtoInputMovie>> = MutableLiveData()

    private val userRepository = RetrofitFactory.instance.create(IUserRepository::class.java)
    private val movieRepository = RetrofitFactory.instance.create(IMovieRepository::class.java)
    private val favorieRepository = RetrofitFactory.instance.create(IFavorieRepository::class.java)


    fun getListFavorie(context: Context) {
        viewModelScope.launch {
            try {
                //Get user data using the token
                val preferences = context.getSharedPreferences("app", AppCompatActivity.MODE_PRIVATE)
                val token = preferences.getString("jwtToken", "???")

                //Get user ID from the token
                val user = userRepository.getToken(token.toString())
                val userId = user.userId

                if (userId != null) {
                    mutableLiveDataLoginUser.postValue(userId.toString())


                    val favories = favorieRepository.getAll()
                    val userFavories = favories.filter { it.idUserRef == userId.toInt() }

                    val movies = mutableListOf<DtoInputMovie>()

                    userFavories.forEach { favorite ->
                        try {
                            // Get movie details by ID
                            val movie = movieRepository.getMovieById(favorite.idMovieRef)
                            movies.add(movie)
                        } catch (e: HttpException) {
                            Log.e("FavorieViewModel", "Error calling getMovieById", e)
                        } catch (e: Exception) {
                            Log.e("FavorieViewModel", "Error calling getMovieById", e)
                        }
                    }

                    mutableMovieLiveData.postValue(movies)
                } else {
                    mutableLiveDataLoginUser.postValue(null)
                }
            } catch (e: HttpException) {
                mutableLiveDataLoginUser.postValue(null)
                Log.e("FavorieViewModel", "Error in getListFavorie", e)
            }
        }
    }


}