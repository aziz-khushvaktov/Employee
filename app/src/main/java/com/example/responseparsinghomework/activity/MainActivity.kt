package com.example.responseparsinghomework.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.responseparsinghomework.R
import com.example.responseparsinghomework.activity.volley.SecondActivity
import com.example.responseparsinghomework.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            bFirstActivity.setOnClickListener { callFirstActivity() }

            bSecondActivity.setOnClickListener { callSecondActivity() }
        }
    }

    private fun callSecondActivity() {
        val intent = Intent(this,SecondActivity::class.java)
        startActivity(intent)
    }

    private fun callFirstActivity() {
        val intent = Intent(this,FirstActivity::class.java)
        startActivity(intent)
    }
}