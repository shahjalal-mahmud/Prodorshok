package com.example.prodorshok.ui.components.drawer

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AppDrawer(
    navController: NavController,
    onCloseDrawer: () -> Unit
) {
    ModalDrawerSheet(modifier = Modifier.width(280.dp)) {
        Text(
            text = "Prodorshok Menu",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        Divider()

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

        DrawerItem("📄 Resume Builder") {
            navController.navigate("resume_builder")
            onCloseDrawer()
        }

        DrawerItem("News") {
            navController.navigate("news")
            onCloseDrawer()
        }
    }
}