package com.example.responseparsinghomework.model

import java.io.Serializable

data class Employee(
    val employee_age: String,
    val employee_name: String,
    val employee_salary: String,
    val id: Int,
    val profile_image: String
): Serializable {
    override fun toString(): String {
        return "Employee{ " + "id = " + id + ", name = " + employee_name + ", salary = " + employee_salary +
                ", age = " + employee_age + ", image = " + profile_image
    }
}