package com.example.artspace

class Gallery(
    private val galleryList: List<Artwork>,
    private var index: Int = 0
) {


    fun getNextArtworkData(): Artwork {
        return if (index == galleryList.count() - 1) {
            galleryList.first()
        } else {
            index++
            galleryList[index]
        }
    }

    fun getPreviousArtworkData(): Artwork {
        return if (index == 0) {
            galleryList.last()
        } else {
            index--
            galleryList[index]
        }
    }
}