package com.example.finalprojectsekolahbeta1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieFavorite::class] , exportSchema = true , version = 2)
abstract class MovieFavoriteDatabase : RoomDatabase() {
    abstract val dao : MovieFavoriteDao

    companion object{
        @Volatile
        private var INSTANCE : MovieFavoriteDatabase? = null

        fun getInstance(context: Context) : MovieFavoriteDatabase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieFavoriteDatabase::class.java,
                        "favorite_movies_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }
}