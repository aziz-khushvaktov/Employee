package com.example.responseparsinghomework.activity.volley

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.responseparsinghomework.R
import com.example.responseparsinghomework.activity.UpdateActivity
import com.example.responseparsinghomework.adapter.EmployeeAdapter
import com.example.responseparsinghomework.databinding.ActivitySecondBinding
import com.example.responseparsinghomework.helper.SwipeHelper
import com.example.responseparsinghomework.model.Emp
import com.example.responseparsinghomework.model.Employee
import com.example.responseparsinghomework.network.volley.VolleyHandler
import com.example.responseparsinghomework.network.volley.VolleyHttp
import com.example.responseparsinghomework.utils.Logger
import com.google.gson.Gson

class SecondActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySecondBinding.inflate(layoutInflater) }
    private val employeeAdapter by lazy { EmployeeAdapter() }
    private var employees: ArrayList<Employee> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            rvVolley.layoutManager = GridLayoutManager(this@SecondActivity,1)
            getAllEmployees()
            swipeRecycler()
        }
    }

    private fun getAllEmployees() {
        VolleyHttp.get(VolleyHttp.API_LIST_EMPLOYEES,VolleyHttp.paramsEmpty(), object : VolleyHandler {
            override fun onSuccess(response: String?) {
                val emp = Gson().fromJson(response,Emp::class.java)
                employees.addAll(emp.data)
                employeeAdapter.submitData(employees)
                binding.rvVolley.adapter = employeeAdapter
                Logger.d("noOne",response.toString())
            }

            override fun onError(error: String?) {
                Logger.d("noOne",error.toString())
            }

        })
    }

    private fun swipeRecycler() {
        object : SwipeHelper(this,binding.rvVolley,false) {
            override fun instantiateUnderlayButton(viewHolder: RecyclerView.ViewHolder?, underlayButtons: MutableList<UnderlayButton>?) {
                // Delete
                underlayButtons?.add(UnderlayButton("Delete", AppCompatResources.getDrawable(this@SecondActivity,
                    R.drawable.ic_baseline_delete_24),resources.getColor(R.color.red), Color.parseColor("#FF3700B3")) { pos: Int ->
                    Toast.makeText(this@SecondActivity, "Deleted click at $pos", Toast.LENGTH_SHORT).show()
                    deleteEmployee(employees[pos])
                    employees.clear()
                })
                // Update
                underlayButtons?.add(UnderlayButton("Update", AppCompatResources.getDrawable(this@SecondActivity, R.drawable.ic_baseline_cloud_upload_24),
                    resources.getColor(R.color.green), Color.parseColor("#FF3700B3")

                ) { pos: Int ->
                    callUpdateActivity(employees[pos])
                })
            }
        }
    }

    private fun deleteEmployee(employee: Employee) {
        VolleyHttp.del(VolleyHttp.API_DELETE_EMPLOYEE + employee.id,object :VolleyHandler {
            override fun onSuccess(response: String?) {
                Toast.makeText(this@SecondActivity, "Successfully deleted!", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: String?) {
                Toast.makeText(this@SecondActivity, "Deleting failed!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun callUpdateActivity(employee: Employee) {
        val intent = Intent(this, UpdateActivity::class.java)
        intent.putExtra("updateEmployee",employee)
        updateLauncher.launch(intent)
    }
    var updateLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            // Load all post
            val employee = data!!.getSerializableExtra("editableEmployee")
            updateEmployee(employee as Employee)
            Logger.d("thisOne",employee.toString())
            Toast.makeText(this, employee.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateEmployee(employee: Employee) {
        VolleyHttp.get(VolleyHttp.API_UPDATE_EMPLOYEE + employee.id,VolleyHttp.paramsUpdate(employee), object : VolleyHandler {
            override fun onSuccess(response: String?) {
                Toast.makeText(this@SecondActivity, "Successfully updated!", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: String?) {
                Toast.makeText(this@SecondActivity, "Updating failed!", Toast.LENGTH_SHORT).show()
            }

        })
    }
}