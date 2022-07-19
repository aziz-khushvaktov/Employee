package com.example.responseparsinghomework.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.responseparsinghomework.databinding.ItemEmployeeListBinding
import com.example.responseparsinghomework.model.Employee

class EmployeeAdapter :
    RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    private val employees: ArrayList<Employee> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        return EmployeeViewHolder(ItemEmployeeListBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.onBind(employees[position])
    }

    override fun getItemCount(): Int = employees.size

    class EmployeeViewHolder(var binding: ItemEmployeeListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(employee: Employee) {
            binding.apply {
                tvId.text = employee.id.toString()
                tvName.text = employee.employee_name
                tvSalary.text = employee.employee_salary
                tvAge.text = employee.employee_age
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(list: ArrayList<Employee>) {
        employees.addAll(list)
        notifyDataSetChanged()
    }
}