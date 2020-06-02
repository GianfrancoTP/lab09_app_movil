package com.example.lab9_gianfranco_traverso

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.lab9_gianfranco_traverso.adapters.FavAdapter
import com.example.lab9_gianfranco_traverso.adapters.OnCategoryItemClickListener
import com.example.lab9_gianfranco_traverso.model.Database
import com.example.lab9_gianfranco_traverso.model.Gif
import com.example.lab9_gianfranco_traverso.model.GifDao
import kotlinx.android.synthetic.main.activity_favoritos.*

class Favoritos : AppCompatActivity(),
    OnCategoryItemClickListener {

    lateinit var database: GifDao
    lateinit var recycler_View : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)
        database = Room.databaseBuilder(applicationContext, Database::class.java,"gif").build().gifDao()

        recycler_View = findViewById<RecyclerView>(R.id.recyclerViewFav)
        recycler_View.layoutManager = LinearLayoutManager(this)

        AsyncTask.execute() {
            val adapter =
                FavAdapter(
                    database.getAllGifs(),
                    this
                )
            recycler_View.adapter = adapter
        }
    }

    override fun onItemClick(item: Gif, position: Int) {
        AsyncTask.execute(){
            val list = database.getAllGifs().toTypedArray()
            database.deleteWord(item)
            val adapter =
                FavAdapter(
                    database.getAllGifs(),
                    this
                )
            runOnUiThread {
                recycler_View.adapter = adapter
            }
        }
    }

    fun search(view: View){
        val word = searchFavorite.text.toString()
        Toast.makeText(applicationContext, word, Toast.LENGTH_LONG).show()
        AsyncTask.execute(){
            val adapter =
                FavAdapter(
                    database.search(word), this
                )
            runOnUiThread {
                adapter.notifyDataSetChanged()
                recycler_View.adapter = adapter
            }
        }
    }
}
