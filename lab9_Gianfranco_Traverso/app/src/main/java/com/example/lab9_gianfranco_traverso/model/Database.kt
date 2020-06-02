package com.example.lab9_gianfranco_traverso.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Gif::class],version = 1)
abstract class Database: RoomDatabase(){
    abstract fun gifDao(): GifDao
}