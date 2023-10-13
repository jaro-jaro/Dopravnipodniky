package cz.jaro.dopravnipodniky.ui.dopravnipodniky

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.NumberPicker
import cz.jaro.compose_dialog.show
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Trakce
import cz.jaro.dopravnipodniky.data.dopravnipodnik.dobaOdPoslednihoHrani
import cz.jaro.dopravnipodniky.data.dopravnipodnik.jsouVsechnyZatrolejovane
import cz.jaro.dopravnipodniky.data.dopravnipodnik.linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.maZastavku
import cz.jaro.dopravnipodniky.data.dopravnipodnik.nevyzvednuto
import cz.jaro.dopravnipodniky.data.dopravnipodnik.plocha
import cz.jaro.dopravnipodniky.data.dopravnipodnik.typMesta
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.urovenMesta
import cz.jaro.dopravnipodniky.dialogState
import cz.jaro.dopravnipodniky.shared.LongPressButton
import cz.jaro.dopravnipodniky.shared.Menic
import cz.jaro.dopravnipodniky.shared.composeString
import cz.jaro.dopravnipodniky.shared.formatovat
import cz.jaro.dopravnipodniky.shared.jednotky.asString
import cz.jaro.dopravnipodniky.shared.jednotky.penez
import cz.jaro.dopravnipodniky.shared.kremze
import cz.jaro.dopravnipodniky.shared.minutes
import cz.jaro.dopravnipodniky.shared.prodejniCenaCloveka
import cz.jaro.dopravnipodniky.shared.vecne
import cz.jaro.dopravnipodniky.snackbarHostState
import cz.jaro.dopravnipodniky.ui.main.DEBUG_TEXT
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun DopravniPodnik(
    tentoDP: DopravniPodnik,
    dp: DopravniPodnik,
    menic: Menic,
) {
    val res = LocalContext.current.resources
    val scope = rememberCoroutineScope()

    if (tentoDP.info.id != dp.info.id) Text(
        text = stringResource(R.string.nevyzvednuto, dp.info.nevyzvednuto.asString()),
        Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
    )
    if (tentoDP.info.id != dp.info.id) Text(
        text = stringResource(
            R.string.doba_od_posledniho_otevreni,
            dp.info.dobaOdPoslednihoHrani.minutes.formatovat(0).composeString()
        ),
        Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
    )
    Text(
        text = stringResource(
            R.string.zisk,
            dp.info.zisk.asString(),
        ),
        Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
    )
    Text(
        text = stringResource(
            R.string.jizdne,
            dp.info.jizdne.asString(),
        ),
        Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
    )
    Text(
        text = stringResource(
            R.string.typ_mesta,
            dp.typMesta.composeString(),
        ),
        Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
    )
    Text(
        text = stringResource(
            R.string.uroven_mesta,
            dp.urovenMesta.formatovat().composeString(),
        ),
        Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
    )
    if (DEBUG_TEXT) Text(
        text = "Potenciál města: ${dp.ulice.sumOf { it.potencial }.formatovat(0).composeString()}",
        Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
    )
    Text(
        text = stringResource(
            R.string.jedoucich_vozidel,
            dp.busy.count {
                it.linka != null && (it.typBusu.trakce !is Trakce.Trolejbus || dp.linka(it.linka)
                    .ulice(dp).jsouVsechnyZatrolejovane())
            },
            dp.busy.count(),
        ),
        Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
    )
    Text(
        text = stringResource(
            R.string.rozloha,
            dp.plocha.value.formatovat().composeString(),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
    )
    Text(
        text = stringResource(
            R.string.pocet_cloveku,
            dp.cloveci.formatovat().composeString(),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
    )
    Text(
        text = stringResource(
            R.string.pocet_ulic,
            dp.ulice.count().formatovat().composeString(),
            dp.ulice.count { it.maTrolej }.formatovat().composeString(),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
    )
    Text(
        text = stringResource(
            R.string.pocet_domu,
            dp.ulice.sumOf { it.baraky.size }.formatovat().composeString(),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
    )
    Text(
        text = stringResource(
            R.string.pocet_zastavek,
            dp.ulice.count { it.maZastavku }.formatovat().composeString(),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
    )

    val oddelavaciCena = prodejniCenaCloveka * dp.cloveci + dp.busy.sumOf { it.prodejniCena.value }.penez

    var vybraneJizdne by rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(dp.info.jizdne) {
        vybraneJizdne = dp.info.jizdne.value.toInt()
    }

    Row(
        Modifier.fillMaxWidth(),
    ) {
        if (tentoDP.info.id != dp.info.id) LongPressButton(
            Modifier
                .padding(all = 8.dp),
            onClick = {
                dialogState.show(
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val actualCena = oddelavaciCena * Random.nextDouble(.5, 1.5)
                                MainScope().launch {

                                    menic.zmenitOstatniDopravniPodniky {
                                        removeAt(indexOfFirst { it.info.id == dp.info.id })
                                    }

                                    menic.zmenitPrachy {
                                        it + actualCena
                                    }

                                    hide()

                                    with(res) {
                                        if (dp.info.jmenoMesta == vecne) snackbarHostState.showSnackbar(
                                            message = getString(R.string.vecne_neni_vecne),
                                            duration = SnackbarDuration.Long,
                                            withDismissAction = true,
                                        )
                                        snackbarHostState.showSnackbar(
                                            message = getString(
                                                R.string.prodali_jste_dp,
                                                dp.info.jmenoMesta,
                                                getString(
                                                    R.string.kc,
                                                    actualCena.value.formatovat(0).asString()
                                                )
                                            ),
                                            duration = SnackbarDuration.Indefinite,
                                            withDismissAction = true,
                                        )
                                    }
                                }
                            }
                        ) {
                            Text(stringResource(R.string.prodat_dp))
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                hide()
                            }
                        ) {
                            Text(stringResource(R.string.zrusit))
                        }
                    },
                    title = {
                        Text(stringResource(R.string.prodat_dp))
                    },
                    content = {
                        Text(stringResource(R.string.za_prodej_dp_dostanete, oddelavaciCena.asString()))
                    },
                )
            },
            onLongPress = {
                scope.launch {
                    if (dp.info.jmenoMesta == kremze)
                        menic.zmenitOstatniDopravniPodniky {
                            val i = indexOfFirst { it.info.id == dp.info.id }
                            this[i] = this[i].copy(
                                info = this[i].info.copy(
                                    jmenoMesta = vecne
                                )
                            )
                        }
                }
            },
        ) {
            Text(
                text = stringResource(R.string.prodat)
            )
        }
        Spacer(modifier = Modifier.weight(1F))
        OutlinedButton(
            onClick = {
                dialogState.show(
                    confirmButton = {
                        TextButton(
                            onClick = {
                                scope.launch {
                                    when {
                                        tentoDP.info.id == dp.info.id -> menic.zmenitDPInfo {
                                            it.copy(
                                                jizdne = vybraneJizdne.penez,
                                            )
                                        }

                                        else -> menic.zmenitOstatniDopravniPodniky {
                                            val i = indexOfFirst { it.info.id == dp.info.id }
                                            this[i] = this[i].copy(
                                                info = this[i].info.copy(
                                                    jizdne = vybraneJizdne.penez,
                                                )
                                            )
                                        }
                                    }
                                }
                                hide()
                            }
                        ) {
                            Text(stringResource(R.string.potvrdit))
                        }
                    },
                    dismissButton = {
//                        TextButton(
//                            onClick = {
//
//                            }
//                        ) {
//                            Text(stringResource(R.string.pruzkum_mineni))
//                        }
                    },
                    title = {
                        Text(stringResource(R.string.zmenit_jizdne))
                    },
                    content = {
                        Surface {
                            Box(
                                Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center,
                            ) {
                                NumberPicker(
                                    value = vybraneJizdne,
                                    onValueChange = { noveJizdne ->
                                        vybraneJizdne = noveJizdne
                                    },
                                    range = 0..100,
                                    dividersColor = LocalContentColor.current,
                                    textStyle = TextStyle(
                                        color = LocalContentColor.current,
                                    ),
                                    modifier = Modifier,
                                )
                            }
                        }
                    },
                )
            },
            Modifier
                .padding(all = 8.dp),
        ) {
            Text(
                text = stringResource(R.string.zmenit_jizdne)
            )
        }
    }
}