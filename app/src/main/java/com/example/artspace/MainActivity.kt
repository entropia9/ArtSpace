package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.artspace.database.ArtworkDatabase
import com.example.artspace.ui.ArtSpaceApp
import com.example.artspace.ui.theme.ArtSpaceTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val galleryDb = ArtworkDatabase.getInstance(this)

        val galleryList = galleryDb.artworkDao().getAll()
        val gallery = Gallery(galleryList)
        setContent {
            ArtSpaceTheme {
                ArtSpaceApp(gallery = gallery)
            }

        }
    }
}


