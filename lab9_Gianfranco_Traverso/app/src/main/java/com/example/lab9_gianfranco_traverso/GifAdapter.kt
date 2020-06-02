package com.example.lab9_gianfranco_traverso

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab9_gianfranco_traverso.model.Gif
import com.example.lab9_gianfranco_traverso.utils.GIF
import com.example.lab9_gianfranco_traverso.utils.loadGif

class GifAdapter(val GIFList: ArrayList<GIF>) : RecyclerView.Adapter<GifAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val GIF : GIF = GIFList[position]
        holder.textView.text = GIF.data.title
        holder.imageview.loadGif(GIF.data.image_url)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.gif_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return GIFList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView = itemView.findViewById<TextView>(R.id.checkBox)
        val imageview = itemView.findViewById(R.id.imageView2) as ImageView
    }
}