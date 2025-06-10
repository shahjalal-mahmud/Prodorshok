package com.example.prodorshok.ui.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun AuthPromptText(
    prompt: String = "Don't have an account?",
    actionText: String = "Create an Account",
    onActionClick: () -> Unit,
    fontSize: TextUnit = 14.sp,
    color: Color = Color.Black
) {
    Row {
        Text(
            text = prompt,
            fontSize = fontSize,
            color = color
        )
        Text(
            text = actionText,
            fontSize = fontSize,
            color = color,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onActionClick() }
        )
    }
}

@Composable
fun AuthTitleText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    fontSize: TextUnit = 40.sp, // 👈 default value
    lineHeight: TextUnit = 44.sp
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold,
        lineHeight = lineHeight,
        textAlign = textAlign,
        modifier = modifier
    )
}

@Composable
fun AuthSubtitleText(
    text: String,
    fontSize: TextUnit = 18.sp,
    lineHeight: TextUnit = 24.sp,
    color: Color = Color(0xFF555555)
) {
    Text(
        text = text,
        fontSize = fontSize,
        lineHeight = lineHeight,
        color = color,
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

