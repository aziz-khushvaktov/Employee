package com.example.responseparsinghomework2.utils

import android.util.Log
import com.example.responseparsinghomework2.networking.volley.VolleyHttp

class Logger {

    companion object {
        fun d(tag: String, msg: String) {
            if (VolleyHttp.IS_TESTER) Log.d(tag, msg)
        }
    }
}