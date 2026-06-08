package cz.jaro.dopravnipodniky.data

import android.util.Log
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DPInfo
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.IntersectionType
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.StavZastavky
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Trakce
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Zastavka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.bonusoveVydajeZaNeekologicnost
import cz.jaro.dopravnipodniky.data.dopravnipodnik.busy
import cz.jaro.dopravnipodniky.data.dopravnipodnik.directionInLine
import cz.jaro.dopravnipodniky.data.dopravnipodnik.getStreets
import cz.jaro.dopravnipodniky.data.dopravnipodnik.jsouVsechnyZatrolejovane
import cz.jaro.dopravnipodniky.data.dopravnipodnik.kapacitaZastavky
import cz.jaro.dopravnipodniky.data.dopravnipodnik.krizovatkyNaLince
import cz.jaro.dopravnipodniky.data.dopravnipodnik.linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.maZastavku
import cz.jaro.dopravnipodniky.data.dopravnipodnik.orientedInLine
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ulice
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlovac
import cz.jaro.dopravnipodniky.shared.BusID
import cz.jaro.dopravnipodniky.shared.Smer
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.Text
import cz.jaro.dopravnipodniky.shared.TurnType
import cz.jaro.dopravnipodniky.shared.arcLength
import cz.jaro.dopravnipodniky.shared.delkaUlice
import cz.jaro.dopravnipodniky.shared.delkaZastavky
import cz.jaro.dopravnipodniky.shared.dobaPobytuNaZastavce
import cz.jaro.dopravnipodniky.shared.entryAndExitLength
import cz.jaro.dopravnipodniky.shared.entryAngle
import cz.jaro.dopravnipodniky.shared.entryLength
import cz.jaro.dopravnipodniky.shared.idealniInterval
import cz.jaro.dopravnipodniky.shared.indexOfFirstOrElse
import cz.jaro.dopravnipodniky.shared.jednotky.PenizZaMinutu
import cz.jaro.dopravnipodniky.shared.jednotky.Vector
import cz.jaro.dopravnipodniky.shared.jednotky.coerceAtMost
import cz.jaro.dopravnipodniky.shared.jednotky.cos
import cz.jaro.dopravnipodniky.shared.jednotky.deg
import cz.jaro.dopravnipodniky.shared.jednotky.div
import cz.jaro.dopravnipodniky.shared.jednotky.formatovat
import cz.jaro.dopravnipodniky.shared.jednotky.formatovatBezEura
import cz.jaro.dopravnipodniky.shared.jednotky.kilometersPerHour
import cz.jaro.dopravnipodniky.shared.jednotky.penez
import cz.jaro.dopravnipodniky.shared.jednotky.penezZaMin
import cz.jaro.dopravnipodniky.shared.jednotky.sin
import cz.jaro.dopravnipodniky.shared.jednotky.sumOfPenizZaMinutu
import cz.jaro.dopravnipodniky.shared.jednotky.tiku
import cz.jaro.dopravnipodniky.shared.jednotky.times
import cz.jaro.dopravnipodniky.shared.jednotky.toDuration
import cz.jaro.dopravnipodniky.shared.length
import cz.jaro.dopravnipodniky.shared.nahodnostProjetiZastavky
import cz.jaro.dopravnipodniky.shared.nasobitelZisku
import cz.jaro.dopravnipodniky.shared.odsazeniBusu
import cz.jaro.dopravnipodniky.shared.posunutiZastavky
import cz.jaro.dopravnipodniky.shared.predsazeniKrizovatky
import cz.jaro.dopravnipodniky.shared.reversedIfNegative
import cz.jaro.dopravnipodniky.shared.signedArcLength
import cz.jaro.dopravnipodniky.shared.signedEntryAndExitLength
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.shared.sumOfDp
import cz.jaro.dopravnipodniky.shared.sumOfIndexed
import cz.jaro.dopravnipodniky.shared.times
import cz.jaro.dopravnipodniky.shared.toText
import cz.jaro.dopravnipodniky.shared.turnParts
import cz.jaro.dopravnipodniky.shared.turnType
import cz.jaro.dopravnipodniky.shared.udrzbaTroleje
import cz.jaro.dopravnipodniky.shared.udrzbaZastavky
import cz.jaro.dopravnipodniky.shared.vecne
import cz.jaro.dopravnipodniky.shared.vecneLinky
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

class Updater(
    hodiny: Hodiny,
    dataSource: PreferencesDataSource,
    dosahlovac: Dosahlovac,
) {
    init {
        update(
            hodiny,
            dataSource,
            dosahlovac
        )
    }
}

@OptIn(ExperimentalTime::class)
private fun update(
    hodiny: Hodiny,
    dataSource: PreferencesDataSource,
    dosahlovac: Dosahlovac,
) {
    hodiny.registerListener(1.seconds) { dt ->
        dataSource.upravitBusy {
            forEachIndexed { i, bus ->
                this[i] = bus.copy(
                    najeto = bus.najeto + dt
                )
            }
        }
    }

    hodiny.registerListener(1.tiku) { dt ->

        val puvodniDp = dataSource.dp.first()

        dataSource.upravitBusy {
            forEachIndexed { i, oldBus ->
                val bus = updateBusAndPeopleMovement(
                    oldBus,
                    puvodniDp,
                    dt,
                    this@registerListener,
                    dataSource,
                    dosahlovac
                )
                this[i] = bus
            }
//                    Log.i("sekání", "tik: ${tik.hezky()}, čas: ${System.currentTimeMillis().hezky()}; Konec posouvání busů")
        }
    }


    hodiny.registerListener(1.seconds) { ubehlo ->

        val puvodniDp = dataSource.dp.first()
        val puvodniVse = dataSource.vse.first()

        var zisk = 0.0.penezZaMin
        var deltaPrachy = 0.0.penez

        val vydelky = puvodniDp.busy.associate { it.id to it.vydelkuj(puvodniDp) }

        val casti = detailZisku(puvodniDp, vydelky)

        puvodniDp.busy.forEach { bus ->

            // pocitani zisku
            zisk -= bus.naklady
            zisk += vydelky[bus.id]!!
//
//            println(vydelky[bus.id]!! - bus.naklady)
//            println(vydelky[bus.id]!!)

            // odebrani penez za naklady + starnuti busuu
            deltaPrachy -= bus.naklady * ubehlo
        }

        // infrastruktura

        val zaZastavky = udrzbaZastavky * puvodniDp.ulice.count { it.maZastavku }
        val zaTroleje = udrzbaTroleje * puvodniDp.ulice.count { it.maTrolej }

//                println(zaZastavky)
//                println(zaTroleje)

        deltaPrachy -= (zaZastavky * ubehlo + zaTroleje * ubehlo)
        zisk -= (zaZastavky + zaTroleje)
//
//        println(-(zaZastavky + zaTroleje))

        // dosahlosti

        dosahlovac.dosahniPocetniDosahlost(
            Dosahlost.SkupinovaDosahlost.Penize::class,
            dataSource.vse.first().prachy.plus(deltaPrachy).value.roundToInt(),
        )

        dosahlovac.dosahniPocetniDosahlost(
            Dosahlost.SkupinovaDosahlost.Bus::class,
            dataSource.dp.first().busy.size,
        )

        if (dataSource.dp.first().busy.any { it.linka != null }) dosahlovac.dosahni(Dosahlost.BusNaLince::class)

        if (puvodniDp.info.jmenoMesta == vecne) {
            dosahlovac.dosahni(Dosahlost.Vecne1::class)
            if (puvodniDp.linky.map { it.cislo.toIntOrNull() }.toSet() == vecneLinky)
                dosahlovac.dosahni(Dosahlost.Vecne2::class)
        }

        // tutorial

        dataSource.upravitTutorial {
            if (it == StavTutorialu.Odkliknuto(StavTutorialu.Tutorialujeme.Vypraveni) && puvodniVse.prachy + deltaPrachy >= 1_000_000.penez) {
                dosahlovac.dosahni(Dosahlost.DopravniPodniky::class)
                StavTutorialu.Tutorialujeme.NovejDp
            } else it
        }

        val detail = Text.Mix(*casti.toTypedArray())
//                 mimoradnosti
//
//                if (nextInt(0, nahodnostKamionu) == 1 && dp.ulicove.any { it.maTrolej }) {
//                    MaterialAlertDialogBuilder(this).apply {
//                        setTitle(R.string.kamion)
//                        setCancelable(false)
//                        setMessage(getString(R.string.kamion_prijel))
//                        setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.cancel() }
//                        show()
//                    }
//
//                    dp.ulicove.forEach { it.maTrolej = false }
//                }
//
//                if (dp.busy.size != 0) {
//
//                    val bus = dp.busy.maxByOrNull { it.ponicenost }!!
//
//                    if (bus.ponicenost > .0) {
//
//                        if (nextInt(0, (10 * TPM / bus.ponicenost).roundToInt()) == 0) {
//
//                            vse.prachy -= bus.typBusu.cena - bus.prodejniCena
//
//                            MaterialAlertDialogBuilder(this).apply {
//                                setTitle(getString(R.string.porouchany, bus.typBusu.trakce))
//                                setCancelable(false)
//                                setMessage(getString(R.string.vas_bus_se_porouchal, bus.typBusu.trakce, bus.evCislo))
//                                setPositiveButton(
//                                    getString(
//                                        R.string.zaplatit,
//                                        (bus.typBusu.cena - bus.prodejniCena).formatovat()
//                                    )
//                                ) { dialog, _ -> dialog.cancel() }
//                                show()
//                            }
//                        }
//                    }
//                }
//
//                 umírání
//
//                if (dp.cloveci != 0) if (nextInt(0, nahodnostSebevrazdy) == 0) {
//                    val clovek = Clovek()
//                    clovek.sebevrazda(this)
//                    Log.i("Sebevražda", "${clovek.jmeno} ve ${clovek.vek.roundToInt()} letech")
//                }
//                if (dp.cloveci != 0) if (nextInt(0, dobaVymreni / dp.cloveci + 1) == 0) {
//                    val clovek = Clovek()
//                    clovek.smrt(this)
//                    Log.i("Zemřel člověk", "${clovek.jmeno} zemřel ve ${clovek.vek.roundToInt()} letech")
//                }
//
        // rození

//                if (nextInt(0, dobaZnovuobnoveniPopulace / dp.cloveci + 1) == 0 && dp.baraky.any { it.kapacita != it.cloveci }) {
//                    val barak = dp.baraky.filter { it.kapacita != it.cloveci }.random()
//                    barak.cloveci++
//                    val clovek = Clovek()
//                    clovek.pojmenuj(this)
//                    Log.i("Narodil se člověk", clovek.jmeno)
//                }
//
        dataSource.upravitPrachy {
//            println(it + deltaPrachy)
            it + deltaPrachy
        }
        dataSource.upravitDPInfo { dpInfo ->
            dpInfo.copy(
//                casPosledniNavstevy = System.currentTimeMillis(),
                zisk = zisk.also {
                    zisky += zisk
//                    println("UPDATE")
//                    println(zisk)
//                    println(zisky)
//                    println(zisky.map { it.value }.average().penezZaMin)
                },
                detailZisku = detail
            )
        }
    }
}

private suspend fun updateBusAndPeopleMovement(
    oldBus: Bus,
    oldDP: DopravniPodnik,
    dt: Duration,
    scope: CoroutineScope,
    dataSource: PreferencesDataSource,
    dosahlovac: Dosahlovac
): Bus {
    var bus = oldBus
    if (bus.linka == null) {
        println("není na lince")
        return bus
    }

    val line = oldDP.linka(bus.linka)
    val streetIDs = line.ulice.reversedIfNegative(bus.smerNaLince)
    val streets = oldDP.getStreets(streetIDs)

    val street = streets[bus.poziceNaLince]
    val nextStreet = streets.getOrNull(bus.poziceNaLince + 1)

    val streetAndBusDirectionInLine = street.directionInLine(streets)
    val orientedStreet = street.orientedInLine(streetAndBusDirectionInLine)

    if (bus.typBusu.trakce is Trakce.Trolejbus && !streets.jsouVsechnyZatrolejovane()) {
        println("nemá troleje")
        return bus
    }

    if (
        bus.poziceNaLince == line.ulice.lastIndex && orientedStreet.second.let { pozice ->
            oldDP.krizovatky.find { it.pozice == pozice }
        }?.type != IntersectionType.Roundabout
    ) {
        println("nemá kruháč")
        return bus
    }

    if (bus.stavZastavky !is StavZastavky.Na) {
        // posouvani busu po mape

        val speed = bus.typBusu.maxRychlost.coerceAtMost(50.kilometersPerHour) // TODO

        val ds = speed * dt
        bus = bus.copy(
            poziceVUlici = bus.poziceVUlici + ds,
        )

        val nextIntersection = oldDP.krizovatky.find { it.pozice == orientedStreet.second }

        val turn = turnType(nextIntersection, street, nextStreet)

        if (bus.poziceVUlici < (delkaUlice - predsazeniKrizovatky) || turn == TurnType.Straight) {


            bus = bus.copy(
                poziceVUlici = bus.poziceVUlici,
                position = Vector(
                    bus.poziceVUlici,
                    sirkaUlice - odsazeniBusu - bus.typBusu.sirka / 2,
                ),
                rotation = 0.deg,
            )

            if (bus.poziceVUlici >= delkaUlice + sirkaUlice + predsazeniKrizovatky) // dokončil průjezd křižovatkou
                bus = moveToNextStreet(bus, line)
        } else { // Je v křižovatce
            var position = bus.position
            var rotation = bus.rotation

            val offsetInIntersection = bus.poziceVUlici - (delkaUlice - predsazeniKrizovatky)

            val turnParts = turn.turnParts(bus.typBusu.sirka)
            val lengthsOfTurnParts = turnParts.map { it.length }
            val anglesOfTurnParts = turnParts.map { it.entryAndExitAngle + it.arcAngle }

            val accumulativeTurnPartLengths = lengthsOfTurnParts.runningReduce { acc, length -> length + acc }
            val accumulativeTurnPartAngles = anglesOfTurnParts.runningReduce { acc, angle -> angle + acc }
            val turnLength = accumulativeTurnPartLengths.last()

            val turnPartIndex =
                accumulativeTurnPartLengths.indexOfFirstOrElse(defaultValue = { lengthsOfTurnParts.lastIndex }) { offsetInIntersection < it }

            val turnPart = turnParts[turnPartIndex]

            val turnPartOffset = accumulativeTurnPartLengths.getOrElse(turnPartIndex - 1, defaultValue = { 0.dp })
            val turnPartRotationOffset = accumulativeTurnPartAngles.getOrElse(turnPartIndex - 1, defaultValue = { 0.deg })

            val offsetInTurnPart = offsetInIntersection - turnPartOffset

            fun step(u: Double): Double = 3 * u * u - 2 * u * u * u

            if (offsetInTurnPart < turnPart.entryLength) {
                val positionInTurnSubPart = (offsetInTurnPart / turnPart.entryAndExitLength)
                    .coerceIn(0F, .5F).toDouble()
                val du = ds / turnPart.entryAndExitLength

                rotation = turnPartRotationOffset + turnPart.entryAndExitAngle * step(positionInTurnSubPart)
                val a = if (turnPart.entryAndExitAngle < 0.deg) 180.deg else 0.deg

                position = Vector(
                    position.x + turnPart.signedEntryAndExitLength * cos(rotation + a) * du,
                    position.y + turnPart.signedEntryAndExitLength * sin(rotation + a) * du,
                )

            } else if (offsetInTurnPart < turnPart.entryLength + turnPart.arcLength) {
                val positionInTurnSubPart = ((offsetInTurnPart - turnPart.entryLength) / turnPart.arcLength)
                    .coerceIn(0F, 1F).toDouble()
                val du = ds / turnPart.arcLength

                val newRotation = turnPartRotationOffset + turnPart.entryAngle + turnPart.arcAngle * positionInTurnSubPart
                val a = if (turnPart.arcAngle < 0.deg) 180.deg else 0.deg

                position = Vector(
                    position.x + turnPart.signedArcLength * cos(rotation + a) * du,
                    position.y + turnPart.signedArcLength * sin(rotation + a) * du,
                )
                rotation = newRotation
            } else {
                val positionInTurnSubPart = ((offsetInTurnPart - turnPart.arcLength) / turnPart.entryAndExitLength)
                    .coerceIn(.5F, 1F).toDouble()
                val du = ds / turnPart.entryAndExitLength

                rotation = turnPartRotationOffset + turnPart.arcAngle + turnPart.entryAndExitAngle * step(positionInTurnSubPart)
                val a = if (turnPart.entryAndExitAngle < 0.deg) 180.deg else 0.deg

                position = Vector(
                    position.x + turnPart.signedEntryAndExitLength * cos(rotation + a) * du,
                    position.y + turnPart.signedEntryAndExitLength * sin(rotation + a) * du,
                )
            }

            bus = bus.copy(
                poziceVUlici = bus.poziceVUlici,
                position = position,
                rotation = rotation,
            )

            if (bus.poziceVUlici >= (delkaUlice - predsazeniKrizovatky) + turnLength) { // dokončil průjezd křižovatkou
                bus = moveToNextStreet(bus, line)
            }
        }
    }

    if (street.zastavka != null) bus = handleBusStop(
        street, dt, scope, bus, streets,
        oldDP, line, dataSource, dosahlovac, street.zastavka,
    )

    if (street.zastavka == null && bus.stavZastavky != StavZastavky.Pred) bus = bus.copy(stavZastavky = StavZastavky.Pred)

    return bus
}

private fun moveToNextStreet(
    oldBus: Bus,
    line: Linka
): Bus {
    var bus = oldBus

    bus = bus.copy(
        poziceVUlici = predsazeniKrizovatky,
        position = Vector(predsazeniKrizovatky, sirkaUlice - odsazeniBusu - bus.typBusu.sirka / 2),
        rotation = 0.deg,
        stavZastavky = StavZastavky.Pred,
        poziceNaLince = bus.poziceNaLince + 1,
    )

    if (bus.poziceNaLince >= line.ulice.size) { // dojel na konec linky
        bus = bus.copy(
            poziceNaLince = 0,
            smerNaLince = bus.smerNaLince * Smer.Negativni,
        )
    }
    return bus
}

private suspend fun handleBusStop(
    street: Ulice,
    dt: Duration,
    scope: CoroutineScope,
    bus: Bus,
    streetsOfLine: List<Ulice>,
    oldDP: DopravniPodnik,
    line: Linka,
    dataSource: PreferencesDataSource,
    dosahlovac: Dosahlovac,
    stop: Zastavka,
): Bus {
    var stavZastavky = bus.stavZastavky

    // pobyt na zastávce
    if (bus.stavZastavky is StavZastavky.Na) {
        stavZastavky = stavZastavky.copy(doba = stavZastavky.doba + dt)

        if (stavZastavky.doba >= dobaPobytuNaZastavce.toDuration()) {

            stavZastavky = StavZastavky.Po

            scope.launch(Dispatchers.IO) {
                movePeopleOnAndOffBus(street, stop, bus, streetsOfLine, oldDP, line, dataSource)
            }
        }
    }

    // zastavení na zastávce
    if (
        stavZastavky == StavZastavky.Pred &&
        bus.poziceVUlici >= posunutiZastavky + delkaZastavky - bus.typBusu.delka
    ) {
        stavZastavky = StavZastavky.Na(
            if (Random.nextInt(0, nahodnostProjetiZastavky) == 0) {
                dosahlovac.dosahni(Dosahlost.ProjetZastavku::class)
                dobaPobytuNaZastavce.toDuration()
            } else 0.seconds
        )

        scope.launch(Dispatchers.IO) {
            // cloveci musi jit domu a na zastavku a taky na záchod
            movePeopleOnAndOffStop(street, stop, dosahlovac, dataSource)
        }
    }

    return bus.copy(stavZastavky = stavZastavky)
}

private suspend fun movePeopleOnAndOffBus(
    street: Ulice,
    stop: Zastavka,
    bus: Bus,
    streetsOfLine: List<Ulice>,
    oldDP: DopravniPodnik,
    line: Linka,
    dataSource: PreferencesDataSource
): Bus {
    var peopleCountOnStreet = street.cloveci
    var peopleCountOnStop = stop.cloveci
    var peopleCountInBus = bus.cloveci

    val gettingOffCount = getGettingOffCount(
        streetsOfLine = streetsOfLine,
        street = street,
        lineCountInStreet = oldDP.linky.count { street.id in it.ulice },
        positionOnLine = bus.poziceNaLince,
        peopleCountOnStop = peopleCountOnStop,
        peopleCountInBus = peopleCountInBus,
    )

    peopleCountInBus -= gettingOffCount
    peopleCountOnStop += gettingOffCount

    val gettingOnCount = bus.getGettingOnCount(
        streetsOfLine = streetsOfLine,
        street = street,
        lineCountInStreet = oldDP.linky.count { street.id in it.ulice },
        line = line,
        busCountOnLine = oldDP.busy.count { it.linka == line.id },
        peopleCountOnStop = peopleCountOnStop,
        dpInfo = oldDP.info,
        peopleCountInBus = peopleCountInBus,
        positionOnLine = bus.poziceNaLince,
    )

    peopleCountInBus += gettingOnCount
    peopleCountOnStop -= gettingOnCount

    if (peopleCountOnStop > street.kapacitaZastavky())
        peopleCountOnStreet += (peopleCountOnStop - street.kapacitaZastavky()).coerceAtMost(street.kapacita - street.cloveci)

    Log.i(
        "Přesunuti lidé",
        "Nastoupilo $gettingOnCount lidí a vystoupilo $gettingOffCount lidí."
    )

    dataSource.upravitPrachy {
        it + oldDP.info.jizdne * gettingOnCount * nasobitelZisku
    }
    dataSource.upravitUlice {
        val streetIndex = indexOfFirst { it.id == street.id }
        this[streetIndex] = street.copy(
            zastavka = Zastavka(peopleCountOnStop),
            cloveci = peopleCountOnStreet
        )
    }
    return bus.copy(
        cloveci = peopleCountInBus,
    )
}

private suspend fun movePeopleOnAndOffStop(
    street: Ulice,
    stop: Zastavka,
    dosahlovac: Dosahlovac,
    dataSource: PreferencesDataSource
) {
    var peopleCountOnStreet = street.cloveci
    var peopleCountOnStop = stop.cloveci

    dosahlovac.dosahni(Dosahlost.BusNaZastavce::class)

    //        println(-cloveciNaZastavce / 4)
    //        println(cloveciVUlici / 4 + 1)
    //        println(cloveciVUlici - ulice.kapacita)
    //        println(ulice.zastavka.kapacita(ulice) - cloveciNaZastavce)

    val overflowOnStop = peopleCountOnStop - street.kapacitaZastavky()
    val overflowAtHomes = peopleCountOnStreet - street.kapacita

    val freeSpaceOnStop = street.kapacitaZastavky() - peopleCountOnStop
    val freeSpaceAtHomes = street.kapacita - peopleCountOnStreet

    val peopleCountToGoToStop = Random.nextInt(
        from = -peopleCountOnStop / 4,
        until = peopleCountOnStreet / 4 + 1,
    )
        .coerceAtMost(-overflowOnStop)
        .coerceAtLeast(overflowAtHomes)
        .coerceAtMost(freeSpaceOnStop)
        .coerceAtLeast(-freeSpaceAtHomes)

    peopleCountOnStreet -= peopleCountToGoToStop
    peopleCountOnStop += peopleCountToGoToStop

    //                                        Log.i(
    //                                            "Přesunuti lidé",
    //                                            (if (lidiCoJdouZDomu >= 0) "Na zastávku na ulici přišlo" else "Ze zastávky odešlo") + " ${lidiCoJdouZDomu.absoluteValue} lidí."
    //                                        )

    dataSource.upravitUlice {
        val streetIndex = indexOfFirst { it.id == street.id }
        this[streetIndex] = street.copy(
            cloveci = peopleCountOnStreet,
            zastavka = Zastavka(peopleCountOnStop)
        )
    }
}

private fun detailZisku(
    puvodniDp: DopravniPodnik,
    vydelky: Map<BusID, PenizZaMinutu>
): List<Text> {
    val vydajeZaZastavky = puvodniDp.ulice.count { it.maZastavku } * udrzbaZastavky
    val vydajeZaTroleje = puvodniDp.ulice.count { it.maTrolej } * udrzbaTroleje
    val vydajeZaInfrastrukturu = vydajeZaZastavky + vydajeZaTroleje

    return buildList {
        this += listOf(
            R.string.celkove_prijmy.toText(puvodniDp.busy.sumOfPenizZaMinutu { vydelky[it.id]!! }.formatovat()),
            "\n".toText(),
            R.string.celkove_vydaje.toText((puvodniDp.busy.sumOfPenizZaMinutu { it.naklady } + vydajeZaInfrastrukturu).formatovat()),
            "\n".toText(),
            R.string.celkovy_zisk.toText(puvodniDp.info.zisk.formatovat()),
            "\n".toText(),
            "\n".toText(),
            R.string.vydaje_za_infrastrukturu.toText(vydajeZaInfrastrukturu.formatovat()),
            "\n".toText(),
            R.string.vydaje_za_zastavky.toText(vydajeZaZastavky.formatovat()),
            "\n".toText(),
            R.string.vydaje_za_troleje.toText(vydajeZaTroleje.formatovat()),
            "\n".toText(),
            "\n".toText(),
            R.string.vydaje_za_ekologii.toText(),
            "\n".toText(),
            R.string.vydaje_za_neekologicke_vozy.toText(puvodniDp.busy.sumOfPenizZaMinutu { it.typBusu.trakce.bonusoveVydajeZaNeekologicnost() }
                .formatovat()),
            "\n".toText(),
            "\n".toText(),
            R.string.zisk_linek.toText(),
            "\n".toText(),
        )

        this += puvodniDp.linky.flatMap { linka ->
            listOf(
                R.string.linka_vydelava_tohle.toText(
                    linka.cislo.toText(),
                    linka.busy(puvodniDp).sumOfPenizZaMinutu { vydelky[it.id]!! - it.naklady }
                        .formatovat()
                ),
                "\n".toText(),
            )
        }
        if (puvodniDp.linky.isEmpty()) this += listOf(
            R.string.nemate_zadnou_linku.toText(),
            "\n".toText(),
        )

        this += listOf(
            "\n".toText(),
            R.string.zisk_vozidel.toText(),
            "\n".toText(),
        )

        this += puvodniDp.busy.flatMap { bus ->
            listOf(
                if (bus.linka == null) {
                    R.string.bus_prodelava_tolik.toText(
                        bus.evCislo.toString().toText(),
                        bus.naklady.formatovat()
                    )
                } else {
                    R.string.bus_vydelava_tolik.toText(
                        bus.evCislo.toString().toText(),
                        vydelky[bus.id]!!.formatovatBezEura(),
                        bus.naklady.formatovatBezEura(),
                        (vydelky[bus.id]!! - bus.naklady).formatovat()
                    )
                },
                "\n".toText(),
            )
        }
        if (puvodniDp.busy.isEmpty()) this += listOf(
            R.string.nemate_zadny_bus.toText(),
            "\n".toText(),
        )
    }
}

var zisky = listOf<PenizZaMinutu>()

private fun getGettingOffCount(
    streetsOfLine: List<Ulice>,
    street: Ulice,
    lineCountInStreet: Int,
    positionOnLine: Int,
    peopleCountOnStop: Int,
    peopleCountInBus: Int,
    nextInt: (Int, Int) -> Int = Random.Default::nextInt,
) = if (positionOnLine == streetsOfLine.indexOfLast { it.maZastavku }) peopleCountInBus
else {
    val zbyvajiciKapacitaZastavky = street.kapacitaZastavky() - peopleCountOnStop
    val minuleUlice = streetsOfLine.take(positionOnLine)

    val maximumLidiCoMuzeVystoupit = min(peopleCountInBus, zbyvajiciKapacitaZastavky)

    /**
     * @see <a href="https://www.desmos.com/calculator/7n8wgwxdle">Desmos</a>
     */
    val nasobitelKapacity = 1.25 + (street.kapacita - 470.0).pow(3) / 50000000
    val nasobitelPristichZastavek = .05 * minuleUlice.count { it.maZastavku }

    /**
     * @see <a href="https://www.desmos.com/calculator/ld49gzvioo">Desmos</a>
     */
    val nasobitelPoctuLinek = 2.0.pow((lineCountInStreet - 1) / 2.0) - 1

    val nasobitel = 1 + nasobitelKapacity + nasobitelPristichZastavek + nasobitelPoctuLinek

    nextInt(0, peopleCountInBus + 1)
        .times(nasobitel)
        .roundToInt()
        .coerceAtLeast(0)
        .coerceAtMost(maximumLidiCoMuzeVystoupit)
}

private fun Bus.getGettingOnCount(
    streetsOfLine: List<Ulice>,
    street: Ulice,
    lineCountInStreet: Int,
    line: Linka,
    busCountOnLine: Int,
    peopleCountOnStop: Int,
    peopleCountInBus: Int,
    dpInfo: DPInfo,
    positionOnLine: Int,
    nextInt: (Int, Int) -> Int = Random.Default::nextInt,
) = if (positionOnLine == streetsOfLine.indexOfLast { it.maZastavku }) 0
else if (street.kapacitaZastavky() == 0) 0
else {
    val spaceLeftInBus = typBusu.kapacita - peopleCountInBus
    val streetsLeft = streetsOfLine.drop(positionOnLine)
    val interval = line.ulice.size * 2 / busCountOnLine

    val maxWantToGetOnCount =
        peopleCountOnStop / lineCountInStreet + Random.nextInt(-5, 5)
    val minHaveToGetOnCount =
        if (peopleCountOnStop > street.kapacitaZastavky()) peopleCountOnStop - street.kapacitaZastavky()
        else 0
    val maxAbleToGetOnCount = min(spaceLeftInBus, peopleCountOnStop)

    val nextStopsMultiplier = .1 * streetsLeft.count { it.maZastavku }
//    val nasobitelHezkehoCisla = if (evCislo in hezkaCisla) 1 - Math.PI / Math.E else .0
    val busAgeMultiplier = .7 - ponicenost

    /**
     * @see <a href="https://www.desmos.com/calculator/6qyvoticme">Desmos</a>
     */
    val fareMultiplier = 1 - dpInfo.jizdne.value / 20.0

    /**
     * @see <a href="https://www.desmos.com/calculator/v6hoawdstb">Desmos</a>
     */
    val intervalMultiplier =
        (0.5 - (interval - idealniInterval).pow(2) / idealniInterval.pow(2)).coerceAtLeast(-.25)

    val multiplier =
        1 + nextStopsMultiplier /*+ nasobitelHezkehoCisla*/ + busAgeMultiplier + fareMultiplier + intervalMultiplier

    nextInt(0, maxWantToGetOnCount.coerceAtLeast(1))
        .times(multiplier)
        .roundToInt()
        .coerceAtLeast(minHaveToGetOnCount)
        .coerceAtMost(maxAbleToGetOnCount)
}

fun Bus.vydelkuj(
    puvodniDp: DopravniPodnik
): PenizZaMinutu {
    if (linka == null) return 0.penezZaMin

    val linka = puvodniDp.linka(linka)
    val ulicove = linka.ulice(puvodniDp)

    if (typBusu.trakce is Trakce.Trolejbus && !ulicove.jsouVsechnyZatrolejovane()) return 0.penezZaMin

    if (ulicove.krizovatkyNaLince().let {
            listOf(it.first(), it.last())
        }.any { pozice ->
            puvodniDp.krizovatky.find { it.pozice == pozice }?.type != IntersectionType.Roundabout
        }) return 0.penezZaMin

    var cloveci = 0

    val nastupujicichZaJizdu = ulicove
        .filter {
            it.maZastavku
        }
        .sumOfIndexed { indexUliceNaLince, ulice ->

            val vystupujici = 0/*getGettingOffCount(
                streetsOfLine = ulicove,
                street = ulice,
                positionOnLine = indexUliceNaLince,
                indexUliceNaLince = indexUliceNaLince,
                linky = puvodniDp.linky,
                peopleCountOnStop = (ulice.kapacitaZastavky() * .6).roundToInt(),
                peopleCountInBus = cloveci,
                nextInt = { a, b ->
                    (a + b) / 2
                },
            ) TODO!*/
//            println((ulice.zacatek to ulice.konec) to vystupujici)

            cloveci -= vystupujici

            val nastupujici = 0/*getGettingOnCount(
                ulicove = ulicove,
                ulice = ulice,
                indexUliceNaLince = indexUliceNaLince,
                linky = puvodniDp.linky,
                linka = linka,
                busy = puvodniDp.busy,
                cloveciNaZastavce = (ulice.kapacitaZastavky() * .6).roundToInt(),
                dpInfo = puvodniDp.info,
                peopleCountInBus = cloveci,
                positionOnLine = indexUliceNaLince,
                nextInt = { a, b ->
                    (a + b) / 2
                },
            ) TODO!*/

            cloveci += nastupujici

            nastupujici
        }

    val nastupujicichZaJizduZpet = ulicove
        .filter {
            it.maZastavku
        }
        .reversed()
        .sumOfIndexed { indexUliceNaLince, ulice ->

            val vystupujici = 0/*getGettingOffCount(
                streetsOfLine = ulicove,
                street = ulice,
                positionOnLine = ulicove.lastIndex - indexUliceNaLince,
                indexUliceNaLince = indexUliceNaLince,
                linky = puvodniDp.linky,
                peopleCountOnStop = (ulice.kapacitaZastavky() * .8).roundToInt(),
                peopleCountInBus = cloveci,
                nextInt = { a, b ->
                    (a + b) / 2
                },
            ) TODO!*/

            cloveci -= vystupujici

            val nastupujici = 0/*getGettingOnCount(
                ulicove = ulicove,
                ulice = ulice,
                indexUliceNaLince = indexUliceNaLince,
                linky = puvodniDp.linky,
                linka = linka,
                busy = puvodniDp.busy,
                cloveciNaZastavce = (ulice.kapacitaZastavky() * .8).roundToInt(),
                dpInfo = puvodniDp.info,
                peopleCountInBus = cloveci,
                positionOnLine = ulicove.lastIndex - indexUliceNaLince,
                nextInt = { a, b ->
                    (a + b) / 2
                },
            ) TODO!*/

            cloveci += nastupujici

            nastupujici
        }

    val nastupujicichZaKolo = nastupujicichZaJizdu + nastupujicichZaJizduZpet

    val ziskZaKolo = puvodniDp.info.jizdne * nastupujicichZaKolo * nasobitelZisku

    val dobaUlic =
        (delkaUlice - predsazeniKrizovatky * 2) * linka.ulice.size * 2 / typBusu.maxRychlost.coerceAtMost(
            50.kilometersPerHour
        )

    val dobaKrizovatek = ulicove.windowed(
        2, partialWindows = true
    ).sumOfDp { dvojice ->
        val ulice = dvojice.first()
        val pristiUlice = dvojice.getOrNull(1)

        val orientovanaUlice = ulice.orientedInLine(ulicove)

        val krizovatka = when (smerNaLince) {
            Smer.Pozitivni -> puvodniDp.krizovatky.find { it.pozice == orientovanaUlice.second }
            Smer.Negativni -> puvodniDp.krizovatky.find { it.pozice == orientovanaUlice.first }
        }

        val zatoceni = turnType(krizovatka, ulice, pristiUlice)
        val delkaKrizovatky = zatoceni.turnParts(typBusu.sirka).sumOfDp { it.length }

        delkaKrizovatky
    } * 2 / typBusu.maxRychlost.coerceAtMost(50.kilometersPerHour)

    val dobaKola = dobaUlic + dobaKrizovatek

    return ziskZaKolo / dobaKola
}