package com.example.zeldaweaponswiki

import android.media.Image
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class weapons_detail : AppCompatActivity() {
    companion object {
        const val EXTRA_WEAPON = "extra_weapon"}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weapons_detail)
        val photo:ImageView = findViewById(R.id.item_weapon_photo)
        val name:TextView = findViewById(R.id.item_weapon_name)
        val desc:TextView = findViewById(R.id.item_weapon_desc)
        val location:TextView = findViewById(R.id.item_weapon_location)
        val power:TextView = findViewById(R.id.item_weapon_power)
        val weapon = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<weapons>(EXTRA_WEAPON, weapons::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<weapons>(EXTRA_WEAPON)
        }
        if (weapon != null) {
            name.text = weapon.name
            Glide
                .with(this@weapons_detail)
                .load(weapon.photo)
                .centerCrop()
                .into(photo)
            desc.text=weapon.description
            location.text=weapon.location
            power.text=weapon.power
        }
    }
}