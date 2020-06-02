package com.example.lab9_gianfranco_traverso.model

import com.google.gson.annotations.SerializedName

data class GIFResponse(val data: GIFObject, val meta: MetaObject)

data class GIFObject(val id: String,
                     @SerializedName(value="imageUrl", alternate= ["image_url"])
                     val imageUrl: String)

data class MetaObject( val status: Int)