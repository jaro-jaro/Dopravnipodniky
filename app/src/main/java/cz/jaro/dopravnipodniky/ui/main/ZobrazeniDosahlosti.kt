package cz.jaro.dopravnipodniky.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cz.jaro.compose_dialog.show
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.dialogState
import cz.jaro.dopravnipodniky.shared.barvaDosahnuteDosahlosti
import cz.jaro.dopravnipodniky.shared.barvaSecretDosahlosti
import cz.jaro.dopravnipodniky.shared.composeString
import cz.jaro.dopravnipodniky.shared.formatovat
import cz.jaro.dopravnipodniky.shared.jednotky.asString

fun zobrazitDosahlosti(dosahlosti: List<Dosahlost.NormalniDosahlost>) = dialogState.show(
    confirmButton = {
        TextButton(
            onClick = {
                hide()
            }
        ) {
            Text(stringResource(android.R.string.ok))
        }
    },
    title = {
        Text(stringResource(cz.jaro.dopravnipodniky.R.string.uspechy))
    },
    icon = {
        Icon(Icons.Default.EmojiEvents, null)
    },
    content = {
        Dosahlosti(dosahlosti)
    },
)

@Composable
fun Dosahlosti(
    dosahlosti: List<Dosahlost.NormalniDosahlost>
) {
    val razeneDosahlosti = remember(dosahlosti) {
        Dosahlost.dosahlosti().map { baseDosahlost ->
            dosahlosti.find { it::class == baseDosahlost::class } ?: baseDosahlost
        }.sortedByDescending {
            (it.stav as? Dosahlost.Stav.Splneno)?.kdy
        }.sortedBy {
            when {
                it.stav is Dosahlost.Stav.Splneno -> 0
                it is Dosahlost.Secret -> 2
                else -> 1
            }
        }
    }
    LazyColumn {
        items(razeneDosahlosti) { dosahlost ->
            OutlinedCard(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = when {
                    dosahlost.stav is Dosahlost.Stav.Splneno -> CardDefaults.outlinedCardColors(
                        containerColor = barvaDosahnuteDosahlosti,
                        contentColor = Color.White,
                    )

                    dosahlost is Dosahlost.Secret -> CardDefaults.outlinedCardColors(
                        containerColor = barvaSecretDosahlosti,
                        contentColor = Color.White,
                    )

                    else -> CardDefaults.outlinedCardColors()
                }
            ) {
                Column(
                    Modifier.fillMaxWidth()
                ) {
                    val jmeno = when {
                        dosahlost is Dosahlost.Secret && dosahlost.napoveda == null && dosahlost.stav !is Dosahlost.Stav.Splneno -> "???"
                        else -> stringResource(dosahlost.jmeno)
                    }
                    Text(jmeno, Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp))
                    val popis = when {
                        dosahlost is Dosahlost.Secret && dosahlost.napoveda == null && dosahlost.stav !is Dosahlost.Stav.Splneno -> "?????????"
                        dosahlost is Dosahlost.Secret && dosahlost.stav !is Dosahlost.Stav.Splneno -> stringResource(dosahlost.napoveda!!)
                        else -> stringResource(dosahlost.popis)
                    }
                    Text(popis, Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp))
                    if (dosahlost !is Dosahlost.Secret || dosahlost.stav is Dosahlost.Stav.Splneno) Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                    ) {
                        Text(dosahlost.odmena.asString())
                        Spacer(Modifier.weight(1F))
                        if (dosahlost !is Dosahlost.Pocetni) Unit
                        else {
                            val splneno = when (dosahlost.stav) {
                                is Dosahlost.Stav.Nesplneno -> 0
                                is Dosahlost.Stav.Pocetni -> (dosahlost.stav as Dosahlost.Stav.Pocetni).pocet
                                is Dosahlost.Stav.Splneno -> dosahlost.cil
                            }
                            Text(
                                "${splneno.formatovat(0).composeString()}/${dosahlost.cil.formatovat(0).composeString()}",
                                textAlign = TextAlign.End
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}