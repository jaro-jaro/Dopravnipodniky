package cz.jaro.dopravnipodniky.classes

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.content.edit
import cz.jaro.dopravnipodniky.*
import cz.jaro.dopravnipodniky.other.App
import cz.jaro.dopravnipodniky.other.LinkaId
import cz.jaro.dopravnipodniky.other.Podtyp.*
import cz.jaro.dopravnipodniky.other.PrefsHelper.dp
import cz.jaro.dopravnipodniky.other.PrefsHelper.vse
import cz.jaro.dopravnipodniky.other.Smer
import cz.jaro.dopravnipodniky.other.Smer.POZITIVNE
import cz.jaro.dopravnipodniky.other.Trakce
import cz.jaro.dopravnipodniky.other.Trakce.AUTOBUS
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.random.Random.Default.nextInt

class Bus(
    val evCislo: Int,
    val typBusu: TypBusu,
    ctx: Context,
) {

    var najeto = 0.0 // h

    var cloveci = 0

    var projeto = false
    var jeNaZastavce = false
    var cekaUzTakhleDlouhoT = 0 // tiku

    /**
     * index ulice na lince
     */
    var poziceNaLince = 0

    /**
     * pozice levého horního rohu px
     */

    var pozice = 0F to 0F

    /**
     * kolik ujel konec busu v ulici ve smeru jizdy v blocich
     */

    var poziceVUlici = 0F
    var smerNaLince = POZITIVNE

    var minulyZisk = .0

    val ponicenost: Double // des.cislo
    get() = (typBusu.nasobitelPonicenosti.pow(najeto)) - 1

    val naklady: Double
    get() = typBusu.nasobitelNakladuu * (ponicenost + 1).pow(6) + // Kč/min
            if (typBusu.trakce == AUTOBUS && typBusu.podtyp != VODIKOVY)
                if (typBusu.podtyp in listOf(HYBRIDNI, ZEMEPLYNOVY))
                    bonusoveVydajeZaPoloekologickeBusy
                else
                    bonusoveVydajeZaNeekologickeBusy
            else
                0

    /**
     * Vypočítá průměrný výdělek busu za minutu
     */

    fun vydelkuj(ctx: Context): Double {
        if (linka == -1L) return .0
        if (typBusu.trakce == Trakce.TROLEJBUS && !dp.linka(linka).trolej(ctx)) return .0

        val linka = dp.linka(linka)

        val uliceSeZastavkama = linka.seznamUlic.map { dp.ulice(it) }.filter { it.zastavka != null } // jenom ty, co maj zastavky

        val pocetUlic = linka.seznamUlic.size
        val pocetZastavek = uliceSeZastavkama.size

        //val prumernyVydelekZaZastavku = ctx.dp.kapacita.coerceAtMost(ctx.dp.busy.sumOf { it.typBusu.kapacita })
        // * ctx.dp.jizdne / uliceSeZastavkama.size
        val nasobitelCloveku = nasobitelPoctuLidiKteryTiNastoupiDoBusuNaZastavceKdyzZastaviANakyLidiTamJsouAMaVSobeJesteVolneMisto(dp).coerceIn(.0, 1.0).pow(2)
        val prumernyVydelekZaZastavku = cloveci * nasobitelCloveku * dp.jizdne

        val rychlostBusuT = typBusu.rychlost * nasobitelRychlosti // bloky za tik
        val rychlostBusuM = TPM * rychlostBusuT // bloky za minutu
        val pocetBlokuNaLince = pocetUlic * velikostUlicovyhoBloku
        val ujetyZlomekLinky = rychlostBusuM / pocetBlokuNaLince.toDouble() // ve zlomku za minutu
        val pocetZastavekZaMinutu = ujetyZlomekLinky * pocetZastavek

        return pocetZastavekZaMinutu * prumernyVydelekZaZastavku * nasobitelZisku
    }

    fun vydelek(ctx: Context): Double {
        return if (linka == -1L) .0 else (vydelkuj(ctx) + minulyZisk) / 2
    }

    val prodejniCena: Int
    get() = (typBusu.cena * (1 - ponicenost) * .95F).roundToInt()

    var id: Long

    var linka: @LinkaId Long = -1

    init {
        val prefs = App.prefs

        val posledniId = prefs.getLong("bus_id", 0)

        id = posledniId + 1

        prefs.edit {
            putLong("bus_id", posledniId + 1)
        }
    }

    fun odjelZeZastavky(ctx: Context) {

        minulyZisk = vydelkuj(ctx)

        val linka = dp.linka(linka)
        val ulice = dp.ulice(linka.seznamUlic[
                when (smerNaLince) {
                    POZITIVNE -> poziceNaLince
                    Smer.NEGATIVNE -> linka.seznamUlic.lastIndex - poziceNaLince
                }
        ])

        val vystupujici = if (cloveci == 0) 0 else nextInt(0, cloveci)

        cloveci -= vystupujici
        ulice.zastavka!!.cloveci += vystupujici

        vse.prachy += dp.jizdne * vystupujici * nasobitelZisku

        val nastupujici =
            if (0 == ulice.zastavka!!.kapacita) 0
            else (nextInt((ulice.zastavka!!.cloveci - ulice.zastavka!!.kapacita).coerceAtLeast(0), ulice.zastavka!!.cloveci + 1) *
                nasobitelPoctuLidiKteryTiNastoupiDoBusuNaZastavceKdyzZastaviANakyLidiTamJsouAMaVSobeJesteVolneMisto(dp)).roundToInt()
                .coerceIn(0, typBusu.kapacita - cloveci).coerceAtMost(ulice.zastavka!!.cloveci)

        cloveci += nastupujici
        ulice.zastavka!!.cloveci -= nastupujici

        if (dp.sledovanejBus == this) {
            Toast.makeText(ctx, ctx.getString(R.string.bus_zastavil, evCislo.formatovat(), vystupujici.formatovat(), nastupujici.formatovat(), cloveci.formatovat(), ulice.zastavka!!.cloveci.formatovat(), (vystupujici * dp.jizdne * nasobitelZisku).roundToInt().formatovat()), Toast.LENGTH_LONG).show()
        }
        Log.i("Zastavil", "Bus ev. č. $evCislo zastavil v ulici ${ulice.id.formatovat()}.\nVystoupilo $vystupujici lidí a nastoupilo $nastupujici lidí.\nAktuálně je v busu $cloveci lidí, na zastávce je ${ulice.zastavka!!.cloveci} lidí.\nVyděláno ${(vystupujici * dp.jizdne * nasobitelZisku).roundToInt().formatovat()} Kč.")
    }
    fun zesebavrazdujSe(ctx: Context) {

        dp.ulicove.forEach { ulice ->

            ulice.baraky.forEach {
                while (it.kapacita <= it.cloveci && cloveci != 0) {

                    cloveci --
                    it.cloveci++
                }
            }
        }

    }
}
