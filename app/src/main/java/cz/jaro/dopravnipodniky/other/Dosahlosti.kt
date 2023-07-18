package cz.jaro.dopravnipodniky.other

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.activities.MainActivity
import cz.jaro.dopravnipodniky.classes.OrigoDosahlost
import cz.jaro.dopravnipodniky.other.PrefsHelper.vse
import java.util.*

object Dosahlosti {

    fun Context.dosahni(cislo: @CisloDosahlosti String, view: View) {
        
        if (cislo.endsWith("X")) {
            val puvodniPocet = vse.pocetniDosahlosti[cislo] ?: 0
            vse.pocetniDosahlosti[cislo] = puvodniPocet + 1
            val pocetDosahnuti = vse.pocetniDosahlosti[cislo]

            val novyCislo = "${cislo.removeSuffix("X")}$pocetDosahnuti"
            return dosahni(novyCislo, view)
        }

        val dosahlost = vse.dosahlosti.firstOrNull { it.cislo == cislo } ?: return
        
        val uzSplneno = dosahlost.splneno

        vse.dosahlosti[vse.dosahlosti.indexOf(dosahlost)].splneno = true

        val origoDosahlost = seznamDosahlosti.first { it.cislo == cislo }

        if (!uzSplneno) {
            vse.dosahlosti[vse.dosahlosti.indexOf(dosahlost)].datumSplneni = Calendar.getInstance()
            vse.prachy += origoDosahlost.odmena

            Log.i("Splněn úspěch", origoDosahlost.jmeno)

            Snackbar.make(view, getString(R.string.uspesne_splnen_uspech, origoDosahlost.jmeno), 10_000).apply {

                setBackgroundTint(getColor(R.color.tmava))
                setTextColor(getColor(R.color.white))

                setTheme(R.style.Widget_Material3_Snackbar_FullWidth)

                setAction(getString(R.string.zobrazit)) {

                    val intent = Intent(this@dosahni, MainActivity::class.java)

                    intent.putExtra("uspechy", true)

                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                    startActivity(intent)
                }
                show()
            }
        }
    }

    val seznamDosahlosti = listOf(
        OrigoDosahlost(
            "linka1",
            App.res.getString(R.string.d_n_linka1),
            App.res.getString(R.string.d_p_linka1),
            10_000.0,
        ),
        OrigoDosahlost(
            "kocka1",
            App.res.getString(R.string.d_n_kocka1),
            App.res.getString(R.string.d_p_kocka1),
            100_000.0,
            true,
            App.res.getString(R.string.d_nap_kocka1),
        ),
        OrigoDosahlost(
            "busNaLince",
            App.res.getString(R.string.d_n_bus_na_lince),
            App.res.getString(R.string.d_p_bus_na_lince),
        ),
        OrigoDosahlost(
            "busNaZastavce",
            App.res.getString(R.string.d_n_bus_na_zastavce),
            App.res.getString(R.string.d_p_bus_na_zastavce)
        ),
        OrigoDosahlost(
            "bus1",
            App.res.getString(R.string.d_n_bus1),
            App.res.getString(R.string.d_p_bus1),
            10_000.0
        ),
        OrigoDosahlost(
            "bus2",
            App.res.getString(R.string.d_n_bus2),
            App.res.getString(R.string.d_p_bus2),
            5_000.0
        ),
        OrigoDosahlost(
            "bus12",
            App.res.getString(R.string.d_n_bus12),
            App.res.getString(R.string.d_p_bus12),
            12_000.0
        ),
        OrigoDosahlost(
            "bus60",
            App.res.getString(R.string.d_n_bus60),
            App.res.getString(R.string.d_p_bus60),
            60_000.0
        ),
        OrigoDosahlost(
            "bus1024",
            App.res.getString(R.string.d_n_bus1024),
            App.res.getString(R.string.d_p_bus1024),
            1_024_000.0
        ),
        OrigoDosahlost(
            "penize200000",
            App.res.getString(R.string.d_n_penize200000),
            App.res.getString(R.string.d_p_penize200000),
        ),
        OrigoDosahlost(
            "penize500000",
            App.res.getString(R.string.d_n_penize500000),
            App.res.getString(R.string.d_p_penize500000),
        ),
        OrigoDosahlost(
            "penize1000000",
            App.res.getString(R.string.d_n_penize1000000),
            App.res.getString(R.string.d_p_penize1000000),
        ),
        OrigoDosahlost(
            "penize2000000",
            App.res.getString(R.string.d_n_penize2000000),
            App.res.getString(R.string.d_p_penize2000000),
        ),
        OrigoDosahlost(
            "penize10000000",
            App.res.getString(R.string.d_n_penize10000000),
            App.res.getString(R.string.d_p_penize10000000),
        ),
        //tak tady je doopravdy konec






























        OrigoDosahlost(
            "projetZastavku",
            App.res.getString(R.string.d_n_projet_zastavku),
            App.res.getString(R.string.d_p_projet_zastavku),
            secret = true,
            napoveda = ""
        ),
        OrigoDosahlost(
            "stejneLinky",
            App.res.getString(R.string.d_n_stejne_linky),
            App.res.getString(R.string.d_p_stejne_linky),
            100_000.0,
            true,
            napoveda = App.res.getString(R.string.d_nap_stejne_linky)
        ),
        OrigoDosahlost(
            "vecne1",
            App.res.getString(R.string.d_n_vecne1),
            App.res.getString(R.string.d_p_vecne1),
            1_000_000.0,
            true
        ),
        OrigoDosahlost(
            "vecne2",
            App.res.getString(R.string.d_n_vecne2),
            App.res.getString(R.string.d_p_vecne2),
            1_000_000_000_000.0,
            true
        ),
        OrigoDosahlost(
            "jostoMesto",
            App.res.getString(R.string.d_n_jostomesto),
            App.res.getString(R.string.d_p_jostomesto),
            Double.NEGATIVE_INFINITY,
            true
        ),
        OrigoDosahlost(
            "citer",
            App.res.getString(R.string.d_n_citer),
            App.res.getString(R.string.d_p_citer),
            Double.NEGATIVE_INFINITY,
            true
        ),
        // tady to končí doopravdy




















































    )
}
