package com.example.lab9_gianfranco_traverso.Networking

import com.example.lab9_gianfranco_traverso.CategoriesList
import com.example.lab9_gianfranco_traverso.Gif
import com.example.lab9_gianfranco_traverso.GifList
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("random")
    fun getRandomGif(@Header("api_key") api_key: String?): Call<Gif>

    @GET("search")
    fun SearchGif(@Header("api_key") api_key: String?,
                  @Query("q") q: String?
    ):  Call<GifList>

    @GET("categories")
    fun getCategories(@Header("api_key") api_key: String?):  Call<CategoriesList>
}