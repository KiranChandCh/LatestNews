package com.ckc.latestnews.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.ckc.latestnews.model.Business
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
                    //BusinessNewsScreen()
                }
            }
        }
    }
}

@Composable
fun BusinessNewsScreen(viewModel: NewsViewModel = viewModel()) {
    val newsData by viewModel.newsData.collectAsState()
    val list = newsData?.Business
    LazyColumn {
        list?.let { businessList ->
            items(businessList.size) { businessItem ->
                BusinessNewsItem(businessList[businessItem])
            }
        }
    }
}

@Composable
fun GetNewsCategories(viewModel: NewsViewModel = viewModel()) {
    val newsCategories by viewModel.newsSections.collectAsState()
    val categories = newsCategories
    LazyColumn {
        categories?.let { businessList ->

        }
    }
}

@Composable
fun BusinessNewsItem(business: Business) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { business.link?.let { url -> openLink(context, url) } },
        verticalAlignment = Alignment.CenterVertically
    ) {
        business.sourceIcon?.let { iconUrl ->
            Image(
                painter = rememberImagePainter(iconUrl),
                contentDescription = "Source Icon",
                modifier = Modifier.size(56.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = business.title ?: "No Title",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = business.source ?: "No Source",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

fun openLink(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}



