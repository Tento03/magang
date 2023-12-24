package com.example.suitmedia.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.suitmedia.api.ApiClient
import com.example.suitmedia.api.User
import com.example.suitmedia.api.UserResponse
import com.example.suitmedia.databinding.ActivityThirdBinding
import com.example.suitmedia.user.UserAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdActivity : AppCompatActivity() {
    companion object{
        const val NAMETAG="nameTag"
    }
    private lateinit var binding: ActivityThirdBinding
    private lateinit var userAdapter: UserAdapter
    private var userList = mutableListOf<User>()
    private var currentPage = 1
    private val perPage = 10
    private var isLoading = false
    private var isLastPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter(userList,object :UserAdapter.onClickListener{
            override fun onClickListener(user: User) {
                if (user!=null){
                    Intent(this@ThirdActivity,SecondActivity::class.java).also {
                        it.putExtra(NAMETAG,user.first_name+user.last_name)
                        startActivity(it)
                    }
                }
            }

        })
        binding.recyclerViewUsers.adapter = userAdapter
        binding.recyclerViewUsers.layoutManager = LinearLayoutManager(this)

        binding.swipeRefreshLayout.setOnRefreshListener {
            if (!isLoading) {
                currentPage = 1
                isLastPage = false
                getUserList()
            }
        }

        getUserList()
        setupScrollListener()
    }

    private fun getUserList() {
        if (!isLoading) {
            isLoading = true
            val apiCall = ApiClient.apiService.getUser(currentPage, perPage)
            apiCall.enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if (response.isSuccessful) {
                        val data = response.body()?.data
                        if (data != null) {
                            if (currentPage == 1) {
                                userList.clear()
                            }
                            userList.addAll(data)
                            userAdapter.notifyDataSetChanged()
                            binding.swipeRefreshLayout.isRefreshing = false

                            if (data.isNullOrEmpty()) {
                                isLastPage = true
                            }
                        }
                    }
                    isLoading = false
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Toast.makeText(this@ThirdActivity, "Gagal nampilkan data", Toast.LENGTH_SHORT).show()
                    binding.swipeRefreshLayout.isRefreshing = false
                    isLoading = false
                }
            })
        }
    }

    private fun setupScrollListener() {
        val layoutManager = binding.recyclerViewUsers.layoutManager as LinearLayoutManager
        binding.recyclerViewUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!binding.swipeRefreshLayout.isRefreshing) {
                    if (!isLoading && !isLastPage && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                    ) {
                        currentPage++
                        getUserList()
                    }
                }
            }
        })
    }
}
