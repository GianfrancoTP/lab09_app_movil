package com.example.lab9_gianfranco_traverso

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.lab9_gianfranco_traverso.Configurations.API_KEY
import com.example.lab9_gianfranco_traverso.Networking.ApiService
import com.example.lab9_gianfranco_traverso.Networking.GifService
import com.example.lab9_gianfranco_traverso.adapters.GifAdapter
import com.example.lab9_gianfranco_traverso.adapters.OnGifItemClickListener
import com.example.lab9_gianfranco_traverso.model.Database
import com.example.lab9_gianfranco_traverso.model.Gif
import com.example.lab9_gianfranco_traverso.model.GifDao
import com.example.lab9_gianfranco_traverso.utils.GIF
import com.example.lab9_gianfranco_traverso.utils.GifList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GifActivity : AppCompatActivity(),
    OnGifItemClickListener {

    var gifList = ArrayList<GIF>()
    lateinit var recycler_View : RecyclerView
    lateinit var service: ApiService
    lateinit var search: String
    lateinit var database: GifDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gif)

        database = Room.databaseBuilder(this, Database::class.java,"gif").build().gifDao()
        service = GifService.buildService(ApiService::class.java)

        search = intent.getStringExtra("String")
        if (search == ""){
            getRandomPost()
        } else {
            getSearchPosts()
        }

        recycler_View = findViewById<RecyclerView>(R.id.recyclerView2)
        recycler_View.layoutManager = LinearLayoutManager(this)

        val adapter = GifAdapter(
            gifList,
            this
        )
        recycler_View.adapter = adapter
    }

    fun getRandomPost(){
        var post: GIF? = null
        service.getRandomGif(API_KEY, "pg").enqueue(object: Callback<GIF> {
            override fun onResponse(call: Call<GIF>?, response: Response<GIF>?) {
                post = response?.body()
                post?.let { gifList.add(it) }
                recycler_View.adapter?.notifyItemInserted(gifList.size - 1)
            }
            override fun onFailure(call: Call<GIF>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })
    }

    fun getSearchPosts(){
        service.SearchGif(API_KEY, search).enqueue(object: Callback<GifList> {
            override fun onResponse(call: Call<GifList>?, response: Response<GifList>?) {
                val posts = response?.body()
                if (posts != null) {
                    val len = posts.data.size-1
                    for (i in posts.data){
                        val post =
                            GIF(data = i)
                        post?.let { gifList.add(it) }
                        recycler_View.adapter?.notifyItemInserted(gifList.size - 1)
                    }
                }
            }

            override fun onFailure(call: Call<GifList>, t: Throwable) {
                Toast.makeText(applicationContext, "${t.message}", Toast.LENGTH_SHORT).show()
                t?.printStackTrace()
            }
        })
    }

    override fun onItemClick(item: GIF, position: Int) {
        Toast.makeText(applicationContext, "Gif añadido a favoritos", Toast.LENGTH_LONG).show()
        val gif = Gif(
            item.data.id,
            item.data.images.downsized_large.url,
            item.data.title
        )
        AsyncTask.execute{
            database.insert(gif)
        }

    }
}
