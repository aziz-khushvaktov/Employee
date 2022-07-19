package com.example.responseparsinghomework.network.volley

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.responseparsinghomework.MyApplication
import com.example.responseparsinghomework.model.Employee
import com.example.responseparsinghomework.utils.Logger
import org.json.JSONObject

class VolleyHttp {

    companion object {
        val TAG = VolleyHttp::class.java.simpleName
        val IS_TESTER = true
        val SERVER_DEVELOPMENT = "https://dummy.restapiexample.com/api/v1/"
        val SERVER_PRODUCTION =  "https://dummy.restapiexample.com/api/v1/"

        fun server(url: String): String {
            if (IS_TESTER) {
                return SERVER_DEVELOPMENT + url
            }
            return SERVER_PRODUCTION + url
        }

        fun headers(): HashMap<String, String> {
            val headers = HashMap<String, String>()
            headers["Content-type"] = "application/json; charset=UTF-8"
            return headers
        }

        /**
         *  Request Method`s
         */

        fun get(api: String, params: HashMap<String, String>, volleyHandler: VolleyHandler) {
            val stringRequest = object : StringRequest(Method.GET, server(api), Response.Listener { response ->
                Logger.d(TAG, response)
                volleyHandler.onSuccess(response)
            },
                Response.ErrorListener { error ->
                    Logger.d(TAG, error.toString())
                    volleyHandler.onError(error.toString())
                }) {
                override fun getParams(): MutableMap<String, String> {
                    return params
                }
            }
            MyApplication.instance!!.addToRequestQueue(stringRequest)
        }

        fun post(api: String, body: HashMap<String, Any>, volleyHandler: VolleyHandler) {
            val stringRequest = object : StringRequest(Method.POST, server(api),
                Response.Listener { response ->
                    Logger.d(TAG, response)
                    volleyHandler.onSuccess(response)
                },
                Response.ErrorListener { error ->
                    Logger.d(TAG, error.toString())
                    volleyHandler.onError(error.toString())
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    return headers()
                }

                override fun getBody(): ByteArray {
                    return JSONObject(body as Map<*, *>).toString().toByteArray()
                }
            }
            MyApplication.instance!!.addToRequestQueue(stringRequest)
        }

        fun put(api: String, body: HashMap<String, Any>, volleyHandler: VolleyHandler) {
            val stringRequest = object : StringRequest(Method.PUT, server(api),
                Response.Listener { response ->
                    Logger.d(TAG, response)
                    volleyHandler.onSuccess(response)
                },
                Response.ErrorListener { error ->
                    Logger.d(TAG, error.toString())
                    volleyHandler.onError(error.toString())
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    return headers()
                }

                override fun getBody(): ByteArray {
                    return JSONObject(body as Map<*, *>).toString().toByteArray()
                }
            }
            MyApplication.instance!!.addToRequestQueue(stringRequest)
        }

        fun del(url: String, volleyHandler: VolleyHandler) {
            val stringRequest = object : StringRequest(Method.DELETE, server(url),
                Response.Listener { response ->
                    Logger.d(TAG, response)
                    volleyHandler.onSuccess(response)
                },
                Response.ErrorListener { error ->
                    Logger.d(TAG, error.toString())
                    volleyHandler.onError(error.toString())
                }) {
            }
            MyApplication.instance!!.addToRequestQueue(stringRequest)
        }

        /**
         *  Request Api`s
         */

        var API_LIST_EMPLOYEES = "employees"
        var API_SINGLE_EMPLOYEE  = "employee/" // id
        var API_UPDATE_EMPLOYEE = "update/" // id
        var API_DELETE_EMPLOYEE = "delete/" // id
        var API_POST_EMPLOYEE = "create"


        fun paramsEmpty(): HashMap<String, String> {
            return HashMap<String, String>()
        }

        fun paramsCreate(employee: Employee): HashMap<String, Any> {
            val params = HashMap<String, Any>()
            params["name"] = employee.employee_name
            params["salary"] = employee.employee_salary
            params["age"] = employee.employee_age
            params["id"] = employee.id
            return params
        }

        fun paramsUpdate(employee: Employee): HashMap<String, String> {
            val params = HashMap<String, String>()
            params["name"] = employee.employee_name
            params["salary"] = employee.employee_salary
            params["age"] = employee.employee_age
            params["id"] = employee.id.toString()
            return params
        }

    }
}