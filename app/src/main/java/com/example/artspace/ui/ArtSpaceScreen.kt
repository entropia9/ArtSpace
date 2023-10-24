package com.example.artspace.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.artspace.Gallery
import com.example.artspace.R
import com.example.artspace.model.Artwork

@Composable
fun ArtSpaceApp(gallery: Gallery, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        var currentArtwork by remember {
            mutableStateOf(gallery.getCurrentArtworkData())
        }
        var imageUrl = currentArtwork.url

        var title = currentArtwork.title

        var artist = currentArtwork.artist

        var year = currentArtwork.year.toString()

        ArtworkDisplay(
            imageUrl,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .weight(5f),
            contentDescription = title
        )
        ArtworkData(
            title = title,
            artist = artist,
            year = year,
            Modifier
                .align(Alignment.Start)
                .padding(bottom = 30.dp)
                .weight(2f)
        )
        ButtonsRow(
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.padding_large))
                .align(Alignment.End)
                .weight(1f),
            nextFunction = {
                currentArtwork = gallery.getNextArtworkData()
                imageUrl = currentArtwork.url
                title = currentArtwork.title
                artist = currentArtwork.artist
                year = currentArtwork.year.toString()
            },
            previousFunction = {
                currentArtwork = gallery.getPreviousArtworkData()
                imageUrl = currentArtwork.url
                title = currentArtwork.title
                artist = currentArtwork.artist
                year = currentArtwork.year.toString()

            }

        )
    }
}

// Displays artwork from url using Glide

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ArtworkDisplay(
    image: String,
    modifier: Modifier = Modifier,
    contentDescription: String = stringResource(
        R.string.placeholder
    )
) {
    Surface(
        color = MaterialTheme.colorScheme.secondary,
        modifier = modifier
            .wrapContentSize()
            .padding(dimensionResource(id = R.dimen.padding_large)),
        shape = RoundedCornerShape(5),
        shadowElevation = 20.dp
    ) {
        GlideImage(
            model = image,
            contentDescription = contentDescription,
            modifier = Modifier
                .size(432.dp)
                .padding(dimensionResource(id = R.dimen.padding_large)),
            transition = CrossFade
        )
    }
}

// Displays title, artist and year
@Composable
fun ArtworkData(title: String, artist: String, year: String, modifier: Modifier = Modifier) {

    Surface(
        color = MaterialTheme.colorScheme.inversePrimary, modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_large)),
        shape = RoundedCornerShape(5)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(style = MaterialTheme.typography.bodyLarge,
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(artist)
                    }
                    append(" ($year)")
                })

        }
    }

}

// Row of buttons next and previous, functions are passed as lambdas
@Composable
fun ButtonsRow(
    modifier: Modifier = Modifier,
    nextFunction: () -> Unit = {},
    previousFunction: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {
        Button(
            onClick = previousFunction,
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = dimensionResource(id = R.dimen.padding_medium),
                    end = dimensionResource(id = R.dimen.padding_small)
                )
        ) {
            Text(
                text = stringResource(id = R.string.button_previous),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Button(
            onClick = nextFunction,
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = dimensionResource(id = R.dimen.padding_small),
                    end = dimensionResource(id = R.dimen.padding_medium)
                )
        ) {
            Text(
                text = stringResource(id = R.string.button_next),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true,
    device = Devices.TABLET)
@Composable
fun Preview() {
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
    ArtSpaceApp(gallery = gallery)
}
