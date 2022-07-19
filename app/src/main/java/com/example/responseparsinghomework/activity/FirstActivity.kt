package com.example.responseparsinghomework.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.responseparsinghomework.R
import com.example.responseparsinghomework.adapter.EmployeeAdapter
import com.example.responseparsinghomework.databinding.ActivityFirstBinding
import com.example.responseparsinghomework.helper.SwipeHelper
import com.example.responseparsinghomework.model.Emp
import com.example.responseparsinghomework.model.Employee
import com.example.responseparsinghomework.model.Posts
import com.example.responseparsinghomework.network.retrofit.RetrofitHttp
import com.example.responseparsinghomework.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

@Suppress("NAME_SHADOWING")
class FirstActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFirstBinding.inflate(layoutInflater) }
    private val employeeAdapter by lazy { EmployeeAdapter() }

    private var employees: ArrayList<Employee> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }


    private fun initViews() {
        binding.apply {
            rvEmployee.adapter = employeeAdapter
            rvEmployee.layoutManager = GridLayoutManager(this@FirstActivity, 1)

            getAllEmployees()

            bFloat.setOnClickListener { callCreateEmployeeActivity() }

            swipeRecycler()
        }
    }

    private fun swipeRecycler() {
        object : SwipeHelper(this,binding.rvEmployee,false) {
            override fun instantiateUnderlayButton(viewHolder: RecyclerView.ViewHolder?, underlayButtons: MutableList<UnderlayButton>?) {
                // Delete
                underlayButtons?.add(UnderlayButton("Delete",AppCompatResources.getDrawable(this@FirstActivity,
                    R.drawable.ic_baseline_delete_24),resources.getColor(R.color.red), Color.parseColor("#FF3700B3")) { pos: Int ->
                    Toast.makeText(this@FirstActivity, "Deleted click at $pos", Toast.LENGTH_SHORT).show()
                    deleteEmployee(employees[pos])
                    employees.clear()
                })
                // Update
                underlayButtons?.add(UnderlayButton("Update", AppCompatResources.getDrawable(this@FirstActivity, R.drawable.ic_baseline_cloud_upload_24),
                    resources.getColor(R.color.green), Color.parseColor("#FF3700B3")

                ) { pos: Int ->
                    callUpdateActivity(employees[pos])

                })
            }
        }
    }

    private fun callUpdateActivity(employee: Employee) {
        val intent = Intent(this,UpdateActivity::class.java)
        intent.putExtra("updateEmployee",employee)
        updateLauncher.launch(intent)

    }

    var updateLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            // Load all post
            val employee = data!!.getSerializableExtra("editableEmployee")
            updateEmployee(employee as Employee)
            Logger.d("thisOne",employee.toString())
            Toast.makeText(this, employee.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun getAllEmployees() {
        RetrofitHttp.employeeService.getAllEmployees()
            .enqueue(object : Callback<Emp> {
                override fun onResponse(call: Call<Emp>, response: Response<Emp>) {
                    employees = response.body()!!.data
                    try {
                        employeeAdapter.submitData(employees)
                    }catch (e:Exception) {
                        e.printStackTrace()
                    }
                    Logger.d("RetrofitHttp",response.body().toString())
                }
                override fun onFailure(call: Call<Emp>, t: Throwable) {

                }
            })
    }

    private fun createNewEmployee(posts: Posts) {
        RetrofitHttp.employeeService.createNewEmployee(posts).enqueue(object : Callback<Posts> {
            override fun onResponse(call: Call<Posts>, response: Response<Posts>) {
                Toast.makeText(this@FirstActivity, "Employee successfully added!", Toast.LENGTH_SHORT).show()
                Logger.d("NewPost","Successfully")
                getAllEmployees()
            }

            override fun onFailure(call: Call<Posts>, t: Throwable) {

            }
        })
    }
    val postLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val posts = data!!.getSerializableExtra("NewPost")
            Logger.d("NewPost",posts.toString())
            createNewEmployee(posts as Posts)
        }
    }

    private fun updateEmployee(employee: Employee) {
        RetrofitHttp.employeeService.updateEmployee(employee.id,employee).enqueue(object : Callback<Employee> {
            override fun onResponse(call: Call<Employee>, response: Response<Employee>) {
                Toast.makeText(this@FirstActivity, "Successfully updated!", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Employee>, t: Throwable) {
                Toast.makeText(this@FirstActivity, "Updating failed!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun deleteEmployee(employee: Employee) {
        RetrofitHttp.employeeService.deleteEmployee(employee.id).enqueue(object : Callback<Employee> {
            override fun onResponse(call: Call<Employee>, response: Response<Employee>) {
                getAllEmployees()
                Toast.makeText(this@FirstActivity, "Successfully deleted!", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Employee>, t: Throwable) {
                Toast.makeText(this@FirstActivity, "Deleting failed!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun callCreateEmployeeActivity() {
        val intent = Intent(this,CreateEmployeeActivity::class.java)
        postLauncher.launch(intent)
    }
}