package com.ckc.latestnews.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.ckc.latestnews.viewmodel.NewsViewModel

@Composable
fun StartNewsDetailScreen(viewModel: NewsViewModel) {
    val item = viewModel.getNewsItem()
    if (item != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Image
            val painter = rememberImagePainter(item.og)
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                alignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.50f)
            )

            // Title
            Text(
                text = item.title ?: "",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Text(text = "News Source",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Red
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                val sourceIcon = rememberImagePainter(item.sourceIcon)
                Image(
                    painter = sourceIcon,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = item.source ?: "",
                    style = MaterialTheme.typography.titleSmall
                )
            }

            // Link
            Text(
                text = item.link ?: "",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.clickable {

                }
            )
        }
    } else {
        Text("Invalid news item")
    }
}
