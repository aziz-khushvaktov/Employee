package com.example.responseparsinghomework.network.volley

interface VolleyHandler {
    fun onSuccess(response: String?)
    fun onError(error: String?)
}