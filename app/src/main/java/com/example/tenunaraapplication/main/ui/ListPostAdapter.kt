package com.example.tenunaraapplication.main.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tenunaraapplication.R

class ListPostAdapter(private val listPostExplore: ArrayList<PostExplore>) :RecyclerView.Adapter<ListPostAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.iv_explore)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_username)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_explore, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, photo) = listPostExplore[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvUsername.text = name
    }

    override fun getItemCount(): Int = listPostExplore.size

}