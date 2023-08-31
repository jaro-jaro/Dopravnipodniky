package cz.jaro.dopravnipodniky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.DestinationsNavHost
import cz.jaro.dopravnipodniky.data.PreferencesDataSource
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.ui.Loading
import cz.jaro.dopravnipodniky.ui.NavGraphs
import cz.jaro.dopravnipodniky.ui.theme.DpTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataSource = get<PreferencesDataSource>()
        val temaFlow = dataSource.dpInfo.map { it.tema }
        val fakeTemaFlow = temaFlow.onEach { delay(6_000) }

        setContent {
            val tema by temaFlow.collectAsStateWithLifecycle(null)
            val fakeTema by fakeTemaFlow.collectAsStateWithLifecycle(null)
            val tutorial by dataSource.tutorial.collectAsStateWithLifecycle(null)

            if (tema != null) DpTheme(
                useDynamicColor = false,
                theme = tema!!
            ) {
                val scope = rememberCoroutineScope()
                if (tutorial is StavTutorialu.Tutorialujeme) {
                    AlertDialog(
                        onDismissRequest = {
                            scope.launch {
                                dataSource.upravitTutorial {
                                    StavTutorialu.Odkliknuto(tutorial!!)
                                }
                            }
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    scope.launch {
                                        dataSource.upravitTutorial {
                                            StavTutorialu.Odkliknuto(tutorial!!)
                                        }
                                    }
                                },
                            ) {
                                if (tutorial == StavTutorialu.Tutorialujeme.Uvod)
                                    Text(stringResource(R.string.pojdme_na_to))
                                else
                                    Text(stringResource(android.R.string.ok))
                            }
                        },
                        dismissButton = {
                            if (tutorial == StavTutorialu.Tutorialujeme.Uvod) TextButton(
                                onClick = {
                                    scope.launch {
                                        dataSource.upravitTutorial {
                                            StavTutorialu.Nic
                                        }
                                    }
                                },
                            ) {
                                Text(stringResource(R.string.preskocit_tutorial))
                            }
                        },
                        icon = {
                            Icon(Icons.Default.Help, null)
                        },
                        title = {
                            Text(stringResource(R.string.tutorial))
                        },
                        text = {
                            Text(stringResource((tutorial as StavTutorialu.Tutorialujeme).text))
                        },
                    )
                }

                DestinationsNavHost(navGraph = NavGraphs.root)
            }
            AnimatedVisibility(
                fakeTema == null,
                enter = slideInVertically(animationSpec = snap(), initialOffsetY = { it }),
                exit = slideOutVertically(animationSpec = tween(300), targetOffsetY = { it }),
            ) {
                Loading()
            }
        }
    }
}
