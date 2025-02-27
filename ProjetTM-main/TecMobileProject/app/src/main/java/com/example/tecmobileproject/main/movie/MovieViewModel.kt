package com.example.tecmobileproject.main.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecmobileproject.dtos.*
import com.example.tecmobileproject.repository.ICommentMovieRepository
import com.example.tecmobileproject.repository.IFavorieRepository
import com.example.tecmobileproject.repository.IMovieRepository
import com.example.tecmobileproject.repository.IUserRepository

import com.example.tecmobileproject.utils.RetrofitFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MovieViewModel : ViewModel() {
    val mutableMovieLiveData: MutableLiveData<List<DtoInputMovie>> = MutableLiveData()
    val mutableLiveDataLoginUser: MutableLiveData<String?> = MutableLiveData()
    val mutableCreateMovieLiveData: MutableLiveData<DtoInputMovie> = MutableLiveData()
    val mutableCreateFavLiveData: MutableLiveData<DtoInputFavorie> = MutableLiveData()
    val mutableUpdateMovieLiveData: MutableLiveData<DtoOutputUpdateMovie?> = MutableLiveData()
    val mutableRatingLiveData: MutableLiveData<List<DtoInputRating>> = MutableLiveData()

    private val movieRepository = RetrofitFactory.instance.create(IMovieRepository::class.java)
    private val ratingRepository = RetrofitFactory.instance.create(IMovieRepository::class.java)
    private val userRepository = RetrofitFactory.instance.create(IUserRepository::class.java)
    private val _deletedMovieLiveData = MutableLiveData<Boolean>()
    val deletedMovieLiveData: LiveData<Boolean>
        get() = _deletedMovieLiveData

    fun startGetAllMovies() {
        viewModelScope.launch {
            try {
                val movies = movieRepository.getAll()
                mutableMovieLiveData.postValue(movies)

            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error calling getAllMovies", e)
            }
        }
    }




    ///DELETE MOVIE


    private val commentMovieRepository = RetrofitFactory.instance.create(ICommentMovieRepository::class.java)
    private val favorieRepository = RetrofitFactory.instance.create(IFavorieRepository::class.java)

    suspend fun deleteRatingMovieById(movieId: Int): Boolean {
        return try {
            ratingRepository.deleteRatingMovieById(movieId)
            true
        } catch (e: HttpException) {
            if (e.code() == 404) {
                Log.d("MovieViewModel", "Rating for movie with ID $movieId not found. Assuming it's already deleted.")
                true
            } else {
                Log.e("MovieViewModel", "Error deleting rating for movie with ID $movieId", e)
                false
            }
        } catch (e: Exception) {
            Log.e("MovieViewModel", "Error deleting rating for movie with ID $movieId", e)
            false
        }
    }
    suspend fun deleteFavorieByIdMovie(movieId: Int): Boolean {
        return try {
            favorieRepository.deleteFavorieByIdMovie(movieId)
            true
        } catch (e: HttpException) {
            if (e.code() == 404) {
                Log.d("MovieViewModel", "Favorites for movie with ID $movieId not found. Assuming they're already deleted.")
                true
            } else {
                Log.e("MovieViewModel", "Error deleting favorites for movie with ID $movieId", e)
                false
            }
        } catch (e: Exception) {
            Log.e("MovieViewModel", "Error deleting favorites for movie with ID $movieId", e)
            false
        }
    }


    suspend fun deleteCommentMoviesByMovieId(movieId: Int): Boolean {
        return try {
            commentMovieRepository.deleteCommentMovieById(movieId)
            true
        } catch (e: HttpException) {
            if (e.code() == 404) {
                Log.d("MovieViewModel", "Comment movies for movie with ID $movieId not found. Assuming they're already deleted.")
                true
            } else {
                Log.e("MovieViewModel", "Error deleting comment movies for movie with ID $movieId", e)
                false
            }
        } catch (e: Exception) {
            Log.e("MovieViewModel", "Error deleting comment movies for movie with ID $movieId", e)
            false
        }
    }



    fun deleteMovieById(movieId: Int) {
        viewModelScope.launch {
            try {
                // Delete comment movies
                val deleteCommentMoviesDeferred = viewModelScope.async { deleteCommentMoviesByMovieId(movieId) }
                val deletedCommentMovies = try {
                    deleteCommentMoviesDeferred.await()
                } catch (e: Exception) {
                    Log.e("MovieViewModel", "Error deleting comment movies for movie with ID $movieId", e)
                    false
                }

                if (deletedCommentMovies != false) {
                    // Delete ratings
                    val deleteRatingDeferred = viewModelScope.async { deleteRatingMovieById(movieId) }
                    val deletedRating = try {
                        deleteRatingDeferred.await()
                    } catch (e: Exception) {
                        Log.e("MovieViewModel", "Error deleting rating for movie with ID $movieId", e)
                        false
                    }

                    if (deletedRating != false) {
                        //Delete favorites
                        val deleteFavorieDeferred = viewModelScope.async { deleteFavorieByIdMovie(movieId) }
                        val deletedFavorie = try {
                            deleteFavorieDeferred.await()
                        } catch (e: Exception) {
                            Log.e("MovieViewModel", "Error deleting favorites for movie with ID $movieId", e)
                            false
                        }

                        if (deletedFavorie != false) {
                            // Delete the movie
                            try {
                                val deletedMovie = movieRepository.deleteMovieById(movieId)
                                if (deletedMovie != null) {
                                    _deletedMovieLiveData.value = true
                                } else {
                                    Log.d("MovieViewModel", "Movie with ID $movieId not found. Assuming it's already deleted.")
                                }
                            } catch (e: HttpException) {
                                if (e.code() == 404) {
                                    Log.d("MovieViewModel", "Movie with ID $movieId not found. Assuming it's already deleted.")
                                } else {
                                    Log.e("MovieViewModel", "Error deleting movie with ID $movieId", e)
                                }
                            } catch (e: Exception) {
                                Log.e("MovieViewModel", "Error deleting movie with ID $movieId", e)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error in async block", e)
            }
        }
    }
        fun getRatingById(movieId: Int) {
            viewModelScope.launch {
                try {
                    val rating = ratingRepository.getRatingMovieById(movieId)
                    val ratings = listOf(rating)
                    mutableRatingLiveData.postValue(ratings)
                } catch (e: Exception) {
                    Log.e("MovieViewModel", "Error obtaining ratings for movie $movieId", e)
                }
            }
        }


        fun updateMovie(dtoOutputUpdateMovie: DtoOutputUpdateMovie) {
            viewModelScope.launch {
                try {
                    val response = movieRepository.update(dtoOutputUpdateMovie)
                    if (response.isSuccessful) {
                        if (response.code() == 204) {
                            mutableUpdateMovieLiveData.postValue(dtoOutputUpdateMovie)
                        } else {
                            mutableUpdateMovieLiveData.postValue(null)
                        }
                    } else {
                        mutableUpdateMovieLiveData.postValue(null)
                    }
                } catch (e: Exception) {
                    mutableUpdateMovieLiveData.postValue(null)
                }
            }
        }


    fun addMovie(dtoOutputCreateMovie: DtoOutputCreateMovie) {
        viewModelScope.launch {
            val response = movieRepository.create(dtoOutputCreateMovie)
            mutableCreateMovieLiveData.postValue(response)
        }
    }

    fun getDataTokenRole(token: String, callback: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                val user = userRepository.getToken(token)
                callback(user.userRole)
            } catch (e: HttpException) {
                mutableLiveDataLoginUser.postValue(null)
                callback(null)
            }
        }
    }

    fun getDataTokenId(token: String, callback: (Int?) -> Unit) {
        viewModelScope.launch {
            try {
                val user = userRepository.getToken(token)
                val userIdInt: Int? = user?.userId?.toIntOrNull()
                callback(userIdInt)
            } catch (e: HttpException) {
                mutableLiveDataLoginUser.postValue(null)
                callback(null)
            }
        }
    }

    fun addFav(favData: DtoOutputFav) {
        viewModelScope.launch {
            val response = movieRepository.createFav(favData)
            mutableCreateFavLiveData.postValue(response)
        }
    }


}



