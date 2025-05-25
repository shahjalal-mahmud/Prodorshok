package com.example.prodorshok.ui.screens.onboarding

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.prodorshok.R
import com.example.prodorshok.ui.components.animation.PopInAnimation
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Slide3Content() {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(400.dp) // Match image size
        ) {
            // 👦 Image goes to bottom layer
            PopInAnimation(visible) {
                Image(
                    painter = painterResource(id = R.drawable.slide3),
                    contentDescription = "Confused Boy",
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Container for first text with proper alignment
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 38.dp, top = 63.dp)
            ) {
                PopInAnimation(visible) {
                    Text(
                        text = "No clear\nroadmap\nto follow",
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.Black)
                    )
                }
            }

            // Container for second text with proper alignment
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 25.dp, top = 65.dp),
            ) {
                PopInAnimation(visible) {
                    Text(
                        text = "I don't know\nwhere to begin",
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.Black)
                    )
                }
            }
        }
    }
}