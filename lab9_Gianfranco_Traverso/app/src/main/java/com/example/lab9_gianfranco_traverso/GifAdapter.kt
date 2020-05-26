package com.example.lab9_gianfranco_traverso

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson

class GifAdapter(val gifList: ArrayList<Gif>) : RecyclerView.Adapter<GifAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gif : Gif = gifList[position]
        holder.textView.text = gif.data.title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.gif_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return gifList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView = itemView.findViewById<TextView>(R.id.text2)
        val imageview = itemView.findViewById(R.id.imageView2) as ImageView
    }
}