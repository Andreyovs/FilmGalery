package com.andreyo.gallery.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andreyo.gallery.R

@Entity(tableName = "films")
data class FilmItem(
    @PrimaryKey(autoGenerate = true)
    val adult: Boolean,
    val backdrop_path : String,
    val genre_ids: String,
    val id: Int?,
    val original_language: String,
    val original_title:String,
    val overview:String,
    val popularity:Double,
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
    val release_date: String,
    val title: String?,
    val video: Boolean,
    val description: String?,
    val vote_average: Double,
    val vote_count: Int,
    val poster: Int = R.drawable.ic_image_black,
    var isFavorite: Boolean = false,
    var isChecked: Boolean = false




    )
