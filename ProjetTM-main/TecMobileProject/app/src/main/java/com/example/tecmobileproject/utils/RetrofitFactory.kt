package com.example.tecmobileproject.utils


import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitFactory {
    private val _url : String = "http://10.0.2.2:5245/api/v1/"

    val gson = GsonBuilder()
        .setLenient()
        .create()

    val instance: Retrofit = Retrofit
        .Builder()
        .baseUrl(_url)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}