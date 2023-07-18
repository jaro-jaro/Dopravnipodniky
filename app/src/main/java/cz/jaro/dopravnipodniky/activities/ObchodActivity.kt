package cz.jaro.dopravnipodniky.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.adapters.ObchodAdapter
import cz.jaro.dopravnipodniky.databinding.ActivityObchodBinding
import cz.jaro.dopravnipodniky.formatovat
import cz.jaro.dopravnipodniky.naklady
import cz.jaro.dopravnipodniky.other.Podtyp.*
import cz.jaro.dopravnipodniky.other.PrefsHelper.dp
import cz.jaro.dopravnipodniky.other.PrefsHelper.vse
import cz.jaro.dopravnipodniky.other.Razeni.*
import cz.jaro.dopravnipodniky.other.Trakce.*
import cz.jaro.dopravnipodniky.other.Tutorial.zobrazitTutorial
import cz.jaro.dopravnipodniky.other.TypyBusu.typy
import cz.jaro.dopravnipodniky.other.Vyrobce.*
import kotlin.math.roundToLong

class ObchodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityObchodBinding

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            finish()

            return true
        }

        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(vse.tema)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityObchodBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(findViewById(R.id.toolbar4))

        title = getString(R.string.obchod)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.tvPrachy.text = getString(R.string.kc, vse.prachy.roundToLong().formatovat())

        binding.rvObchod.layoutManager = LinearLayoutManager(this)
        update()

        if (zobrazitTutorial(5)) {
            dp.filtrovatTrakce += AUTOBUS
            dp.filtrovatTrakce += ELEKTROBUS
            dp.filtrovatNaklady += naklady(50 - 1.0)
            dp.filtrovatNaklady += naklady(60 - 1.0)
            dp.filtrovatNaklady += naklady(65 - 1.0)
            dp.filtrovatNaklady += naklady(70 - 1.0)
            dp.filtrovatNaklady += naklady(75 - 1.0)
            dp.filtrovatNaklady += naklady(80 - 1.0)
            dp.filtrovatJestliNaToMam = true
        }

        // zapnout podle posledniho nastaveni

        binding.chTrakceA.isChecked = AUTOBUS in dp.filtrovatTrakce
        binding.chTrakceT.isChecked = TROLEJBUS in dp.filtrovatTrakce
        binding.chTrakceE.isChecked = ELEKTROBUS in dp.filtrovatTrakce

        binding.chPodTypObyc.isChecked = OBYCEJNY in dp.filtrovatPodtyp
        binding.chPodTypDieselovy.isChecked = DIESELOVY in dp.filtrovatPodtyp
        binding.chPodTypZemeplynovy.isChecked = ZEMEPLYNOVY in dp.filtrovatPodtyp
        binding.chPodTypHybridni.isChecked = HYBRIDNI in dp.filtrovatPodtyp
        binding.chPodTypVodikovy.isChecked = VODIKOVY in dp.filtrovatPodtyp
        binding.chPodTypParcialni.isChecked = PARCIALNI in dp.filtrovatPodtyp

        binding.chDelka9.isChecked = 0..11 in dp.filtrovatDelka
        binding.chDelka12.isChecked = 11..14 in dp.filtrovatDelka
        binding.chDelka15.isChecked = 14..17 in dp.filtrovatDelka
        binding.chDelka18.isChecked = 17..19 in dp.filtrovatDelka
        binding.chDelka24.isChecked = 19..119 in dp.filtrovatDelka

        binding.chTypSolaris.isChecked = SOLARIS in dp.filtrovatTyp
        binding.chTypKarosa.isChecked = KAROSA in dp.filtrovatTyp
        binding.chTypVolvo.isChecked = VOLVO in dp.filtrovatTyp
        binding.chTypRenault.isChecked = RENAULT in dp.filtrovatTyp
        binding.chTypIveco.isChecked = IVECO in dp.filtrovatTyp
        binding.chTypIrisbus.isChecked = IRISBUS in dp.filtrovatTyp
        binding.chTypIkarus.isChecked = IKARUS in dp.filtrovatTyp
        binding.chTypHelulies.isChecked = HEULIEZ in dp.filtrovatTyp
        binding.chTypSkoda.isChecked = SKODA in dp.filtrovatTyp
        binding.chTypPraga.isChecked = PRAGA in dp.filtrovatTyp
        binding.chTypTatra.isChecked = TATRA in dp.filtrovatTyp
        binding.chTypEkova.isChecked = EKOVA in dp.filtrovatTyp
        binding.chTypHESSSSSS.isChecked = HESS in dp.filtrovatTyp
        binding.chTypBogdan.isChecked = BOGDAN in dp.filtrovatTyp

        binding.chCena100.isChecked = 0..100_000 in dp.filtrovatPrachy
        binding.chCena200.isChecked = 100_000..200_000 in dp.filtrovatPrachy
        binding.chCena300.isChecked = 200_000..900_000 in dp.filtrovatPrachy

        binding.chCenaMojePrachy.isChecked = dp.filtrovatJestliNaToMam

        binding.chNaklady50.isChecked = naklady(50 - 1.0) in dp.filtrovatNaklady
        binding.chNaklady60.isChecked = naklady(60 - 1.0) in dp.filtrovatNaklady
        binding.chNaklady65.isChecked = naklady(65 - 1.0) in dp.filtrovatNaklady
        binding.chNaklady70.isChecked = naklady(70 - 1.0) in dp.filtrovatNaklady
        binding.chNaklady75.isChecked = naklady(75 - 1.0) in dp.filtrovatNaklady
        binding.chNaklady80.isChecked = naklady(80 - 1.0) in dp.filtrovatNaklady
        binding.chNaklady85.isChecked = naklady(85 - 1.0) in dp.filtrovatNaklady
        binding.chNaklady90.isChecked = naklady(90 - 1.0) in dp.filtrovatNaklady
        binding.chNaklady95.isChecked = naklady(95 - 1.0) in dp.filtrovatNaklady
        binding.chNaklady100.isChecked = naklady(100 - 1.0) in dp.filtrovatNaklady
        binding.chNaklady500.isChecked = naklady(500 - 1.0) in dp.filtrovatNaklady

        binding.chCenaSestupne.isChecked  = dp.raditCenou == SESTUPNE
        binding.chCenaVzestupne.isChecked = dp.raditCenou == VZESTUPNE

        binding.chNakladySestupne.isChecked  = dp.raditNakladama == SESTUPNE
        binding.chNakladyVzestupne.isChecked = dp.raditNakladama == VZESTUPNE

        binding.chKapacitaSestupne.isChecked =  dp.raditKapacitou == SESTUPNE
        binding.chKapacitaVzestupne.isChecked = dp.raditKapacitou == VZESTUPNE

        update()

        // text u nekterych chipu

        binding.chTypSolaris.text = SOLARIS.toString()
        binding.chTypKarosa.text = KAROSA.toString()
        binding.chTypVolvo.text = VOLVO.toString()
        binding.chTypRenault.text = RENAULT.toString()
        binding.chTypIveco.text = IVECO.toString()
        binding.chTypIrisbus.text = IRISBUS.toString()
        binding.chTypIkarus.text = IKARUS.toString()
        binding.chTypHelulies.text = HEULIEZ.toString()
        binding.chTypSkoda.text = SKODA.toString()
        binding.chTypSOR.text = SOR.toString()
        binding.chTypJelcz.text = JELCZ.toString()
        binding.chTypPraga.text = PRAGA.toString()
        binding.chTypTatra.text = TATRA.toString()
        binding.chTypEkova.text = EKOVA.toString()
        binding.chTypHESSSSSS.text = HESS.toString()
        binding.chTypBogdan.text = BOGDAN.toString()

        binding.chNaklady50.text = naklady(50 - 1.0)
        binding.chNaklady60.text = naklady(60 - 1.0)
        binding.chNaklady65.text = naklady(65 - 1.0)
        binding.chNaklady70.text = naklady(70 - 1.0)
        binding.chNaklady75.text = naklady(75 - 1.0)
        binding.chNaklady80.text = naklady(80 - 1.0)
        binding.chNaklady85.text = naklady(85 - 1.0)
        binding.chNaklady90.text = naklady(90 - 1.0)
        binding.chNaklady95.text = naklady(95 - 1.0)
        binding.chNaklady100.text = naklady(100 - 1.0)
        binding.chNaklady500.text = naklady(500 - 1.0)

        var filtrovat = false
        var radit = false

        binding.btnFiltrovat.setOnClickListener {
            filtrovat = !filtrovat
            if (filtrovat) {
                binding.svFiltry.visibility = VISIBLE
                binding.rvObchod.visibility = GONE
                binding.svRadit.visibility = GONE
                binding.btnFiltrovat.text = getString(R.string.schovat_viltry)
                binding.btnRadit.text = getString(R.string.radit)
                radit = false
            } else {
                binding.svFiltry.visibility = GONE
                binding.rvObchod.visibility = VISIBLE
                binding.svRadit.visibility = GONE
                binding.btnFiltrovat.text = getString(R.string.filtrovat)
                binding.btnRadit.text = getString(R.string.radit)
                radit = false
            }
        }
        binding.btnRadit.setOnClickListener {
            radit = !radit
            if (radit) {
                binding.svRadit.visibility = VISIBLE
                binding.rvObchod.visibility = GONE
                binding.svFiltry.visibility = GONE
                binding.btnRadit.text = getString(R.string.schovat_razeni)
                binding.btnFiltrovat.text = getString(R.string.filtrovat)
                filtrovat = false
            } else {
                binding.svRadit.visibility = GONE
                binding.rvObchod.visibility = VISIBLE
                binding.svFiltry.visibility = GONE
                binding.btnRadit.text = getString(R.string.radit)
                binding.btnFiltrovat.text = getString(R.string.filtrovat)
                filtrovat = false
            }
        }

        binding.cgRaditCena.children.forEach { chip ->
            chip.setOnClickListener {
                dp.raditCenou = when (it) {
                    binding.chCenaSestupne -> SESTUPNE
                    binding.chCenaVzestupne -> VZESTUPNE
                    else -> NIJAK
                }

                dp.raditNakladama = NIJAK
                dp.raditKapacitou = NIJAK

                binding.chNakladySestupne.isChecked = false
                binding.chNakladyVzestupne.isChecked = false
                binding.chKapacitaSestupne.isChecked = false
                binding.chKapacitaVzestupne.isChecked = false

                update()
            }
        }

        binding.cgRaditNaklady.children.forEach { chip ->
            chip.setOnClickListener {
                dp.raditNakladama = when (it) {
                    binding.chNakladySestupne -> SESTUPNE
                    binding.chNakladyVzestupne -> VZESTUPNE
                    else -> NIJAK
                }

                dp.raditCenou = NIJAK
                dp.raditKapacitou = NIJAK

                binding.chCenaSestupne.isChecked = false
                binding.chCenaVzestupne.isChecked = false
                binding.chKapacitaSestupne.isChecked = false
                binding.chKapacitaVzestupne.isChecked = false

                update()
            }
        }

        binding.cgRaditKapacita.children.forEach { chip ->
            chip.setOnClickListener {
                dp.raditKapacitou = when (it) {
                    binding.chKapacitaSestupne -> SESTUPNE
                    binding.chKapacitaVzestupne -> VZESTUPNE
                    else -> NIJAK
                }

                dp.raditCenou = NIJAK
                dp.raditNakladama = NIJAK

                binding.chCenaSestupne.isChecked = false
                binding.chCenaVzestupne.isChecked = false
                binding.chNakladySestupne.isChecked = false
                binding.chNakladyVzestupne.isChecked = false

                update()
            }
        }

        binding.cgTrakce.children.forEach { chip ->
            chip.setOnClickListener {
                dp.filtrovatTrakce = binding.cgTrakce.checkedChipIds.map {
                    when (it) {
                        R.id.chTrakceA -> AUTOBUS
                        R.id.chTrakceT -> TROLEJBUS
                        R.id.chTrakceE -> ELEKTROBUS
                        else -> SUSBUS
                    }
                }
                update()
            }
            chip.setOnLongClickListener {
                if (binding.cgTrakce.checkedChipIds.size == binding.cgTrakce.childCount - 1) {
                    binding.cgTrakce.checkedChipIds.forEach { chipId ->
                        findViewById<Chip>(chipId).isChecked = false
                    }
                } else {
                    binding.cgTrakce.children.forEach { chip2 ->
                        (chip2 as Chip).isChecked = chip2 != it
                    }
                }
                dp.filtrovatTrakce = binding.cgTrakce.checkedChipIds.map {
                    when (it) {
                        R.id.chTrakceA -> AUTOBUS
                        R.id.chTrakceT -> TROLEJBUS
                        R.id.chTrakceE -> ELEKTROBUS
                        else -> SUSBUS
                    }
                }
                update()
                true
            }
        }
        binding.cgPodTyp.children.forEach { chip ->
            chip.setOnClickListener {
                dp.filtrovatPodtyp = binding.cgPodTyp.checkedChipIds.map {
                    when (it) {
                        R.id.chPodTypObyc -> OBYCEJNY
                        R.id.chPodTypDieselovy -> DIESELOVY
                        R.id.chPodTypZemeplynovy -> ZEMEPLYNOVY
                        R.id.chPodTypHybridni -> HYBRIDNI
                        R.id.chPodTypVodikovy -> VODIKOVY
                        R.id.chPodTypParcialni -> PARCIALNI
                        else -> OBYCEJNY
                    }
                }
                update()
            }
            chip.setOnLongClickListener {
                if (binding.cgPodTyp.checkedChipIds.size == binding.cgPodTyp.childCount - 1) {
                    binding.cgPodTyp.checkedChipIds.forEach { chipId ->
                        findViewById<Chip>(chipId).isChecked = false
                    }
                } else {
                    binding.cgPodTyp.children.forEach { chip2 ->
                        (chip2 as Chip).isChecked = chip2 != it
                    }
                }
                dp.filtrovatPodtyp = binding.cgPodTyp.checkedChipIds.map {
                    when (it) {
                        R.id.chPodTypObyc -> OBYCEJNY
                        R.id.chPodTypDieselovy -> DIESELOVY
                        R.id.chPodTypZemeplynovy -> ZEMEPLYNOVY
                        R.id.chPodTypHybridni -> HYBRIDNI
                        R.id.chPodTypVodikovy -> VODIKOVY
                        R.id.chPodTypParcialni -> PARCIALNI
                        else -> OBYCEJNY
                    }
                }
                update()
                true
            }
        }
        binding.cgDelka.children.forEach { chip ->
            chip.setOnClickListener {
                dp.filtrovatDelka = binding.cgDelka.checkedChipIds.map {
                    when (it) {
                        R.id.chDelka9 -> 0..11
                        R.id.chDelka12 -> 11..14
                        R.id.chDelka15 -> 14..17
                        R.id.chDelka18 -> 17..19
                        R.id.chDelka24 -> 19..119
                        else -> 0..11
                    }
                }
                update()
            }
            chip.setOnLongClickListener {
                if (binding.cgDelka.checkedChipIds.size == binding.cgDelka.childCount - 1) {
                    binding.cgDelka.checkedChipIds.forEach { chipId ->
                        findViewById<Chip>(chipId).isChecked = false
                    }
                } else {
                    binding.cgDelka.children.forEach { chip2 ->
                        (chip2 as Chip).isChecked = chip2 != it
                    }
                }
                dp.filtrovatDelka = binding.cgDelka.checkedChipIds.map {
                    when (it) {
                        R.id.chDelka9 -> 0..11
                        R.id.chDelka12 -> 11..14
                        R.id.chDelka15 -> 14..17
                        R.id.chDelka18 -> 17..19
                        R.id.chDelka24 -> 19..119
                        else -> 0..11
                    }
                }
                update()
                true
            }
        }
        binding.cgTyp.children.forEach { chip ->
            chip.setOnClickListener {
                dp.filtrovatTyp = binding.cgTyp.checkedChipIds.map {
                    when (it) {
                        R.id.chTypSolaris -> SOLARIS
                        R.id.chTypKarosa -> KAROSA
                        R.id.chTypVolvo -> VOLVO
                        R.id.chTypRenault -> RENAULT
                        R.id.chTypIveco -> IVECO
                        R.id.chTypIrisbus -> IRISBUS
                        R.id.chTypIkarus -> IKARUS
                        R.id.chTypHelulies -> HEULIEZ
                        R.id.chTypSkoda -> SKODA
                        R.id.chTypSOR -> SOR
                        R.id.chTypJelcz -> JELCZ
                        R.id.chTypPraga -> PRAGA
                        R.id.chTypTatra -> TATRA
                        R.id.chTypEkova -> EKOVA
                        R.id.chTypHESSSSSS -> HESS
                        R.id.chTypBogdan -> BOGDAN
                        else -> HEULIEZ
                    }
                }
                update()
            }
            chip.setOnLongClickListener {
                if (binding.cgTyp.checkedChipIds.size == binding.cgTyp.childCount - 1) {
                    binding.cgTyp.checkedChipIds.forEach { chipId ->
                        findViewById<Chip>(chipId).isChecked = false
                    }
                } else {
                    binding.cgTyp.children.forEach { chip2 ->
                        (chip2 as Chip).isChecked = chip2 != it
                    }
                }
                dp.filtrovatTyp = binding.cgTyp.checkedChipIds.map {
                    when (it) {
                        R.id.chTypSolaris -> SOLARIS
                        R.id.chTypKarosa -> KAROSA
                        R.id.chTypVolvo -> VOLVO
                        R.id.chTypRenault -> RENAULT
                        R.id.chTypIveco -> IVECO
                        R.id.chTypIrisbus -> IRISBUS
                        R.id.chTypIkarus -> IKARUS
                        R.id.chTypHelulies -> HEULIEZ
                        R.id.chTypSkoda -> SKODA
                        R.id.chTypSOR -> SOR
                        R.id.chTypJelcz -> JELCZ
                        R.id.chTypPraga -> PRAGA
                        R.id.chTypTatra -> TATRA
                        R.id.chTypEkova -> EKOVA
                        R.id.chTypHESSSSSS -> HESS
                        R.id.chTypBogdan -> BOGDAN
                        else -> HEULIEZ
                    }
                }
                update()
                true
            }
        }
        binding.cgNaklady.children.forEach { chip ->
            chip.setOnClickListener {
                dp.filtrovatNaklady = binding.cgNaklady.checkedChipIds.map {
                    binding.cgNaklady.findViewById<Chip>(it).text.toString()
                }
                update()
            }
            chip.setOnLongClickListener { chipNaKterejSiKliknul ->
                if (binding.cgNaklady.checkedChipIds.size == binding.cgNaklady.childCount - 1) {
                    binding.cgNaklady.checkedChipIds.forEach { chipId ->
                        findViewById<Chip>(chipId).isChecked = false
                    }
                } else {
                    binding.cgNaklady.children.forEach { chip2 ->
                        (chip2 as Chip).isChecked = chip2 != chipNaKterejSiKliknul
                    }
                }
                dp.filtrovatNaklady = binding.cgNaklady.checkedChipIds.map {
                    binding.cgNaklady.findViewById<Chip>(it).text.toString()
                }
                update()
                true
            }
        }
        binding.cgCena.children.forEach { chip ->
            chip.setOnClickListener {
                dp.filtrovatPrachy = binding.cgCena.checkedChipIds.filter { it != R.id.chCenaMojePrachy }.map {
                    when (it) {
                        R.id.chCena100 -> 0..100_000
                        R.id.chCena200 -> 100_000..200_000
                        R.id.chCena300 -> 200_000..900_000
                        else -> 0..0
                    }
                }
                dp.filtrovatJestliNaToMam = binding.chCenaMojePrachy.isChecked
                update()
            }
            chip.setOnLongClickListener {
                if (binding.cgCena.checkedChipIds.size == binding.cgCena.childCount - 1) {
                    binding.cgCena.checkedChipIds.forEach { chipId ->
                        findViewById<Chip>(chipId).isChecked = false
                    }
                } else {
                    binding.cgCena.children.forEach { chip2 ->
                        (chip2 as Chip).isChecked = chip2 != it
                    }
                }
                dp.filtrovatPrachy = binding.cgCena.checkedChipIds.filter { it != R.id.chCenaMojePrachy }.map {
                    when (it) {
                        R.id.chCena100 -> 0..100_000
                        R.id.chCena200 -> 100_000..200_000
                        R.id.chCena300 -> 200_000..900_000
                        else -> 0..0
                    }
                }
                dp.filtrovatJestliNaToMam = binding.chCenaMojePrachy.isChecked
                update()
                true
            }
        }
    }

    private fun update() {
        binding.rvObchod.adapter = ObchodAdapter(this,
            typy.asSequence().filter {
                if (dp.filtrovatTrakce.isEmpty()) true
                else it.trakce in dp.filtrovatTrakce
            }.filter {
                if (dp.filtrovatPodtyp.isEmpty()) true
                else it.podtyp in dp.filtrovatPodtyp
            }.filter { bus ->
                if (dp.filtrovatDelka.isEmpty()) true
                else dp.filtrovatDelka.any { bus.delka > it.first && bus.delka < it.last }
            }.filter {
                if (dp.filtrovatTyp.isEmpty()) true
                else it.vyrobce in dp.filtrovatTyp
            }.filter {
                if (dp.filtrovatNaklady.isEmpty()) true
                else naklady(it.nasobitelNakladuu, it.trakce == TROLEJBUS) in dp.filtrovatNaklady
            }.filter { bus ->
                if (dp.filtrovatPrachy.isEmpty()) true
                else dp.filtrovatPrachy.any { bus.cena in it }
            }.filter { bus ->
                if (!dp.filtrovatJestliNaToMam) true
                else bus.cena <= vse.prachy
            }.sortedBy {
                when (dp.raditCenou) {
                    NIJAK -> 0
                    VZESTUPNE -> it.cena
                    SESTUPNE -> -it.cena
                }
            }.sortedBy {
                when (dp.raditNakladama) {
                    NIJAK -> 0.0
                    VZESTUPNE -> it.nasobitelNakladuu
                    SESTUPNE -> -it.nasobitelNakladuu
                }
            }.sortedBy {
                when (dp.raditKapacitou) {
                    NIJAK -> 0
                    VZESTUPNE -> it.kapacita
                    SESTUPNE -> -it.kapacita
                }
            }.toList()
        )
    }
}
