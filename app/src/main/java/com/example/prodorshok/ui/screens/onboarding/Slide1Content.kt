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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
fun Slide1Content() {
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
            modifier = Modifier.wrapContentSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            // ❓ Question Marks as BACKGROUND layer, positioned in the middle
            PopInAnimation(visible) {
                Image(
                    painter = painterResource(id = R.drawable.question_mark),
                    contentDescription = "Question Marks",
                    modifier = Modifier
                        .size(width = 290.dp, height = 75.dp)
                        .align(Alignment.TopCenter)
                        .offset(y = 157.dp) // Adjust this to be about halfway between the two
                )
            }

            // FOREGROUND content
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
                // 🗨️ Text Bubble with Text
                PopInAnimation(visible) {
                    Box(contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(id = R.drawable.text_bubble),
                            contentDescription = "Text Bubble",
                            modifier = Modifier.size(width = 300.dp, height = 160.dp)
                        )
                        Text(
                            text = "Lost in career \nconfusion?",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                            style = MaterialTheme.typography.titleLarge.copy(color = Color.Black),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(5.dp)) // Keeps the gap consistent

                // 👦 Confused Boy Image
                PopInAnimation(visible) {
                    Image(
                        painter = painterResource(id = R.drawable.onbording_slide1),
                        contentDescription = "Confused Boy",
                        modifier = Modifier.size(width = 290.dp, height = 400.dp)
                    )
                }
            }
        }
    }
}
