package com.example.artspace.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("gallery")
data class Artwork(
    @PrimaryKey val id:Int,
    @ColumnInfo(name="title") val title:String,
    @ColumnInfo(name="artist") val artist:String,
    @ColumnInfo(name="year") val year:Int,
    @ColumnInfo(name="url") val url:String
 )
