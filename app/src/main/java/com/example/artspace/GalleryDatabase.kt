package com.example.artspace

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Artwork::class], version = 1)
abstract class GalleryDatabase : RoomDatabase() {
    abstract fun artworkDao(): ArtworkDao

    companion object {

        fun getInstance(context: Context): GalleryDatabase {

            return Room.databaseBuilder(
                context,
                GalleryDatabase::class.java,
                "gallery_database"
            )
                .createFromAsset("database/gallery.db")
                .allowMainThreadQueries()
                .build()
        }

    }

}