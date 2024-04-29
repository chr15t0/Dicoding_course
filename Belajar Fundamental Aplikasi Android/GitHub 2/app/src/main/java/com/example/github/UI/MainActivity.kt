package com.example.github.UI

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.R
import com.example.github.data.response.ItemsItem
import com.example.github.databinding.ActivityMainBinding
import com.google.android.material.search.SearchBar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

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


    }

    private fun setUserData(user : List<ItemsItem>){
        val adapter = userAdapter()
        adapter.submitList(user)
        binding.rvUsers.adapter = adapter
        adapter.setOnItemClickCallback(object : userAdapter.OnItemClickCallback{
            override fun onItemClicked(user: ItemsItem) {
                val detailUser = Intent(this@MainActivity,DetailUserActivity::class.java)
                detailUser.putExtra(DetailUserActivity.EXTRA_USERNAME,user.login)
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