package com.example.ucproomdatabase.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFFBBDEFB) // Light Blue
                    )
                )
            )
    ) {
        // Header Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, start = 16.dp, end = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Selamat Datang di Sistem \n Universitas Muhammadiyah Yogyakarta",
                    color = Color(0xFF1976D2), // Dark Blue
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Illustration
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.nasa),
                contentDescription = "Logo UI",
                modifier = Modifier.size(200.dp)
            )
        }

        // Buttons
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 48.dp, top = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
            .onPointerEnter { isHovered.value = true }
            .onPointerExit { isHovered.value = false }
    ) {
        Text(text)
    }
}

// Extensions untuk Klik
fun Modifier.onPointerEnter(onEnter: () -> Unit): Modifier = this.then(
    Modifier.pointerInput(Unit) {
        detectTapGestures(onPress = {
            onEnter()
        })
    }
)

fun Modifier.onPointerExit(onExit: () -> Unit): Modifier = this.then(
    Modifier.pointerInput(Unit) {
        detectTapGestures(onPress = {
            onExit()
        })
    }
)
