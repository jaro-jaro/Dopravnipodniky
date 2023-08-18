package cz.jaro.dopravnipodniky

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.classes.DopravniPodnik
import cz.jaro.dopravnipodniky.classes.maZastavku
import cz.jaro.dopravnipodniky.classes.pocetLinek
import cz.jaro.dopravnipodniky.jednotky.bloku
import cz.jaro.dopravnipodniky.jednotky.metru
import cz.jaro.dopravnipodniky.jednotky.penez
import cz.jaro.dopravnipodniky.jednotky.penezZaMin
import cz.jaro.dopravnipodniky.jednotky.toTiky
import cz.jaro.dopravnipodniky.theme.black
import cz.jaro.dopravnipodniky.theme.blue_500
import cz.jaro.dopravnipodniky.theme.blue_900
import cz.jaro.dopravnipodniky.theme.brown_500
import cz.jaro.dopravnipodniky.theme.cyan_300
import cz.jaro.dopravnipodniky.theme.cyan_500
import cz.jaro.dopravnipodniky.theme.cyan_900
import cz.jaro.dopravnipodniky.theme.green_500
import cz.jaro.dopravnipodniky.theme.green_900
import cz.jaro.dopravnipodniky.theme.lightBlue_500
import cz.jaro.dopravnipodniky.theme.lightGreen_500
import cz.jaro.dopravnipodniky.theme.orange_500
import cz.jaro.dopravnipodniky.theme.pink_500
import cz.jaro.dopravnipodniky.theme.purple_300
import cz.jaro.dopravnipodniky.theme.purple_500
import cz.jaro.dopravnipodniky.theme.purple_900
import cz.jaro.dopravnipodniky.theme.red_300
import cz.jaro.dopravnipodniky.theme.red_500
import cz.jaro.dopravnipodniky.theme.red_900
import cz.jaro.dopravnipodniky.theme.yellow_300
import cz.jaro.dopravnipodniky.theme.yellow_500
import cz.jaro.dopravnipodniky.theme.yellow_900
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


/**
 * 0.1 Busy HOTOVO
 *
 * 0.2 Obchod HOTOVO
 *
 * 0.3 Začátky s penízkama HOTOVO
 *
 * 0.4 Zastávky HOTOVO
 *
 * 0.5 Vytváření linek  HOTOVO
 *
 * 0.5.1 Vybírání a zobrazování linek HOTOVO
 *
 * 0.6 Ježdění busíků HOTOVO
 *
 * 0.7 Další penízky HOTOVO
 *
 * 0.8 Priblizovani, oddalovani, posouvání, nuda, hodne chyb HOTOVO
 *
 * 0.9 Generování města ZATÍM SNAD HOTOVO
 *
 * 0.10 Zastavování na zastávkách + Projíždění (ne to, co už tam je) HOTOVO
 *
 * 0.11 Dopravní podniky HOTOVO
 *
 * 0.11.1 zastavky na LongClick (EDIT: Ten nakonec ne, nahradil ho Editor) a s dialogem HOTOVO
 *
 * 0.12 Detaily zisku HOTOVO
 *
 * 0.13 Už zas penízky HOTOVO
 *
 * 1.0 Dodělat všechna todocka, Hotovo? Hotovo!!
 *
 * 1.1 Trolejové tratě
 *
 * 1.1.1 Vícenásobné kupování busů
 *
 * 1.1.2 Editor a rozprostření busů na lince
 *
 * 1.2 Material You
 *
 * 1.2.1 Achievementy + Platba za zastávky a troleje
 *
 * 1.3 Filtrování obchodu
 *
 * 1.3.1 Táhnutí linky
 *
 * 1.4 Tutoriál
 *
 * 1.4.1 Propojení trolejí
 *
 *
 * 1.5 Mimořádnosti
 *
 * 1.6 Dodělat všechna todočka, Hotová základní hra
 *
 * 2.0 Domy
 *
 * 2.0.1 Řazení obchodu
 *
 * 2.0.2 Animace kalibrace
 *
 * 2.0.3 Středové domy
 *
 * 2.1 Ceny jízdenek
 *
 * 2.2 Cestující, Kapacity zastávek
 *
 * 2.2.1 Kalibrace busu
 *
 * 2.2.2 Překlad do angličtiny
 *
 * 2.3 Sídliště
 *
 * 2.4 Rozšiřování města
 *
 * 2.? Dodělat všechna todočka, Hotový "Město update"
 *
 * 3.0 Čas
 *
 * 3.1 Zdražování busů
 *
 * 3.? Servis + pohonné hmoty
 *
 * 3.? Dodělat všechna todočka, Hotový "Čas update"
 *
 * 4.? Questy
 *
 * 4.? Minihry
 *
 * 4.? Statistiky
 *
 * 4.? Porovnávání s kámíkama
 *
 * 4.? Trendy
 *
 * 4.? Dodělat všechna todočka, Hotový "Porovnávání update"
 *
 *
 * ?.? Custom barvy
 *
 */
// TODO 2.2 Cestující, Kapacity zastávek

const val todocka = 0

val minimumInvestice = 1_000_000L.penez
val pocatecniCenaMesta = 3_141_592.penez//1_200_000L.penez

// vykreslovani

const val pocatecniPriblizeni = .75F
const val oddalenyRezim = .6F
const val maximalniOddaleni = .2F
val pocatecniPosunutiX = 0.dp//200
val pocatecniPosunutiY = 0.dp//600

val ulicovyBlok = 150.bloku

val sirkaUlice = 34.bloku
val velikostZastavky = 48.bloku
val odsazeniBaraku = 5.bloku
const val barakuVUlici = 5
val velikostBaraku = (ulicovyBlok - odsazeniBaraku * (barakuVUlici + 1)) / barakuVUlici
val sirkaObrubniku = 3.bloku
val sirkaBusu = 10.bloku

val sirkaTroleje = 1.bloku
val predsazeniTrolejiS = 8.bloku
val predsazeniTrolejiL = 28.bloku
val rozchodTroleji = 5.bloku
val odsazeniTroleje = 6.bloku
val odsazeniPrvniTroleje = odsazeniTroleje
val odsazeniDruheTroleje = odsazeniTroleje + rozchodTroleji
val odsazeniTretiTroleje = odsazeniDruheTroleje + odsazeniTroleje + odsazeniTroleje
val odsazeniCtvrteTroleje = odsazeniTretiTroleje + rozchodTroleji
val odsazeniTroleji = listOf(odsazeniPrvniTroleje, odsazeniDruheTroleje, odsazeniTretiTroleje, odsazeniCtvrteTroleje)

// OOO----BBTBBBBTBB-------BBTBBBBTBB----OOO

val CERNA = Color(0, 0, 0)
val BILA = Color(255, 255, 255)
val sedUlice = Color(135, 135, 135)
val sedPozadi = Color(16, 16, 16)
val sedZastavky = Color(200, 200, 200)
val sedObrubniku = Color(200, 200, 200)
val sedTroleje = Color(32, 32, 32)
val sedBaraku = Color(150, 150, 150)
val ZLUTA = Color(255, 255, 0)

const val velikostUlicovyhoBloku = 150 // bloku

// další konstanty

const val TPS = 30
const val TPM = TPS * 60
const val TPH = TPM * 60

const val nasobitelDelkyBusu = 2F
val delkaUlice = 100.metru
val pocatecniObnosPenez = 150_000.penez
const val nasobitelZiskuPoOffline = 1 / 20.0
const val nasobitelZisku = 1
const val nasobitelUrovne = 1 / 1.0
const val nasobitelRozsahlosti = 200.0
const val nasobitelMaxCloveku = PI / 2
const val nahodnostVymreniKazdouMinutu = 30
const val nahodnostVymreniKazdyTik = nahodnostVymreniKazdouMinutu * TPM
const val nahodnostNarozeniKazdyTik = (1.5 * nahodnostVymreniKazdyTik).toInt()
val dobaKalibraceS = 1.seconds
val dobaKalibraceT = dobaKalibraceS.toTiky()
val dobaPobytuNaZastavceS = 1.seconds
val dobaPobytuNaZastavceT = dobaPobytuNaZastavceS.toTiky()
val cenaZastavky = 5_000.penez
val cenaTroleje = 20_000.penez
val udrzbaZastavky = 100.penez
val udrzbaTroleje = 1_000.penez
val bonusoveVydajeZaNeekologickeBusy = 800.penezZaMin
val bonusoveVydajeZaPoloekologickeBusy = 400.penezZaMin
const val nahodnostProjetiZastavky = 200
const val nahodnostKamionuKazdouMinutu = 45
const val nahodnostKamionuKazdyTik = nahodnostKamionuKazdouMinutu * TPM
const val nahodnostSebevrazdyKazdouMinutu = 10
const val nahodnostSebevrazdyKazdyTik = nahodnostSebevrazdyKazdouMinutu * TPS * 60
val cenaPruzkumuVerejnehoMineni = 50_000.penez

// ---------------- SEZNAMY -----------------

val BARVICKY /*jeej*/ = listOf(
    Triple(black, R.string.cerna, false),
    Triple(red_500, R.string.cervena, false),
    Triple(green_500, R.string.zelena, true),
    Triple(blue_500, R.string.modra, false),
    Triple(yellow_500, R.string.zluta, true),
    Triple(cyan_500, R.string.tyrkysova, true),
    Triple(purple_500, R.string.fialova, true),
    Triple(red_900, R.string.tmave_cervena, false),
    Triple(green_900, R.string.tmave_zelena, false),
    Triple(blue_900, R.string.tmave_modra, false),
    Triple(yellow_900, R.string.tmave_zluta, false),
    Triple(cyan_900, R.string.tmave_tyrkysova, false),
    Triple(purple_900, R.string.tmave_fialova, false),
    Triple(red_300, R.string.svetle_cervena, true),
    Triple(lightGreen_500, R.string.svetle_zelena, false),
    Triple(lightBlue_500, R.string.svetle_modra, true),
    Triple(yellow_300, R.string.svetle_zluta, true),
    Triple(cyan_300, R.string.svetle_tyrkysova, true),
    Triple(purple_300, R.string.svetle_fialova, true),
    Triple(pink_500, R.string.ruzova, false),
    Triple(orange_500, R.string.oranzova, false),
    Triple(brown_500, R.string.hneda, false),
)

// ---------------- FUNKCE -----------------
/**
 * Zformátuje číslo, aby vypadalo hezky
 */
fun Number.formatovat(): Text {
    if (this == Double.POSITIVE_INFINITY.toLong()) return R.string.nekonecne_mnoho.toText()
    if (this == Double.NEGATIVE_INFINITY.toLong()) return R.string.nekonecne_malo.toText()

    val vysledek = mutableListOf<Char>()

    this.toString().toList().reversed().forEachIndexed { i, cislice ->
        vysledek += cislice

        if ((i + 1) % 3 == 0) {
            vysledek += " ".single()
        }
    }

    vysledek.reverse()

    return vysledek.joinToString("").removePrefix(" ").toText()
}

/**
 * Vrátí náklady busu slovy
 *
 * Pro trolejbusy odpovídá dvojnásobku
 */
fun naklady(naklady: Double, trolejbus: Boolean = false): Int {

    val nakladove = if (trolejbus) naklady * 2 else naklady

    return when {

        nakladove < 50 -> R.string.velmi_nizke
        nakladove < 60 -> R.string.hodne_nizke
        nakladove < 65 -> R.string.nizke
        nakladove < 70 -> R.string.pomerne_nizke
        nakladove < 75 -> R.string.snizene
        nakladove < 80 -> R.string.normalni
        nakladove < 85 -> R.string.pomerne_vysoke
        nakladove < 90 -> R.string.vysoke
        nakladove < 95 -> R.string.hodne_vysoke
        nakladove < 100 -> R.string.velmi_vysoke
        nakladove < 500 -> R.string.muzejni_bus
        else -> R.string.JOSTOVSKE

    }
}

/**
 * Vrátí úroveň města na základě jeho počtu obyvatel, počtu potenciálů, počtu ulic a nepřímo i plochy
 */

fun uroven(plocha: Int, obyv: Int, ulice: Int, potencialy: Int): Int =
    (obyv / plocha.toDouble() * potencialy * ulice * nasobitelUrovne).roundToInt()

/**
 * Vrátí velikost města na základě jeho plochy, počtu obyvatel a úrovně
 */

fun velkomesto(plocha: Int, obyvatel: Int, uroven: Int): Text = when {
    plocha >= 500_000_000 && obyvatel >= 5_000_000 && uroven >= 10_000_000 -> R.string.mesto_jostless
    plocha >= 25_000_000 && obyvatel >= 500_000 && uroven >= 500_000 -> R.string.super_mesto
    plocha >= 15_000_000 && obyvatel >= 250_000 && uroven >= 100_000 -> R.string.ultra_velkomesto
    plocha >= 5_000_000 && obyvatel >= 150_000 && uroven >= 25_000 -> R.string.velkomesto
    plocha >= 2_500_000 && obyvatel >= 40_000 && uroven >= 600 -> R.string.velke_mesto
    plocha >= 1_500_000 && obyvatel >= 10_000 && uroven >= 300 -> R.string.mesto
    plocha >= 500_000 && obyvatel >= 4_000 && uroven >= 40 -> R.string.mestecko
    else -> R.string.vesnice
}.toText()


/**
 * Udělá ideální barvu pozadí cardviewu za daných kritérií
 *
 * 2 -> výrazně
 *
 * 4 -> normálně
 *
 * 6 -> tmavě
 */

//fun pozadi(delitel: Int): Int {
//    return Color.rgb(vse.barva.red / delitel, vse.barva.green / delitel, vse.barva.blue / delitel)
//}

/**
 * Vrátí násobitel náhodnosti, vytvořené z kapacity busu a kapacity zastávky, udávající, kolik lidí je ochotno nastoupit do busu závisle na ceně jízdenky a rozsáhlosti dopravního podniku
 */

fun nasobitelPoctuLidiKteryTiNastoupiDoBusuNaZastavceKdyzZastaviANakyLidiTamJsouAMaVSobeJesteVolneMisto(dp: DopravniPodnik): Double {

    val naRozsahlosti = sqrt(1.0 - (1.0 / (dp.busy.size.coerceAtLeast(1) + 1))) *
            (dp.ulicove.filter { it.maZastavku }.count { it.pocetLinek(dp) != 0 }.toDouble() /
                    dp.ulicove.size.toDouble())

    var naJizdnem = 1.0 -
            abs(
                .6 -
                        abs(
                            .5 -
                                    ((dp.jizdne.value - nasobitelRozsahlosti * naRozsahlosti) /
                                            (nasobitelRozsahlosti * naRozsahlosti / 2.0)
                                            ).pow(2)
                        )
            )

    if (dp.jizdne.value <= nasobitelRozsahlosti * naRozsahlosti * 2 / 5.0) {
        naJizdnem = 4 / 9.0
    }

    //Log.d("funguj", "---${dp.jizdne}/${(naRozsahlosti * nasobitelRozsahlosti).roundToInt()}---")
    //Log.d("funguj", naJizdnem.toString())
    //Log.d("funguj", naRozsahlosti.toString())
    //Log.d("funguj", sqrt(naJizdnem.coerceAtLeast(.0)).toString())

    return sqrt(naJizdnem.coerceAtLeast(.0))
}

fun soucinPromenneNaRozsahlostiVNasobiteliPoctuLidiKteryTiNastoupiDoBusuNaZastavceKdyzZastaviANakyLidiTamJsouAMaVSobeJesteVolneMistoANasobiteleRozsahlosti(
    dp: DopravniPodnik
): Double {


    return sqrt(1.0 - (1.0 / (dp.busy.size.coerceAtLeast(1) + 1))) *
            (dp.ulicove.filter { it.maZastavku }.count { it.pocetLinek(dp) != 0 }.toDouble() /
                    dp.ulicove.size.toDouble()) *
            nasobitelRozsahlosti
}


val Duration.minutes get() = inWholeSeconds / 60.0
val Duration.hours get() = minutes / 60.0