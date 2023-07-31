package cz.jaro.dopravnipodniky.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import cz.jaro.dopravnipodniky.BuildConfig
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.activities.DopravniPodnikyActivity
import cz.jaro.dopravnipodniky.cenaPruzkumuVerejnehoMineni
import cz.jaro.dopravnipodniky.databinding.PodnikBinding
import cz.jaro.dopravnipodniky.formatovat
import cz.jaro.dopravnipodniky.other.PrefsHelper.vse
import cz.jaro.dopravnipodniky.soucinPromenneNaRozsahlostiVNasobiteliPoctuLidiKteryTiNastoupiDoBusuNaZastavceKdyzZastaviANakyLidiTamJsouAMaVSobeJesteVolneMistoANasobiteleRozsahlosti
import cz.jaro.dopravnipodniky.uliceMetru
import cz.jaro.dopravnipodniky.uroven
import cz.jaro.dopravnipodniky.velkomesto
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.roundToLong
import kotlin.random.Random
import kotlin.random.Random.Default.nextDouble


class DopravniPodnikyAdapter(private val ctx: DopravniPodnikyActivity): RecyclerView.Adapter<DopravniPodnikyAdapter.ViewHolder>() {

    class ViewHolder(binding: PodnikBinding) : RecyclerView.ViewHolder(binding.root) {

        val tvJmenoPodniku = binding.tvJmenoPodniku
        val tvDobaNeaktivity = binding.tvDobaNeaktivity
        val tvZiskDp = binding.tvZiskDp
        val tvNevyzvednuto = binding.tvNevyzvednuto
        val tvPlocha = binding.tvPlocha
        val tvVelkomesto = binding.tvVelkomesto
        val tvUroven = binding.tvUroven
        val tvObyvatele = binding.tvObyvatele
        val tvBusyDp = binding.tvBusyDp
        val tvPocetUlicDp = binding.tvPocetUlicDp
        val tvPocetBarakuDp = binding.tvPocetBarakuDp
        val tvPocetZastavek = binding.tvPocetZastavek
        val cvPodnik = binding.cvPodnik
        val btnProdatPodnik = binding.btnProdatPodnik
        val btnZvecnit = binding.btnZvecnit
        val ibOtevrit = binding.ibOtevrit
        val clPodnikNahore = binding.clPodnikNahore
        val clPodnikDole = binding.clPodnikDole
        val tvPodnikOdsazeni = binding.tvPodnikOdsazeni
        val btnJizdenky = binding.btnJizdenky

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = PodnikBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val podnik = vse.podniky[position]

        podnik.apply {
            holder.tvJmenoPodniku.text = jmenoMesta

            val plocha = (velikostMesta.second.first - velikostMesta.first.first) * (velikostMesta.second.second - velikostMesta.first.second) * uliceMetru.pow(2).roundToInt()
            val uroven = uroven(plocha, cloveci, ulicove.size, ulicove.sumOf { it.potencial })

            holder.tvDobaNeaktivity.text = ctx.getString(R.string.doba_od_posledniho_otevreni, pocetSekundOdPoslednihoHrani / 60)
            holder.tvZiskDp.text = ctx.getString(R.string.zisk_kc, zisk.roundToLong().formatovat())
            holder.tvNevyzvednuto.text = ctx.getString(R.string.nevyzvednuto, nevyzvednuto.roundToLong().formatovat())
            holder.tvPlocha.text = ctx.getString(R.string.m_ctverecnych, plocha.formatovat())
            holder.tvUroven.text = "${uroven.formatovat()}. úroveň města" + if (BuildConfig.DEBUG) " (" + ulicove.sumOf { it.potencial }.formatovat() + ".)" else ""
            holder.tvVelkomesto.text = velkomesto(plocha, cloveci, uroven)
            holder.tvObyvatele.text = ctx.getString(R.string.obyvatel, cloveci.formatovat())
            holder.tvBusyDp.text = ctx.getString(R.string.pocet_busu, busy.filter { it.linka != -1L }.size, busy.size)
            holder.tvPocetUlicDp.text = ctx.getString(R.string.pocet_ulic, ulicove.size, ulicove.count { it.trolej })
            holder.tvPocetBarakuDp.text = ctx.getString(R.string.pocet_domu, baraky.size.formatovat())
            holder.tvPocetZastavek.text = ctx.getString(R.string.pocet_zastavek, zastavky.size.formatovat())
            holder.btnJizdenky.text = ctx.getString(R.string.jizdne, jizdne.formatovat())

            holder.btnZvecnit.visibility = if ("Křemže" in jmenoMesta) View.VISIBLE else View.GONE

            holder.tvPodnikOdsazeni.visibility = if (podnik == vse.podniky.last()) View.VISIBLE else View.GONE

            holder.btnZvecnit.setOnClickListener {
                jmenoMesta = "Věčné"

                ctx.finish()
            }


            holder.btnJizdenky.setOnClickListener {
                MaterialAlertDialogBuilder(ctx).apply {
                    setTitle(R.string.vyse_jizdneho)

                    val np = NumberPicker(context)

                    np.minValue = 0
                    np.maxValue = 100 + jizdne
                    np.value = jizdne
                    np.setFormatter { context.getString(R.string.kc, it.formatovat()) }

                    setView(np)

                    setNeutralButton(R.string.pruzkum_mineni) { _, _ ->
                        MaterialAlertDialogBuilder(context).apply {
                            setTitle(R.string.pruzkum_mineni)
                            setMessage(R.string.doopravdy_chcete_udelat_pruzkum)
                            setPositiveButton(context.getString(R.string.ano_kc, cenaPruzkumuVerejnehoMineni.formatovat())) { dialog, _ ->
                                dialog.cancel()

                                vse.prachy -= cenaPruzkumuVerejnehoMineni

                                MaterialAlertDialogBuilder(context).apply {

                                    setTitle(context.getString(R.string.vysledky))

                                    val map = mutableMapOf<Int, Int>()

                                    repeat(25) {
                                        val cena = (soucinPromenneNaRozsahlostiVNasobiteliPoctuLidiKteryTiNastoupiDoBusuNaZastavceKdyzZastaviANakyLidiTamJsouAMaVSobeJesteVolneMistoANasobiteleRozsahlosti(podnik) *
                                            nextDouble(.5, 1.5)).toInt()

                                        map[cena] = (map[cena] ?: 0) + 1
                                    }

                                    val list = map.toList().sortedBy { -it.second }

                                    setMessage(context.getString(R.string.vysledky_hlasovani) +
                                        list.joinToString {
                                            context.getString(R.string.pro_cenu_hlasovalo_lidi, it.first.toString(), it.second.toString())
                                        } + context.getString(R.string.prumer_je, map.keys.average().roundToInt().toString())
                                    )

                                    setPositiveButton(context.getString(R.string.nastavit_jizdne_na, map.keys.average().roundToInt().toString())) { dialog, _ ->
                                        dialog.cancel()
                                        podnik.jizdne = map.keys.average().roundToInt()
                                        ulozit()
                                    }
                                    setNegativeButton(R.string.zrusit) { dialog, _ -> dialog.cancel() }

                                    show()
                                }
                            }
                            setNegativeButton(R.string.ne) { dialog, _ -> dialog.cancel()}
                            show()
                        }
                    }

                    setPositiveButton(R.string.potvrdit) { dialog, _ ->

                        jizdne = np.value

                        dialog.cancel()
                        ulozit()
                    }

                    show()
                }
            }
            holder.btnJizdenky.setOnLongClickListener {
                MaterialAlertDialogBuilder(ctx).apply {
                    setTitle(R.string.vyse_jizdneho)

                    val clEditText = LayoutInflater.from(ctx).inflate(R.layout.edit_text, null)

                    val layout = clEditText.findViewById<TextInputLayout>(R.id.tilEditText)
                    val editText = clEditText.findViewById<TextInputEditText>(R.id.etVybirani)

                    layout.setHint(R.string.vyse_jizdneho)

                    setView(clEditText)

                    setPositiveButton(R.string.potvrdit) { dialog, _ ->
                        dialog.cancel()

                        jizdne = editText.text.toString().toInt()
                        ulozit()
                    }

                    show()
                }
                true
            }

            holder.ibOtevrit.setOnClickListener {
                vse.aktualniDp = position

                ctx.finish()
            }


            holder.cvPodnik.setOnClickListener {
                when(holder.clPodnikDole.visibility) {
                    View.VISIBLE -> {
                        holder.clPodnikDole.visibility = View.GONE
                    }
                    View.GONE -> {
                        holder.clPodnikDole.visibility = View.VISIBLE
                    }
                    View.INVISIBLE -> {
                        holder.clPodnikDole.visibility = View.VISIBLE
                    }
                }
            }

            holder.btnProdatPodnik.setOnClickListener {

                if (vse.aktualniDp == position) {

                    Toast.makeText(
                        ctx,
                        ctx.getString(R.string.nelze_prodat_dp),
                        Toast.LENGTH_LONG
                    ).show()

                    return@setOnClickListener
                }


                val ziskZaBusy = busy.sumOf { it.prodejniCena }
                val ziskZaUlice = ulicove.size * 50_000

                val celkZisk = ziskZaBusy + ziskZaUlice

                MaterialAlertDialogBuilder(ctx).apply {
                    setTitle(R.string.prodat_dp)

                    setMessage(context.getString(R.string.za_prodej_dp_dostanete, ziskZaBusy.formatovat(), ziskZaUlice.formatovat(), (.2F * celkZisk).roundToLong().formatovat(), (2 * celkZisk).formatovat()))

                    setPositiveButton(R.string.prodat) { dialog, _ ->

                        dialog.cancel()

                        val uplnecelkovaCena = celkZisk * Random.nextDouble(.2, 2.0)

                        vse.prachy += uplnecelkovaCena

                        vse.podniky[position].zesebevrazdujSe()

                        vse.podniky.removeAt(position)

                        notifyItemRemoved(position)

                        if (vse.aktualniDp > position) vse.aktualniDp -= 1

                        Toast.makeText(ctx, context.getString(R.string.prodali_jste, jmenoMesta, uplnecelkovaCena.roundToLong().formatovat()), Toast.LENGTH_LONG).show()

                        ulozit()
                    }

                    setNegativeButton(R.string.zrusit) { dialog, _ -> dialog.cancel() }

                    show()
                }
            }

            holder.btnProdatPodnik.setOnLongClickListener {
                if (vse.zobrazitLinky && !vse.automatickyEvC) {
                    Toast.makeText(ctx, ctx.getString(R.string.ahoj_joste), Toast.LENGTH_SHORT).show()
                }
                return@setOnLongClickListener true
            }
        }
    }

    override fun getItemCount(): Int = vse.podniky.size

    private fun ulozit() {

        val tvPrachy = ctx.findViewById<TextView>(R.id.tvPodnikyPrachy)
        val toolbar = ctx.findViewById<Toolbar>(R.id.toolbar5)
        val fab = ctx.findViewById<ExtendedFloatingActionButton>(R.id.fabDp)

        ctx.setTheme(vse.tema)

        notifyDataSetChanged()
        tvPrachy.text = ctx.getString(R.string.kc, vse.prachy.roundToLong().formatovat())

    }
}
