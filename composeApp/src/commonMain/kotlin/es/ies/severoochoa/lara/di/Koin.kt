package es.ies.severoochoa.lara.di

import es.ies.severoochoa.lara.ui.feature.transcribe.TranscibreScreenViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val viewModelModule = module {
    factoryOf(::TranscibreScreenViewModel)
}

fun initKoin() {
    startKoin {
        modules(
            viewModelModule
        )
    }
}
