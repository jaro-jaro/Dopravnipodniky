package cz.jaro.dopravnipodniky.classes

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.other.PrefsHelper.dp
import kotlin.math.pow
import kotlin.random.Random
import kotlin.random.Random.Default.nextDouble

class Clovek {
    private val muz = Random.nextBoolean()

    var vek = (nextDouble(1.0, 10.0).pow(2) + nextDouble(-10.0, 10.0)).coerceAtLeast(.0)

    var jmeno = ""

    fun pojmenuj(ctx: Context) {

        val inputStream = ctx.resources.openRawResource(if (muz) R.raw.muzska_jmena else R.raw.zenska_jmena)

        val bufferedReader = inputStream.bufferedReader()

        val jmena = bufferedReader.use { it.readText().split("\n") }

        val inputStreamPrijm = ctx.resources.openRawResource(R.raw.prijmeni)

        val bufferedReaderPrijm = inputStreamPrijm.bufferedReader()

        val prijmeni1 = bufferedReaderPrijm.use { it.readText().split("\n") }

        var prijmeni = prijmeni1.random()

        if (!muz) {
            if (prijmeni.endsWith("ý")) {
                prijmeni = prijmeni.removeSuffix("ý") + "á"
            } else {
                prijmeni = prijmeni.removeSuffix("a")
                prijmeni = prijmeni.replace(Regex("e.\\b"), prijmeni.last().toString())
                prijmeni += "ová"
            }
        }

        jmeno = jmena.random() + " " + prijmeni
    }

    fun sebevrazda(ctx: Context) {
        dp.baraky.filter { it.cloveci != 0 }.random().cloveci --
        pojmenuj(ctx)
        MaterialAlertDialogBuilder(ctx).apply{
            setTitle(R.string.sebevrazda)

            setCancelable(false)

            val inputStream = ctx.resources.openRawResource(R.raw.sebevrazdy)

            val bufferedReader = inputStream.bufferedReader()

            val sebevrazdy = bufferedReader.use { it.readText().split("\n") }

            setMessage(context.getString(R.string.sebevrazda_dialog, jmeno, if (muz) "" else context.getString(R.string.zenska_pripona_a), sebevrazdy.random().replace("$", if (muz) "" else context.getString(R.string.zenska_pripona_a)), if (muz) context.getString(R.string.muzska_pripona_ho) else context.getString(R.string.zenska_pripona_ji)))

            setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.cancel()}

            show()
        }
    }
    fun smrt(ctx: Context) {
        dp.baraky.filter { it.cloveci != 0 }.random().cloveci --
        pojmenuj(ctx)

    }
}
