package cz.jaro.dopravnipodniky.shared

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.data.generace.DetailGeneraceV2
import cz.jaro.dopravnipodniky.shared.jednotky.penez
import cz.jaro.dopravnipodniky.shared.jednotky.penezZaMin
import cz.jaro.dopravnipodniky.shared.jednotky.toTiky
import cz.jaro.dopravnipodniky.ui.theme.green800
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

const val oddalenyRezim = 1.2F
const val maximalniOddaleni = .1F

val pocatecniObnosPenez = /*Double.POSITIVE_INFINITY.penez*/
    150_000.penez/*5_000_000.penez*//*5_000_000_000.penez*/

// Velikosti

// Ulice a křižovatka
val ulicovyBlok = 80.dp
val streetLength = ulicovyBlok
val markingWidth = .1.dp
val laneWidth = 3.6.dp
val streetWidth = laneWidth * 2 + markingWidth
val roundaboutLaneWidth = laneWidth * 1.5F
val curbWidth = .2.dp
val sidewalkWidth = 2.7.dp

val intersectionOffset = 15.dp
val intersectionBeginning = streetLength - intersectionOffset
val bareStreetLength = streetLength - intersectionOffset * 2
val intersectionReach = 30.dp

// Musí platit markingPartLength = bareStreetLength / (2n - 1), aby seděly fáze na okrajích křižovatek
// Tento výraz zajistí aby markingPartLength ≈ 1.dp, v případě bareStreetLength=50.dp -> markingPartLength=(50/49).dp (n=25)
val markingPartLength = (bareStreetLength / (bareStreetLength - 1.dp)).dp

// Zastávka
val stopWidth = 2.6.dp
val stopLength = 18.dp
val stopOffset = 41.dp
val stopEntryLength = 6.dp
val stopMarkingsOffset = .1.dp
val stopPostWidth = .5.dp
val stopPostThickness = .3.dp
val stopPostOffset = .25.dp

// Bus
val odsazeniBusu = .25.dp
val zakladniSirkaBusu = 2.500.dp
const val busRearAxlePosition = .25F

// Barák
val odsazeniBaraku = 8.dp
val mezeraMeziBaraky = 1.dp
const val barakuVUlici = 5
val velikostBaraku = (ulicovyBlok - mezeraMeziBaraky * (barakuVUlici - 1) - odsazeniBaraku * 2) / barakuVUlici
val zaobleniBaraku = 5.dp

// Troleje
val sirkaTroleje = .2.dp
val predsazeniTrolejiS = intersectionOffset - 1.dp
val predsazeniTrolejiL = intersectionOffset + 1.dp
val rozchodTroleji = .5.dp
val odsazeniTroleje = sidewalkWidth + 1.5.dp
val odsazeniPrvniTroleje = odsazeniTroleje
val odsazeniDruheTroleje = odsazeniTroleje + rozchodTroleji
val odsazeniCtvrteTroleje = streetWidth - odsazeniPrvniTroleje
val odsazeniTretiTroleje = streetWidth - odsazeniDruheTroleje
val odsazeniTroleji =
    listOf(odsazeniPrvniTroleje, odsazeniDruheTroleje, odsazeniTretiTroleje, odsazeniCtvrteTroleje)

// Barvy

// Dosáhlosti
val barvaSecretDosahlosti = Color(0xFF101010)
val barvaDosahnuteDosahlosti = green800

// Vykreslování
val streetColor = Color(135, 135, 135)
val unusedBusColor = Color(100, 100, 100)
val curbColor = Color(170, 170, 170)
val sidewalkColor = Color(200, 200, 200)
val backgroundColor = Color(16, 16, 16)
val overheadLineColor = Color(32, 32, 32)
val lineMarkingColor = Color(255, 255, 255)
val stopMarkingColor = Color(255, 255, 0)

// Rychlost hry

val vychoziStavHry = StavHry.Hra
var stavHry by mutableStateOf<StavHry>(vychoziStavHry)
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

const val nasobitelZisku = 5
const val nasobitelZiskuPoOffline = nasobitelZisku * .04
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