package com.example.responseparsinghomework2.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.responseparsinghomework2.databinding.ItemUsersListBinding
import com.example.responseparsinghomework2.model.User

class UserAdapter(): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var users: ArrayList<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemUsersListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind(users[position])
    }

    override fun getItemCount(): Int = users.size

    fun submitUsers(user: ArrayList<User>) {
        users.addAll(user)
    }

    class UserViewHolder(var binding: ItemUsersListBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(user: User) {
            binding.apply {
                tvId.text = "Id: " + user.id.toString()
                tvTitle.text ="Title: " + user.title
                tvBody.text = "Body: " + user.body
                tvUserId.text = "userId: " + user.userId.toString()
            }
        }
    }
}