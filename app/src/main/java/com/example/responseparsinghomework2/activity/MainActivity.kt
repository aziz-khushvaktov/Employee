package com.example.responseparsinghomework2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.responseparsinghomework2.activity.retrofit.RetrofitActivity
import com.example.responseparsinghomework2.activity.volley.VolleyActivity
import com.example.responseparsinghomework2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            bRetrofitActivity.setOnClickListener { callRetrofitActivity() }

            bVolleyActivity.setOnClickListener { callVolleyActivity() }
        }
    }

    private fun callVolleyActivity() {
        val intent = Intent(this,VolleyActivity::class.java)
        startActivity(intent)
    }

    private fun callRetrofitActivity() {
        val intent = Intent(this,RetrofitActivity::class.java)
        startActivity(intent)
    }
}