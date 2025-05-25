package com.example.prodorshok.ui.screens.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prodorshok.R
import com.example.prodorshok.ui.components.common.SubtitleText
import com.example.prodorshok.ui.components.common.TitleText
import com.example.prodorshok.ui.components.common.TopBackgroundImage
import com.example.prodorshok.ui.utils.addFeedback

@Composable
fun FeedbackScreen(navController: NavController) {
    var rating by remember { mutableStateOf(0) }
    var message by remember { mutableStateOf("") }

    val borderColor = Color(0xFFFFC107) // Amber
    val cardShape = RoundedCornerShape(topStart = 42.dp)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopBackgroundImage(imageRes = R.drawable.top_wave_blue)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp, start = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = 24.dp)
                    .drawBehind {
                        val strokeWidth = 4.dp.toPx()
                        val path = Path()

                        // Manually draw top and start border
                        path.moveTo(0f, size.height)               // bottom-left
                        path.lineTo(0f, 0f)                        // top-left
                        path.lineTo(size.width, 0f)                // top-right

                        drawPath(
                            path = path,
                            color = borderColor,
                            style = Stroke(
                                width = strokeWidth,
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                                },
                shape = cardShape,
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = null
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 24.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    TitleText("Give Feedback")
                    Spacer(modifier = Modifier.height(4.dp))
                    SubtitleText("We highly value your feedback!")
                    Spacer(modifier = Modifier.height(16.dp))

                    // Star Rating
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        (1..5).forEach { i ->
                            IconButton(
                                onClick = { rating = i },
                                modifier = Modifier
                                    .padding(horizontal = 6.dp)
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(
                                        color = if (i <= rating) borderColor else Color(0xFFF0F0F0),
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Star $i",
                                    tint = if (i <= rating) Color.White else Color.Gray,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Text("Write Your Experiences ___")
                    Spacer(modifier = Modifier.height(4.dp))

                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        placeholder = { Text("Type here") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        maxLines = 12
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            addFeedback(rating, message)
                            rating = 0
                            message = ""
                        },
                        enabled = rating > 0 && message.isNotBlank(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF9800),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Submit Now", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}
