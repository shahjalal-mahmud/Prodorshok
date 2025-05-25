package com.example.prodorshok.ui.screens.onboarding

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.prodorshok.R
import com.example.prodorshok.ui.components.animation.PopInAnimation
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Slide2Content() {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Center content a bit lower than center
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 30.dp) // Move layout down
        ) {

            // 🗨️ Text Bubble + Text
            PopInAnimation(visible) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(300.dp)      // Increased width
                        .height(220.dp)     // Increased height
                        .offset(x = 30.dp)  // Move bubble + text to the right
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.text_buuble2),
                        contentDescription = "Text Bubble",
                        modifier = Modifier
                            .fillMaxSize() // match size of the Box
                    )
                    Text(
                        text = "Don’t know where to\neven begin. I wish\nsomeone had guided\nme earlier",
                        modifier = Modifier
                            .padding(horizontal = 32.dp, vertical = 24.dp), // Padding adjusted for larger bubble
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.Black),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp)) // Gap between bubble and boy

            // 👦 Tensed Boy Image
            PopInAnimation(visible) {
                Image(
                    painter = painterResource(id = R.drawable.tensed_boy),
                    contentDescription = "Confused Boy",
                    modifier = Modifier
                        .width(290.dp)
                        .height(400.dp)
                )
            }
        }
    }
}
