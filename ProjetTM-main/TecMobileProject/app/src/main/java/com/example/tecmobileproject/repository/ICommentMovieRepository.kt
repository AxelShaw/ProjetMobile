package com.example.tecmobileproject.repository

import com.example.tecmobileproject.dtos.DtoInputCommentMovie
import com.example.tecmobileproject.dtos.DtoInputMovie
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ICommentMovieRepository {
    @GET("commentmovie")
    suspend fun getAll() : List<DtoInputCommentMovie>

    @DELETE("commentmovie/{id}")
    suspend fun deleteCommentMovieById(@Path("id") idComMovie: Int): DtoInputCommentMovie
}