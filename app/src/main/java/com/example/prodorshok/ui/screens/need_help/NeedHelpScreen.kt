package com.example.prodorshok.ui.screens.need_help

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.prodorshok.R
import com.example.prodorshok.ui.components.common.FAQ
import com.example.prodorshok.ui.components.common.FAQDrawerBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NeedHelpScreen(navController: NavController) {

    val faqList = listOf(
        FAQ("I can't log in. What should I do?", "Make sure your email and password are correct. If you forgot your password, use the 'Forgot Password' option to reset it."),
        FAQ("I did not receive the reset password email.", "Please check your Spam or Junk folder. If you still don't find it, try resending the email after a few minutes."),
        FAQ("I'm unable to sign up with my email.", "Your email might already be registered. Try logging in or resetting your password."),
        FAQ("I forgot my password. How can I recover my account?", "Click on 'Forgot Password' in the Login screen and follow the instructions to reset your password."),
        FAQ("I didn't get the verification code. What should I do?", "Wait a few minutes. If you still don't get it, request a new code or contact support."),
        FAQ("I need more help.", "Please use the 'Contact Us' option from the menu. We're here to assist you!")
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 🌊 Background Image
        Image(
            painter = painterResource(id = R.drawable.top_wave_yellow),
            contentDescription = "Wave Header",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .align(Alignment.TopCenter)
        )

        // 📦 Overlay with Transparent Box
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFFDFDE3).copy(alpha = 0.35f))
        ) {
            // 🔙 TopAppBar Custom Transparent
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack, // 🔁 Looks visually bolder
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Need Help?",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            // 📜 FAQ List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(faqList) { faq ->
                    FAQDrawerBox(faq)
                }
            }
        }
    }
}
