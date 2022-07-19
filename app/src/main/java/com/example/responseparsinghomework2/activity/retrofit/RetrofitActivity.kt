package com.example.responseparsinghomework2.activity.retrofit

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.responseparsinghomework2.R
import com.example.responseparsinghomework2.activity.CreateActivity
import com.example.responseparsinghomework2.activity.UpdateActivity
import com.example.responseparsinghomework2.adapter.UserAdapter
import com.example.responseparsinghomework2.databinding.ActivityRetrofitBinding
import com.example.responseparsinghomework2.helper.SwipeHelper
import com.example.responseparsinghomework2.model.User
import com.example.responseparsinghomework2.networking.retrofit.RetrofitHttp
import com.example.responseparsinghomework2.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitActivity : AppCompatActivity() {

    private val binding by lazy { ActivityRetrofitBinding.inflate(layoutInflater) }
    private val userAdapter by lazy { UserAdapter() }
    val users: ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            bFloat.setOnClickListener { callCreateActivity() }
        }
        getAllUsers()
        swipeRecycler()

    }


    val userLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val user = data!!.getSerializableExtra("NewUser")
            createUser(user as User)
            Logger.d("createUser",user.toString())
        }
    }

    val updateLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val user = data!!.getSerializableExtra("returnUser")
            updateUser(user as User)
            Logger.d("updateUser",user.toString())
        }
    }

    private fun callCreateActivity() {
        val intent = Intent(this,CreateActivity::class.java)
        userLauncher.launch(intent)
    }

    private fun callUpdateActivity(user: User) {
        val intent = Intent(this,UpdateActivity::class.java)
        intent.putExtra("updateUser",user)
        updateLauncher.launch(intent)
    }

    private fun getAllUsers() {
        RetrofitHttp.userService.getAllUsers().enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>,
            ) {
                users.addAll(response.body()!!)
                userAdapter.submitUsers(users)
                binding.rvRetrofit.adapter = userAdapter
                Log.d("oneoneone", response.body().toString())
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d("oneoneone", "Data didn't come!")
            }
        })
    }

    private fun createUser(user: User) {
        RetrofitHttp.userService.createUser(user).enqueue(object : Callback<User> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<User>, response: Response<User>) {
                userAdapter.notifyDataSetChanged()
                Toast.makeText(this@RetrofitActivity, "User saved successfully!", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@RetrofitActivity, "Saving user failed!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun updateUser(user: User) {
        RetrofitHttp.userService.updateUser(user.id,user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Toast.makeText(this@RetrofitActivity, "User updated successfully!", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@RetrofitActivity, "Updating user failed!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun deleteUser(user: User) {
        RetrofitHttp.userService.deleteUser(user.id).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Toast.makeText(this@RetrofitActivity, "User deleted successfully!", Toast.LENGTH_SHORT).show()
                userAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@RetrofitActivity, "Deleting user failed!", Toast.LENGTH_SHORT).show()
            }

        })
    }


    private fun swipeRecycler() {
        object : SwipeHelper(this,binding.rvRetrofit,false) {
            override fun instantiateUnderlayButton(viewHolder: RecyclerView.ViewHolder?, underlayButtons: MutableList<UnderlayButton>?) {
                // Delete
                underlayButtons?.add(UnderlayButton("Delete", AppCompatResources.getDrawable(this@RetrofitActivity,
                    R.drawable.ic_baseline_delete_24),resources.getColor(R.color.red), Color.parseColor("#FF3700B3")) { pos: Int ->
                    Toast.makeText(this@RetrofitActivity, "Deleted click at $pos", Toast.LENGTH_SHORT).show()
                    deleteUser(users[pos])
                    users.clear()
                })
                // Update
                underlayButtons?.add(UnderlayButton("Update", AppCompatResources.getDrawable(this@RetrofitActivity, R.drawable.ic_baseline_cloud_upload_24),
                    resources.getColor(R.color.green), Color.parseColor("#FF3700B3")

                ) { pos: Int ->
                    callUpdateActivity(users[pos])

                })
            }
        }
    }


}