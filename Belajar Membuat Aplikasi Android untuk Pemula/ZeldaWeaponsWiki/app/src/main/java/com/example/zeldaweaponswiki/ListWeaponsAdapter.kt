package com.example.zeldaweaponswiki

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListWeaponsAdapter (private val listWeapons:ArrayList<weapons>):RecyclerView.Adapter<ListWeaponsAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val weaponPhoto:ImageView = itemView.findViewById(R.id.item_weapon_photo)
        val weaponName:TextView = itemView.findViewById(R.id.item_weapon_name)
        val weaponDesc:TextView = itemView.findViewById(R.id.item_weapon_desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_weapons,parent,false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listWeapons.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name,desc,photo,location, power) = listWeapons[position]
        Glide.with(holder.itemView.context)
            .load(photo) // URL Gambar
            .into(holder.weaponPhoto)
        holder.weaponName.text=name
        holder.weaponDesc.text=desc
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listWeapons[holder.adapterPosition]) }


    }
    interface OnItemClickCallback {
        fun onItemClicked(data: weapons)
    }
}