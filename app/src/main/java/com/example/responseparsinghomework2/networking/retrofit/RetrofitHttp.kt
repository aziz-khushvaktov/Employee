package com.example.responseparsinghomework2.networking.retrofit

import com.example.responseparsinghomework2.networking.retrofit.service.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHttp {

    val TAG = RetrofitHttp::class.java.simpleName
    private val IS_TESTER = true
    val SERVER_DEVELOPMENT = "https://jsonplaceholder.typicode.com/"
    val SERVER_PRODUCTION = "https://jsonplaceholder.typicode.com/"

    private var retrofit = Retrofit.Builder().baseUrl(SERVER_DEVELOPMENT).addConverterFactory(GsonConverterFactory.create()).build()

    private fun server(): String {
        if (IS_TESTER) {
            return SERVER_DEVELOPMENT
        }
        return SERVER_PRODUCTION
    }

    val userService: UserService = retrofit.create(UserService::class.java)
}