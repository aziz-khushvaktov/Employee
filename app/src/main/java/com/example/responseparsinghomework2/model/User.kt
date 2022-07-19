package com.example.responseparsinghomework2.model

import java.io.Serializable

data class User(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
): Serializable {
    override fun toString(): String {
        return "User { " + "body = " + body + ", id = " + id + ", title = " +
                title + ", userId = " + userId + " }"
    }
}