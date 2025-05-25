package com.example.prodorshok.ui.screens.contact_us

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prodorshok.ui.components.common.AssetIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactUsScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contact Us") },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        containerColor = Color.Transparent
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFE0F7FA), Color(0xFFFFFFFF))
                    )
                )
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Connect with Us!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00796B)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Use AssetIcon for each ContactRow
                ContactRow(
                    label = "Facebook",
                    iconFilename = "facebook_icon.svg"
                ) {
                    openUrl(context, "https://www.facebook.com/yourpage")
                }

                ContactRow(
                    label = "LinkedIn",
                    iconFilename = "linkedin_icon.svg"
                ) {
                    openUrl(context, "https://www.linkedin.com/in/yourprofile")
                }

                ContactRow(
                    label = "Instagram",
                    iconFilename = "instagram_icon.svg"
                ) {
                    openUrl(context, "https://www.instagram.com/yourprofile")
                }

                ContactRow(
                    label = "YouTube",
                    iconFilename = "youtube_icon.svg"
                ) {
                    openUrl(context, "https://www.youtube.com/yourchannel")
                }

                Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color.Gray)

                ContactRow(
                    label = "Email",
                    iconFilename = "email_icon.svg"
                ) {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:support@prodorshok.com")
                    }
                    context.startActivity(emailIntent)
                }

                ContactRow(
                    label = "Phone",
                    iconFilename = "phone_icon.svg"
                ) {
                    val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:+880123456789")
                    }
                    context.startActivity(dialIntent)
                }

                ContactRow(
                    label = "Address",
                    iconFilename = "address_icon.svg"
                ) {
                    openUrl(context, "https://maps.google.com/?q=Your+Address+Here")
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}


@Composable
fun ContactRow(label: String, iconFilename: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Use AssetIcon to load the SVG from assets/icons/
            AssetIcon(
                filename = iconFilename,
                modifier = Modifier
                    .size(32.dp)
                    .background(Color.White, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = label,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


fun openUrl(context: android.content.Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}