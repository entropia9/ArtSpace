package com.example.artspace

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ArtworkDao{
    @Query("SELECT * FROM gallery")
    fun getAll():List<Artwork>

    @Query("SELECT * FROM gallery ORDER BY year")
    fun getAllByYear():List<Artwork>
}