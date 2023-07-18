package cz.jaro.dopravnipodniky.other

import com.google.gson.GsonBuilder
import cz.jaro.dopravnipodniky.classes.DopravniPodnik
import cz.jaro.dopravnipodniky.classes.TridaVsechnoDohromady
import cz.jaro.dopravnipodniky.pocatecniCenaMesta

object PrefsHelper {

    private lateinit var _vse: TridaVsechnoDohromady

    var dp: DopravniPodnik

        get() = vse.podniky[vse.aktualniDp]
        set(value) {
            vse.podniky[vse.aktualniDp] = value
        }

    var vse: TridaVsechnoDohromady

        get() {

            if (!this@PrefsHelper::_vse.isInitialized) {
                val prefs = App.prefs
                val gson = GsonBuilder().serializeSpecialFloatingPointValues().create()

                _vse = gson.fromJson(prefs.getString("vse", ""), TridaVsechnoDohromady::class.java)
                    ?: TridaVsechnoDohromady(Generator(pocatecniCenaMesta).vygenerujMiMestoAToHnedVykricnik())

            }

            return _vse
        }

        set(value) {
            _vse = value

        }
}
