package com.example.github.data.retrofit

import com.example.github.data.response.DetailUserResponse
import com.example.github.data.response.GitHubResponse
import com.example.github.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUser(
        @Query("q") query : String
    ): Call<GitHubResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        //tulis sesuai yang di get untuk pathnya
        @Path("username") username:String
    ):Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        //tulis sesuai yang di get untuk pathnya
        @Path("username") username:String
    ):Call<List<ItemsItem>>
    //karena berupa JSON Array

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        //tulis sesuai yang di get untuk pathnya
        @Path("username") username:String
    ):Call<List<ItemsItem>>
}