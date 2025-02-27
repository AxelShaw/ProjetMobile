package com.example.tecmobileproject.repository

import com.example.tecmobileproject.dtos.*
import retrofit2.Response
import retrofit2.http.*

interface IMovieRepository {
    @GET("movie")
    suspend fun getAll() : List<DtoInputMovie>

    @POST("movie")
    suspend fun create(@Body dtoOutputCreateMovie: DtoOutputCreateMovie): DtoInputMovie

    @DELETE("movie/{id}")
    suspend fun deleteMovieById(@Path("id") idMovie: Int): DtoInputMovie

    @PUT("movie")
    suspend fun update(@Body dtoOutputUpdateMovie: DtoOutputUpdateMovie): Response<Unit>

    @GET("movie/{id}")
    suspend fun getMovieById(@Path("id") idMovie: Int): DtoInputMovie

    @DELETE("ratingmovie/{id}")
    suspend fun deleteRatingMovieById(@Path("id") movieRefId: Int): DtoInputRating

    @GET("ratingmovie/{id}")
    suspend fun getRatingMovieById(@Path("id") movieRefId: Int): DtoInputRating

    @POST("favorie")
    suspend fun createFav(@Body dtoOutputFav: DtoOutputFav): DtoInputFavorie





}