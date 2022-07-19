package com.example.responseparsinghomework.model

import java.io.Serializable

data class Post(
    val age: String,
    val id: Int,
    val name: String,
    val salary: String
): Serializable {
    override fun toString(): String {
        return "Post{ " + "id = " + id + ", age = " + age +
                ", name = " + name + ", salary = " + salary + " }"
    }
}