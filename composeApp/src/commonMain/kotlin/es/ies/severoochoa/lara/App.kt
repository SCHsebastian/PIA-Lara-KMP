package es.ies.severoochoa.lara

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import es.ies.severoochoa.lara.common.Context
import es.ies.severoochoa.lara.di.appModule
import es.ies.severoochoa.lara.theme.PiaLaraTheme
import es.ies.severoochoa.lara.ui.feature.transcribe.TranscribeScreen
import es.ies.severoochoa.lara.ui.feature.transcribe.viewmodel.TranscibreScreenViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

@Composable
internal fun App(context: Context) {
    KoinApplication(application = {
        modules(appModule(context))
    }) {
        PiaLaraTheme {
            Surface {
                val navController: NavHostController = rememberNavController()
                NavHost(
                    navController,
                    startDestination = "transcribe"
                ) {
                    composable("transcribe") {
                        val viewModel: TranscibreScreenViewModel = koinInject()
                        TranscribeScreen(
                            state = viewModel.state.value,
                            events = viewModel::onEvent
                        )
                    }
                }
            }
        }
    }
}