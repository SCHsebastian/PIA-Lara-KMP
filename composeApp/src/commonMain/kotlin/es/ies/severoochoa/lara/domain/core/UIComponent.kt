package es.ies.severoochoa.lara.domain.core

sealed class UIComponent {

    data class ToastSimple(
        val title:String,
    ): UIComponent()

    data class DialogSimple(
        val title:String,
        val description:String
    ): UIComponent()

}