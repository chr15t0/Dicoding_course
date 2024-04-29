package com.example.github.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.github.database.Favorite
import com.example.github.database.FavoriteDao
import com.example.github.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository (application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorites() : LiveData<List<Favorite>> = mFavoriteDao.getAllFavorites()


    fun insert(favorite: Favorite){
        executorService.execute{mFavoriteDao.insert(favorite)}
    }

    fun delete(favorite: Favorite){
        executorService.execute{mFavoriteDao.delete(favorite)}
    }

}