package cz.jaro.dopravnipodniky.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.jaro.dopravnipodniky.BuildConfig.DEBUG
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.databinding.DosahlostBinding
import cz.jaro.dopravnipodniky.formatovat
import cz.jaro.dopravnipodniky.other.Dosahlosti.dosahni
import cz.jaro.dopravnipodniky.other.Dosahlosti.seznamDosahlosti
import cz.jaro.dopravnipodniky.other.PrefsHelper.vse
import java.util.Calendar.*
import kotlin.math.roundToLong

class DosahlostiAdapter(private val ctx: Context) : RecyclerView.Adapter<DosahlostiAdapter.ViewHolder>() {

    class ViewHolder(binding: DosahlostBinding) : RecyclerView.ViewHolder(binding.root) {
        val btnDosahnout = binding.btnDosahnout
        val cvDosahlost = binding.cvDosahlostA
        val tvNazevDosahlosti = binding.tvNazevDosahlosti
        val tvOdmena = binding.tvOdmena
        val tvPocetSplneni = binding.tvPocetSplneniA
        val tvPopisDosahlosti = binding.tvPopisDosahlosti
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = DosahlostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {

        val dosahlost = vse.dosahlosti.sortedByDescending { dosahlost ->

            dosahlost.datumSplneni

        }.sortedBy { dosahlost ->
            val secret = seznamDosahlosti.first { it.cislo == dosahlost.cislo }.secret

            if (dosahlost.splneno) 0 else if (secret) 2 else 1
        }[holder.adapterPosition]

        val origoDosahlost = seznamDosahlosti.first { it.cislo == dosahlost.cislo }

        val regex = Regex("[0-9]+$")

        holder.tvOdmena.visibility = if (origoDosahlost.secret && !dosahlost.splneno) GONE else VISIBLE

        holder.tvNazevDosahlosti.text =
            if (origoDosahlost.secret && !dosahlost.splneno && origoDosahlost.napoveda == null) {
                "???"
            } else {
                origoDosahlost.jmeno
            }

        if (dosahlost.splneno) {
            holder.cvDosahlost.setCardBackgroundColor(ctx.getColor(R.color.green_800))
        } else if (origoDosahlost.secret) {
            holder.cvDosahlost.setCardBackgroundColor(ctx.getColor(R.color.tmava))
        } else {
            holder.cvDosahlost.setCardBackgroundColor(Color.TRANSPARENT)
        }

        holder.tvPopisDosahlosti.text =
            if (origoDosahlost.secret && !dosahlost.splneno && origoDosahlost.napoveda == null) {
                "?????????"
            } else if (origoDosahlost.secret && !dosahlost.splneno) {
                origoDosahlost.napoveda
            } else {
                origoDosahlost.popis
            }

        holder.tvOdmena.text = ctx.getString(R.string.kc, origoDosahlost.odmena.roundToLong().formatovat())

        if (DEBUG) {
            holder.btnDosahnout.visibility = VISIBLE

            holder.btnDosahnout.setOnClickListener {

                ctx.dosahni(origoDosahlost.cislo, holder.btnDosahnout)
            }

            holder.tvPopisDosahlosti.text = holder.tvPopisDosahlosti.text.toString() + " " + dosahlost.datumSplneni
                ?.let { "${it[DAY_OF_MONTH]}. ${it[MONTH]}. ${it[HOUR_OF_DAY]}:${it[MINUTE]}:${it[SECOND]}" }
        }

        if (origoDosahlost.cislo.contains(regex)) {
            val pocetCasti = vse.pocetniDosahlosti[regex.replace(origoDosahlost.cislo, "X")] ?: run {
                holder.tvPocetSplneni.text = ""
                holder.tvPocetSplneni.visibility = GONE
                return
            }

            holder.tvPocetSplneni.text = "${pocetCasti.formatovat()}/${regex.find(origoDosahlost.cislo)!!.value.toInt().formatovat()}"
            holder.tvPocetSplneni.visibility = VISIBLE
        } else {

            holder.tvPocetSplneni.text = ""
            holder.tvPocetSplneni.visibility = GONE
        }

    }

    override fun getItemCount(): Int = vse.dosahlosti.size

}
