package es.ies.severoochoa.lara

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import es.ies.severoochoa.lara.ui.feature.transcribe.TranscribeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    PiaLaraTheme {
        Surface {
            val navController: NavHostController = rememberNavController()
            NavHost(
                navController,
                startDestination = "transcribe"
            ) {
                composable("transcribe") {
                    TranscribeScreen(navController)
                }
            }
        }
    }
}