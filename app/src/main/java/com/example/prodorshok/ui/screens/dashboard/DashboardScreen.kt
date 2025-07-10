// ui/screens/DashboardScreen.kt

package com.example.prodorshok.ui.screens.dashboard

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.prodorshok.R
import com.example.prodorshok.ui.components.dashboard.Feature
import com.example.prodorshok.ui.components.dashboard.FeatureGrid
import com.example.prodorshok.ui.components.dashboard.UpcomingEventCard
import com.example.prodorshok.ui.components.drawer.AppDrawer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    userName: String,
    onNotificationClick: () -> Unit,
    onFeatureClick: (Feature) -> Unit,
    navController: NavController,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Permanent Google Meet URL for mentor session (replace with your own)
    val mentorSessionUrl = "https://meet.google.com/hsx-bqyb-ssg"

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                navController = navController,
                onCloseDrawer = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Welcome, $userName 👋",
                            fontSize = 18.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = onNotificationClick) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications"
                            )
                        }
                    }
                )
            },
            containerColor = Color(0xFFF9FAFB),
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .background(Color(0xFFF9FAFB))
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Upcoming Events
                    UpcomingEventCard(
                        date = "Today, 2:00 PM",
                        onClickViewAll = { /* TODO */ },
                        onClickJoin = {
                            val intent = Intent(Intent.ACTION_VIEW, mentorSessionUrl.toUri())
                            context.startActivity(intent)
                        }
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Features Header
                    Text(
                        text = "Explore Features",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 4.dp),
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Feature Grid
                    FeatureGrid(
                        features = listOf(
                            Feature("Chat with AI", R.drawable.ic_chat_ai),
                            Feature("Talk with Mentor", R.drawable.ic_mentor),
                            Feature("1:1 Sessions", R.drawable.ic_session),
                            Feature("Mental Support", R.drawable.ic_mental),
                            Feature("Job Prep", R.drawable.ic_job),
                            Feature("Learning Community", R.drawable.ic_community),
                            Feature("Skill Analysis", R.drawable.ic_skill),
                            Feature("Resume Builder", R.drawable.ic_resume),
                        ),
                        onFeatureClick = onFeatureClick
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        )
    }
}

