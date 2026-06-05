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
import androidx.compose.material3.FloatingActionButton
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
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.example.elderlyapp.camera.rememberCameraManager
import com.example.elderlyapp.gallery.SharedImage
import com.example.elderlyapp.ui.components.MemoryCard
import com.example.elderlyapp.ui.intents.MemoryScreenIntents
import com.example.elderlyapp.ui.states.MemoryScreenState
import com.example.elderlyapp.ui.store.MemoryStore
import org.koin.compose.koinInject


@Composable
fun App(
    store: MemoryStore = koinInject()
) {

    val state by store.states.collectAsStateWithLifecycle(initialValue = MemoryScreenState())

    var tempObjectPhoto by remember { mutableStateOf<SharedImage?>(null) }
    var tempLocalPhoto by remember { mutableStateOf<SharedImage?>(null) }

    val objectCamera = rememberCameraManager { result ->
        if (result != null) tempObjectPhoto = result
    }
    val localCamera = rememberCameraManager { result ->
        if (result != null) {
            tempLocalPhoto = result
            tempObjectPhoto?.let { obj ->
                store.accept(MemoryScreenIntents.CreateMemory(objectPhoto = obj, localPhoto = result))
            }
        }
    }

    LaunchedEffect(state.isSaving) {
        if (!state.isSaving && tempObjectPhoto != null && tempLocalPhoto != null && state.error == null) {
            tempObjectPhoto = null
            tempLocalPhoto = null
        }
    }
    LaunchedEffect(Unit) {
        store.accept(MemoryScreenIntents.LoadMemories)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("To Remember")},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    when {
                        tempObjectPhoto == null -> objectCamera.launch()
                        tempLocalPhoto == null -> localCamera.launch()
                    }
                }
            ){
                val buttonText = when {
                    tempObjectPhoto == null -> "Object Photo"
                    tempLocalPhoto == null -> "Local Photo"
                    else -> "Saving"
                }
                Text(
                    text = buttonText,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(it)) {
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
                    items(state.memories){ memory ->
                        MemoryCard(memory)
                    }
                }
            }

            if (state.isSaving) {
                Card(
                    modifier = Modifier.align(Alignment.Center),
                    colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.7f))
                ) {
                    Row(
                        modifier = Modifier.padding(24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CircularProgressIndicator(color = Color.White)
                        Text("Saving...", color = Color.White)
                    }
                }
            }

            state.error?.let { errorMsg ->
                Snackbar(
                    modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp),
                    action = {
                        Button(
                            onClick = {
                                store.accept(MemoryScreenIntents.LoadMemories)
                            }
                        ){
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