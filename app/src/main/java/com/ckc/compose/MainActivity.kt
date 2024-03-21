package com.ckc.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ckc.compose.ui.theme.JetpackComposeTheme

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
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxSize()) {
            // First LazyColumn
            LazyColumn(modifier = Modifier.weight(1f).fillMaxHeight()) {
                items(20) { index ->
                    Text(text = "Left Item #$index", modifier = Modifier.padding(8.dp))
                }
            }

            // Divider
            Divider(
                color = androidx.compose.ui.graphics.Color.Black,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )

            // Second LazyColumn
            LazyColumn(modifier = Modifier.weight(1f).fillMaxHeight()) {
                items(20) { index ->
                    Text(text = "Right Item #$index", modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}