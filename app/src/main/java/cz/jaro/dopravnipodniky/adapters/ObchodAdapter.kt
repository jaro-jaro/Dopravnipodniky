package cz.jaro.dopravnipodniky.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.activities.ObchodActivity
import cz.jaro.dopravnipodniky.classes.Bus
import cz.jaro.dopravnipodniky.classes.TypBusu
import cz.jaro.dopravnipodniky.formatovat
import cz.jaro.dopravnipodniky.naklady
import cz.jaro.dopravnipodniky.other.Dosahlosti.dosahni
import cz.jaro.dopravnipodniky.other.LinkaId
import cz.jaro.dopravnipodniky.other.Podtyp.*
import cz.jaro.dopravnipodniky.other.PrefsHelper.dp
import cz.jaro.dopravnipodniky.other.PrefsHelper.vse
import cz.jaro.dopravnipodniky.other.Trakce.*
import cz.jaro.dopravnipodniky.pozadi
import java.util.*
import kotlin.math.roundToLong

class ObchodAdapter(private val ctx: ObchodActivity, private val seznamTypuBusu: List<TypBusu>) : RecyclerView.Adapter<ObchodAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val ivZavrenoIkonka = view.findViewById<ImageView>(R.id.ivObchodZavrenoIkonka)!!
        val ivOtevrenoIkonka = view.findViewById<ImageView>(R.id.ivObchodOtevrenoIkonka)!!
        val clZavreno = view.findViewById<ConstraintLayout>(R.id.clObchodZavreno)!!
        val clOtevreno = view.findViewById<ConstraintLayout>(R.id.clObchodOtevreno)!!
        val tvModel = view.findViewById<TextView>(R.id.tvObchodModel)!!
        val tvOtevrenoModel = view.findViewById<TextView>(R.id.tvObchodOtevrenoModel)!!
        val cvBus = view.findViewById<CardView>(R.id.cvObchodBus)!!
        val tvCena = view.findViewById<TextView>(R.id.tvObchodCena)!!
        val tvTrakce = view.findViewById<TextView>(R.id.tvOtevrenoTrakce)!!
        val tvNaklady = view.findViewById<TextView>(R.id.tvObchodNaklady)!!
        val tvPopis = view.findViewById<TextView>(R.id.tvObchodPopis)!!
        val btnKoupit = view.findViewById<Button>(R.id.btnKoupit)!!

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.bus_z_obchodu, parent, false)

        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val typ = seznamTypuBusu[position]

        typ.apply {
            holder.tvNaklady.text = ctx.getString(R.string.bus_ma_naklady, naklady(
                nasobitelNakladuu,
                trakce == TROLEJBUS
            ))

            holder.tvCena.text = ctx.getString(R.string.kc, cena.toLong().formatovat())

            holder.tvModel.text = model
            holder.tvOtevrenoModel.text = model
            holder.tvPopis.text = popis
            holder.tvTrakce.text = ctx.getString(R.string.neco_mezera_neco_jinyho, podtyp, trakce)

            val ikonka = when (trakce to podtyp) {
                AUTOBUS to DIESELOVY -> R.drawable.diesel
                AUTOBUS to ZEMEPLYNOVY -> R.drawable.zemeplyn
                AUTOBUS to HYBRIDNI -> R.drawable.hybrid
                AUTOBUS to VODIKOVY -> R.drawable.vodik
                TROLEJBUS to OBYCEJNY -> R.drawable.trolej
                TROLEJBUS to PARCIALNI -> R.drawable.parcial
                ELEKTROBUS to OBYCEJNY -> R.drawable.elektro

                else -> R.drawable.blbobus_69tr_urcite_to_neni_rickroll
            }

            holder.ivOtevrenoIkonka.setImageResource(ikonka)
            holder.ivZavrenoIkonka.setImageResource(ikonka)

            holder.ivOtevrenoIkonka.setColorFilter(vse.barva)
            holder.ivZavrenoIkonka.setColorFilter(vse.barva)

            holder.cvBus.setCardBackgroundColor(pozadi(4))

            holder.btnKoupit.text = ctx.getString(R.string.koupit, cena.formatovat())

            if (Locale.getDefault().language != "cs") {
                holder.tvPopis.text = ctx.getString(R.string.bohuzel_popis_vozidla_jenom_v_cs)
                holder.tvPopis.setOnClickListener {
                    holder.tvPopis.text = popis
                }
            }

            holder.btnKoupit.setOnClickListener {
                val aktualniEvCisla = dp.busy.map { it.evCislo }

                fun zeptatSeNaPocet(dalsiKrok: (Int) -> Unit) {

                    MaterialAlertDialogBuilder(ctx).apply {
                        setTitle(R.string.nadpis_vicenasobne_kupovani)

                        val clEditText = LayoutInflater.from(ctx).inflate(R.layout.edit_text, null)

                        val layout = clEditText.findViewById<TextInputLayout>(R.id.tilEditText)
                        val editText = clEditText.findViewById<TextInputEditText>(R.id.etVybirani)

                        layout.hint = ctx.getString(R.string.pocet_busuu, trakce)

                        setView(clEditText)

                        setPositiveButton(android.R.string.ok) { dialog, _ ->
                            dialog.cancel()

                            if (editText.text!!.isEmpty() || editText.text.toString().toInt() < 1) {

                                Toast.makeText(ctx, R.string.zadejte_validni_pocet, Toast.LENGTH_LONG).show()
                                return@setPositiveButton
                            }

                            if (editText.text.toString().toInt() > 500) {

                                Toast.makeText(ctx, R.string.bohuzel_ne_vic_nez_500, Toast.LENGTH_LONG).show()
                                return@setPositiveButton
                            }

                            dalsiKrok(editText.text.toString().toInt())
                        }
                        show()
                    }
                }

                fun vybratLinku(dalsiKrok: (@LinkaId Long) -> Unit ) {

                    MaterialAlertDialogBuilder(ctx).apply {
                        setTitle(R.string.vyberte_linku)

                        val seznamLinek = when (trakce) {

                            TROLEJBUS -> dp.linky.filter { it.trolej(ctx) }
                            else -> dp.linky
                        }

                        if (seznamLinek.isEmpty()) {

                            dalsiKrok(-1)
                            return
                        }

                        val arr = seznamLinek.map { it.cislo.toString() }.toTypedArray()

                        setItems(arr) { dialog, i ->
                            dialog.cancel()

                            dalsiKrok(seznamLinek[i].id)
                        }
                        show()
                    }
                }

                fun zadejteEvC(dalsiKrok: (Int) -> Unit) {

                    MaterialAlertDialogBuilder(ctx).apply {
                        setTitle(ctx.getString(R.string.zadejte_ev_c, trakce))

                        val clEditText = LayoutInflater.from(ctx).inflate(R.layout.edit_text, null)

                        val layout = clEditText.findViewById<TextInputLayout>(R.id.tilEditText)
                        val editText = clEditText.findViewById<TextInputEditText>(R.id.etVybirani)

                        layout.hint = ctx.getString(R.string.ev_c_busu, trakce)

                        setView(clEditText)

                        setPositiveButton(android.R.string.ok) { dialog, _ ->
                            dialog.cancel()

                            dalsiKrok(editText.text.toString().toInt())
                        }
                        show()
                    }
                }

                fun koupit(seznamEvC: List<Int>, naLinku: @LinkaId Long, ctx: ObchodActivity) {
                    if (cena * seznamEvC.size > vse.prachy) {

                        Toast.makeText(ctx, R.string.malo_penez, Toast.LENGTH_SHORT).show()
                        ulozit()
                        return
                    }

                    vse.prachy -= seznamEvC.size * cena

                    seznamEvC.forEach {

                        val bus = Bus(it, typ, ctx)
                        bus.linka = naLinku

                        if (naLinku != -1L) {
                            ctx.dosahni("busNaLince1", holder.btnKoupit)
                            dp.linka(naLinku).busy += bus.id
                        }

                        dp.busy += bus
                        ctx.dosahni("busX", holder.btnKoupit)
                    }

                    Toast.makeText(ctx, ctx.getString(R.string.uspesne_koupeno_tolik_busuu, seznamEvC.size, trakce), Toast.LENGTH_SHORT).show()
                    ulozit()
                }

                when {
                    vse.automatickyEvC && vse.vicenasobnyKupovani -> {

                        zeptatSeNaPocet { pocet ->
                            vybratLinku { linka ->

                                val neobsazenaEvC = (1..1_000_000).filter { it !in aktualniEvCisla }

                                val seznamEvC = List(pocet) { neobsazenaEvC[it] }

                                koupit(seznamEvC, linka, ctx)
                            }
                        }
                    }
                    vse.automatickyEvC && !vse.vicenasobnyKupovani -> {

                        val nejnizsiNoveEvc = (1..10000).toList().first { it !in aktualniEvCisla }

                        koupit(listOf(nejnizsiNoveEvc), -1, ctx)
                    }
                    !vse.automatickyEvC && vse.vicenasobnyKupovani -> {

                        zeptatSeNaPocet { pocet ->
                            vybratLinku { linka ->
                                zadejteEvC { evC ->
                                    if (
                                        (evC until evC + pocet).toList().any { cislo -> dp.busy.any { it.evCislo == cislo } }
                                    ) {

                                        Toast.makeText(ctx, R.string.ev_c_existuje, Toast.LENGTH_SHORT).show()
                                        return@zadejteEvC
                                    }
                                    koupit((evC until evC + pocet).toList(), linka, ctx)
                                }
                            }
                        }
                    }
                    !vse.automatickyEvC && !vse.vicenasobnyKupovani -> {

                        zadejteEvC { evC ->

                            if (dp.busy.any { it.evCislo == evC }) {

                                Toast.makeText(ctx, R.string.ev_c_existuje, Toast.LENGTH_SHORT).show()
                                return@zadejteEvC
                            }

                            koupit(listOf(evC), -1, ctx)
                        }
                    }
                }
            }

            holder.cvBus.setOnClickListener {
                when (holder.clZavreno.visibility) {
                    View.VISIBLE -> {
                        holder.clZavreno.visibility = View.GONE
                        holder.clOtevreno.visibility = View.VISIBLE
                    }
                    View.GONE -> {
                        holder.clZavreno.visibility = View.VISIBLE
                        holder.clOtevreno.visibility = View.GONE
                    }
                    View.INVISIBLE -> {
                        holder.clZavreno.visibility = View.VISIBLE
                        holder.clOtevreno.visibility = View.GONE
                    }
                }
            }
        }
    }
    override fun getItemCount(): Int = seznamTypuBusu.size

    private val tvPrachy = ctx.findViewById<TextView>(R.id.tvPrachy)

    private fun ulozit() {

        tvPrachy.text = ctx.getString(R.string.kc, vse.prachy.roundToLong().formatovat())

    }

}