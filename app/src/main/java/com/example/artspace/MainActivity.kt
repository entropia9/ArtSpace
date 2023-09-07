package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val galleryDb = ArtworkDatabase.getInstance(this)

        val galleryList = galleryDb.artworkDao().getAll()
        val gallery = Gallery(galleryList)
        setContent {
            ArtSpaceTheme {
                ArtSpaceApp(galleryList[0], gallery = gallery)
            }

        }
    }
}

@Composable
fun ArtSpaceApp(initialArtwork: Artwork, gallery: Gallery) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        var imageUrl by remember {
            mutableStateOf(initialArtwork.url)
        }
        var title by remember {
            mutableStateOf(initialArtwork.title)
        }
        var artist by remember {
            mutableStateOf(initialArtwork.artist)
        }
        var year by remember {
            mutableStateOf(initialArtwork.year.toString())
        }
        ArtworkDisplay(
            imageUrl,
            modifier = Modifier.weight(0.6f)
        )
        ArtworkData(
            title = title,
            artist = artist,
            year = year,
            Modifier
                .weight(0.18f)
                .padding(bottom = 20.dp)
        )
        ButtonsRow(nextFunction = {
            val nextArtwork = gallery.getNextArtworkData()
            imageUrl = nextArtwork.url
            title = nextArtwork.title
            artist = nextArtwork.artist
            year = nextArtwork.year.toString()
        },
        previousFunction = {
            val previousArtwork = gallery.getPreviousArtworkData()
            imageUrl = previousArtwork.url
            title = previousArtwork.title
            artist = previousArtwork.artist
            year = previousArtwork.year.toString()

        }

        )
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ArtworkDisplay(image: String, modifier: Modifier = Modifier) {
    Surface(
        color = MaterialTheme.colorScheme.secondary,
        modifier = modifier.wrapContentSize(),
        shadowElevation = 20.dp
    ) {
        GlideImage(
            model = image,
            contentDescription = "placeholder",
            modifier = modifier
                .padding(30.dp)
                .size(250.dp),
            transition = CrossFade
        )
    }
}

@Composable
fun ArtworkData(title: String, artist: String, year: String, modifier: Modifier = Modifier) {

    Surface(
        color = MaterialTheme.colorScheme.tertiary, modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                text = title,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 10.dp)
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
                .padding(start = 16.dp, end = 8.dp)
        ) {
            Text(text = stringResource(id = R.string.button_previous))
        }
        Button(
            onClick = nextFunction,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp, end = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.button_next))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme {
        //  ArtSpaceApp()
    }
}