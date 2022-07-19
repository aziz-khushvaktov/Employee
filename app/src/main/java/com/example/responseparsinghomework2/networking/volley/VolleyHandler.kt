package com.example.responseparsinghomework2.networking.volley

interface VolleyHandler {
    fun onSuccess(response: String?)
    fun onError(error: String?)
}