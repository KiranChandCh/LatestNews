package com.ckc.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
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
                    SampleText("hero")
                }
            }
        }
    }
}

@Composable
fun SampleText(value: String) {
    Text(text = value,
        fontSize = 18.sp,
        color = Color.Red,
        modifier = Modifier
            .fillMaxSize() // This will make the parent container fill the available space
            .wrapContentSize(Alignment.Center)) // This will center the content within the parent container)
}

@Preview
@Composable
fun SampleTextPreview() {
    SampleText(value = "Hello")
}