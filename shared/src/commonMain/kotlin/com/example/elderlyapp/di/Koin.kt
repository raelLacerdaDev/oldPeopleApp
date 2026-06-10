package com.example.elderlyapp.di



import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.elderlyapp.data.AppDatabase
import com.example.elderlyapp.data.repositories.MemoryRepository
import com.example.elderlyapp.ui.executors.MemoryScreenExecutor
import com.example.elderlyapp.ui.intents.MemoryScreenIntents
import com.example.elderlyapp.ui.reducer.MemoryScreenReducer
import com.example.elderlyapp.ui.states.MemoryScreenState
import com.example.elderlyapp.ui.store.MemoryStore
import com.example.elderlyapp.ui.viewModel.MemoryViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


val sharedModule = module {
    single { get<AppDatabase>().getDao() }
    single { MemoryRepository(get(), get()) }
    single<StoreFactory> { DefaultStoreFactory() }
    factory<MemoryStore> {
        val storeFactory: StoreFactory = get()
        val repository: MemoryRepository = get()

        object : MemoryStore, Store<MemoryScreenIntents, MemoryScreenState, Nothing> by storeFactory.create(
            name = "MemoryStore",
            initialState = MemoryScreenState(),
            executorFactory = { MemoryScreenExecutor(repository)},
            reducer = MemoryScreenReducer()
        ){}
    }
    viewModel { MemoryViewModel(store = get()) }
}


expect val platformModule: Module


fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(sharedModule, platformModule)
    }
}

object KoinInitializer {
    fun start() {
        initKoin {}
    }
}