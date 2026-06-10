package com.example.elderlyapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.elderlyapp.data.dto.MemoryDto
import com.example.elderlyapp.utilities.loadImageFromPath
import elderlyapp.shared.generated.resources.Res
import elderlyapp.shared.generated.resources.delete_24dp_000000_FILL0_wght400_GRAD0_opsz24
import org.jetbrains.compose.resources.painterResource

@Composable
fun MemoryCard(
    memory: MemoryDto,
    onDeleteClick:() -> Unit,
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
                modifier = Modifier.fillMaxWidth().heightIn(150.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val objectBitmap = remember(memory.objectPath) { loadImageFromPath(memory.objectPath) }
                Box(modifier = Modifier.weight(1f).fillMaxSize()) {
                    if (objectBitmap != null) {
                        Image(
                            bitmap = objectBitmap,
                            contentDescription = "Object",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize().clip(CircleShape)
                        )
                    } else {
                        Text("Error Object", modifier = Modifier.align(Alignment.Center))
                    }
                }
                val localBitmap = remember(memory.localPath) { loadImageFromPath(memory.localPath) }
                Box(modifier = Modifier.weight(1f).fillMaxSize()) {
                    if (localBitmap != null) {
                        Image(
                            bitmap = localBitmap,
                            contentDescription = "Local",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize().clip(CircleShape)
                        )
                    } else {
                        Text("Error Local", modifier = Modifier.align(Alignment.Center))
                    }
                }
                IconButton(
                   onClick = onDeleteClick
                ){
                    Icon(
                        painter = painterResource(Res.drawable.delete_24dp_000000_FILL0_wght400_GRAD0_opsz24),
                        contentDescription = "Delete Memory Button"
                    )
                }
            }
        }
    }
}