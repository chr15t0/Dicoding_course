package com.example.github.UI

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github.data.response.ItemsItem
import com.example.github.databinding.ItemsUsersBinding

class userAdapter: ListAdapter<ItemsItem,userAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    class MyViewHolder (val binding :ItemsUsersBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind (user:ItemsItem){
            binding.tvItemUser.text = "${user.login}"
            Glide.with(binding.imgItemUser.context)
                .load(user.avatarUrl)
                .into(binding.imgItemUser)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemsUsersBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(user)
        }
    }

    companion object{
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<ItemsItem>(){
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: ItemsItem)
    }
}