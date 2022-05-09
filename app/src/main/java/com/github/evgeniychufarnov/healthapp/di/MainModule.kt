package com.github.evgeniychufarnov.healthapp.di

import com.github.evgeniychufarnov.healthapp.data.RecordsRepository
import com.github.evgeniychufarnov.healthapp.domain.IRecordsRepository
import com.github.evgeniychufarnov.healthapp.ui.adding.DialogFragmentAddingVm
import com.github.evgeniychufarnov.healthapp.ui.main.MainActivityVm
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single<IRecordsRepository> { RecordsRepository(FirebaseFirestore.getInstance()) }
    viewModel { MainActivityVm(get()) }
    viewModel { DialogFragmentAddingVm(get()) }
}