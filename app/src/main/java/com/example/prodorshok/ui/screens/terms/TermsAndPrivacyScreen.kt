package com.example.prodorshok.ui.screens.terms

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TermsAndPrivacyScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp)
    ) {
        // Title
        Text(
            text = "Prodorshok — Terms of Service & Privacy Policy",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 24.dp)
        )

        /** -------- Terms of Service -------- */
        SectionTitle("Terms of Service")
        SectionSubTitle("1. Acceptance of Terms")
        SectionBody("By accessing or using Prodorshok (\"we\", \"our\", \"the app\", \"the service\"), you agree to comply with and be bound by these Terms of Service. If you do not agree, you may not use the service.")

        SectionSubTitle("2. Services Provided")
        SectionBody("Prodorshok offers personalized career guidance through AI tools and human mentorship. We aim to help users explore career options, set goals, and achieve success with informed decisions.")

        SectionSubTitle("3. User Accounts")
        SectionBody("You must create an account to access full features. You are responsible for maintaining the confidentiality of your account information. You must provide accurate, complete information during registration.")

        SectionSubTitle("4. Eligibility")
        SectionBody("You must be at least 13 years old to use our services. If you are under 18, you must have parental or guardian consent.")

        SectionSubTitle("5. Payment and Subscription")
        SectionBody("Basic services are free. Premium services require payment. Subscriptions auto-renew unless canceled before the renewal date.")

        SectionSubTitle("6. User Conduct")
        SectionBody("You agree not to misuse, disrupt, or attempt unauthorized access to our services, or use the app for activities not related to career guidance.")

        SectionSubTitle("7. AI and Human Mentorship Disclaimer")
        SectionBody("AI suggestions are generated based on input data but do not guarantee career success. Human mentors provide advice based on their expertise, but final career decisions are your own responsibility.")

        SectionSubTitle("8. Intellectual Property")
        SectionBody("All content, logos, trademarks, and features in Prodorshok are owned by Prodorshok unless otherwise stated. You may not use, reproduce, or distribute any content without permission.")

        SectionSubTitle("9. Termination")
        SectionBody("We reserve the right to suspend or terminate your access to the app if you violate these Terms.")

        SectionSubTitle("10. Modifications")
        SectionBody("We may update these Terms at any time. Users will be notified of significant changes.")

        SectionSubTitle("11. Governing Law")
        SectionBody("These Terms are governed by the laws of Bangladesh.")

        SectionSubTitle("12. Contact")
        SectionBody("For any questions, contact us at: support@prodorshok.com")

        Divider(modifier = Modifier.padding(vertical = 24.dp))

        /** -------- Privacy Policy -------- */
        SectionTitle("Privacy Policy")
        SectionSubTitle("1. Information We Collect")
        SectionBody("We collect Personal Information (name, email, phone, academic info), Usage Data (device info, cookies), and Career Interests.")

        SectionSubTitle("2. How We Use Your Information")
        SectionBody("We use your data to provide personalized career guidance, connect you with mentors, improve our services, and send updates or offers.")

        SectionSubTitle("3. Sharing of Information")
        SectionBody("We do not sell, trade, or rent your data. We share only with trusted mentors, service providers, or legal authorities when required.")

        SectionSubTitle("4. Data Retention")
        SectionBody("We retain your information as long as needed to provide services or comply with legal obligations.")

        SectionSubTitle("5. Data Security")
        SectionBody("We use industry-standard security measures to protect your data. However, no method is 100% secure.")

        SectionSubTitle("6. User Rights")
        SectionBody("You have the right to access, correct, or delete your data, and opt-out of marketing communications.")

        SectionSubTitle("7. Third-Party Services")
        SectionBody("We may use third-party services (e.g., analytics tools) that collect and analyze app usage.")

        SectionSubTitle("8. Children's Privacy")
        SectionBody("Prodorshok is not directed to children under 13. We do not knowingly collect data without parental consent.")

        SectionSubTitle("9. Changes to Privacy Policy")
        SectionBody("We may update this Privacy Policy. Continued use of the app means you accept the changes.")

        SectionSubTitle("10. Contact")
        SectionBody("If you have questions about privacy, contact us at: privacy@prodorshok.com")

        Spacer(modifier = Modifier.height(32.dp))

        // Accept and Continue Button
        Button(
            onClick = {
                navController.navigate("signup")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        ) {
            Text(text = "Accept and Continue")
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        ),
        modifier = Modifier.padding(vertical = 12.dp)
    )
}

@Composable
fun SectionSubTitle(subTitle: String) {
    Text(
        text = subTitle,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun SectionBody(body: String) {
    Text(
        text = body,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}
