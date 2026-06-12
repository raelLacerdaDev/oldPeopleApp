package com.example.elderlyapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.elderlyapp.data.dto.MemoryDto
import com.example.elderlyapp.utilities.formatDateString
import com.example.elderlyapp.utilities.loadImageFromPath
import elderlyapp.shared.generated.resources.Res
import elderlyapp.shared.generated.resources.ic_trash
import org.jetbrains.compose.resources.painterResource


@Composable
fun MemoryCard(
    memory: MemoryDto,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    val formattedDate = remember(memory.creationDate) {
        formatDateString(memory.creationDate.toString())
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Memory?") },
            text = { Text("Are you sure you want to delete this memory? This action cannot be undone.") },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteDialog = false
                        onDeleteClick()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Memory saved on:",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                IconButton(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_trash),
                        contentDescription = "Delete Memory Button",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val objectBitmap = remember(memory.objectPath) { loadImageFromPath(memory.objectPath) }
                PhotoBox(
                    bitmap = objectBitmap,
                    label = "Object",
                    modifier = Modifier.weight(1f)
                )

                val localBitmap = remember(memory.localPath) { loadImageFromPath(memory.localPath) }
                PhotoBox(
                    bitmap = localBitmap,
                    label = "Location",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
