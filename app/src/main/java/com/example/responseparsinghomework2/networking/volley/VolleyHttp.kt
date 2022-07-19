package com.example.responseparsinghomework2.networking.volley

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.responseparsinghomework2.MyApplication
import com.example.responseparsinghomework2.model.User
import com.example.responseparsinghomework2.utils.Logger
import org.json.JSONObject
import java.lang.reflect.Method

class VolleyHttp {

    companion object {
        private val TAG = VolleyHttp::class.java.simpleName
        val IS_TESTER = true
        private val SERVER_DEVELOPMENT = "https://jsonplaceholder.typicode.com/"
        private val SERVER_PRODUCTION = "https://jsonplaceholder.typicode.com/"

        private fun server(url: String): String {
            if (IS_TESTER) {
                return SERVER_DEVELOPMENT + url
            }
            return SERVER_PRODUCTION + url
        }

        fun headers(): HashMap<String,String> {
            val headers = HashMap<String,String>()
            headers["Content-type"] = "application/json; charset=UTF-8"
            return headers
        }

        /**
         *  Request Method`s
         */

        fun get(api: String,params: HashMap<String,String>,volleyHandler: VolleyHandler) {
           var stringRequest = object : StringRequest(Method.GET, server(api), Response.Listener { response ->
               volleyHandler.onSuccess(response)
               Logger.d(TAG, response)
           },
           Response.ErrorListener { error ->
               Logger.d(TAG, error.toString())
               volleyHandler.onError(error.toString())
           }) {
               override fun getParams(): MutableMap<String, String>? {
                   return params
               }
           }
            MyApplication.instance!!.addToRequestQueue(stringRequest)
       }

        fun post(api: String, body: HashMap<String, Any>, volleyHandler: VolleyHandler) {
            val stringRequest = object : StringRequest(Method.POST, server(api), Response.Listener { response ->
                volleyHandler.onSuccess(response)
            },
            Response.ErrorListener { error ->
                volleyHandler.onError(error.toString())
            }) {
                override fun getHeaders(): MutableMap<String, String> {
                    return headers()
                }

                override fun getBody(): ByteArray {
                    return JSONObject(body as Map<*,*>).toString().toByteArray()
                }
            }
            MyApplication.instance!!.addToRequestQueue(stringRequest)
        }

        fun put(api: String, body: HashMap<String, Any>, volleyHandler: VolleyHandler) {
            val stringRequest = object : StringRequest(Method.PUT, server(api),Response.Listener { response ->
                volleyHandler.onSuccess(response)
            },
            Response.ErrorListener { error ->
                volleyHandler.onError(error.toString())
            }) {
                override fun getHeaders(): MutableMap<String, String> {
                    return headers()
                }

                override fun getBody(): ByteArray {
                    return JSONObject(body as Map<*,*>).toString().toByteArray()
                }
            }
            MyApplication.instance!!.addToRequestQueue(stringRequest)
        }

        fun del(api: String, volleyHandler: VolleyHandler) {
            val stringRequest = object : StringRequest(Method.DELETE, server(api), Response.Listener { response ->
                volleyHandler.onSuccess(response)
            },
            Response.ErrorListener { error ->
                volleyHandler.onError(error.toString())
            }) {}
            MyApplication.instance!!.addToRequestQueue(stringRequest)
        }

        /**
         *  Request Api`s
         */

        var API_LIST_POST = "posts"
        var API_SINGLE_POST = "posts/" //id
        var API_CREATE_POST = "posts"
        var API_UPDATE_POST = "posts/" //id
        var API_DELETE_POST = "posts/" //id

        /**
         *  Request Param`s
         */

        fun paramsEmpty(): HashMap<String, String> {
            return HashMap<String, String>()
        }

        fun paramsCreate(user: User): HashMap<String, Any> {
            val params = HashMap<String, Any>()
            params["title"] = user.title
            params["body"] = user.body
            params["userId"] = user.userId
            return params
        }

        fun paramsUpdate(user: User): HashMap<String, Any> {
            val params = HashMap<String, Any>()
            params["id"] = user.id
            params["title"] = user.title
            params["body"] = user.body
            params["userId"] = user.userId
            return params
        }
    }
}