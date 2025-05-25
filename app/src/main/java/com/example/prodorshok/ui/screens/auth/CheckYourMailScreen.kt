package com.example.prodorshok.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.prodorshok.ui.components.common.AuthTitleText
import com.example.prodorshok.ui.components.common.BodyTextSmall
import com.example.prodorshok.ui.components.common.SecondaryActionText
import com.example.prodorshok.viewmodel.auth.AuthViewModel
import com.example.prodorshok.viewmodel.auth.CheckMailViewModel

@Composable
fun CheckYourMailScreen(
    navController: NavController,
    email: String,
    checkMailViewModel: CheckMailViewModel = remember { CheckMailViewModel() },
    authViewModel: AuthViewModel = remember { AuthViewModel() }
) {
    val primaryBlue = Color(0xFF007DFF)
    val orange = Color(0xFFFFA900)
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryBlue),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MailOutline,
                    contentDescription = null,
                    tint = orange,
                    modifier = Modifier.size(64.dp)
                )

                AuthTitleText(text = "Check your\nMail", textAlign = TextAlign.Center)

                BodyTextSmall(text = "We’ve sent a password recovery mail to:\n$email")


                Button(
                    onClick = {
                        checkMailViewModel.openEmailApp(context)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = orange)
                ) {
                    Text("Open Email", color = Color.White)
                }

                Spacer(modifier = Modifier.height(8.dp))

                BodyTextSmall(text = "Didn’t get the mail?", fontSize = 13)

                Button(
                    onClick = {
                        authViewModel.sendPasswordReset(
                            email = email,
                            onSuccess = {
                                checkMailViewModel.startTimer()
                            },
                            onFailure = {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            }
                        )
                        checkMailViewModel.startTimer()
                    },
                    enabled = !checkMailViewModel.isTimerRunning,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = orange)
                ) {
                    Text(
                        text = if (checkMailViewModel.isTimerRunning)
                            "Resend in ${checkMailViewModel.remainingTime}s"
                        else "Resend Email",
                        color = Color.White
                    )
                }

                TextButton(onClick = {
                    navController.navigate("login")
                }) {
                    BodyTextSmall(text = "Skip, I’ll confirm later", fontSize = 12)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    BodyTextSmall(text = "Still not working? Check your spam folder.", fontSize = 11)
                    SecondaryActionText(text = "Try using mobile number instead")
                }
            }
        }
    }
}
