package com.example.artspace.database

import androidx.room.Dao
import androidx.room.Query
import com.example.artspace.model.Artwork

@Dao
interface ArtworkDao{
    @Query("SELECT * FROM gallery")
    fun getAll():List<Artwork>

    @Query("SELECT * FROM gallery ORDER BY year")
    fun getAllByYear():List<Artwork>
}