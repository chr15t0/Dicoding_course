package com.example.zeldaweaponswiki

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class weapons(
    val name:String,
    val description:String,
    val photo:String,
    val location:String,
    val power:String):Parcelable
