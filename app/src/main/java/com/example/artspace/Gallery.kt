package com.example.artspace

import com.example.artspace.model.Artwork

class Gallery(
    private val galleryList: List<Artwork>,
    private var index: Int = 0
) {

    fun getCurrentArtworkData(): Artwork {
        return if (index in galleryList.indices) {
            galleryList[index]
        } else galleryList[0]
    }

    fun getNextArtworkData(): Artwork {
        return if (index == galleryList.size - 1) {
            index = 0
            galleryList.first()
        } else {
            index++
            galleryList[index]
        }
    }

    fun getPreviousArtworkData(): Artwork {
        return if (index == 0) {
            index = galleryList.size - 1
            galleryList.last()
        } else {
            index--
            galleryList[index]
        }
    }
}