package es.ies.severoochoa.lara

import androidx.compose.ui.window.ComposeUIViewController
import es.ies.severoochoa.lara.common.Context

fun MainViewController() = ComposeUIViewController { App(Context()) }