package com.example.responseparsinghomework.utils

import android.util.Log
import com.example.responseparsinghomework.network.retrofit.RetrofitHttp

object Logger {

    fun d(tag: String, msg: String) {
        if (RetrofitHttp.IS_TESTER) Log.d(tag, msg)
    }

}