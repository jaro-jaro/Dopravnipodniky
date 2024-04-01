package cz.jaro.dopravnipodniky.ui.malovani

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.Dp
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.maZastavku
import cz.jaro.dopravnipodniky.data.dopravnipodnik.seznamKrizovatek
import cz.jaro.dopravnipodniky.data.serializers.DpSerializer
import cz.jaro.dopravnipodniky.shared.barvaPozadi
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.jednotky.metru
import cz.jaro.dopravnipodniky.shared.jednotky.plus
import cz.jaro.dopravnipodniky.shared.jednotky.toDp
import cz.jaro.dopravnipodniky.shared.jednotky.toDpSKrizovatkama
import cz.jaro.dopravnipodniky.shared.jednotky.toPx
import cz.jaro.dopravnipodniky.shared.jednotky.ulicovychBloku
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.ui.linky.vybirani.namalovatVybiraniLinky
import cz.jaro.dopravnipodniky.ui.main.DEBUG_MODE
import kotlinx.serialization.Serializable

typealias SerializableDp = @Serializable(with = DpSerializer::class) Dp

@Composable
fun Mesto(
    malovatBusy: Boolean,
    malovatLinky: Boolean,
    malovatTroleje: Boolean,
    kliklyKrizovatky: List<Pozice<UlicovyBlok>>?,
    dp: DopravniPodnik,
    modifier: Modifier,
    tx: Float,
    ty: Float,
    priblizeni: Float,
) {

//    println(dp.ulicove.map { "(${it.zacatek.x.value} ${it.zacatek.y.value} - ${it.konec.x.value} ${it.konec.y.value})" })

    val nakreslitLinky = remember(
        dp.linky,
        dp.ulice.map { listOf(it.zacatek, it.konec, it.zacatekX, it.zacatekY, it.orientace, it.id, it.maZastavku) },
    ) {
        getNamalovatLinky(dp.linky, dp.ulice)
    }

    val nakreslitBusy = remember(
        dp.busy,
        dp.ulice,
        dp.linky,
    ) {
        dp.busy.map { bus ->
            getNamalovatBus(bus, dp)
        }
    }

    Canvas(
        modifier = modifier,
    ) {
        drawRect(
//            color = dp.tema.darkColorScheme.surface,
//            color = dp.tema.darkColorScheme.surfaceVariant,
            color = barvaPozadi,
            size = size
        )

        scale(
            scale = priblizeni,
        ) {
            translate(
                left = tx + size.center.x,
                top = ty + size.center.y,
            ) {
                if (DEBUG_MODE) drawCircle(
                    color = Color.White,
                    radius = 20.metru.toDp().toPx(),
                    center = Pozice(0.ulicovychBloku).toDpSKrizovatkama().plus(sirkaUlice / 2).toPx(),
                    style = Stroke(
                        width = 2.metru.toDp().toPx()
                    )
                )

                dp.seznamKrizovatek.forEach { (pozice, krizovatka) ->
                    namalovatKrizovatku(dp.ulice, pozice, krizovatka)
                }

                dp.ulice.forEach { ulice ->
                    ulice.baraky.forEach { barak ->
                        barak.draw(dp.info.tema, ulice)
                    }
                }

                dp.ulice.forEach { ulice ->
                    ulice.draw()
                }

                if (malovatBusy) nakreslitBusy.forEach { nakreslitBus ->
                    nakreslitBus()
                }

                if (malovatLinky) nakreslitLinky.forEach { nakreslitKousekLinky ->
                    nakreslitKousekLinky(kliklyKrizovatky != null)
                }

                if (kliklyKrizovatky != null) namalovatVybiraniLinky(
                    kliklyKrizovatky = kliklyKrizovatky,
                    barva = dp.info.tema.barva,
                )

                if (malovatTroleje) {
                    dp.ulice.forEach { ulice ->
                        if (ulice.maTrolej) ulice.nakreslitTroleje()
                    }
                    dp.seznamKrizovatek.forEach { (pozice, krizovatka) ->
                        nakreslitTrolejeNaKrizovatku(dp.ulice, pozice, krizovatka)
                    }
                }
            }
        }
    }
}

fun Offset(x: Float = 0F, y: Float = 0F) = Offset(x, y)