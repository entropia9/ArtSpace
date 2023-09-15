package com.example.artspace.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.artspace.model.Artwork


@Database(entities = [Artwork::class], version = 2)
abstract class ArtworkDatabase : RoomDatabase() {
    abstract fun artworkDao(): ArtworkDao

    companion object {

        fun getInstance(context: Context): ArtworkDatabase {

            return Room.databaseBuilder(
                context,
                ArtworkDatabase::class.java,
                "gallery_database"
            )
                .createFromAsset("database/gallery.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }

    }

}