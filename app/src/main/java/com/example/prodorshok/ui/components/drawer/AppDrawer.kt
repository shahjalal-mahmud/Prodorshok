package com.example.prodorshok.ui.components.drawer

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.prodorshok.ui.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppDrawer(
    navController: NavController,
    onCloseDrawer: () -> Unit,
) {
    val auth = FirebaseAuth.getInstance()
    ModalDrawerSheet(modifier = Modifier.width(280.dp)) {
        Text(
            text = "Prodorshok Menu",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        HorizontalDivider()

        DrawerItem("🏠 Home") {
            navController.navigate("home")
            onCloseDrawer()
        }

        DrawerItem("📞 Contact with us") {
            navController.navigate("contact_us")
            onCloseDrawer()
        }

        DrawerItem("✍️ Give Feedback") {
            navController.navigate("feedback")
            onCloseDrawer()
        }

        DrawerItem("🗂 Show all feedbacks") {
            navController.navigate("feedback_list")
            onCloseDrawer()
        }

        DrawerItem("❓ Need Help?") {
            navController.navigate("need_help")
            onCloseDrawer()
        }

        DrawerItem("💎 Get Premium") {
            navController.navigate("premium")
            onCloseDrawer()
        }

        DrawerItem("🧳 Find Jobs") {
            navController.navigate("job_prep")
            onCloseDrawer()
        }

        DrawerItem("📚 Courses Recommended") {
            navController.navigate("courses")
            onCloseDrawer()
        }

        DrawerItem("📄 Career QnA") {
            navController.navigate(Screen.CareerQnA.route)
            onCloseDrawer()
        }

        DrawerItem("News") {
            navController.navigate("news")
            onCloseDrawer()
        }
        // Add a divider before the logout button
        HorizontalDivider()

        // Add the logout button
        DrawerItem("🚪 Logout") {
            auth.signOut()
            Toast.makeText(navController.context, "You have logged out.", Toast.LENGTH_SHORT).show()
            navController.navigate("login") {
                popUpTo("login") { inclusive = true }
            }
            onCloseDrawer()
        }
    }
}