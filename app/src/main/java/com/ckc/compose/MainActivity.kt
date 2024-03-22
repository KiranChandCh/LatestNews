package com.ckc.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ckc.compose.ui.theme.JetpackComposeTheme
import androidx.compose.foundation.lazy.items


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
                    CreateLazyColumnMultipleRows()
                }
            }
        }
    }
}

@Composable
fun CreateLazyColumnMultipleRows() {
    Box(modifier = Modifier.fillMaxSize()) { // Use Box to fill the screen
        ItemList() // This now directly includes your list of items with images and text
    }
}

@Composable
fun ItemList() {
    val items = getDummyItems() // Get the dummy data
    LazyColumn {
        items(items) { item ->
            val imagePainter = painterResource(id = R.drawable.ic_launcher_background) // Use your actual image resource
            // Make sure to use item.text to get the text property of the current item
            ImageTextItem(imagePainter = imagePainter, text = item.value)
        }
    }
}

fun getDummyItems(): List<DataItem> {
    return List(20) { index -> DataItem(R.drawable.ic_launcher_background, "Item #$index") }
}

@Composable
fun ImageTextItem(imagePainter: Painter, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
        Image(
            painter = imagePainter,
            contentDescription = "Item Image",
            modifier = Modifier
                .padding(end = 8.dp)
                .width(50.dp) // Set a fixed width for the image for consistency
                .fillMaxHeight(),
            contentScale = ContentScale.Crop // Add this to scale the image appropriately
        )
        Text(text = text, modifier = Modifier.padding(start = 8.dp)) // Ensure text is also padded
    }
}


