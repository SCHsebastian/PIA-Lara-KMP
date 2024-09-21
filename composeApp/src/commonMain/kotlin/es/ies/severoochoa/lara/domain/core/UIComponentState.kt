package es.ies.severoochoa.lara.domain.core

/**
 * A generic class for hiding/showing some ui component.
 */
sealed class UIComponentState {

   data object Show: UIComponentState()
   data object Hide: UIComponentState()

}
