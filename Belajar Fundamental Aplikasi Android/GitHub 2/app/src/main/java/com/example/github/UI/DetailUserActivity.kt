package com.example.github.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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
import com.example.github.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_USERNAME="extra_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
    }
    private lateinit var binding : ActivityDetailUserBinding
    private val detailUserViewModel: DetailUserViewModel  by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME,username)
//        detailUserViewModel = ViewModelProvider(this).get(detailUserViewModel::class.java)

        showLoading(true)
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

        val sectionsPagerAdapter = SectionsPagerAdapter(this,bundle)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs,viewPager){tab,position->
            tab.text =resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation=0f
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}