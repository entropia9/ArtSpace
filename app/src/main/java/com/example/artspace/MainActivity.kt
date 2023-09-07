package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.compose.ArtSpaceTheme

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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
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
            modifier = Modifier.weight(0.7f)
        )
        ArtworkData(
            title = title,
            artist = artist,
            year = year,
            Modifier
                .weight(0.3f)
                .padding(bottom = 50.dp)
        )
        ButtonsRow(
            modifier = Modifier.padding(bottom = 20.dp),
            nextFunction = {
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
        shape = RoundedCornerShape(5),
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
        color = MaterialTheme.colorScheme.inversePrimary, modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
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

@Preview(showBackground = true)
@Composable
fun Preview() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)
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
            modifier = Modifier.weight(0.7f)
        )
        ArtworkData(
            title = title,
            artist = artist,
            year = year.toString(),
            Modifier
                .weight(0.3f)
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

