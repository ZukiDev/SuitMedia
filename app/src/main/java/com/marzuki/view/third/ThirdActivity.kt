package com.marzuki.view.third

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.marzuki.data.ApiService
import com.marzuki.data.User
import com.marzuki.data.UserResponse
import com.marzuki.suitmedia.R
import com.marzuki.view.adapter.UserAdapter
import com.marzuki.view.second.SecondActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdActivity : AppCompatActivity(), UserAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var userAdapter: UserAdapter

    private var users: MutableList<User> = mutableListOf()
    private var currentPage = 1
    private val perPage = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        recyclerView = findViewById(R.id.recyclerView)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        userAdapter = UserAdapter(users, this)
        recyclerView.adapter = userAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        swipeRefreshLayout.setOnRefreshListener {
            currentPage = 1
            fetchUsers()
        }

        fetchUsers()
    }

    private fun fetchUsers() {
        val apiService = ApiService.create()
        val call = apiService.getUsers(currentPage, perPage)

        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val userList = response.body()?.data ?: emptyList()

                    if (currentPage == 1) {
                        users.clear()
                    }

                    users.addAll(userList)

                    userAdapter.notifyDataSetChanged()

                    swipeRefreshLayout.isRefreshing = false

                    currentPage++
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                swipeRefreshLayout.isRefreshing = false
            }
        })
    }

    override fun onItemClick(user: User) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("selected_user_name", user.first_name+" "+user.last_name)
        startActivity(intent)
    }
}