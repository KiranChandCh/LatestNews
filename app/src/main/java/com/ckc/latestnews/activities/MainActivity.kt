package com.ckc.latestnews.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
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
    val newsCategories by viewModel.newsSections.collectAsState()
    val newsBySections by viewModel.newsBySections.collectAsState()
    val categories = newsCategories
    val latestNews = newsBySections
}



