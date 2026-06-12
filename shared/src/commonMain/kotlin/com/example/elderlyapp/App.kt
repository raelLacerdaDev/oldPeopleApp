package com.example.elderlyapp



import androidx.compose.animation.AnimatedContent
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.backhandler.BackHandler
import com.example.elderlyapp.ui.components.CreationStepScreen
import com.example.elderlyapp.ui.components.MemoryCard
import com.example.elderlyapp.utilities.CreationFlowStep
import elderlyapp.shared.generated.resources.Res
import elderlyapp.shared.generated.resources.ic_arrow_back
import org.jetbrains.compose.resources.painterResource



@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
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

    var currentStep by rememberSaveable { mutableStateOf(CreationFlowStep.NONE) }

    // so pra apresentar ksksksksks saporra ta deprecated
    BackHandler(enabled = currentStep != CreationFlowStep.NONE) {
        currentStep = when (currentStep) {
            CreationFlowStep.LOCAL_PHOTO -> CreationFlowStep.OBJECT_PHOTO
            CreationFlowStep.CONFIRMATION -> CreationFlowStep.LOCAL_PHOTO
            else -> CreationFlowStep.NONE
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (currentStep) {
                            CreationFlowStep.NONE -> "Welcome!"
                            CreationFlowStep.OBJECT_PHOTO -> "Step 1 of 2"
                            CreationFlowStep.LOCAL_PHOTO -> "Step 2 of 2"
                            CreationFlowStep.CONFIRMATION -> "Almost done!"
                        },
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    if (currentStep != CreationFlowStep.NONE) {
                        IconButton(onClick = {
                            currentStep = when (currentStep) {
                                CreationFlowStep.LOCAL_PHOTO -> CreationFlowStep.OBJECT_PHOTO
                                CreationFlowStep.CONFIRMATION -> CreationFlowStep.LOCAL_PHOTO
                                else -> CreationFlowStep.NONE
                            }
                        }) {
                          Icon(painterResource(Res.drawable.ic_arrow_back), contentDescription = "Go Back")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            if (currentStep == CreationFlowStep.NONE) {
                FloatingActionButton(onClick = {
                    currentStep = CreationFlowStep.OBJECT_PHOTO
                }) {
                    Text(
                        text =  "Create New Memory",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AnimatedContent(targetState = currentStep, label = "FlowAnimation") { step ->
                when (step) {
                    CreationFlowStep.NONE -> {
                        Box(modifier = Modifier.fillMaxSize()) {
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
                        }
                    }
                    CreationFlowStep.OBJECT_PHOTO -> {
                        CreationStepScreen(
                            title = "What do you want to remember?",
                            subtitle = "Take a clear picture of the object.",
                            bitmap = objectBitmap,
                            onCaptureClick = { objectCamera.launch() },
                            onNextClick = { currentStep = CreationFlowStep.LOCAL_PHOTO }
                        )
                    }
                    CreationFlowStep.LOCAL_PHOTO -> {
                        CreationStepScreen(
                            title = "Where is it located?",
                            subtitle = "Now, take a picture of the place you stored it.",
                            bitmap = localBitmap,
                            onCaptureClick = { localCamera.launch() },
                            onNextClick = { currentStep = CreationFlowStep.CONFIRMATION }
                        )
                    }
                    CreationFlowStep.CONFIRMATION -> {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("All set?", style = MaterialTheme.typography.headlineMedium)
                            Spacer(modifier = Modifier.height(32.dp))
                            Button(
                                onClick = {
                                    viewModel.onIntent(MemoryScreenIntents.SaveMemoryBundle)
                                    currentStep = CreationFlowStep.NONE
                                },
                                modifier = Modifier.fillMaxWidth().height(64.dp)
                            ) {
                                Text("Save Memory", style = MaterialTheme.typography.titleLarge)
                            }
                        }
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