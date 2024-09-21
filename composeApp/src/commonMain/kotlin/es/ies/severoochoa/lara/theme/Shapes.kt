package es.ies.severoochoa.lara.theme

import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun DefaultButtonWithBorderPrimaryTheme() = buttonColors(
    containerColor = MaterialTheme.colorScheme.background,
    contentColor = MaterialTheme.colorScheme.primary,
    disabledContainerColor =  MaterialTheme.colorScheme.background,
    disabledContentColor = MaterialTheme.colorScheme.primary
)

@Composable
fun DefaultButtonTheme() = buttonColors(
    containerColor = MaterialTheme.colorScheme.primary,
    contentColor = MaterialTheme.colorScheme.background,
    disabledContentColor = MaterialTheme.colorScheme.primary
)