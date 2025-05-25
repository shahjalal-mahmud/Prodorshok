package com.example.prodorshok.ui.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun AuthPromptText(
    prompt: String = "Don't have an account?",
    actionText: String = "Create an Account",
    onActionClick: () -> Unit
) {
    Row {
        Text(
            text = prompt,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black
        )
        Text(
            text = actionText,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onActionClick() }
        )
    }
}

@Composable
fun AuthTitleText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = text,
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 44.sp,
        textAlign = textAlign,
        modifier = modifier
    )
}

@Composable
fun AuthSubtitleText(text: String) {
    Text(
        text = text,
        fontSize = 18.sp,
        color = Color(0xFF555555),
        lineHeight = 24.sp,
        textAlign = TextAlign.Center
    )
}

@Composable
fun BodyTextSmall(
    text: String,
    color: Color = Color.Gray,
    fontSize: Int = 14,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = text,
        fontSize = fontSize.sp,
        color = color,
        textAlign = textAlign
    )
}
@Composable
fun TitleText(
    text: String,
    color: Color = Color.Black,
    fontWeight: FontWeight = FontWeight.Bold,
    fontSize: Int = 32,
    textAlign: TextAlign = TextAlign.Center
){
    Text(
        text = text,
        fontSize = fontSize.sp,
        color = color,
        fontWeight = fontWeight,
        textAlign = textAlign
    )
}
@Composable
fun SubtitleText(
    text: String,
    color: Color = Color.Black,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: Int = 18,
    textAlign: TextAlign = TextAlign.Center
){
    Text(
        text = text,
        fontSize = fontSize.sp,
        color = color,
        fontWeight = fontWeight,
        textAlign = textAlign
    )
}
@Composable
fun SecondaryActionText(
    text: String,
    color: Color = Color(0xFF007DFF),
    fontWeight: FontWeight = FontWeight.Bold,
    fontSize: Int = 11
) {
    Text(
        text = text,
        fontSize = fontSize.sp,
        color = color,
        fontWeight = fontWeight
    )
}

