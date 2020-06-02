package com.example.lab9_gianfranco_traverso

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab9_gianfranco_traverso.adapters.SubAdapter

class SubCategory : AppCompatActivity() {

    lateinit var recycler_View : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)
        var names = intent.getStringArrayListExtra("list")

        recycler_View = findViewById<RecyclerView>(R.id.recyclerView3)
        recycler_View.layoutManager = LinearLayoutManager(this)

        val adapter =
            SubAdapter(names)
        recycler_View.adapter = adapter
    }

    fun click(view: View){
        val search = view.findViewById<TextView>(R.id.text3).text
        val intent = Intent(view.context, GifActivity::class.java)
        intent.putExtra("String", search)
        view.context.startActivity(intent)
    }

}
