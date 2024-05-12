package com.ckc.latestnews.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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
    newsItem.title?.let { Text(text = it) }
}
