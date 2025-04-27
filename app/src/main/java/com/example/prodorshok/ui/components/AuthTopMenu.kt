package com.example.prodorshok.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
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

@Composable
fun AuthTopMenu(
    navController: NavController,
    onMenuItemClick: (String) -> Unit = {},
    iconTintColor: Color = Color.White // Default White if not given
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 24.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        // Three-Dot Menu (Top Left)
        var expanded by remember { mutableStateOf(false) }

        Box {
            IconButton(
                onClick = { expanded = true },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu",
                    tint = iconTintColor // <-- using the dynamic color here
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
                        onMenuItemClick("Need Help")
                    }
                )
                DropdownMenuItem(
                    text = { Text("Contact Us") },
                    onClick = {
                        expanded = false
                        onMenuItemClick("Contact Us")
                    }
                )
                DropdownMenuItem(
                    text = { Text("Give Feedback") },
                    onClick = {
                        expanded = false
                        onMenuItemClick("Give Feedback")
                    }
                )
            }
        }

        // Back Button (Cross Icon)
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = iconTintColor // <-- using the dynamic color here too
            )
        }
    }
}
