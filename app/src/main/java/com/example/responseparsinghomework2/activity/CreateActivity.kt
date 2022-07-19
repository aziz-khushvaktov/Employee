package com.example.responseparsinghomework2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.responseparsinghomework2.databinding.ActivityCreateBinding
import com.example.responseparsinghomework2.model.User

class CreateActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCreateBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            bSave.setOnClickListener { backToMain() }
        }
    }

    private fun backToMain() {
        val intent = Intent()
        binding.apply {
            val user = User(etBody.text.toString(),1,etTitle.text.toString(),etUserId.text.toString().toInt())
            intent.putExtra("NewUser",user)
            setResult(RESULT_OK,intent)
        }
        finish()
    }
}