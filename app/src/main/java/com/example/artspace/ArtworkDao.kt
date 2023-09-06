package com.example.artspace

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ArtworkDao{
    @Query("SELECT * FROM artwork")
    fun getAll():List<Artwork>

    @Query("SELECT * FROM artwork ORDER BY year")
    fun getAllByYear():List<Artwork>
}