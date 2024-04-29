package com.example.github.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.github.R
import com.example.github.database.Favorite
import com.example.github.databinding.ActivityDetailUserBinding
import com.example.github.helper.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_USERNAME="extra_user"
        const val EXTRA_AVATARURL="extra_avatarurl"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
    }

    private var buttonclicked :Boolean = false
    private var favorite: Favorite? = null

    private lateinit var binding : ActivityDetailUserBinding
    private lateinit var detailUserViewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailUserViewModel = obtainViewModel(this@DetailUserActivity)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATARURL)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME,username)


        if (username != null) {
            detailUserViewModel.setDetailUser(username)
        }
        detailUserViewModel.user.observe(this) {
            if (it != null) {
                binding.tvName.text = it.name
                binding.tvUsername.text = it.login
                binding.tvFollowers.text = "${it.followers} Followers"
                binding.tvFollowing.text = "${it.following} Following"
                Glide.with(this@DetailUserActivity)
                    .load(it.avatarUrl)
                    .centerCrop()
                    .into(binding.ivUser)
                showLoading(false)
            }
        }


        detailUserViewModel.getAllFavorites().observe(this){favoriteList->
            if (favoriteList!=null){
                for (detail in favoriteList){
                    if (detail.username == username.toString()){
                        buttonclicked = true
                        binding.fabFavorite.setImageResource(R.drawable.ic_favorite_fill)
                    }
                }
            }

        }

//        favorite = intent.getParcelableExtra(EXTRA_USERNAME)
        favorite = Favorite(username.toString(),avatarUrl.toString())
        binding.fabFavorite.setOnClickListener{
            if (!buttonclicked){
                buttonclicked = true
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite_fill)
                detailUserViewModel.insert(favorite as Favorite)

            }else{
                buttonclicked = false
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
                detailUserViewModel.delete(favorite as Favorite)
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this,bundle)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs,viewPager){tab,position->
            tab.text =resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation=0f
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity,factory).get(DetailUserViewModel::class.java)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}