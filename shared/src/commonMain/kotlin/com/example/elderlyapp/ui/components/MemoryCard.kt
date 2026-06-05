package com.example.elderlyapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.elderlyapp.data.dto.MemoryDto
import com.example.elderlyapp.utilities.loadImageFromPath

@Composable
fun MemoryCard(
    memory: MemoryDto,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Memória criada em: ${memory.creationDate}",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth().height(150.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val objectBitmap = remember(memory.objectPath) { loadImageFromPath(memory.objectPath) }
                Box(modifier = Modifier.weight(1f).fillMaxSize()) {
                    if (objectBitmap != null) {
                        Image(
                            bitmap = objectBitmap,
                            contentDescription = "Object",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Text("Erro objeto", modifier = Modifier.align(Alignment.Center))
                    }
                }
                val localBitmap = remember(memory.localPath) { loadImageFromPath(memory.localPath) }
                Box(modifier = Modifier.weight(1f).fillMaxSize()) {
                    if (localBitmap != null) {
                        Image(
                            bitmap = localBitmap,
                            contentDescription = "Local",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Text("Error local", modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}