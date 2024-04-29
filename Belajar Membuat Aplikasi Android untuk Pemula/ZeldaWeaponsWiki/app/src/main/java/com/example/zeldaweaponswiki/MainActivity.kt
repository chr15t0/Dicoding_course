package com.example.zeldaweaponswiki

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvWeapons : RecyclerView
    private val list = ArrayList<weapons>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {  }
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvWeapons = findViewById(R.id.rv_weapons)
        rvWeapons.setHasFixedSize(true)
        list.addAll(getListWeapons())
        showRecyclerList()


    }

    private fun getListWeapons():ArrayList<weapons>{
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDesc = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.getStringArray(R.array.data_photo)
        val dataLoc = resources.getStringArray(R.array.data_location)
        val dataPower = resources.getStringArray(R.array.data_power)
        val listWeapons = ArrayList<weapons>()
        for (i in dataName.indices){
            val weapon = weapons(dataName[i],dataDesc[i],dataPhoto[i],dataLoc[i],dataPower[i])
            listWeapons.add(weapon)
        }
        return listWeapons
    }

    private fun showRecyclerList(){
        rvWeapons.layoutManager = LinearLayoutManager(this)
        val listweaponsadapter =  ListWeaponsAdapter(list)
        rvWeapons.adapter = listweaponsadapter
        listweaponsadapter.setOnItemClickCallback(object : ListWeaponsAdapter.OnItemClickCallback{
            override fun onItemClicked(data: weapons) {
                val detailsIntent = Intent(this@MainActivity,weapons_detail::class.java)
                detailsIntent.putExtra(weapons_detail.EXTRA_WEAPON,data)
                startActivity(detailsIntent)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_list->{
                rvWeapons.layoutManager=LinearLayoutManager(this)
            }
            R.id.action_grid->{
                rvWeapons.layoutManager=GridLayoutManager(this,2)
            }
            R.id.action_about->{
                val aboutIntent = Intent(this@MainActivity,about_page::class.java)
                startActivity(aboutIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}