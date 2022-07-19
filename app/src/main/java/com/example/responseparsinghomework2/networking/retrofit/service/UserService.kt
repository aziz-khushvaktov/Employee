package com.example.responseparsinghomework2.networking.retrofit.service

import com.example.responseparsinghomework2.model.User
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @Headers("Content-type:application/json")

    @GET("posts")
    fun getAllUsers(): Call<ArrayList<User>>

    @GET("posts/{id}")
    fun getSingleUser(@Path("id") id: Int): Call<User>

    @PUT("posts/{id}")
    fun updateUser(@Path("id") id: Int, @Body user: User): Call<User>

    @POST("posts")
    fun createUser(@Body user: User): Call<User>

    @DELETE("posts/{id}")
    fun deleteUser(@Path("id") id: Int): Call<User>
}