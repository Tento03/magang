package com.example.suitmedia.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getUser(
        @Query("page")page:Int,
        @Query("per_page")per_page:Int
    ): Call<UserResponse>
}