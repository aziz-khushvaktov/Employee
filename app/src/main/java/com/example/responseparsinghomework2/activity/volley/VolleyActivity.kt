package com.example.responseparsinghomework2.activity.volley

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.responseparsinghomework2.R
import com.example.responseparsinghomework2.activity.CreateActivity
import com.example.responseparsinghomework2.activity.UpdateActivity
import com.example.responseparsinghomework2.adapter.UserAdapter
import com.example.responseparsinghomework2.databinding.ActivityVolleyBinding
import com.example.responseparsinghomework2.helper.SwipeHelper
import com.example.responseparsinghomework2.model.User
import com.example.responseparsinghomework2.networking.volley.VolleyHandler
import com.example.responseparsinghomework2.networking.volley.VolleyHttp
import com.example.responseparsinghomework2.utils.Logger
import com.google.gson.Gson

class VolleyActivity : AppCompatActivity() {

    private val binding by lazy { ActivityVolleyBinding.inflate(layoutInflater) }
    private val userAdapter by lazy { UserAdapter() }
    var users: ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            bFloat.setOnClickListener { callCreateActivity() }
        }
        getAllPosts()
        swipeRecycler()
    }

    @SuppressLint("NotifyDataSetChanged")
    val userLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val user = data!!.getSerializableExtra("NewUser")
                createUser(user as User)
                userAdapter.notifyDataSetChanged()
                Logger.d("comingUser",user.toString())
            }
        }

    val updateLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val data: Intent? = result.data
        val user = data!!.getSerializableExtra("returnUser")
        Logger.d("updateUser",user.toString())
        updateUser(user as User)
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

    private fun swipeRecycler() {
        object : SwipeHelper(this,binding.rvVolley,false) {
            override fun instantiateUnderlayButton(viewHolder: RecyclerView.ViewHolder?, underlayButtons: MutableList<UnderlayButton>?) {
                // Delete
                underlayButtons?.add(UnderlayButton("Delete", AppCompatResources.getDrawable(this@VolleyActivity,
                    R.drawable.ic_baseline_delete_24),resources.getColor(R.color.red), Color.parseColor("#FF3700B3")) { pos: Int ->
                    Toast.makeText(this@VolleyActivity, "Deleted click at $pos", Toast.LENGTH_SHORT).show()
                    deleteUser(users[pos])
                    users.clear()
                })
                // Update
                underlayButtons?.add(UnderlayButton("Update", AppCompatResources.getDrawable(this@VolleyActivity, R.drawable.ic_baseline_cloud_upload_24),
                    resources.getColor(R.color.green), Color.parseColor("#FF3700B3")

                ) { pos: Int ->
                    callUpdateActivity(users[pos])

                })
            }
        }
    }

    private fun getAllPosts() {
        VolleyHttp.get(VolleyHttp.API_LIST_POST,VolleyHttp.paramsEmpty(),object : VolleyHandler {
            override fun onSuccess(response: String?) {
                val userArray = Gson().fromJson(response,Array<User>::class.java)
                users.addAll(userArray)
                userAdapter.submitUsers(users)
                binding.rvVolley.adapter = userAdapter

                Log.d("volley", userArray.toString())
            }
            override fun onError(error: String?) {
                Log.d("volley", error.toString())
            }
        })
    }

    private fun createUser(user: User) {
        VolleyHttp.post(VolleyHttp.API_CREATE_POST,VolleyHttp.paramsCreate(user),object : VolleyHandler {
            override fun onSuccess(response: String?) {
                Logger.d("NewUser",response.toString())
                Toast.makeText(this@VolleyActivity, "User saved successfully!", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: String?) {
                Toast.makeText(this@VolleyActivity, "Saving user failed!", Toast.LENGTH_SHORT).show()
                Logger.d("NewUser",error.toString())
            }

        })
    }

    private fun updateUser(user: User) {
        VolleyHttp.put(VolleyHttp.API_UPDATE_POST + user.id,VolleyHttp.paramsUpdate(user), object : VolleyHandler {
            override fun onSuccess(response: String?) {
                Toast.makeText(this@VolleyActivity, "User updated successfully!", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: String?) {
                Toast.makeText(this@VolleyActivity, "Updating user failed!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun deleteUser(user: User) {
        VolleyHttp.del(VolleyHttp.API_DELETE_POST + user.id, object : VolleyHandler {
            @SuppressLint("NotifyDataSetChanged")
            override fun onSuccess(response: String?) {
                val us = user
                Logger.d("deletingUser",us.toString())
                userAdapter.notifyDataSetChanged()
                Toast.makeText(this@VolleyActivity, "User deleted successfully!", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: String?) {
                Toast.makeText(this@VolleyActivity, "Deleting user failed!", Toast.LENGTH_SHORT).show()
            }

        })
    }

}