package com.example.lab9_gianfranco_traverso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab9_gianfranco_traverso.Configurations.API_KEY
import com.example.lab9_gianfranco_traverso.Configurations.BASE_URL
import com.example.lab9_gianfranco_traverso.Networking.ApiService
import com.example.lab9_gianfranco_traverso.Networking.GifService
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GifActivity : AppCompatActivity() {

    var gifList = ArrayList<Gif>()
    lateinit var recycler_View : RecyclerView
    lateinit var service: ApiService
    lateinit var search: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gif)

        service = GifService.buildService(ApiService::class.java)

        search = intent.getStringExtra("String")
        if (search == ""){
            getRandomPost()
        } else {
            getSearchPosts()
        }

        recycler_View = findViewById<RecyclerView>(R.id.recyclerView2)
        recycler_View.layoutManager = LinearLayoutManager(this)

        val adapter = GifAdapter(gifList)
        recycler_View.adapter = adapter
    }

    fun getRandomPost(){
        var post: Gif? = null
        service.getRandomGif(API_KEY).enqueue(object: Callback<Gif> {
            override fun onResponse(call: Call<Gif>?, response: Response<Gif>?) {
                post = response?.body()
                post?.let { gifList.add(it) }
                recycler_View.adapter?.notifyItemInserted(gifList.size - 1)
                Toast.makeText(applicationContext, post?.data?.embed_url, Toast.LENGTH_LONG).show()
            }
            override fun onFailure(call: Call<Gif>?, t: Throwable?) {
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
                        val post = Gif(data = i)
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
}
