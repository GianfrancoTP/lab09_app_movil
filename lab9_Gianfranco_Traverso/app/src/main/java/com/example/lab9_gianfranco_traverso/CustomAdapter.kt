package com.example.lab9_gianfranco_traverso

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(val caList: ArrayList<Categories>, var clickListener: OnItemClickListener) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initialize(caList.get(position), clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return caList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView = itemView.findViewById<TextView>(R.id.text)

        fun initialize(item: Categories, action:OnItemClickListener){
            textView.text = item.name

            itemView.setOnClickListener {
                action.onItemClick(item, adapterPosition)
            }
        }
    }
}

interface OnItemClickListener{
    fun onItemClick(item: Categories, position: Int)
}