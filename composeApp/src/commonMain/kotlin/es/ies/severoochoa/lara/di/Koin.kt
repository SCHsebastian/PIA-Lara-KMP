package es.ies.severoochoa.lara.di

import es.ies.severoochoa.lara.common.Context
import es.ies.severoochoa.lara.domain.core.AppDataStore
import es.ies.severoochoa.lara.domain.core.AppDataStoreManager
import es.ies.severoochoa.lara.ui.feature.transcribe.viewmodel.TranscibreScreenViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun appModule(context: Context) = module{
    single<AppDataStore> { AppDataStoreManager(context) }
    factoryOf(::TranscibreScreenViewModel)
}