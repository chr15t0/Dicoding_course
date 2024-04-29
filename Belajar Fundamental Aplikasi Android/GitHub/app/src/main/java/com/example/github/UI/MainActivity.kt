package com.example.github.UI

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.R
import com.example.github.darkmode.Darkmode
import com.example.github.darkmode.SettingPreferences
import com.example.github.darkmode.dataStore
import com.example.github.data.response.ItemsItem
import com.example.github.databinding.ActivityMainBinding
import com.example.github.helper.DarkModeViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val  perf = SettingPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this,DarkModeViewModelFactory(perf)).get(MainViewModel::class.java)

        mainViewModel.getThemeSettings().observe(this){isLightModeActive:Boolean->
            if(isLightModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }

        mainViewModel.listUser.observe(this){user->
            setUserData(user)
        }

        val layoutManager =LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this,layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }
        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener{textView,actionId,event->
                searchBar.setText(searchView.text)
                searchView.hide()
                mainViewModel.setSearchUsers(searchView.text.toString())
                false
            }
        }

        binding.topAppBar.setOnMenuItemClickListener{menuItem->
            when(menuItem.itemId){
                R.id.favorite_menu->{
                    val intentFav = Intent(this@MainActivity,FavoriteActivity::class.java)
                    startActivity(intentFav)
                    true
                }
                R.id.dark_mode->{
                    val intentDark = Intent(this@MainActivity, Darkmode::class.java)
                    startActivity(intentDark)
                    true
                }
                R.id.share_menu->{
                    val intentShare=Intent(Intent.ACTION_SEND)
                    intentShare.type = "text/plain"
                    intentShare.putExtra(Intent.EXTRA_TEXT,"Sharing from MyApp")
                    startActivity(Intent.createChooser(intentShare,"Share Via"))
                    true
                }
                else->false
            }
        }

    }

    private fun setUserData(user : List<ItemsItem>){
        val adapter = userAdapter()
        adapter.submitList(user)
        binding.rvUsers.adapter = adapter
        adapter.setOnItemClickCallback(object : userAdapter.OnItemClickCallback{
            override fun onItemClicked(user: ItemsItem) {
                val detailUser = Intent(this@MainActivity,DetailUserActivity::class.java)
                detailUser.putExtra(DetailUserActivity.EXTRA_USERNAME,user.login)
                detailUser.putExtra(DetailUserActivity.EXTRA_AVATARURL,user.avatarUrl)
                startActivity(detailUser)
            }

        })
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}