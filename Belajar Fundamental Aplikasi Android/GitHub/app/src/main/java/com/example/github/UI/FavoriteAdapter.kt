package com.example.github.UI

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github.Repository.FavoriteDiffCallback
import com.example.github.database.Favorite
import com.example.github.databinding.ItemsUsersBinding

class FavoriteAdapter:RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private val listFavorite = ArrayList<Favorite>()
    fun setListFavorite(listFavorite: List<Favorite>) {
        val diffCallback = FavoriteDiffCallback(this.listFavorite, listFavorite)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(listFavorite)
        diffResult.dispatchUpdatesTo(this)
    }

    class FavoriteViewHolder(private val binding: ItemsUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (favorite: Favorite){
            binding.tvItemUser.text = favorite.username
            Glide.with(binding.imgItemUser.context)
                .load(favorite.avatarUrl)
                .into(binding.imgItemUser)
            binding.cardView.setOnClickListener {
                val intent = Intent(it.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USERNAME, favorite.username)
                it.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemsUsersBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

}
