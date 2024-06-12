package com.ckc.latestnews.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var viewModel: NewsViewModel
    //private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StartApp()
        }
    }

    @Composable
    fun StartApp() {
        //val apiService = RetrofitInstance.api // Initialize your API service
        //val newsRepository = NewsRepositoryImpl(apiService) // Initialize your repository
        //val viewModelFactory = NewsViewModelFactory(newsRepository)
        //viewModel = viewModelProvider(viewModelFactory)
        viewModel = hiltViewModel()
        viewModel.fetchNewsCategories()

        var isSplashScreenShown by remember { mutableStateOf(false) }
        val navController = rememberNavController()
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = LatestNewsScreen.Splash.name
            ) {
                composable(route = LatestNewsScreen.Splash.name) { backStackEntry ->
                    isSplashScreenShown = true
                    SplashScreen(navController)
                }
                composable(route = LatestNewsScreen.Start.name) { backStackEntry ->
                    if (!isSplashScreenShown) {
                        // If the splash screen hasn't been shown yet, navigate to the splash screen
                        navController.navigate(LatestNewsScreen.Splash.name) {
                            // Pop up to the start destination to remove the splash screen from the back stack
                            popUpTo(LatestNewsScreen.Splash.name) {
                                inclusive = true
                            }
                        }
                    } else {
                        GetNewsCategories(navController)
                    }
                }
                composable(route = LatestNewsScreen.NewsDetail.name) {
                    StartNewsDetailScreen(viewModel)
                }
            }
        }
    }

    enum class LatestNewsScreen(@StringRes val title: Int) {
        Splash(title = R.string.splash),
        Start(title = R.string.app_name),
        NewsDetail(title = R.string.news_detail_screen),
    }

    @Composable
    fun SplashScreen(navController: NavHostController) {
        Image(painter = painterResource(id = R.drawable.pulse_news),
            contentDescription = null,
            Modifier.fillMaxWidth())

        // Navigate to the main screen after the delay
        LaunchedEffect(Unit) {
            delay(2000)
            navController.navigate(LatestNewsScreen.Start.name) {
                popUpTo(LatestNewsScreen.Splash.name) {
                    inclusive = true
                }
            }
        }
    }

    @Composable
    fun GetNewsCategories(navController: NavHostController) {
        val newsBySections by viewModel.newsBySections.collectAsState()
        val latestNews = newsBySections
        LazyColumn {
            itemsIndexed(latestNews.entries.toList()) { _, (category, newsItems) ->
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

