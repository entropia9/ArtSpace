package com.example.artspace

class Gallery(
    private val galleryList: List<Artwork>,
    var index: Int = 0
) {


    fun getNextArtworkData(): Artwork {
        return if (index == galleryList.size - 1) {
            index=0
            galleryList.first()
        } else {
            index++
            galleryList[index]
        }
    }

    fun getPreviousArtworkData(): Artwork {
        return if (index == 0) {
            index=galleryList.size-1
            galleryList.last()
        } else {
            index--
            galleryList[index]
        }
    }
}