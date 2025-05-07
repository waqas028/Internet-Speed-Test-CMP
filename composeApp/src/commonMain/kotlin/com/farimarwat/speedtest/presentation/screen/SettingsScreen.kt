package com.farimarwat.speedtest.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.farimarwat.speedtest.utils.getAppVersion
import com.farimarwat.speedtest.utils.sendFeedbackEmail
import com.farimarwat.speedtest.utils.shareText

@Composable
fun SettingsScreen(modifier: Modifier = Modifier){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        SettingsItem(
            title = "Share App",
            icon = Icons.Default.Share,
            iconTint = MaterialTheme.colorScheme.secondary,
            onClick = {
                shareText("Check out this amazing Speed Test app!")
            }
        )

        SettingsItem(
            title = "Rate App",
            icon = Icons.Default.Star,
            iconTint = MaterialTheme.colorScheme.tertiary,
            onClick = {
                /* Open Play Store rating page */
            }
        )

        SettingsItem(
            title = "Send Feedback",
            icon = Icons.Default.Email,
            iconTint = MaterialTheme.colorScheme.error,
            onClick = {
                val toEmail = "waqaswaseem679@gmail.com"
                val subject = "Feedback for Your App"
                val body = "Hi,\nI have some feedback about your app..."
                sendFeedbackEmail(toEmail, subject, body)
            }
        )

        SettingsItem(
            title = "Privacy Policy",
            icon = Icons.Default.Warning,
            iconTint = MaterialTheme.colorScheme.primary,
            onClick = {
                /* Open privacy policy */
            }
        )

        SettingsInfoItem(
            title = "App Version",
            value = getAppVersion(),
            icon = Icons.Default.Info,
            iconTint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun SettingsItem(
    title: String,
    icon: ImageVector,
    iconTint: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = iconTint.copy(alpha = 0.1f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun SettingsInfoItem(
    title: String,
    value: String,
    icon: ImageVector,
    iconTint: Color
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = iconTint.copy(alpha = 0.1f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}