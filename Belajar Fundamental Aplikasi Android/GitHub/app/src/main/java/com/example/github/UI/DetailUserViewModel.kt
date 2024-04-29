package com.example.github.UI

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github.Repository.FavoriteRepository
import com.example.github.data.response.DetailUserResponse
import com.example.github.data.response.ItemsItem
import com.example.github.data.retrofit.ApiConfig
import com.example.github.database.Favorite
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel (application: Application):ViewModel() {
    private val _user =MutableLiveData<DetailUserResponse>()
    val user : LiveData<DetailUserResponse> = _user

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    companion object{
        private const val TAG = "DetailUserViewModel"
    }

    fun setDetailUser(username:String){
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object :Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful){
                    _user.value = response.body()
                }else{
                    Log.d(TAG,"Failure")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.d(TAG,"Failure")
            }

        })
    }

    fun insert(favorite:Favorite){
        mFavoriteRepository.insert(favorite)
    }


    fun delete(favorite: Favorite){
        mFavoriteRepository.delete(favorite)
    }

    fun getAllFavorites() : LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorites()

}