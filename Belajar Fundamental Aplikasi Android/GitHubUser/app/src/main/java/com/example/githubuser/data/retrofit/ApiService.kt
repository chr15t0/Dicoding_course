package com.example.githubuser.data.retrofit

import com.example.githubuser.data.response.GitHubResponse
import com.example.githubuser.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") query: String
    ):Call<List<ItemsItem>>
}