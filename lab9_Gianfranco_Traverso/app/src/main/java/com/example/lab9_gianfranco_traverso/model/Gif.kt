package com.example.lab9_gianfranco_traverso.model

import androidx.room.*
import com.example.lab9_gianfranco_traverso.model.Gif.Companion.TABLE_NAME


@Dao
interface GifDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Gif)

    @Query("SELECT * FROM ${TABLE_NAME}")
    fun getAllGifs(): List<Gif>

    @Delete
    fun deleteWord(Gif: Gif?)

    @Query("SELECT * FROM ${TABLE_NAME} WHERE title LIKE :string")
    fun search(string: String): List<Gif>

}
@Entity(tableName = TABLE_NAME)
data class Gif(
    @PrimaryKey
    @ColumnInfo(name = ID)
    val id: String,
    @ColumnInfo(name = URL)
    val url: String,
    @ColumnInfo(name = TITLE)
    val title: String
) {
    companion object {
        const val TITLE = "title"
        const val TABLE_NAME = "gif"
        const val ID = "id"
        const val URL = "url"
    }
}