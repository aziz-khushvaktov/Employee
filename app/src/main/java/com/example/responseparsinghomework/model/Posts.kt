package com.example.responseparsinghomework.model

import java.io.Serializable

data class Posts(
    val data: Post,
    val status: String

): Serializable {
    override fun toString(): String {
        return "Posts{ " + data + ", status = " + status
    }
}