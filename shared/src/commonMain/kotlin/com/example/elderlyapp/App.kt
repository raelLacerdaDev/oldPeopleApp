package com.example.elderlyapp



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.elderlyapp.camera.rememberCameraManager
import com.example.elderlyapp.ui.intents.MemoryScreenIntents
import com.example.elderlyapp.ui.viewModel.MemoryViewModel
import org.koin.compose.viewmodel.koinViewModel


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import com.example.elderlyapp.ui.components.MemoryCard



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    viewModel: MemoryViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val objectBitmap = remember(state.tempObjectPhoto) { state.tempObjectPhoto?.toImageBitmap() }
    val localBitmap = remember(state.tempLocalPhoto) { state.tempLocalPhoto?.toImageBitmap() }

    val objectCamera = rememberCameraManager { result ->
        viewModel.onIntent(MemoryScreenIntents.CaptureObjectPhoto(result))
    }

    val localCamera = rememberCameraManager { result ->
        viewModel.onIntent(MemoryScreenIntents.CaptureLocalPhoto(result))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Welcome!",
                        style = MaterialTheme.typography.titleLargeEmphasized
                    ) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Text(
                   text =  "Create New Memory",
                   modifier = Modifier.padding(16.dp)
                )

            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(modifier = Modifier.fillMaxSize().weight(1f)) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (state.memories.isEmpty()) {
                    Text(
                        text = "Cannot Find Memories Yet",
                        modifier = Modifier.align(Alignment.Center).padding(32.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.memories) { memory ->
                            MemoryCard(
                                memory = memory,
                                onDeleteClick = { viewModel.onIntent(MemoryScreenIntents.DeleteMemory(memory.id)) }
                            )
                        }
                    }
                }
                if (state.isSaving || state.isDeleting) {
                    Card(
                        modifier = Modifier.align(Alignment.Center),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Row(
                            modifier = Modifier.padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            CircularProgressIndicator(color = Color.White)
                            Text(
                                text = if (state.isSaving) "Saving..." else "Deleting...",
                                color = Color.White
                            )
                        }
                    }
                }
                state.error?.let { errorMsg ->
                    Snackbar(
                        modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp),
                        action = {
                            Button(onClick = { viewModel.onIntent(MemoryScreenIntents.LoadMemories) }) {
                                Text("Try Again")
                            }
                        }
                    ) {
                        Text(errorMsg)
                    }
                }
            }
        }
    }
}
