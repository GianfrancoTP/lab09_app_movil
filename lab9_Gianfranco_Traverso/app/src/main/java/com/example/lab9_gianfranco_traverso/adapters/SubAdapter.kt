package com.example.lab9_gianfranco_traverso.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab9_gianfranco_traverso.R

class SubAdapter(val gifList: ArrayList<String>) : RecyclerView.Adapter<SubAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val string : String = gifList[position]
        holder.textView.text = string
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.sub_layout, parent, false)
        return ViewHolder(
            v
        )
    }

    override fun getItemCount(): Int {
        return gifList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView = itemView.findViewById<TextView>(R.id.text3)
    }
}