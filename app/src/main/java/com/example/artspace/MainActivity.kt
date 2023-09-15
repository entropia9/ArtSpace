package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.artspace.database.ArtworkDatabase
import com.example.compose.ArtSpaceTheme


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
            modifier = Modifier.align(Alignment.CenterHorizontally),
            contentDescription = title
        )
        ArtworkData(
            title = title,
            artist = artist,
            year = year,
            Modifier
                .align(Alignment.Start)
                .padding(bottom = 50.dp)
        )
        ButtonsRow(
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.padding_large))
                .align(Alignment.End),
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
                .size(433.dp)
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
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                text = title,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 10.dp),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(text = buildAnnotatedString {
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
            Text(text = stringResource(id = R.string.button_previous))
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
            Text(text = stringResource(id = R.string.button_next))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .verticalScroll(rememberScrollState())
    ) {
        val imageUrl by remember {
            mutableStateOf("")
        }
        val title by remember {
            mutableStateOf("test")
        }
        val artist by remember {
            mutableStateOf("test")
        }
        val year by remember {
            mutableIntStateOf(2)
            mutableIntStateOf(2)
        }
        ArtworkDisplay(
            imageUrl,
            modifier = Modifier.align(Alignment.Start)
        )
        ArtworkData(
            title = title,
            artist = artist,
            year = year.toString(),
            Modifier
                .align(Alignment.Start)
                .padding(bottom = 50.dp)
        )
        ButtonsRow(
            Modifier.padding(bottom = 20.dp),
            nextFunction = {

            },
            previousFunction = {

            }

        )
    }
}

