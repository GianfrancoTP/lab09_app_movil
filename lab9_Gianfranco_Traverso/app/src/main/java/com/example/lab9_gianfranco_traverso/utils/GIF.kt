package com.example.lab9_gianfranco_traverso.utils

class GIF(
    val data: Data
)

class Large(
    val url: String
)

class Images(
    val downsized_large: Large
)

class Data(
    val id: String,
    val title: String,
    val images: Images,
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