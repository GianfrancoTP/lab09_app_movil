package com.example.lab9_gianfranco_traverso.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab9_gianfranco_traverso.R
import com.example.lab9_gianfranco_traverso.model.Gif
import com.example.lab9_gianfranco_traverso.utils.loadGif


class FavAdapter(val GIFList:List<Gif>, var clickListener: OnCategoryItemClickListener) : RecyclerView.Adapter<FavAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initialize(GIFList[position], clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fav_layout, parent, false)
        return ViewHolder(
            v
        )
    }

    override fun getItemCount(): Int {
        return GIFList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView = itemView.findViewById<TextView>(R.id.TitleText)
        val imageview = itemView.findViewById(R.id.gif_image) as ImageView

        fun initialize(item: Gif, action: OnCategoryItemClickListener){
            textView.text = item.title
            imageview.loadGif(item.url)

            itemView.setOnClickListener {
                action.onItemClick(item, adapterPosition)
            }
        }
    }
}

interface OnCategoryItemClickListener{
    fun onItemClick(item: Gif, position: Int)
}