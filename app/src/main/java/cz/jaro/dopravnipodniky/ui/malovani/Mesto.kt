package cz.jaro.dopravnipodniky.ui.malovani

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.Dp
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DPInfo
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.maZastavku
import cz.jaro.dopravnipodniky.data.dopravnipodnik.seznamKrizovatek
import cz.jaro.dopravnipodniky.data.serializers.DpSerializer
import cz.jaro.dopravnipodniky.shared.barvaPozadi
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.ui.linky.vybirani.namalovatVybiraniLinky
import kotlinx.serialization.Serializable

typealias SerializableDp = @Serializable(with = DpSerializer::class) Dp

@Composable
fun Mesto(
    malovatBusy: Boolean,
    malovatLinky: Boolean,
    malovatTroleje: Boolean,
    kliklyKrizovatky: List<Pozice<UlicovyBlok>>,
    ulice: List<Ulice>,
    linky: List<Linka>,
    busy: List<Bus>,
    dpInfo: DPInfo,
    modifier: Modifier,
    tx: Float,
    ty: Float,
    priblizeni: Float,
) {

//    println(dp.ulicove.map { "(${it.zacatek.x.value} ${it.zacatek.y.value} - ${it.konec.x.value} ${it.konec.y.value})" })

    val nakreslitLinky = remember(
        linky,
        ulice.map { listOf(it.zacatek, it.konec, it.zacatekX, it.zacatekY, it.orientace, it.id, it.maZastavku) },
    ) {
        getNamalovatLinky(linky, ulice)
    }

    val nakreslitBusy = remember(
        busy,
        ulice,
        linky,
    ) {
        busy.map { bus ->
            getNamalovatBus(bus, linky, ulice)
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
                ulice.seznamKrizovatek.forEach { krizovatka ->
                    namalovatKrizovatku(ulice, krizovatka)
                }

                ulice.forEach { ulice ->
                    ulice.baraky.forEach { barak ->
                        barak.draw(dpInfo.tema, ulice)
                    }
                }

                ulice.forEach { ulice ->
                    ulice.draw()
                }

                if (malovatBusy) nakreslitBusy.forEach { nakreslitBus ->
                    nakreslitBus()
                }

                if (malovatLinky) nakreslitLinky.forEach { nakreslitKousekLinky ->
                    nakreslitKousekLinky()
                }

                namalovatVybiraniLinky(
                    kliklyKrizovatky = kliklyKrizovatky,
                    barva = dpInfo.tema.barva,
                )

                if (malovatTroleje) {
                    ulice.forEach { ulice ->
                        if (ulice.maTrolej) ulice.nakreslitTroleje()
                    }
                    ulice.seznamKrizovatek.forEach { krizovatka ->
                        nakreslitTrolejeNaKrizovatku(ulice, krizovatka)
                    }
                }
            }
        }
    }
}

fun Offset(x: Float = 0F, y: Float = 0F) = Offset(x, y)