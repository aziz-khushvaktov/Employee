package com.example.responseparsinghomework.network.retrofit.service

import com.example.responseparsinghomework.model.Emp
import com.example.responseparsinghomework.model.Employee
import com.example.responseparsinghomework.model.Post
import com.example.responseparsinghomework.model.Posts
import retrofit2.Call
import retrofit2.http.*

interface EmployeeService {

    @Headers("Content-type:application/json")

    @GET("employees")
    fun getAllEmployees(): Call<Emp>

    @GET("employee/{id}")
    fun getEmployee(@Path("id") id: Int): Call<Employee>

    @POST("create")
    fun createNewEmployee(@Body employee: Posts): Call<Posts>

    @PUT("update/{id}")
    fun updateEmployee(@Path("id") id: Int, @Body employee: Employee): Call<Employee>

    @DELETE("delete/{id}")
    fun deleteEmployee(@Path("id") id: Int): Call<Employee>
}