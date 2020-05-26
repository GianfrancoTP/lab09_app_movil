package com.example.lab9_gianfranco_traverso

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab9_gianfranco_traverso.Configurations.API_KEY
import com.example.lab9_gianfranco_traverso.Configurations.BASE_URL
import com.example.lab9_gianfranco_traverso.Networking.ApiService
import com.example.lab9_gianfranco_traverso.Networking.GifService
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), OnItemClickListener {

    val categoriesList = ArrayList<Categories>()
    lateinit var service: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        service = GifService.buildService(ApiService::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = CustomAdapter(categoriesList, this)
        recyclerView.adapter = adapter

        Categories()
    }

    fun Search(view: View){
        val search = editText.text.toString()
        val intent = Intent(view.context, GifActivity::class.java)
        intent.putExtra("String", search)
        view.context.startActivity(intent)
    }

    fun Categories(){
        service.getCategories(API_KEY).enqueue(object: Callback<CategoriesList> {
            override fun onResponse(call: Call<CategoriesList>?, response: Response<CategoriesList>?) {
                val posts = response?.body()
                if (posts != null) {
                    val len = posts.data.size-1
                    for (i in posts.data){
                        categoriesList.add(Categories(name = i.name, subcategories = i.subcategories))
                        recyclerView.adapter?.notifyItemInserted(categoriesList.size - 1)
                    }
                }
            }

            override fun onFailure(call: Call<CategoriesList>, t: Throwable) {
                Toast.makeText(applicationContext, "${t.message}", Toast.LENGTH_SHORT).show()
                t?.printStackTrace()
            }
        })
    }

    override fun onItemClick(item: Categories, position: Int) {
        val intent = Intent( this, SubCategory::class.java)
        val names = ArrayList<String>()
        for (i in item.subcategories){
            names.add(i.name)
        }
        intent.putStringArrayListExtra("list", names)
        this.startActivity(intent)
    }
}
