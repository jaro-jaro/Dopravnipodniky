package cz.jaro.dopravnipodniky.shared

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.data.generace.DetailGeneraceV2
import cz.jaro.dopravnipodniky.shared.jednotky.metru
import cz.jaro.dopravnipodniky.shared.jednotky.penez
import cz.jaro.dopravnipodniky.shared.jednotky.penezZaMin
import cz.jaro.dopravnipodniky.shared.jednotky.toDp
import cz.jaro.dopravnipodniky.shared.jednotky.toTiky
import cz.jaro.dopravnipodniky.ui.theme.green800
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

private val Double.m get() = metru.toDp()
private val Int.m get() = metru.toDp()

const val oddalenyRezim = 1.2F
const val maximalniOddaleni = .1F

val pocatecniObnosPenez = /*Double.POSITIVE_INFINITY.penez*/
    150_000.penez/*5_000_000.penez*//*5_000_000_000.penez*/

// Velikosti

// Ulice
val ulicovyBlok = 72.m
val delkaUlice = ulicovyBlok
val sirkaUlice = 10.m
val sirkaChodniku = 1.m
val predsazeniKrizovatky = 5.dp

// Barák
val odsazeniBaraku = 1.m
const val barakuVUlici = 5
val velikostBaraku = (ulicovyBlok - odsazeniBaraku * (barakuVUlici + 1)) / barakuVUlici
val zaobleniBaraku = predsazeniKrizovatky

// Zastávka
val sirkaZastavky = 3.m
val delkaZastavky = 18.m
val odsazeniZastavky = 1.m
val sirkaSloupku = .4.m
val tloustkaSloupku = .1.m
val odsazeniSloupku = sirkaChodniku - .1.m - sirkaSloupku
val sirkaCary = .2.dp

// Bus
val odsazeniBusu = sirkaChodniku + .75.m
val zakladniSirkaBusu = 2.500.metru

// Troleje
val sirkaTroleje = .2.dp
val predsazeniTrolejiS = predsazeniKrizovatky - 1.dp
val predsazeniTrolejiL = predsazeniKrizovatky + 1.dp
val rozchodTroleji = .5.m
val odsazeniTroleje = sirkaChodniku + 1.5.m
val odsazeniPrvniTroleje = odsazeniTroleje
val odsazeniDruheTroleje = odsazeniTroleje + rozchodTroleji
val odsazeniCtvrteTroleje = sirkaUlice - odsazeniPrvniTroleje
val odsazeniTretiTroleje = sirkaUlice - odsazeniDruheTroleje
val odsazeniTroleji =
    listOf(odsazeniPrvniTroleje, odsazeniDruheTroleje, odsazeniTretiTroleje, odsazeniCtvrteTroleje)

// Barvy

// Dosáhlosti
val barvaSecretDosahlosti = Color(0xFF101010)
val barvaDosahnuteDosahlosti = green800

// Vykreslování
val barvaUlice = Color(135, 135, 135)
val barvaPozadi = Color(16, 16, 16)
val barvaNepouzivanehoBusu = Color(100, 100, 100)
val barvaChodniku = Color(200, 200, 200)
val barvaTroleje = Color(32, 32, 32)

// Rychlost hry

//const val FPS = 60
var stavHry by mutableStateOf<StavHry>(StavHry.Hra)
val TPS get() = stavHry.tps
val millisPerTik get() = 1000L / TPS
val zrychlovacHry get() = stavHry.zrychleni

// Generace

val minimumInvestice = 1_000_000L.penez
const val nasobitelInvesticeProHloubkuRekurce = (1 / 2.0) / 65536.0
const val nahodnostStaveniKOkupantum = .6F
const val nahodnostStaveniKNeokupantum = 1.1F
const val nahodnostNaZacatku = .5F
const val rozdilNahodnosti = .05F
const val nahodnostPoObnoveni = .35F
const val nasobitelRedukce = .75F
const val nahodnostVytvoreniKruhaceNaSouseda = .1F
val pocatecniDeatilGenerace = DetailGeneraceV2(
    investice = 1_200_000L.penez,
    nazevMestaSeed = 18,
    michaniSeed = 19250533,
    sanceSeed = 19250533,
    barakySeed = 19250533,
    panelakySeed = 19250533,
    stredovySeed = 19250533,
    kapacitaSeed = 19250533,
    kruhaceSeed = -1421411346,
)

// Zisk

const val nasobitelZiskuPoOffline = .20
const val nasobitelZisku = 5
const val idealniInterval = 3.5

// Ceny

val cenaZastavky = 5_000.penez
val cenaKruhace = 15_000.penez
val cenaTroleje = 20_000.penez
val udrzbaZastavky = 100.penezZaMin
val udrzbaTroleje = 1_000.penezZaMin
val bonusoveVydajeZaNeekologickeBusy = 800.penezZaMin
val bonusoveVydajeZaPoloekologickeBusy = 400.penezZaMin
val cenaPruzkumuVerejnehoMineni = 50_000.penez
val prodejniCenaCloveka = 256.penez // Ano, jsou to otroci

// Náhodnosti

const val nahodnostProjetiZastavky = 200
const val nahodnostKamionuKazdouMinutu = 45
const val nahodnostSebevrazdyKazdouMinutu = 10
const val nahodnostVymreniKazdouMinutu = 30
const val nahodnostNarozeniKazdouMinutu = (1.5 * nahodnostVymreniKazdouMinutu).toInt()

// Zastávky

const val nasobitelKapacityZastavky = Math.PI / 2
val dobaPobytuNaZastavce = 1.seconds.toTiky()

// Čísla

val hezkaCisla =
    Json.decodeFromString<List<Int>>("[1,2,3,4,5,6,8,9,10,12,15,16,18,20,24,25,27,30,32,36,40,45,48,50,54,60,64,72,75,80,81,90,96,100,108,120,125,128,135,144,150,160,162,180,192,200,216,225,240,243,250,256,270,288,300,320,324,360,375,384,400,405]")
val vecneLinky = setOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 16, 21, 34, 44, 71, 72, 73)

// Města

const val kremze = "Křemže"
const val vecne = "Věčné"