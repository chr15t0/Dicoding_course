package com.example.github.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity (tableName = "favorite_database")
@Parcelize
data class Favorite (
    @PrimaryKey(autoGenerate = false)
    var username: String = "",
    var avatarUrl: String? = null,
):Parcelable{

}