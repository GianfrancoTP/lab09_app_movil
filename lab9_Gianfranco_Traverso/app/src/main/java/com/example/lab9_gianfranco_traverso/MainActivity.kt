package com.example.lab9_gianfranco_traverso

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab9_gianfranco_traverso.Configurations.API_KEY
import com.example.lab9_gianfranco_traverso.Networking.ApiService
import com.example.lab9_gianfranco_traverso.Networking.GifService
import com.example.lab9_gianfranco_traverso.adapters.CustomAdapter
import com.example.lab9_gianfranco_traverso.adapters.OnItemClickListener
import com.example.lab9_gianfranco_traverso.utils.Categories
import com.example.lab9_gianfranco_traverso.utils.CategoriesList
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(),
    OnItemClickListener {

    val categoriesList = ArrayList<Categories>()
    lateinit var service: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        service = GifService.buildService(ApiService::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter =
            CustomAdapter(
                categoriesList,
                this
            )
        recyclerView.adapter = adapter

        Categories()

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Name, email address
            val name = user.displayName
            val email = user.email


            val username = findViewById<TextView>(R.id.username)
            emailText.text = email.toString()
            username.text = name.toString()
        }
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
                        categoriesList.add(
                            Categories(
                                name = i.name,
                                subcategories = i.subcategories
                            )
                        )
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

    fun Favoritos(view: View){
        val intent = Intent( this, Favoritos()::class.java)
        this.startActivity(intent)
    }

    fun onLogout(view: View) {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent( this, LoginActivity::class.java)
        this.startActivity(intent)
        Toast.makeText(
            applicationContext, "Logout successful.",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun openMap(view: View){
        val intent = Intent( this, MapsActivity::class.java)
        this.startActivity(intent)
    }
}
