package com.example.responseparsinghomework.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.responseparsinghomework.databinding.ActivityUpdateBinding
import com.example.responseparsinghomework.model.Emp
import com.example.responseparsinghomework.model.Employee

class UpdateActivity : AppCompatActivity() {

    private val binding by lazy { ActivityUpdateBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            val employee = intent.getSerializableExtra("updateEmployee")
            employee as Employee
            etId.setText(employee.id.toString())
            etName.setText(employee.employee_name)
            etSalary.setText(employee.employee_salary)
            etAge.setText(employee.employee_age)


            bUpdate.setOnClickListener { backToFinish() }
        }
    }

    private fun backToFinish() {
        val backEmployee: Employee
        binding.apply {
            backEmployee = Employee(etAge.text.toString(),etName.text.toString(),etSalary.text.toString(),etId.text.toString().toInt(),"")
        }
        val intent = Intent()
        intent.putExtra("editableEmployee",backEmployee)
        setResult(RESULT_OK,intent)
        finish()
    }
}