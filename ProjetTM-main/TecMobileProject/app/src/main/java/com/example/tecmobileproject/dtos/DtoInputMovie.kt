package com.example.tecmobileproject.dtos

import java.io.Serializable

data class DtoInputMovie(val idMovie:Int, val nameMovie:String, val runtimeMinute:Int, val movieType:String,
val descriptionMovie:String, val imageMovie:String, val filmGenre: String, val director:String, val release_movie:String):
    Serializable