package com.ckc.kotlincoroutine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.ckc.kotlincoroutine.ui.theme.JetpackComposeTheme
import com.ckc.kotlincoroutine.viewmodel.DogViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue



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
                    RandomDogImage()
                    //ImageWithText()
                }
            }
        }
    }
}

/*@Composable
fun ImageWithText() {
    // Remember a mutable state
    var imageUrl by remember { mutableStateOf<String?>(null) }

    // Simulate setting the URL (e.g., from a button click or lifecycle event)
    Button(onClick = { imageUrl = "https://images.dog.ceo//breeds//pekinese//n02086079_13647.jpg" }) {
        Text("Load Image")
    }

    // Display the image if the URL is not null
    imageUrl?.let {
        Image(
            painter = rememberImagePainter(it),
            contentDescription = "Loaded Image",
            modifier = Modifier.fillMaxWidth()
        )
    } ?: Text("Image URL is null")
}*/

@Composable
fun RandomDogImage(dogViewModel: DogViewModel = viewModel()) {
    val imageUrl by dogViewModel.dogImageUrl.collectAsState()

    imageUrl?.let {
        Image(
            painter = rememberImagePainter(it),
            contentDescription = "Random Dog Image",
            modifier = Modifier.fillMaxWidth()
        )
    } ?: Text("Loading...")
}



