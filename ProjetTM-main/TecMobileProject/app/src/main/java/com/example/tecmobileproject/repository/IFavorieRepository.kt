package com.example.tecmobileproject.repository

import com.example.tecmobileproject.dtos.DtoInputFavorie
import com.example.tecmobileproject.dtos.DtoInputMovie
import com.example.tecmobileproject.dtos.DtoInputUser
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface IFavorieRepository {

    @GET("favorie")
    suspend fun getAll() : List<DtoInputFavorie>

    @DELETE("favorie/movie/{id}")
    suspend fun deleteFavorieByIdMovie(@Path("id") idMovie: Int): DtoInputFavorie

    @GET("favorie/{id}")
    suspend fun getFavorieById(@Path("id") idUser: Int): DtoInputFavorie

}