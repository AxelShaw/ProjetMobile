package com.example.tecmobileproject.main.user


import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecmobileproject.dtos.*

import com.example.tecmobileproject.repository.IUserRepository
import com.example.tecmobileproject.utils.RetrofitFactory

import kotlinx.coroutines.launch
import retrofit2.HttpException

class UserViewModel : ViewModel(){

    val mutableLiveDataLoginUser: MutableLiveData<String?> = MutableLiveData()


    private val userRepository = RetrofitFactory.instance.create(IUserRepository::class.java)



    fun launchGetUser(dtoLogin: DtoLogin) {
        viewModelScope.launch {
            try {
                val user = userRepository.connexion(dtoLogin)
                mutableLiveDataLoginUser.postValue(user)

            } catch (e: HttpException) {
                mutableLiveDataLoginUser.postValue(null)
            }
        }
    }

    fun launchGetUserGoogle(dtoLogin: DtoLogin) {
        viewModelScope.launch {
            try {
                val user = userRepository.connexionGoogle(dtoLogin)
                mutableLiveDataLoginUser.postValue(user)

            } catch (e: HttpException) {
                mutableLiveDataLoginUser.postValue(null)
            }
        }
    }



    fun getDataToken(token: String){
        viewModelScope.launch {
            try {
                val user = userRepository.getToken(token)
            } catch (e: HttpException) {
                mutableLiveDataLoginUser.postValue(null)
            }
        }
    }




}