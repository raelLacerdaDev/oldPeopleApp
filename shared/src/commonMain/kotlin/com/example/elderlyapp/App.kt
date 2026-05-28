package com.example.elderlyapp


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.elderlyapp.camera.rememberCameraManager


@Composable
@Preview
fun App() {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    val cameraManager = rememberCameraManager { sharedImage ->
        imageBitmap = sharedImage?.toImageBitmap()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        imageBitmap?.let { bitmap ->
            Image(
                bitmap = bitmap,
                contentDescription = "Foto tirada pela câmera",
                modifier = Modifier
                    .size(300.dp)
                    .padding(16.dp)
            )
        } ?: run {
            Text(
                text = "Nenhuma foto tirada",
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(onClick = { cameraManager.launch() }) {
            Text("Tirar Foto")
        }
    }
}