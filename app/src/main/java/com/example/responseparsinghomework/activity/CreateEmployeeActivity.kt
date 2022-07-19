package com.example.responseparsinghomework.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.responseparsinghomework.databinding.ActivityCreateEmployeeBinding
import com.example.responseparsinghomework.model.Post
import com.example.responseparsinghomework.model.Posts

class CreateEmployeeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCreateEmployeeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            bSave.setOnClickListener { backToFinish() }
        }
    }

    private fun backToFinish() {
        binding.apply {
            val post = Post(etAge.text.toString(),etId.text.toString().toInt(),etName.text.toString(),etSalary.text.toString())
            var posts = Posts(post,"successful")
            val intent = Intent()
            intent.putExtra("NewPost",posts)
            setResult(RESULT_OK,intent)
            finish()
        }
    }
}