package com.example.responseparsinghomework.network.retrofit

import com.example.responseparsinghomework.network.retrofit.service.EmployeeService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHttp {

    private var TAG = RetrofitHttp::class.java.simpleName
    var IS_TESTER = true
    private var SERVER_DEVELOPMENT = "https://dummy.restapiexample.com/api/v1/"
    private var SERVER_PRODUCTION =  "https://dummy.restapiexample.com/api/v1/"

    private var retrofit = Retrofit.Builder().baseUrl(server()).addConverterFactory(GsonConverterFactory.create()).build()

    private fun server(): String {
        if (IS_TESTER) {
            return SERVER_DEVELOPMENT
        }
        return SERVER_PRODUCTION
    }

    val employeeService: EmployeeService = retrofit.create(EmployeeService::class.java)
}