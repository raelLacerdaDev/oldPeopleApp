package com.example.elderlyapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CreationStepScreen(
    title: String,
    subtitle: String,
    bitmap: androidx.compose.ui.graphics.ImageBitmap?,
    onCaptureClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, style = MaterialTheme.typography.headlineMedium, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = subtitle, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (bitmap != null) {
                Image(
                    bitmap = bitmap,
                    contentDescription = "Photo taken",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            } else {
                Text("There's not photo yet", color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onCaptureClick,
            modifier = Modifier.fillMaxWidth().height(64.dp)
        ) {
            Text(if (bitmap == null) "Take photo" else "Take other", style = MaterialTheme.typography.titleLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (bitmap != null) {
            Button(
                onClick = onNextClick,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.fillMaxWidth().height(64.dp)
            ) {
                Text("Look like good, next!", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}