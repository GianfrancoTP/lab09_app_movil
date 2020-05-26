package com.example.lab9_gianfranco_traverso.utils

class Gif(
    val data: Data
)

class Data(
    val id: String,
    val title: String,
    val image_url: String,
    val name: String
)

class GifList(
    val data: List<Data>
)

class Subcategories(
    val name: String
)

class Categories (
    val name: String,
    val subcategories: List<Subcategories>
)

class CategoriesList(
    val data: List<Categories>
)