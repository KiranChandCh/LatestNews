package com.ckc.latestnews.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.ckc.latestnews.model.NewsItem
import com.ckc.latestnews.ui.theme.JetpackComposeTheme
import com.ckc.latestnews.viewmodel.NewsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GetNewsCategories()
                }
            }
        }
    }
}

@Composable
fun GetNewsCategories(viewModel: NewsViewModel = viewModel()) {
    val newsBySections by viewModel.newsBySections.collectAsState()
    val latestNews = newsBySections
    NewsScreen(newsBySection = latestNews)
}

@Composable
fun NewsScreen(newsBySection: Map<String, List<NewsItem>>) {
    LazyColumn {
        itemsIndexed(newsBySection.entries.toList()) { _, (category, newsItems) ->
            Carousel(title = category, items = newsItems)
        }
    }
}

@Composable
fun Carousel(title: String, items: List<NewsItem>) {
    Column {
        Text(text = title, style = TextStyle(fontSize = 24.sp))
        LazyRow {
            items(items) { newsItem ->
                NewsItem(newsItem)
            }
        }
    }
}

@Composable
fun NewsItem(newsItem: NewsItem) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(200.dp)
            .padding(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Image
            newsItem.og?.let { imageUrl ->
                LoadImageFromUrl(imageUrl = imageUrl, modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f)
                )
            }

            // Text
            newsItem.title?.let { title ->
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.25f) // 20% of the height
                        .padding(4.dp)
                        .align(Alignment.BottomStart)
                )
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun LoadImageFromUrl(imageUrl: String, modifier: Modifier) {
    val imageLoader = ImageLoader(LocalContext.current)
    val request = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .build()
    val painter = rememberImagePainter(
        request = request,
        imageLoader = imageLoader,
    )
    Image(
        painter = painter,
        contentDescription = "Image",
        modifier = modifier,
        contentScale = ContentScale.FillHeight
    )
}

