package cz.jaro.dopravnipodniky.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import cz.jaro.dopravnipodniky.*
import cz.jaro.dopravnipodniky.classes.Zastavka
import cz.jaro.dopravnipodniky.other.PrefsHelper.dp
import cz.jaro.dopravnipodniky.other.PrefsHelper.vse
import cz.jaro.dopravnipodniky.other.Smer.POZITIVNE
import cz.jaro.dopravnipodniky.other.toSmer
import kotlin.math.roundToLong


class LinkyAdapter(private val ctx: Context) : RecyclerView.Adapter<LinkyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val btnBarva = view.findViewById<Button>(R.id.btnLinkyBarvaLinky)!!
        val tvLinka = view.findViewById<TextView>(R.id.tvCisloLinky)!!
        val ibOdstranit = view.findViewById<Button>(R.id.ibOdstranit)!!
        val ibDistribuovat = view.findViewById<Button>(R.id.ibDistribuovat)!!
        val ibZtroleizovat = view.findViewById<Button>(R.id.ibZtroleizovat)!!
        val clLinka = view.findViewById<ConstraintLayout>(R.id.clLinka)
        val cvLinka = view.findViewById<CardView>(R.id.cvLinka)
        val odsazeni = view.findViewById<TextView>(R.id.tvLinkaOdsazeni)!!

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.linka, parent, false)

        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val linka = dp.linky[position]

        linka.apply {

            holder.btnBarva.setBackgroundColor(barvicka)

            holder.tvLinka.text = cislo.toString()

            if (vse.tutorial == 3) {
                holder.ibDistribuovat.visibility = GONE
                holder.ibZtroleizovat.visibility = GONE
            }

            holder.odsazeni.visibility = if (linka == dp.linky.last()) VISIBLE else GONE

            holder.ibZtroleizovat.setOnClickListener {

                MaterialAlertDialogBuilder(ctx).apply {
                    setTitle(R.string.pridat_troleje_linka)
                    setMessage(context.getString(R.string.pridat_troleje_nebo_zastavky_linka_dialog, vse.prachy.roundToLong().formatovat(), (seznamUlic.count { !dp.ulice(it).trolej } * cenaTroleje).formatovat(), (vse.prachy - seznamUlic.count { !dp.ulice(it).trolej } * cenaTroleje).roundToLong().formatovat(), context.getString(R.string.troleje)))
                    setPositiveButton(R.string.ano) { dialog, _ ->
                        dialog.cancel()

                        if (vse.prachy < seznamUlic.count { !dp.ulice(it).trolej } * cenaTroleje) {
                            Toast.makeText(ctx, R.string.malo_penez, Toast.LENGTH_SHORT).show()
                            return@setPositiveButton
                        }

                        seznamUlic.forEach {
                            val ulice = dp.ulice(it)
                            if (!ulice.trolej) {
                                ulice.trolej = true
                                vse.prachy -= cenaTroleje
                            }
                        }
                        Toast.makeText(ctx, R.string.uspesne_pridany_troleje, Toast.LENGTH_SHORT).show()
                    }
                    show()
                }
            }

            holder.ibZtroleizovat.setOnLongClickListener {

                MaterialAlertDialogBuilder(ctx).apply {
                    setTitle(R.string.pridat_zastavky_linka)
                    setMessage(context.getString(R.string.pridat_troleje_nebo_zastavky_linka_dialog, vse.prachy.roundToLong().formatovat(), (seznamUlic.count { dp.ulice(it).zastavka == null } * cenaZastavky).formatovat(), (vse.prachy - (seznamUlic.count { dp.ulice(it).zastavka == null } * cenaZastavky)).roundToLong().formatovat(), context.getString(R.string.zastavky)))
                    setPositiveButton(R.string.ano) { dialog, _ ->
                        dialog.cancel()

                        if (vse.prachy < seznamUlic.count { dp.ulice(it).zastavka == null } * cenaZastavky) {
                            Toast.makeText(ctx, R.string.malo_penez, Toast.LENGTH_SHORT).show()
                            return@setPositiveButton
                        }

                        seznamUlic.forEach {
                            val ulice = dp.ulice(it)
                            if (ulice.zastavka == null) {
                                val zastavka = Zastavka(ulice.id, context)
                                ulice.zastavka = zastavka
                                vse.prachy -= cenaZastavky
                                dp.zastavky += zastavka
                            }
                        }
                        Toast.makeText(ctx, R.string.uspesne_pridany_zastavky, Toast.LENGTH_SHORT).show()
                    }
                    show()
                }
                true
            }

            holder.ibOdstranit.setOnClickListener {

                MaterialAlertDialogBuilder(ctx).apply {

                    setTitle(R.string.odstranit_linku)
                    setMessage(R.string.jste_si_vedomi_odstraneni_linky)

                    setPositiveButton(R.string.ano) { dialog, _ ->

                        busy.forEach { busId ->
                            val bus = dp.bus(busId)

                            bus.linka = -1L

                            bus.poziceNaLince = 0
                            bus.poziceVUlici = 0F
                            bus.smerNaLince = POZITIVNE
                        }

                        dp.linky.removeAt(position)
                        notifyItemRemoved(position)

                        dialog.cancel()
                    }

                    setNegativeButton(R.string.zrusit) { dialog, _ -> dialog.cancel() }

                    show()
                }
            }

            holder.ibDistribuovat.setOnClickListener {

                val delkaLinky = seznamUlic.size * velikostUlicovyhoBloku
                val pocetBusu = busy.size

                if (pocetBusu == 0) {
                    Toast.makeText(ctx, R.string.pridejte_vozidla_na_linku, Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (pocetBusu >= 10 * 2 * seznamUlic.size) {
                    Toast.makeText(ctx, R.string.moc_vozidel_na_rozmisteni, Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val odstupy = (2 * delkaLinky) / pocetBusu.toDouble()

                busy.forEachIndexed { i, busId ->
                    val j = i % if (pocetBusu == 1) 1.0 else (pocetBusu / 2.0)

                    val otocitSmer = 2 * i / pocetBusu >= 1

                    val pozice = j * odstupy
                    val poziceNaLince = pozice / velikostUlicovyhoBloku
                    val poziceVulici = pozice % velikostUlicovyhoBloku

                    val bus = dp.bus(busId)

                    bus.poziceNaLince = poziceNaLince.toInt()
                    bus.poziceVUlici = poziceVulici.toFloat()
                    bus.smerNaLince = otocitSmer.toSmer()

                }
                Toast.makeText(ctx, R.string.uspesne_rozmisteno, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = dp.linky.size
}
