package com.example.ucproomdatabase.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucproomdatabase.R
import com.example.ucproomdatabase.ui.navigation.AlamatNavigasi

object DestinasiHome : AlamatNavigasi {
    override val route: String = "Home"
}

@Composable
fun HomeView(
    onClickDosen: () -> Unit = { },
    onClickMataKuliah: () -> Unit = { },
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFF2196F3) // Sharper Blue
                    )
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Section with Logo and Text
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.nasa),
                    contentDescription = "Logo UI",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(bottom = 16.dp)
                )
                Text(
                    text = "Selamat Datang di Sistem\nUniversitas Muhammadiyah Yogyakarta",
                    color = Color(0xFF0D47A1), // Darker Blue
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Buttons inside a full Box
        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFBBDEFB))
                .padding(24.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Menu :",
                    color = Color(0xFF0D47A1),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                HoverButton(
                    text = "Dosen",
                    defaultColor = Color(0xFF1976D2),
                    hoverColor = Color(0xFF64B5F6),
                    textColor = Color.White,
                    onClick = onClickDosen
                )

                HoverButton(
                    text = "Mata Kuliah",
                    defaultColor = Color.White,
                    hoverColor = Color(0xFFE3F2FD),
                    textColor = Color(0xFF1976D2),
                    onClick = onClickMataKuliah
                )
            }
        }
    }
}

@Composable
fun HoverButton(
    text: String,
    defaultColor: Color,
    hoverColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    val isHovered = remember { mutableStateOf(false) }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isHovered.value) hoverColor else defaultColor,
            contentColor = textColor
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(vertical = 8.dp)
    ) {
        Text(text)
    }
}
