package com.example.tecmobileproject.repository

import com.example.tecmobileproject.dtos.DtoLogin
import com.example.tecmobileproject.dtos.Token
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IUserRepository {
    @POST("login")
    suspend fun connexion(@Body loginData: DtoLogin): String

    @POST("loginGoogle")
    suspend fun connexionGoogle(@Body loginData: DtoLogin): String

    @GET("login")
    suspend fun getToken(@Query("token") token: String): Token
}