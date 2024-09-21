package es.ies.severoochoa.lara.domain.core

sealed class NetworkState{

   data object Good: NetworkState()

   data object Failed: NetworkState()

}
