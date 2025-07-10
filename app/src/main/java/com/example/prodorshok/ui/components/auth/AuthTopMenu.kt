package com.example.prodorshok.ui.components.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.prodorshok.ui.utils.slideInFromTop

@Composable
fun AuthTopMenu(
    navController: NavController,
    showBackButton: Boolean = true,
    showMenuDots: Boolean = true,
    iconTintColor: Color = Color.White,
    onMenuItemClick: (String) -> Unit = {},
    showAnimation: Boolean = true // New parameter to control animation
) {
    // Apply the slide-in animation to the entire row
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .slideInFromTop(show = showAnimation) // Use the animation modifier here
            .padding(top = 24.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        if (showBackButton) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = iconTintColor
                )
            }
        } else {
            Spacer(modifier = Modifier.size(40.dp)) // Preserve layout spacing
        }

        if (showMenuDots) {
            var expanded by remember { mutableStateOf(false) }

            Box {
                IconButton(
                    onClick = { expanded = true },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Menu",
                        tint = iconTintColor
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.width(180.dp)
                ) {
                    DropdownMenuItem(
                        text = { Text("Need Help?") },
                        onClick = {
                            expanded = false
                            onMenuItemClick("need_help")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Contact Us") },
                        onClick = {
                            expanded = false
                            onMenuItemClick("contact_us")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Give Feedback") },
                        onClick = {
                            expanded = false
                            onMenuItemClick("feedback")
                        }
                    )
                }
            }
        } else {
            Spacer(modifier = Modifier.size(40.dp)) // Maintain layout
        }
    }
}
