package cz.jaro.dopravnipodniky.ui.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.spec.Direction
import cz.jaro.compose_dialog.show
import cz.jaro.dopravnipodniky.BuildConfig
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.Vse
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.data.generace.DetailGeneraceV2
import cz.jaro.dopravnipodniky.data.generace.Generator
import cz.jaro.dopravnipodniky.dialogState
import cz.jaro.dopravnipodniky.shared.Menic
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.je
import cz.jaro.dopravnipodniky.shared.jednotky.penez
import cz.jaro.dopravnipodniky.ui.destinations.DopravniPodnikyScreenDestination
import cz.jaro.dopravnipodniky.ui.theme.Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.pow
import kotlin.random.Random
import kotlin.reflect.KClass


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CombinedIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onLongClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    onClickLabel: String? = null,
    onLongClickLabel: String? = null,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .size(40.0.dp)
            .clip(CircleShape)
            .background(color = if (enabled) colors.containerColor else colors.disabledContainerColor)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
                onDoubleClick = onDoubleClick,
                onClickLabel = onClickLabel,
                onLongClickLabel = onLongClickLabel,
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = 20.0.dp
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        val contentColor = if (enabled) colors.contentColor else colors.disabledContentColor
        CompositionLocalProvider(LocalContentColor provides contentColor, content = content)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
fun MainAppBar(
    dp: DopravniPodnik,
    dosahni: (KClass<out Dosahlost>) -> Unit,
    menic: Menic,
    navigate: (Direction) -> Unit,
    vse: Vse
) {
    TopAppBar(
        title = {
//                    Text("${dp.info.jmenoMesta} — $seed")
            Text(dp.info.jmenoMesta)
        },
        navigationIcon = {
            if (
                !(vse.tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                !(vse.tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                !(vse.tutorial je StavTutorialu.Tutorialujeme.Zastavky) &&
                !(vse.tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                !(vse.tutorial je StavTutorialu.Tutorialujeme.Obchod) &&
                !(vse.tutorial je StavTutorialu.Tutorialujeme.Vypraveni)
            ) CombinedIconButton(
                onClick = {
                    navigate(DopravniPodnikyScreenDestination)
                },
                onLongClick = {

                    dosahni(Dosahlost.Kocka::class)

                    dialogState.show(
                        confirmButton = {
                            TextButton(
                                onClick = ::hide
                            ) {
                                Text(stringResource(R.string.kocka))
                            }
                        },
                        content = {
                            Image(
                                painterResource(R.drawable.super_tajna_vec_doopravdy_na_me_neklikej),
                                stringResource(R.string.kocka),
                                Modifier.clickable {
                                    menic.zmenitPrachy {
                                        if (it < Double.POSITIVE_INFINITY.penez)
                                            Double.POSITIVE_INFINITY.penez
                                        else
                                            12.0.pow(6).penez
                                    }
                                }
                            )
                        },
                    )
                },
            ) {
                Icon(Icons.Default.LocationCity, stringResource(R.string.dopravni_podniky))
            }
        },
        actions = {
            if (
                !(vse.tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                !(vse.tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                !(vse.tutorial je StavTutorialu.Tutorialujeme.Zastavky) &&
                !(vse.tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                !(vse.tutorial je StavTutorialu.Tutorialujeme.Obchod)
            ) IconButton(
                onClick = {
                    zobrazitDosahlosti(vse.dosahlosti)
                }
            ) {
                Icon(Icons.Default.EmojiEvents, stringResource(R.string.uspechy))
            }
            if (BuildConfig.DEBUG) IconButton(
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
                        val novyDP = Generator(
                            detailGenerace = DetailGeneraceV2(
                                investice = 1200000.penez,
                                nazevMestaSeed = 18,
                                michaniSeed = 19250533,
                                sanceSeed = 19250533,
                                barakySeed = 19250533,
                                panelakySeed = 19250533,
                                stredovySeed = 19250533,
                                kapacitaSeed = 19250533,
                                kruhaceSeed = Random.nextInt(),
                            ),
                            tema = Theme.Zelene
                        ) {}
                        menic.zmenitOstatniDopravniPodniky {
                            add(novyDP)
                        }
                        delay(500)
                        menic.zmenitDopravniPodnik(novyDP.info.id)
                    }
                }
            ) {
                Icon(Icons.Default.Refresh, stringResource(R.string.uspechy))
            }
//            if (BuildConfig.DEBUG) IconButton(
//                onClick = {
//                    zrychlovacHry = if (zrychlovacHry == 1F) 60F else 1F
//                }
//            ) {
//                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
//                    Icon(
//                        if (zrychlovacHry == 1F) Icons.AutoMirrored.Filled.Accessible else Icons.AutoMirrored.Filled.AccessibleForward,
//                        stringResource(R.string.uspechy)
//                    )
//                }
//            }
            if (vse.tutorial je StavTutorialu.Tutorialujeme.Uvod) IconButton(
                onClick = {
                    menic.zmenitTutorial {
                        StavTutorialu.Tutorialujeme.Uvod
                    }
                }
            ) {
                Icon(Icons.AutoMirrored.Filled.Help, stringResource(R.string.tutorial))
            }
            if (vse.tutorial je StavTutorialu.Tutorialujeme.Zastavky) IconButton(
                onClick = {
                    menic.zmenitTutorial {
                        StavTutorialu.Tutorialujeme.Zastavky
                    }
                }
            ) {
                Icon(Icons.AutoMirrored.Filled.Help, stringResource(R.string.tutorial))
            }
            if (vse.tutorial je StavTutorialu.Tutorialujeme.Garaz) IconButton(
                onClick = {
                    menic.zmenitTutorial {
                        StavTutorialu.Tutorialujeme.Garaz
                    }
                }
            ) {
                Icon(Icons.AutoMirrored.Filled.Help, stringResource(R.string.tutorial))
            }
            var show by remember { mutableStateOf(false) }
            var debug by remember { mutableStateOf(DEBUG_MODE) }
            var evc by remember { mutableStateOf(vse.nastaveni.automatickyUdelovatEvC) }
            var multi by remember { mutableStateOf(vse.nastaveni.vicenasobnyKupovani) }
            var tema by remember { mutableStateOf(dp.info.tema) }
            IconButton(
                onClick = {
                    show = !show
                }
            ) {
                Icon(Icons.Default.MoreVert, null)
            }
            DropdownMenu(
                expanded = show,
                onDismissRequest = {
                    show = false
                }
            ) {
                if (
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Zastavky) &&
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Obchod)
                ) DropdownMenuItem(
                    text = {
                        Text(stringResource(R.string.resetovat_tutorial))
                    },
                    onClick = {
                        menic.zmenitTutorial {
                            StavTutorialu.Tutorialujeme.Uvod
                        }
                        show = false
                    },
                    leadingIcon = {
                        Icon(Icons.AutoMirrored.Filled.Help, null)
                    }
                )
                if (
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Zastavky) &&
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Obchod)
                ) DropdownMenuItem(
                    text = {
                        Text(stringResource(R.string.nastaveni))
                    },
                    onClick = {
                        dialogState.show(
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        hide()
                                    }
                                ) {
                                    Text(stringResource(android.R.string.ok))
                                }
                            },
                            icon = {
                                Icon(Icons.Default.Settings, null)
                            },
                            title = {
                                Text(stringResource(R.string.nastaveni))
                            },
                            content = {
                                var expanded by remember { mutableStateOf(false) }
                                ExposedDropdownMenuBox(
                                    expanded = expanded,
                                    onExpandedChange = { expanded = !expanded },
                                ) {
                                    OutlinedTextField(
                                        modifier = Modifier
                                            .menuAnchor()
                                            .padding(vertical = 4.dp)
                                            .fillMaxWidth(),
                                        readOnly = true,
                                        value = stringResource(tema.jmeno),
                                        leadingIcon = {
                                            Icon(Icons.Default.Circle, null, tint = tema.barva)
                                        },
                                        onValueChange = {},
                                        label = { Text(stringResource(R.string.tema_aplikace)) },
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = expanded
                                            )
                                        },
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Number,
                                        )
                                    )
                                    ExposedDropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false },
                                    ) {
                                        Theme.entries.forEach { tohleTema ->
                                            DropdownMenuItem(
                                                text = { Text(stringResource(tohleTema.jmeno)) },
                                                onClick = {
                                                    menic.zmenitDPInfo {
                                                        it.copy(
                                                            tema = tohleTema
                                                        )
                                                    }
                                                    tema = tohleTema
                                                    expanded = false
                                                },
                                                leadingIcon = {
                                                    Icon(
                                                        Icons.Default.Circle,
                                                        null,
                                                        tint = tohleTema.barva
                                                    )
                                                },
                                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                            )
                                        }
                                    }
                                }
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        stringResource(R.string.automaticky_prirazovat_ev_c),
                                        Modifier.weight(1F)
                                    )
                                    Switch(
                                        checked = evc,
                                        onCheckedChange = {
                                            menic.zmenitNastaveni { n ->
                                                n.copy(
                                                    automatickyUdelovatEvC = it
                                                )
                                            }
                                            evc = it
                                        }
                                    )
                                }
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        stringResource(R.string.vicenasobne_kupovani),
                                        Modifier.weight(1F)
                                    )
                                    Switch(
                                        checked = multi,
                                        onCheckedChange = {
                                            menic.zmenitNastaveni { n ->
                                                n.copy(
                                                    vicenasobnyKupovani = it
                                                )
                                            }
                                            multi = it
                                        }
                                    )
                                }
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text("DEBUG MÓD", Modifier.weight(1F))
                                    Switch(
                                        checked = debug,
                                        onCheckedChange = {
                                            DEBUG_MODE = !DEBUG_MODE
                                            debug = DEBUG_MODE
                                        }
                                    )
                                }
                            }
                        )
                        show = false
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Settings, null)
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(stringResource(R.string.o_aplikaci))
                    },
                    onClick = {
                        dialogState.show(
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        hide()
                                    }
                                ) {
                                    Text(stringResource(android.R.string.ok))
                                }
                            },
                            icon = {
                                Icon(Icons.Default.Info, null)
                            },
                            title = {
                                Text(stringResource(R.string.o_aplikaci))
                            },
                            content = {
                                Text("Verze aplikace: ${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})")
                                Text("2021-${LocalDate.now().year} RO studios, člen skupiny JARO")
                                Text("2019-${LocalDate.now().year} JARO")
                                Text("Všechna data o busech byla ukradena bez svolení majitelů. Na naše data není v žádném případě spolehnutí,")
                                Text("Simulate crash...", Modifier.clickable {
                                    throw RuntimeException("Test exception")
                                }, fontSize = 10.sp)
                            }
                        )
                        show = false
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Info, null)
                    }
                )
            }
        }
    )
}