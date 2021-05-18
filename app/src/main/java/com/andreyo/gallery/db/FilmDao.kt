package com.andreyo.gallery.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface FilmDao {

    @Query("SELECT * FROM films ORDER BY popularity DESC")
    fun getMovies(): LiveData<List<FilmItem>>

    @Query("SELECT * FROM films WHERE isFavorite = 1")
    fun getFavoriteMovies(): LiveData<List<FilmItem>>

    @Query("UPDATE films SET isFavorite = :isFavorite WHERE id = :filmId")
    fun updateFavoriteStatus(filmId: Int, isFavorite: Boolean)

    @Query("SELECT * FROM films WHERE isFavorite = :filmId LIMIT 1")
    fun getFilmById(filmId: Int): LiveData<FilmItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFilm(film: FilmItem)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFilms(films: List<FilmItem>)
}
