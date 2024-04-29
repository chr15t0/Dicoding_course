package com.example.github.UI

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github.data.response.GitHubResponse
import com.example.github.data.response.ItemsItem
import com.example.github.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel:ViewModel() {
    private val _listFollower = MutableLiveData<List<ItemsItem>>()
    val listFollower : LiveData<List<ItemsItem>> = _listFollower

    companion object{
        private const val TAG = "FollowersViewModel"
    }

    fun setlistFollower(username:String){
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object :Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful){
                    _listFollower.value=response.body()
                }else{
                    Log.d(TAG,"Failure ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.d(TAG,"Failure ${t.message}")
            }

        })
    }
}