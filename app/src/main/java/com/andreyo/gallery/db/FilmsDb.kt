package com.andreyo.gallery.db
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [FilmItem::class], version = 3)
abstract class FilmsDb : RoomDatabase() {

    abstract fun getMovieDao(): FilmDao

    companion object {

        private const val NUMBER_OF_THREADS = 4
        val dbWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        @Volatile
        private var INSTANCE: FilmsDb? = null

        fun getInstance(context: Context): FilmsDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FilmsDb::class.java,
                    "films_db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
