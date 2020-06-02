package com.example.lab9_gianfranco_traverso.Networking

import com.example.lab9_gianfranco_traverso.utils.CategoriesList
import com.example.lab9_gianfranco_traverso.utils.GIF
import com.example.lab9_gianfranco_traverso.utils.GifList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("random")
    fun getRandomGif(@Header("api_key") api_key: String?,
                     @Query("rating") rating: String?
                     ): Call<GIF>

    @GET("search")
    fun SearchGif(@Header("api_key") api_key: String?,
                  @Query("q") q: String?
    ):  Call<GifList>

    @GET("categories")
    fun getCategories(@Header("api_key") api_key: String?):  Call<CategoriesList>
}