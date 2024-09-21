package es.ies.severoochoa.lara.domain.core

sealed class ProgressBarState{

   data object ButtonLoading: ProgressBarState()

   data object ScreenLoading: ProgressBarState()

   data object FullScreenLoading: ProgressBarState()

   data object Idle: ProgressBarState()

}

