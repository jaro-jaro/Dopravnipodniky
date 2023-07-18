package cz.jaro.dopravnipodniky.adapters

import android.view.LayoutInflater
import android.view.View.*
import android.view.ViewGroup
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.activities.GarazActivity
import cz.jaro.dopravnipodniky.classes.Bus
import cz.jaro.dopravnipodniky.databinding.BusBinding
import cz.jaro.dopravnipodniky.formatovat
import cz.jaro.dopravnipodniky.other.Dosahlosti.dosahni
import cz.jaro.dopravnipodniky.other.Podtyp
import cz.jaro.dopravnipodniky.other.PrefsHelper.dp
import cz.jaro.dopravnipodniky.other.PrefsHelper.vse
import cz.jaro.dopravnipodniky.other.Smer
import cz.jaro.dopravnipodniky.other.Trakce
import cz.jaro.dopravnipodniky.pozadi
import kotlin.math.roundToLong

class BusyAdapter(private val ctx: GarazActivity) : RecyclerView.Adapter<BusyAdapter.ViewHolder>() {

    var vybirani = false
    val vybrane = mutableListOf<Bus>()

    class ViewHolder(binding: BusBinding) : RecyclerView.ViewHolder(binding.root) {

        val ivIkonka = binding.ivIkonka
        val vsIkonka = binding.vsIkonka
        val ivVybrano = binding.ivVybrano
        val ibSledovat = binding.ibSledovat
        val clZavreno = binding.clZavreno
        val clOtevreno = binding.clOtevreno
        val tvLinka = binding.tvLinka
        val tvEvC = binding.tvZavrenoEvC
        val cvBus = binding.cvBus
        val tvTypBusu = binding.tvTypBusu
        val tvBusModel = binding.tvBusModel
        val tvPonicenost = binding.tvPonicenost
        val tvNaklady = binding.tvNaklady
        val btnLinka = binding.btnLinka
        val btnProdat = binding.btnProdat
        val tvBusOdsazeni = binding.tvBusOdsazeni

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = BusBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val bus = dp.busy[position]
        
        bus.apply {

            val linkaId = linka

            if (linkaId != (-1).toLong()) {
                holder.tvLinka .text = ctx.getString(R.string.linka_tohle, dp.linka(linkaId).cislo)
                holder.btnLinka.text = ctx.getString(R.string.linka_tohle, dp.linka(linkaId).cislo)
            }
            else {
                holder.tvLinka.text = ""
                holder.btnLinka.text = ctx.getString(R.string.vypravit)
            }

            val ikonka = when (typBusu.trakce to typBusu.podtyp) {
                Trakce.AUTOBUS to Podtyp.DIESELOVY -> R.drawable.diesel
                Trakce.AUTOBUS to Podtyp.ZEMEPLYNOVY -> R.drawable.zemeplyn
                Trakce.AUTOBUS to Podtyp.HYBRIDNI -> R.drawable.hybrid
                Trakce.AUTOBUS to Podtyp.VODIKOVY -> R.drawable.vodik
                Trakce.TROLEJBUS to Podtyp.OBYCEJNY -> R.drawable.trolej
                Trakce.TROLEJBUS to Podtyp.PARCIALNI -> R.drawable.parcial
                Trakce.ELEKTROBUS to Podtyp.OBYCEJNY -> R.drawable.elektro

                else -> R.drawable.blbobus_69tr_urcite_to_neni_rickroll
            }
            holder.ivIkonka.setImageResource(ikonka)

            if (vybirani) {
                holder.vsIkonka.displayedChild = 0

                if (bus in vybrane) {
                    holder.ivVybrano.setImageResource(R.drawable.ic_baseline_check_box_48)

                    holder.clZavreno.setBackgroundColor(pozadi(2))
                    holder.cvBus.setCardBackgroundColor(pozadi(2))
                } else {
                    holder.ivVybrano.setImageResource(R.drawable.ic_baseline_check_box_outline_blank_48)

                    holder.clZavreno.setBackgroundColor(pozadi(6))
                    holder.cvBus.setCardBackgroundColor(pozadi(6))
                }
            } else {
                holder.vsIkonka.displayedChild = 1

                holder.clZavreno.setBackgroundColor(pozadi(4))
                holder.cvBus.setCardBackgroundColor(pozadi(4))
            }

            if (vybirani) { //todo
                val uzgone = holder.ibSledovat.visibility == GONE
                holder.ibSledovat.visibility = GONE
                if (!uzgone) {
                    holder.ibSledovat.startAnimation(loadAnimation(ctx, android.R.anim.slide_out_right))
                }
            } else {
                holder.ibSledovat.startAnimation(loadAnimation(ctx, R.anim.slide_in_right))
                holder.ibSledovat.visibility = VISIBLE
            }

            holder.ibSledovat.setIconResource(if (dp.sledovanejBus == bus) R.drawable.ic_baseline_gps_fixed_24 else R.drawable.ic_baseline_gps_not_fixed_24)

            holder.ivIkonka.setColorFilter(vse.barva)
            holder.ivVybrano.setColorFilter(vse.barva)

            holder.tvEvC.text = ctx.getString(R.string.ev_c, evCislo)

            holder.tvTypBusu.text = typBusu.trakce.toString()
            holder.tvBusModel.text = typBusu.model
            holder.tvPonicenost.text = ctx.getString(R.string.ponicenost, (ponicenost * 100/* % */))
            holder.tvNaklady.text = ctx.getString(R.string.naklady, naklady.roundToLong().formatovat())

            holder.btnProdat.text = ctx.getString(R.string.prodat_za, prodejniCena.formatovat())

            holder.tvBusOdsazeni.visibility = if (bus == dp.busy.last()) VISIBLE else GONE

            holder.ibSledovat.setOnClickListener {
                if (dp.sledovanejBus == bus) {
                    dp.sledovanejBus = null
                } else {
                    dp.sledovanejBus = bus
                }

                ulozit()
            }

            holder.btnLinka.setOnClickListener {

                MaterialAlertDialogBuilder(ctx).apply {
                    setTitle(R.string.vyberte_linku)

                    val seznamLinek = when (typBusu.trakce) {

                        Trakce.TROLEJBUS -> dp.linky.filter { it.trolej(ctx) }
                        else -> dp.linky
                    }

                    if (seznamLinek.isEmpty()) {
                        Toast.makeText(ctx, R.string.nejprve_si_vytvorte_linku, Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }

                    val arr = seznamLinek.map { it.cislo.toString() }.toTypedArray()

                    setItems(arr) { dialog, i ->

                        linka = seznamLinek[i].id

                        poziceNaLince = 0
                        poziceVUlici = 0F


                        dp.linka(linka).busy.add(id)

                        dialog.cancel()

                        ulozit()

                        context.dosahni("busNaLince", holder.clOtevreno)
                    }

                    setNeutralButton(R.string.odebrat_bus_z_linek) { dialog, _ ->

                        dp.linka(linka).busy.remove(id)

                        linka = -1L

                        poziceNaLince = 0
                        poziceVUlici = 0F
                        smerNaLince = Smer.POZITIVNE

                        dialog.cancel()

                        ulozit()
                    }

                    setNegativeButton(android.R.string.cancel) { dialog, _ ->
                        dialog.cancel()
                    }

                    show()
                }
            }

            holder.btnProdat.setOnClickListener {

                MaterialAlertDialogBuilder(ctx).apply {
                    setIcon(R.drawable.ic_baseline_delete_24_white)
                    //setTheme(R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered)
                    setTitle(R.string.fakt_chcete_prodat_bus)

                    setNegativeButton(R.string.ne) { dialog, _ ->
                        dialog.cancel()
                    }

                    setPositiveButton(R.string.ano) { dialog, _ ->

                        vse.prachy += prodejniCena

                        if (linka != -1L) dp.linka(linka).busy.remove(id)

                        zesebavrazdujSe(ctx)

                        dp.busy.removeAt(position)

                        ulozit()

                        dialog.cancel()
                    }
                    show()
                }
            }

            holder.cvBus.setOnClickListener {
                if (vybirani) {
                    if (bus in vybrane) {
                        if (vybrane.size == 1) {
                            vybrane -= bus
                            vybirani = false
                        } else {
                            vybrane -= bus
                        }
                    } else {
                        vybrane += bus
                    }
                    ulozit()
                } else {
                    when(holder.clOtevreno.visibility) {
                        VISIBLE -> {
                            holder.tvLinka.visibility = VISIBLE
                            holder.clOtevreno.visibility = GONE
                        }
                        GONE -> {
                            holder.tvLinka.visibility = GONE
                            holder.clOtevreno.visibility = VISIBLE
                        }
                        INVISIBLE -> {
                            holder.tvLinka.visibility = VISIBLE
                            holder.clOtevreno.visibility = GONE
                        }
                    }
                }
            }
            holder.cvBus.setOnLongClickListener {
                if (vybirani) {
                    if (bus in vybrane) {
                        if (vybrane.size == 1) {
                            vybrane -= bus
                            vybirani = false
                        } else {
                            vybrane -= bus
                        }
                    } else {
                        vybrane += bus
                    }
                } else {
                    vybrane += bus
                    vybirani = true
                }
                ulozit()
                true
            }
        }
    }

    override fun getItemCount(): Int = dp.busy.size

    fun ulozit() {
        notifyDataSetChanged()
        ctx.update(vybirani, vybrane)
    }
}
