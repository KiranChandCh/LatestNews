package com.ckc.latestnews.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.ckc.latestnews.model.NewsItem
import com.ckc.latestnews.viewmodel.NewsViewModel
import androidx.navigation.compose.rememberNavController
import com.ckc.latestnews.R

class MainActivity : ComponentActivity() {
    private val viewModel: NewsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GetNewsCategories()
        }
    }

    enum class LatestNewsScreen(@StringRes val title: Int) {
        Start(title = R.string.app_name),
        NewsDetail(title = R.string.news_detail_screen),
    }

    @Composable
    fun GetNewsCategories(
        navController: NavHostController = rememberNavController()
    ) {
        val newsBySections by viewModel.newsBySections.collectAsState()
        val latestNews = newsBySections
        NavHost(
            navController = navController,
            startDestination = LatestNewsScreen.Start.name
        ) {
            composable(route = LatestNewsScreen.Start.name) { backStackEntry ->
                NewsScreen(newsBySection = latestNews, navController)
            }
            composable(route = LatestNewsScreen.NewsDetail.name) {
                StartNewsDetailScreen(viewModel)
            }
        }
    }

    @Composable
    fun NewsScreen(newsBySection: Map<String?, List<NewsItem>>, navController: NavHostController) {
        LazyColumn {
            itemsIndexed(newsBySection.entries.toList()) { _, (category, newsItems) ->
                Carousel(title = category, items = newsItems, navController)
            }
        }
    }

    @Composable
    fun Carousel(title: String?, items: List<NewsItem>, navController: NavHostController) {
        Column {
            title?.let { Text(text = it, style = TextStyle(fontSize = 24.sp)) }
            LazyRow {
                items(items) { newsItem ->
                    NewsItem(newsItem, navController)
                }
            }
        }
    }

    @Composable
    fun NewsItem(newsItem: NewsItem, navController: NavHostController) {
        Card(
            modifier = Modifier
                .width(160.dp)
                .height(200.dp)
                .padding(4.dp)
                .clickable {
                    viewModel.setNewsItem(newsItem)
                    navController.navigate(LatestNewsScreen.NewsDetail.name)
                }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Image
                newsItem.og?.let { imageUrl ->
                    LoadImageFromUrl(
                        imageUrl = imageUrl, modifier = Modifier
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
}

