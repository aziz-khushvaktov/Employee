package com.example.responseparsinghomework2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.responseparsinghomework2.databinding.ActivityUpdateBinding
import com.example.responseparsinghomework2.model.User
import com.example.responseparsinghomework2.utils.Logger

class UpdateActivity : AppCompatActivity() {

    private val binding by lazy { ActivityUpdateBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        val user = intent.getSerializableExtra("updateUser")
        user as User
        Logger.d("lolInfo",user.toString())
        binding.apply {
            etBody.setText(user.body)
            etTitle.setText(user.title)
            etUserId.setText(user.userId.toString())

            bUpdate.setOnClickListener { backToMain(user) }
        }
    }

    private fun backToMain(user: User) {
        binding.apply {
            val user2 = User(etBody.text.toString(),user.id,etTitle.text.toString(),etUserId.text.toString().toInt())

            val intent = Intent()
            intent.putExtra("returnUser",user2)
            setResult(RESULT_OK,intent)
            finish()
        }

    }
}