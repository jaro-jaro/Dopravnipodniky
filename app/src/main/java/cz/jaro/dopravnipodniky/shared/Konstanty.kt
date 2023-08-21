package cz.jaro.dopravnipodniky.shared

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.shared.jednotky.metru
import cz.jaro.dopravnipodniky.shared.jednotky.penez
import cz.jaro.dopravnipodniky.shared.jednotky.penezZaMin
import cz.jaro.dopravnipodniky.shared.jednotky.toTiky
import kotlin.math.PI
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
 * TODO 2.2 Cestující, Kapacity zastávek
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

const val todocka = 0

val minimumInvestice = 1_000_000L.penez
val pocatecniCenaMesta = 1_200_000L.penez

// vykreslovani

const val pocatecniPriblizeni = .75F
const val oddalenyRezim = .6F
const val maximalniOddaleni = .2F
val pocatecniPosunutiX = 0.dp //200
val pocatecniPosunutiY = 0.dp //600

val ulicovyBlok = 150.dp

val sirkaUlice = 34.dp
val velikostZastavky = 48.dp
val odsazeniBaraku = 5.dp
const val barakuVUlici = 5
val velikostBaraku = (ulicovyBlok - odsazeniBaraku * (barakuVUlici + 1)) / barakuVUlici
val sirkaObrubniku = 3.dp
val sirkaBusu = 10.dp

val sirkaTroleje = 1.dp
val predsazeniTrolejiS = 8.dp
val predsazeniTrolejiL = 28.dp
val rozchodTroleji = 5.dp
val odsazeniTroleje = 6.dp
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
val pocatecniObnosPenez = /*Double.POSITIVE_INFINITY.penez*//*150_000.penez*//*5_000_000.penez*/5_000_000_000.penez
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
