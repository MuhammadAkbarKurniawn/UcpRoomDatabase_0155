package com.example.ucproomdatabase.ui.customwidget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTopBar(
    onBack: () -> Unit,
    showBackButton: Boolean = true,
    judul: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2196F3)) // Set the background color to a blue shade
            .padding(16.dp),
        contentAlignment = Alignment.Center // Ensure content is centered
    ) {
        // Show the back button only if showBackButton is true
        if (showBackButton) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White // Make the back button icon white
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        // Title Text
        Text(
            text = judul,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White, // White color for the title text
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
