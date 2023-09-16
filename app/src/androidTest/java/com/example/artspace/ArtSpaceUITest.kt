package com.example.artspace

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.artspace.model.Artwork
import com.example.compose.ArtSpaceTheme
import org.junit.Rule
import org.junit.Test

class ArtSpaceUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun press_next() {
        val initialArtwork = Artwork(
            id = 1,
            title = "one",
            artist = "entropia",
            year = 2023,
            url = ""
        )
        val nextArtwork = Artwork(
            id = 2,
            title = "two",
            artist = "entropia",
            year = 2033,
            url = ""
        )
        val gallery = Gallery(listOf(initialArtwork, nextArtwork))
        composeTestRule.setContent {

            ArtSpaceTheme() {
                ArtSpaceApp(gallery = gallery)
            }


        }
        composeTestRule.onNodeWithText(initialArtwork.title).assertExists("Text does not exist")
        composeTestRule.onNodeWithText("${initialArtwork.artist} (${initialArtwork.year})")
            .assertExists("Text does not exist")
        composeTestRule.onNodeWithText("Next").performClick()
        composeTestRule.onNodeWithText(nextArtwork.title).assertExists("Text does not exist")
        composeTestRule.onNodeWithText("${nextArtwork.artist} (${nextArtwork.year})")
            .assertExists("Text does not exist")
        composeTestRule.onNodeWithText("Next").performClick()
        composeTestRule.onNodeWithText(initialArtwork.title).assertExists("Text does not exist")
        composeTestRule.onNodeWithText("${initialArtwork.artist} (${initialArtwork.year})")
            .assertExists("Text does not exist")
    }

    @Test
    fun press_previous() {
        val artwork1 = Artwork(
            id = 1,
            title = "one",
            artist = "entropia",
            year = 2023,
            url = ""
        )
        val artwork2 = Artwork(
            id = 2,
            title = "two",
            artist = "entropia",
            year = 2033,
            url = ""
        )

        val artwork3 = Artwork(
            id = 3,
            title = "three",
            artist = "entropia",
            year = 2056,
            url = ""
        )
        val gallery = Gallery(listOf(artwork1, artwork2, artwork3))
        composeTestRule.setContent {

            ArtSpaceTheme() {
                ArtSpaceApp(gallery = gallery)
            }


        }
        composeTestRule.onNodeWithText(artwork1.title).assertExists("Text does not exist")
        composeTestRule.onNodeWithText("${artwork1.artist} (${artwork1.year})")
            .assertExists("Text does not exist")
        composeTestRule.onNodeWithText("Previous").performClick()
        composeTestRule.onNodeWithText(artwork3.title).assertExists("Text does not exist")
        composeTestRule.onNodeWithText("${artwork3.artist} (${artwork3.year})")
            .assertExists("Text does not exist")
        composeTestRule.onNodeWithText("Previous").performClick()
        composeTestRule.onNodeWithText(artwork2.title).assertExists("Text does not exist")
        composeTestRule.onNodeWithText("${artwork2.artist} (${artwork2.year})")
            .assertExists("Text does not exist")
    }
}