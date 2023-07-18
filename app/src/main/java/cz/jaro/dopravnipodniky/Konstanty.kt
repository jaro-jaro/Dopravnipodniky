package cz.jaro.dopravnipodniky

import android.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import cz.jaro.dopravnipodniky.classes.DopravniPodnik
import cz.jaro.dopravnipodniky.other.App
import cz.jaro.dopravnipodniky.other.PrefsHelper.vse
import processing.core.PApplet
import processing.core.PConstants.HALF_PI
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt


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

// generace města

const val minimumInvestice = 1_000_000L
const val pocatecniCenaMesta = 1_200_000L // prachy
const val nasobitelInvestice = 1 / 65536.0 // prachy
const val nahodnostPoObnoveni = .35F
const val nahodnostNaZacatku = .5F
const val rozdilNahodnosti = .05F

const val nasobitelRedukce = .75F

// vykreslovani

const val pocatecniPriblizeni = .75F
const val pocatecniPosunutiX = 200F
const val pocatecniPosunutiY = 600F

const val sirkaUlice = 30 // bloku
const val velikostZastavky = 48
const val velikostBaraku = 24 // bloku
const val sirkaObrubniku = 1F
const val sirkaBusu = 10F
const val odsazeniBaraku = 3F

// O---BBTBBBBTBB----BBTBBBBTBB---O

const val CERNA = 0F
const val BILA = 255F
const val sedUlice = 135F
const val sedPozadi = 16F
const val sedZastavky = 200F
const val sedObrubniku = 200F
const val sedTroleje = 32F
const val sedBaraku = 150F
val ZLUTA = PApplet().color(255, 255, 0)

const val velikostUlicovyhoBloku = 150 // bloku

// další konstanty

const val TPS = 30
const val TPM = TPS * 60
const val TPH = TPM * 60

const val nasobitelDelkyBusu = 2F
const val uliceMetru = 100.0
const val pocatecniObnosPenez = 150_000.0  // prachy
const val nasobitelRychlosti = 1 / 60F
const val nasobitelZiskuPoOffline = 1 / 20.0  // prachy
const val nasobitelZisku = 1.0  // prachy
const val nasobitelUrovne = 1 / 1.0
const val nasobitelRozsahlosti = 200.0
const val nasobitelMaxCloveku = HALF_PI
const val dobaVymreni = 30 * TPM
const val dobaZnovuobnoveniPopulace = (1.5 * dobaVymreni).toInt()
const val dobaKalibraceS = 1 // sekunda
const val dobaKalibraceT = dobaKalibraceS * TPS // sekunda
const val dobaPobytuNaZastavceS = 1F // s
const val dobaPobytuNaZastavceT = dobaPobytuNaZastavceS * TPS
const val cenaZastavky = 5_000  // prachy
const val cenaTroleje = 20_000  // prachy
const val udrzbaZastavky = 100  // prachy
const val udrzbaTroleje = 1_000  // prachy
const val bonusoveVydajeZaNeekologickeBusy = 800  // prachy // Kč/min
const val bonusoveVydajeZaPoloekologickeBusy = 400  // prachy // Kč/min
const val nahodnostProjetiZastavky = 200
const val nahodnostKamionu = 45 * TPM // tiky, za jak dlouho prijede
const val nahodnostSebevrazdy = 10 * TPM
const val cenaPruzkumuVerejnehoMineni = 50_000 // prachy

// ---------------- SEZNAMY -----------------

val BARVICKY /*jeej*/ = listOf(
    App.res.getColor(R.color.black),
    App.res.getColor(R.color.red_500),
    App.res.getColor(R.color.green_500),
    App.res.getColor(R.color.blue_500),
    App.res.getColor(R.color.yellow_500),
    App.res.getColor(R.color.cyan_500),
    App.res.getColor(R.color.purple_500),
    App.res.getColor(R.color.red_900),
    App.res.getColor(R.color.green_900),
    App.res.getColor(R.color.blue_900),
    App.res.getColor(R.color.yellow_900),
    App.res.getColor(R.color.cyan_900),
    App.res.getColor(R.color.purple_900),
    App.res.getColor(R.color.red_300),
    App.res.getColor(R.color.light_green_500),
    App.res.getColor(R.color.light_blue_500),
    App.res.getColor(R.color.yellow_300),
    App.res.getColor(R.color.cyan_300),
    App.res.getColor(R.color.purple_300),
    App.res.getColor(R.color.pink_500),
    App.res.getColor(R.color.orange_500),
    App.res.getColor(R.color.brown_500),
)
val NAZVYBARVICEK = listOf(
    App.res.getString(R.string.cerna)           ,
    App.res.getString(R.string.cervena)         ,
    App.res.getString(R.string.zelena)          ,
    App.res.getString(R.string.modra)           ,
    App.res.getString(R.string.zluta)           ,
    App.res.getString(R.string.tyrkysova)       ,
    App.res.getString(R.string.fialova)         ,
    App.res.getString(R.string.tmave_cervena)   ,
    App.res.getString(R.string.tmave_zelena)    ,
    App.res.getString(R.string.tmave_modra)     ,
    App.res.getString(R.string.tmave_zluta)     , // App.res.getString(R.string.hovnova)
    App.res.getString(R.string.tmave_tyrkysova) ,
    App.res.getString(R.string.tmave_fialova)   ,
    App.res.getString(R.string.svetle_cervena)  ,
    App.res.getString(R.string.svetle_zelena)   ,
    App.res.getString(R.string.svetle_modra)    ,
    App.res.getString(R.string.svetle_zluta)    ,
    App.res.getString(R.string.svetle_tyrkysova),
    App.res.getString(R.string.svetle_fialova)  ,
    App.res.getString(R.string.ruzova)          ,
    App.res.getString(R.string.oranzova)        ,
    App.res.getString(R.string.hneda)           ,
)

val SVETLEBARVICKY = listOf(
    false,
    false,
    true ,
    false,
    true ,
    true ,
    true ,
    false,
    false,
    false,
    false,
    false,
    false,
    true ,
    false,
    true ,
)

val TEMATA = listOf(
    R.style.Theme_DopravníPodniky_Cerne, // cerne
    R.style.Theme_DopravníPodniky_Cervene,
    R.style.Theme_DopravníPodniky_Ruzove,
    R.style.Theme_DopravníPodniky_Fialove,
    R.style.Theme_DopravníPodniky_SyteFialove,
    R.style.Theme_DopravníPodniky_Indigove,
    R.style.Theme_DopravníPodniky_Modre,
    R.style.Theme_DopravníPodniky_SvetleModre,
    R.style.Theme_DopravníPodniky_Tyrkysove,
    R.style.Theme_DopravníPodniky_Modrozelene,
    R.style.Theme_DopravníPodniky_Zelene,
    R.style.Theme_DopravníPodniky_SvetleZelene,
    R.style.Theme_DopravníPodniky_Limetkove,
    R.style.Theme_DopravníPodniky_Zlute,
    R.style.Theme_DopravníPodniky_Jantarove,
    R.style.Theme_DopravníPodniky_Oranzove,
    R.style.Theme_DopravníPodniky_SyteOranzove,
)
val BARVICKYTEMAT = listOf(
    App.res.getColor(R.color.t0),
    App.res.getColor(R.color.t1),
    App.res.getColor(R.color.t2),
    App.res.getColor(R.color.t3),
    App.res.getColor(R.color.t4),
    App.res.getColor(R.color.t5),
    App.res.getColor(R.color.t6),
    App.res.getColor(R.color.t7),
    App.res.getColor(R.color.t8),
    App.res.getColor(R.color.t9),
    App.res.getColor(R.color.t10),
    App.res.getColor(R.color.t11),
    App.res.getColor(R.color.t12),
    App.res.getColor(R.color.t13),
    App.res.getColor(R.color.t14),
    App.res.getColor(R.color.t15),
    App.res.getColor(R.color.t16),
)

val NAZVYTEMAT = listOf(
    App.res.getString(R.string.cerne),
    App.res.getString(R.string.cervene),
    App.res.getString(R.string.ruzove),
    App.res.getString(R.string.fialove),
    App.res.getString(R.string.syte_fialove),
    App.res.getString(R.string.indigove),
    App.res.getString(R.string.modre),
    App.res.getString(R.string.svetle_modre),
    App.res.getString(R.string.tyrkysove),
    App.res.getString(R.string.modrozelene),
    App.res.getString(R.string.zelene),
    App.res.getString(R.string.svetle_zelene),
    App.res.getString(R.string.limetkove),
    App.res.getString(R.string.zlute),
    App.res.getString(R.string.jantarove),
    App.res.getString(R.string.oranzove),
    App.res.getString(R.string.syte_cervene),
)

// ---------------- FUNKCE -----------------
/**
 * Zformátuje číslo, aby vypadalo hezky
 */
fun Number.formatovat(): String {
    if (this == Double.POSITIVE_INFINITY.toLong()) return App.res.getString(R.string.nekonecne_mnoho)
    if (this == Double.NEGATIVE_INFINITY.toLong()) return App.res.getString(R.string.nekonecne_malo)

    val vysledek = mutableListOf<Char>()

    this.toString().toList().reversed().forEachIndexed {i , cislice ->
        vysledek += cislice

        if ((i + 1) % 3 == 0) {
            vysledek += " ".single()
        }
    }

    vysledek.reverse()

    return vysledek.joinToString("").removePrefix(" ")
}

/**
 * Vrátí náklady busu slovy
 *
 * Pro trolejbusy odpovídá dvojnásobku
 */
fun naklady(naklady: Double, trolejbus: Boolean = false): String {

    val nakladove = if (trolejbus) naklady * 2 else naklady

    val slovemNaklady = App.res.getString(when {

        nakladove < 50  -> R.string.velmi_nizke
        nakladove < 60  -> R.string.hodne_nizke
        nakladove < 65  -> R.string.nizke
        nakladove < 70  -> R.string.pomerne_nizke
        nakladove < 75  -> R.string.snizene
        nakladove < 80  -> R.string.normalni
        nakladove < 85  -> R.string.pomerne_vysoke
        nakladove < 90  -> R.string.vysoke
        nakladove < 95  -> R.string.hodne_vysoke
        nakladove < 100 -> R.string.velmi_vysoke
        nakladove < 500 -> R.string.muzejni_bus
        else -> R.string.JOSTOVSKE

    } )
    return slovemNaklady
}

/**
 * Vrátí úroveň města na základě jeho počtu obyvatel, počtu potenciálů, počtu ulic a nepřímo i plochy
 */

fun uroven(plocha: Int, obyv: Int, ulice: Int, potencialy: Int): Int =
    (obyv / plocha.toDouble() * potencialy * ulice * nasobitelUrovne).roundToInt()

/**
 * Vrátí velikost města na základě jeho plochy, počtu obyvatel a úrovně
 */

fun velkomesto(plocha: Int, obyv: Int, uroven: Int): String {

    return App.res.getString(when {
        plocha >= 500_000_000 && obyv >= 5_000_000 && uroven >= 10_000_000 -> R.string.mesto_jostless
        plocha >= 25_000_000 && obyv >= 500_000 && uroven >= 500_000 -> R.string.super_mesto
        plocha >= 15_000_000 && obyv >= 250_000 && uroven >= 100_000 -> R.string.ultra_velkomesto
        plocha >= 5_000_000 && obyv >= 150_000 && uroven >= 25_000 -> R.string.velkomesto
        plocha >= 2_500_000 && obyv >= 40_000 && uroven >= 600 -> R.string.velke_mesto
        plocha >= 1_500_000 && obyv >= 10_000 && uroven >= 300 -> R.string.mesto
        plocha >= 500_000 && obyv >= 4_000 && uroven >= 40 -> R.string.mestecko
        else -> R.string.vesnice
    } )
}

/**
 * Udělá ideální barvu pozadí cardviewu za daných kritérií
 *
 * 2 -> výrazně
 *
 * 4 -> normálně
 *
 * 6 -> tmavě
 */

fun pozadi(delitel: Int): Int {
    return Color.rgb(vse.barva.red / delitel, vse.barva.green / delitel, vse.barva.blue / delitel)
}

/**
 * Vrátí násobitel náhodnosti, vytvořené z kapacity busu a kapacity zastávky, udávající, kolik lidí je ochotno nastoupit do busu závisle na ceně jízdenky a rozsáhlosti dopravního podniku
 */

fun nasobitelPoctuLidiKteryTiNastoupiDoBusuNaZastavceKdyzZastaviANakyLidiTamJsouAMaVSobeJesteVolneMisto(dp: DopravniPodnik): Double {

    val naRozsahlosti = sqrt(1.0 - (1.0 / (dp.busy.size.coerceAtLeast(1) + 1))) *
            (dp.zastavky.count { dp.ulice(it.ulice).pocetLinek != 0 }.toDouble() /
                    dp.ulicove.size.toDouble())

    var naJizdnem = 1.0 - 
        abs( .6 - 
            abs(.5 - 
                ((dp.jizdne - nasobitelRozsahlosti * naRozsahlosti) /
                    (nasobitelRozsahlosti * naRozsahlosti / 2.0)
                ).pow(2)
            )
        )
    
    if (dp.jizdne <= nasobitelRozsahlosti * naRozsahlosti  * 2/5.0) {
        naJizdnem = 4/9.0
    }

    //Log.d("funguj", "---${dp.jizdne}/${(naRozsahlosti * nasobitelRozsahlosti).roundToInt()}---")
    //Log.d("funguj", naJizdnem.toString())
    //Log.d("funguj", naRozsahlosti.toString())
    //Log.d("funguj", sqrt(naJizdnem.coerceAtLeast(.0)).toString())

    return sqrt(naJizdnem.coerceAtLeast(.0))
}
fun soucinPromenneNaRozsahlostiVNasobiteliPoctuLidiKteryTiNastoupiDoBusuNaZastavceKdyzZastaviANakyLidiTamJsouAMaVSobeJesteVolneMistoANasobiteleRozsahlosti(dp: DopravniPodnik): Double {


    return sqrt(1.0 - (1.0 / (dp.busy.size.coerceAtLeast(1) + 1))) *
            (dp.zastavky.count { dp.ulice(it.ulice).pocetLinek != 0 }.toDouble() /
                    dp.ulicove.size.toDouble()) *
            nasobitelRozsahlosti
}
