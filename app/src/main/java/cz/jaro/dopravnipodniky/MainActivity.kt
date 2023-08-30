package cz.jaro.dopravnipodniky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import cz.jaro.dopravnipodniky.ui.NavGraphs
import cz.jaro.dopravnipodniky.ui.theme.DpTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataSource = get<PreferencesDataSource>()

        setContent {
            val dpInfo by dataSource.dpInfo.collectAsStateWithLifecycle(null)
            val tutorial by dataSource.tutorial.collectAsStateWithLifecycle(null)

            if (dpInfo != null && tutorial != null) DpTheme(
                useDynamicColor = false,
                theme = dpInfo!!.tema
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
        }
    }
}
