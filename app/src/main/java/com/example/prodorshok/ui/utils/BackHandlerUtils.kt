package com.example.prodorshok.ui.utils

// BackHandlerUtils.kt

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AutoSmartBackHandler(
    navController: NavController
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val coroutineScope = rememberCoroutineScope()

    // State variables for dialog and back press logic
    var showExitDialog by remember { mutableStateOf(false) }
    var backPressedOnce by remember { mutableStateOf(false) }
    var backPressJob by remember { mutableStateOf<Job?>(null) }

    // Get the current screen name from the navController
    val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route

    // Handle back press
    BackHandler {
        if (currentScreen == "splash" || currentScreen == "onboarding") {
            showExitDialog = true
        } else {
            if (navController.previousBackStackEntry == null) {
                if (backPressedOnce) {
                    activity?.finish() // Finish the app if back is pressed twice
                } else {
                    backPressedOnce = true
                    Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()

                    // Reset after 2 seconds
                    backPressJob?.cancel()
                    backPressJob = coroutineScope.launch {
                        delay(2000)
                        backPressedOnce = false
                    }
                }
            } else {
                navController.popBackStack() // Go back to the previous screen
            }
        }
    }

    // Display the Exit confirmation dialog
    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Exit App") },
            text = { Text("Are you sure you want to exit?") },
            confirmButton = {
                Button(
                    onClick = {
                        showExitDialog = false
                        activity?.finish() // Close the app
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showExitDialog = false }
                ) {
                    Text("No")
                }
            }
        )
    }
}
