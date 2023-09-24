package cz.jaro.dopravnipodniky.shared

import cz.jaro.dopravnipodniky.data.dopravnipodnik.Trakce.Autobus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Trakce.Elektrobus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Trakce.Trolejbus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.TypBusu
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Vyrobce.BOGDAN
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Vyrobce.EKOVA
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Vyrobce.HESS
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Vyrobce.HEULIEZ
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Vyrobce.IKARUS
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Vyrobce.IRISBUS
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Vyrobce.IVECO
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Vyrobce.KAROSA
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Vyrobce.PRAGA
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Vyrobce.RENAULT
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Vyrobce.SKODA
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Vyrobce.SOLARIS
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Vyrobce.SOR
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Vyrobce.TATRA
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Vyrobce.VOLVO
import cz.jaro.dopravnipodniky.shared.jednotky.kilometruZaHodinu
import cz.jaro.dopravnipodniky.shared.jednotky.metru
import cz.jaro.dopravnipodniky.shared.jednotky.penez
import cz.jaro.dopravnipodniky.shared.jednotky.penezZaMin
import kotlin.time.Duration.Companion.hours

// upravovat max hodin a max naklady

private val autobusy = listOf(
    TypBusu(
        model = "Škoda 706 RTO MTZ",
        trakce = Autobus.Dieslovy,
        vyrobce = SKODA,
        kapacita = 63,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 250.0.penezZaMin,
        cena = 100_000.penez,
        delka = 10.81F.metru,
        sirka = 2.500.metru,
        vydrz = 140.hours,
        popis = """
            |Škoda 706 RTO (rámový trambusový osobní) je typ československého autobusu, který byl vyráběn národním podnikem Karosa mezi lety 1958 a 1972 (první prototyp již roku 1956). Odvozen byl z nákladního automobilu Škoda 706 RT. Ve výrobě byl nahrazen autobusy Karosa řady Š. V Polsku byl ale licenčně vyráběn pod označeními Jelcz 272 a Jelcz 041 až do roku 1977. MTZ - městský autobus pro tuzemské zákazníky. Má dvoje skládací elektropneumaticky ovládané dvojkřídlové dveře. Modifikace MTZ byla dodávána všem dopravním podnikům v Československu a byla primárně určena pro provoz v rámci městské hromadné dopravy. Některé autobusy (dle provozovatele) byly také vybaveny místem pro průvodčího. V Karose bylo vyrobeno celkem 14 451 vozů 706 RTO. K tomu je ale potřeba připočítat ještě autobusy a karoserie vyrobené v SVA Holýšov a v LIAZu Rýnovice.
            |
            |Výrobce: Karosa Vysoké Mýto
            |Rok výroby: 1956 – 1972
            |Délka: 10 810 mm
            |Šířka: 2 500 mm
            |Hmotnost: 8 590 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 20
            |Míst k stání: 43
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 706 RTO MEX",
        trakce = Autobus.Dieslovy,
        vyrobce = SKODA,
        kapacita = 63,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 248.0.penezZaMin,
        cena = 100_000.penez,
        delka = 10.81F.metru,
        sirka = 2.500.metru,
        vydrz = 140.hours,
        popis = """
            |Škoda 706 RTO (rámový trambusový osobní) je typ československého autobusu, který byl vyráběn národním podnikem Karosa mezi lety 1958 a 1972 (první prototyp již roku 1956). Odvozen byl z nákladního automobilu Škoda 706 RT. Ve výrobě byl nahrazen autobusy Karosa řady Š. V Polsku byl ale licenčně vyráběn pod označeními Jelcz 272 a Jelcz 041 až do roku 1977. MEX - městský autobus určený pro export. Varianta MEX se příliš nelišila od MTZ. Rozdíly byly pouze v detailech (např. u vozů pro Kubu to byly masivní nárazníky), některé vozy MEX dokonce jezdily i v československých městech. V Karose bylo vyrobeno celkem 14 451 vozů 706 RTO. K tomu je ale potřeba připočítat ještě autobusy a karoserie vyrobené v SVA Holýšov a v LIAZu Rýnovice.
            |
            |Výrobce: Karosa Vysoké Mýto
            |Rok výroby: 1956 – 1972
            |Délka: 10 810 mm
            |Šířka: 2 500 mm
            |Hmotnost: 8 590 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 20
            |Míst k stání: 43
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 706 RTO-K",
        trakce = Autobus.Dieslovy,
        vyrobce = SKODA,
        kapacita = 105,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 248.0.penezZaMin,
        cena = 150_000.penez,
        delka = 16.12F.metru,
        sirka = 2.500.metru,
        vydrz = 140.hours,
        popis = """
            |Škoda 706 RTO-K je model československého kloubového (článkového) autobusu, který byl vyroben v jednom exempláři národním podnikem Karosa Vysoké Mýto v roce 1960 (označení Škoda pochází od výrobce motoru). Vůz byl odvozen ze známého standardního autobusu Škoda 706 RTO. Po neúspěchu výroby kloubových „erťáků“ v ČSSR se začaly v hojném počtu vyrábět v Polsku (podobně jako standardní vozy 706 RTO). Zde byly vyráběny nejprve jako Jelcz AP 02 (o kousek delší než 706 RTO-K) a poté ve verzi Jelcz AP 021 (o něco kratší než 706 RTO-K) až do roku 1975. Nejednalo se o licenční výrobu, ale vlastní vývoj Poláků.
            |
            |Výrobce: Karosa Vysoké Mýto
            |Rok výroby: 1960
            |Délka: 16 120 mm
            |Šířka: 2 500 mm
            |Hmotnost: 12 165 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 64
            |Míst k stání: 41
        """.trimMargin()
    ),
    TypBusu(
        model = "Karosa ŠM 11",
        trakce = Autobus.Dieslovy,
        vyrobce = KAROSA,
        kapacita = 96,
        maxRychlost = 70.kilometruZaHodinu,
        maxNaklady = 220.0.penezZaMin,
        cena = 90_000.penez,
        delka = 10.985F.metru,
        sirka = 2.500.metru,
        vydrz = 150.hours,
        popis = """
            |Karosa ŠM 11 je model městského autobusu vyráběného národním podnikem Karosa mezi lety 1964 a 1981. Je to nástupce městské modifikace vozu Škoda 706 RTO. ŠM 11 je standardní dvounápravový autobus určený především pro městskou hromadnou dopravu (Škoda městský – označení Škoda pochází od výrobce motorů pro celou karosáckou řadu Š).
            |
            |Výrobce: Karosa Vysoké Mýto
            |Rok výroby: 1964 – 1981
            |Délka: 10 985 mm
            |Šířka: 2 500 mm
            |Hmotnost: 7 800 kg
            |Maximální rychlost: 70 km/h
            |Míst k sezení: 29
            |Míst k stání: 67
        """.trimMargin()
    ),
    TypBusu(
        model = "Karosa ŠM 16,5",
        trakce = Autobus.Dieslovy,
        vyrobce = KAROSA,
        kapacita = 150,
        maxRychlost = 70.kilometruZaHodinu,
        maxNaklady = 220.0.penezZaMin,
        cena = 120_000.penez,
        delka = 16.66F.metru,
        sirka = 2.500.metru,
        vydrz = 150.hours,
        popis = """
            |Karosa ŠM 16,5 je městský kloubový autobus řady Š od národního podniku Karosa Vysoké Mýto ze 60. let 20. století. Vůz ŠM 16,5 byl nástupcem modelu Škoda 706 RTO-K, který byl vyroben pouze v jednom prototypu.
            |
            |Výrobce: Karosa Vysoké Mýto
            |Rok výroby: 1966 – 1969
            |Délka: 16 660 mm
            |Šířka: 2 500 mm
            |Hmotnost: 11 800 kg
            |Maximální rychlost: 70 km/h
            |Míst k sezení: 50
            |Míst k stání: 100
        """.trimMargin()
    ),
    TypBusu(
        model = "Karosa B 731",
        trakce = Autobus.Dieslovy,
        vyrobce = KAROSA,
        kapacita = 94,
        maxRychlost = 70.kilometruZaHodinu,
        maxNaklady = 200.0.penezZaMin,
        cena = 90_000.penez,
        delka = 11.055F.metru,
        sirka = 2.500.metru,
        vydrz = 324.hours,
        popis = """
            |Karosa B 731 je model městského autobusu, který vyráběla společnost Karosa Vysoké Mýto v letech 1981–1996, první prototypy vznikly v roce 1974. Jde o nástupce úspěšného autobusu Karosa ŠM 11, jehož výroba byla ukončena v roce 1981.
            |
            |Výrobce: Karosa Vysoké Mýto
            |Rok výroby: 1974 – 1996
            |Délka: 11 055 mm
            |Šířka: 2 500 mm
            |Hmotnost: 9 400 kg
            |Maximální rychlost: 70 km/h
            |Míst k sezení: 31
            |Míst k stání: 63
        """.trimMargin()
    ),
    TypBusu(
        model = "Karosa B 732",
        trakce = Autobus.Dieslovy,
        vyrobce = KAROSA,
        kapacita = 94,
        maxRychlost = 70.kilometruZaHodinu,
        maxNaklady = 180.0.penezZaMin,
        cena = 100_000.penez,
        delka = 11.055F.metru,
        sirka = 2.500.metru,
        vydrz = 232.hours,
        popis = """
            |Karosa B 732 je model příměstského a městského třídveřového autobusu, který vyráběla společnost Karosa Vysoké Mýto v letech 1983 až 1997.
            |
            |Výrobce: Karosa Vysoké Mýto
            |Rok výroby: 1983 – 1997
            |Délka: 11 055 mm
            |Šířka: 2 500 mm
            |Hmotnost: 9 400 kg
            |Maximální rychlost: 70 km/h
            |Míst k sezení: 31
            |Míst k stání: 63
        """.trimMargin()
    ),
    TypBusu(
        model = "Karosa B 741",
        trakce = Autobus.Dieslovy,
        vyrobce = KAROSA,
        kapacita = 150,
        maxRychlost = 70.kilometruZaHodinu,
        maxNaklady = 150.0.penezZaMin,
        cena = 150_000.penez,
        delka = 17.355F.metru,
        sirka = 2.500.metru,
        vydrz = 324.hours,
        popis = """
            |Karosa B 741 je městský kloubový autobus vyráběný společností Karosa Vysoké Mýto v letech 1991 až 1997. V mnohých městech vystřídal starší, již nevyhovující maďarské vozy typu Ikarus 280. V součastnosti tento typ autobusu již v Česká republice v pravidelném provozu nepotkáme, proto je tento autobus již zařazen mezi autobusy muzejní.
            |
            |Výrobce: Karosa Vysoké Mýto
            |Rok výroby: 1991 – 1997
            |Délka: 17 355 mm
            |Šířka: 2 500 mm
            |Hmotnost: 14 000 kg
            |Maximální rychlost: 70 km/h
            |Míst k sezení: 42
            |Míst k stání: 108
        """.trimMargin()
    ),
    TypBusu(
        model = "Karosa B 831", // je to velkej hnus
        trakce = Autobus.Dieslovy,
        vyrobce = KAROSA,
        kapacita = 103,
        maxRychlost = 70.kilometruZaHodinu,
        maxNaklady = 200.0.penezZaMin,
        cena = 105_000.penez,
        delka = 11.485F.metru,
        sirka = 2.500.metru,
        vydrz = (17 * 7).hours,
        popis = """
            |Karosa B 831 je městský autobus, který byl vyroben ve třech kusech národním podnikem Karosa Vysoké Mýto mezi lety 1987 a 1989. Mělo se jednat o nástupce typu Karosa B 731 a spolu s trolejbusem Škoda 17Tr se autobus B 831 měl stát součástí nové unifikované řady silničních vozidel pro městskou hromadnou dopravu. V současnosti existuje jediný vůz B 831, který je jako historický exponát v majetku Technického muzea v Brně.
            |
            |Výrobce: Karosa Vysoké Mýto
            |Rok výroby: 1987 – 1989
            |Délka: 11 485 mm
            |Šířka: 2 500 mm
            |Hmotnost: 9 400 kg
            |Maximální rychlost: 70 km/h
            |Míst k sezení: 31
            |Míst k stání: 72
        """.trimMargin()
    ),
    TypBusu(
        model = "Karosa B 832",
        trakce = Autobus.Dieslovy,
        vyrobce = KAROSA,
        kapacita = 94,
        maxRychlost = 70.kilometruZaHodinu,
        maxNaklady = 180.0.penezZaMin,
        cena = 110_000.penez,
        delka = 11.055F.metru,
        sirka = 2.500.metru,
        vydrz = 280.hours,
        popis = """
            |Karosa B 832 je model městského autobusu, který vyráběla společnost Karosa Vysoké Mýto v letech 1997 až 1999. Autobus B 832 je konstrukčně téměř shodný s vozem B 732. Jedná se o dvounápravový autobus s hranatou, polosamonosnou karoserií panelové konstrukce a motorem nacházejícím se za zadní nápravou. Designově je stejný jako B 732.
            |
            |Výrobce: Karosa Vysoké Mýto
            |Rok výroby: 1997 – 1999
            |Délka: 11 055 mm
            |Šířka: 2 500 mm
            |Hmotnost: 9 500 kg
            |Maximální rychlost: 70 km/h
            |Míst k sezení: 31
            |Míst k stání: 63
        """.trimMargin()
    ),
    TypBusu(
        model = "Karosa B 841",
        trakce = Autobus.Dieslovy,
        vyrobce = KAROSA,
        kapacita = 150,
        maxRychlost = 70.kilometruZaHodinu,
        maxNaklady = 120.0.penezZaMin,
        cena = 160_000.penez,
        delka = 17.355F.metru,
        sirka = 2.500.metru,
        vydrz = 200.hours,
        popis = """
            |Karosa B 841 je model městského kloubového autobusu, který vyráběla společnost Karosa Vysoké Mýto v letech 1997 až 1999.
            |
            |Výrobce: Karosa Vysoké Mýto
            |Rok výroby: 1997 – 1999
            |Délka: 17 355 mm
            |Šířka: 2 500 mm
            |Hmotnost: 13 700 kg
            |Maximální rychlost: 70 km/h
            |Míst k sezení: 42
            |Míst k stání: 108
        """.trimMargin()
    ),
    TypBusu(
        model = "Karosa B 931",
        trakce = Autobus.Dieslovy,
        vyrobce = KAROSA,
        kapacita = 94,
        maxRychlost = 70.kilometruZaHodinu,
        maxNaklady = 99.0.penezZaMin,
        cena = 115_000.penez,
        delka = 11.345F.metru,
        sirka = 2.500.metru,
        vydrz = 256.hours,
        popis = """
            |Karosa B 931 je model městského autobusu, který vyráběla společnost Karosa Vysoké Mýto mezi lety 1995 a 2002 (od roku 1999 v upravené variantě B 931E). Jedná se o nástupce klasického městského typu Karosa B 731.
            |
            |Výrobce: Karosa Vysoké Mýto
            |Rok výroby: 1995 – 2002
            |Délka: 11 345 mm
            |Šířka: 2 500 mm
            |Hmotnost: 10 100 kg
            |Maximální rychlost: 70 km/h
            |Míst k sezení: 31
            |Míst k stání: 63
        """.trimMargin()
    ),
    TypBusu(
        model = "Karosa B 932",
        trakce = Autobus.Dieslovy,
        vyrobce = KAROSA,
        kapacita = 94,
        maxRychlost = 70.kilometruZaHodinu,
        maxNaklady = 98.0.penezZaMin,
        cena = 120_000.penez,
        delka = 11.345F.metru,
        sirka = 2.500.metru,
        vydrz = 256.hours,
        popis = """
            |Karosa B 932 je model městského autobusu, který vyráběla společnost Karosa Vysoké Mýto v letech 1997 až 2002. Jde o nástupce typu Karosa B 732.
            |
            |Výrobce: Karosa Vysoké Mýto
            |Rok výroby: 1997 – 2002
            |Délka: 11 345 mm
            |Šířka: 2 500 mm
            |Hmotnost: 10 200 kg
            |Maximální rychlost: 70 km/h
            |Míst k sezení: 31
            |Míst k stání: 63
        """.trimMargin()
    ),
    TypBusu(
        model = "Karosa B 941",
        trakce = Autobus.Dieslovy,
        vyrobce = KAROSA,
        kapacita = 160,
        maxRychlost = 70.kilometruZaHodinu,
        maxNaklady = 99.0.penezZaMin,
        cena = 170_000.penez,
        delka = 17.615F.metru,
        sirka = 2.500.metru,
        vydrz = 256.hours,
        popis = """
            |Karosa B 941 je model městského kloubového autobusu, který vyráběla společnost Karosa Vysoké Mýto v letech 1995 až 2001. Inovovaná varianta vozidla, vyráběná od roku 1999, je označena B 941E. Autobus B 941 je nástupcem typu B 741, ve výrobě byl nahrazen typem B 961 v roce 2002.
            |
            |Výrobce: Karosa Vysoké Mýto
            |Rok výroby: 1995 – 2001
            |Délka: 17 615 mm
            |Šířka: 2 500 mm
            |Hmotnost: 14 400 kg
            |Maximální rychlost: 70 km/h
            |Míst k sezení: 42
            |Míst k stání: 118
        """.trimMargin()
    ),
    TypBusu(
        model = "Karosa B 951",
        trakce = Autobus.Dieslovy,
        vyrobce = KAROSA,
        kapacita = 99,
        maxRychlost = 70.kilometruZaHodinu,
        maxNaklady = 92.0.penezZaMin,
        cena = 125_000.penez,
        delka = 11.32F.metru,
        sirka = 2.500.metru,
        vydrz = 248.hours,
        popis = """
            |Karosa B 951 je městský autobus, který vyráběla společnost Karosa v letech 2001 až 2007 (ve variantě B 951E od roku 2003). Jedná se o nástupce typu B 931.
            |
            |Výrobce: Karosa Vysoké Mýto
            |Rok výroby: 2001 – 2007
            |Délka: 11 320 mm
            |Šířka: 2 500 mm
            |Hmotnost: 10 200 kg
            |Maximální rychlost: 70 km/h
            |Míst k sezení: 31
            |Míst k stání: 68
        """.trimMargin()
    ),
    TypBusu(
        model = "Karosa B 952",
        trakce = Autobus.Dieslovy,
        vyrobce = KAROSA,
        kapacita = 99,
        maxRychlost = 70.kilometruZaHodinu,
        maxNaklady = 89.0.penezZaMin,
        cena = 130_000.penez,
        delka = 11.32F.metru,
        sirka = 2.500.metru,
        vydrz = 251.hours,
        popis = """
            |Karosa B 952 je model městského autobusu, který vyráběla Karosa Vysoké Mýto v letech 2002 až 2006, od roku 2003 ale pouze v inovované verzi B 952E. Vůz B 952 je nástupcem typu B 932.
            |
            |Výrobce: Karosa Vysoké Mýto
            |Rok výroby: 2002 – 2006
            |Délka: 11 320 mm
            |Šířka: 2 500 mm
            |Hmotnost: 10 200 kg
            |Maximální rychlost: 70 km/h
            |Míst k sezení: 31
            |Míst k stání: 68
        """.trimMargin()
    ),
    TypBusu(
        model = "Karosa B 961",
        trakce = Autobus.Dieslovy,
        vyrobce = KAROSA,
        kapacita = 167,
        maxRychlost = 70.kilometruZaHodinu,
        maxNaklady = 89.0.penezZaMin,
        cena = 180_000.penez,
        delka = 17.59F.metru,
        sirka = 2.500.metru,
        vydrz = 251.hours,
        popis = """
            |Karosa B 961 je městský kloubový autobus, který vyráběla společnost Karosa Vysoké Mýto v letech 2002 až 2006 (od roku 2003 pak v inovované verzi označené B 961E). B 961 je nástupce typu B 941.
            |
            |Výrobce: Karosa Vysoké Mýto
            |Rok výroby: 2000 – 2006
            |Délka: 17 590 mm
            |Šířka: 2 500 mm
            |Hmotnost: 14 400 kg
            |Maximální rychlost: 70 km/h
            |Míst k sezení: 45
            |Míst k stání: 122
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 30",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 40,
        maxRychlost = 60.kilometruZaHodinu,
        maxNaklady = 300.0.penezZaMin,
        cena = 60_000.penez,
        delka = 8.4F.metru,
        sirka = 2.300.metru,
        vydrz = 100.hours,
        popis = """
            |První exemplář Ikarus 30 opustil brány továrny Matthiasland 24. února 1951. Symbolicky začala pro maďarský průmysl nová éra. Kázmér Schmiedt a jeho tým byli zodpovědní za návrh autobusu. Ve srovnání s Modelem Tr 3.5 se kapacita vozidla výrazně zvýšila a může být vybavena sedadly 30 + 1 v dálkovém provedení.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1951 – 1957
            |Délka: 8 400 mm
            |Šířka: 2 300 mm
            |Hmotnost: 9 070 kg
            |Maximální rychlost: 60 km/h
            |Míst k sezení: 30
            |Míst k stání: 10
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 31",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 45,
        maxRychlost = 60.kilometruZaHodinu,
        maxNaklady = 250.0.penezZaMin,
        cena = 70_000.penez,
        delka = 8.54F.metru,
        sirka = 2.420.metru,
        vydrz = 120.hours,
        popis = """
            |Bezprostředním předchůdcem konstrukce je Ikarus 30, který zdědil své hlavní technické vlastnosti a siluetu. Ikarus 30 byl vyroben v roce 1951 a používán pro městské i dálkové účely, ale byl téměř zcela nevhodný pro první úkol. Kromě vysoké pořizovací ceny a výrobních nákladů byla kapacita stojícího cestujícího velmi nízká. To motivovalo inženýry společnosti Ikarus, aby začali navrhovat vozidlo, které by nahradilo model 30.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1956 – 1965
            |Délka: 8 540 mm
            |Šířka: 2 420 mm
            |Hmotnost: 9 370 kg
            |Maximální rychlost: 60 km/h
            |Míst k sezení: 35
            |Míst k stání: 10
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 60",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 60,
        maxRychlost = 60.kilometruZaHodinu,
        maxNaklady = 290.0.penezZaMin,
        cena = 65_000.penez,
        delka = 9.45F.metru,
        sirka = 2.420.metru,
        vydrz = 110.hours,
        popis = """
            |Podle cílů prvního pětiletého plánu byl autobus Ikarus 60 navržen společností Csepel-Steyr v roce 1951 v továrně Ikarus v Matthiaslandu. Trolejbusová verze je Ikarus 60T.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1952 – 1959
            |Délka: 9 450 mm
            |Šířka: 2 420 mm
            |Maximální rychlost: 60 km/h
            |Míst k sezení: 24
            |Míst k stání: 36
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 66",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 80,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 200.0.penezZaMin,
        cena = 80_000.penez,
        delka = 11.33F.metru,
        sirka = 2.500.metru,
        vydrz = 130.hours,
        popis = """
            |Ikarus 66 je městský-příměstský autobus typu továrny Ikarus. Jeho obvyklá přezdívka je ''''faros'''' (česky dřevěné vlákno), pravděpodobně inspirované uspořádáním motoru vzadu vozidla a doprovodným charakteristickým motorovým stanem vyčnívajícím ze zadní části karoserie vozidla. Většina z 9 260 vyrobených kusů byla exportována do Německé demokratické republiky a s přibližně 2 700 vozidly prodanými společnostem Volán v Maďarsku to byl jeden z definujících typů autobusové dopravy v letech 1960 a 1970.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1952 – 1973
            |Délka: 11 330 mm
            |Šířka: 2 500 mm
            |Hmotnost: 13 000 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 34
            |Míst k stání: 46
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 180",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 186,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 190.0.penezZaMin,
        cena = 110_000.penez,
        delka = 16.5F.metru,
        sirka = 2.500.metru,
        vydrz = 140.hours,
        popis = """
            |Ikarus 180 je první série příměstsko-městských kloubových autobusů vyráběných společností Ikarus Body and Vehicle Factory. Sólové verze jsou typy 556 a 557. První kopie (prototyp) byla vydána v roce 1961, vozidlo bylo později ve vlastnictví FAÜ a poté MÁVAUT v Pécsi a nakonec Hotelbusz (GA-79-65). Autobus byl ve výrobě od roku 1966 do roku 1973. Celkem bylo v sérii vyrobeno 7 802 autobusů, z toho 520 pro domácí účely, z toho 416 pro hlavní město. Poslední maďarská 180 byla stažena z oběhu BKV v roce 1980.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1966 – 1973
            |Délka: 16 500 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 36
            |Míst k stání: 150
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 190",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 105,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 170.0.penezZaMin,
        cena = 90_000.penez,
        delka = 11F.metru,
        sirka = 2.500.metru,
        vydrz = 150.hours,
        popis = """
            |Ikarus 190 byl typ autobusu vyvinutý pro příměstský systém továrny Ikarus, který byl přímo v souladu s přísnými německými normami.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1973 – 1977
            |Délka: 11 000 mm
            |Šířka: 2 500 mm
            |Hmotnost: 16 000 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 45
            |Míst k stání: 60
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 211",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 60,
        maxRychlost = 70.kilometruZaHodinu,
        maxNaklady = 170.0.penezZaMin,
        cena = 70_000.penez,
        delka = 8.5F.metru,
        sirka = 2.500.metru,
        vydrz = 150.hours,
        popis = """
            |Ikarus 211 byl největší midi autobus v továrně Ikarus. Společným rysem odpružených vozidel vyráběných v Székesfehérváru je motor IFA (s výkonem 92 kW/125 k) a převodovka 5+1.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1973 – 1990
            |Délka: 8 500 mm
            |Šířka: 2 500 mm
            |Hmotnost: 9 000 kg
            |Maximální rychlost: 70 km/h
            |Míst k sezení: 31
            |Míst k stání: 29
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 216",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 66,
        maxRychlost = 70.kilometruZaHodinu,
        maxNaklady = 170.0.penezZaMin,
        cena = 75_000.penez,
        delka = 8.96F.metru,
        sirka = 2.500.metru,
        vydrz = 150.hours,
        popis = """
            |Ikarus 216 je typ Ikarus sotva známý v Maďarsku. V letech 1989 až 1990 bylo do Kuvajtu vyvezeno vyšší množství tohoto typu. Na první pohled se může zdát jako první člen 200 kloubového autobusu sólo.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1985 – 1993
            |Délka: 8 960 mm
            |Šířka: 2 500 mm
            |Hmotnost: 9 610 kg
            |Maximální rychlost: 70 km/h
            |Míst k sezení: 35
            |Míst k stání: 31
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 222",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 77,
        maxRychlost = 78.kilometruZaHodinu,
        maxNaklady = 160.0.penezZaMin,
        cena = 80_000.penez,
        delka = 9.445F.metru,
        sirka = 2.500.metru,
        vydrz = 100.hours,
        popis = """
            |Ikarus 222, sólový městský a příměstský autobus vyvinutý továrnou Ikarus v roce 1975. Nebyl sériově vyráběn, byly vyrobeny pouze 3 prototypy, které obdržely číslování K1, K2 a K3.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1975 – 1979
            |Délka: 9 445 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 78 km/h
            |Míst k sezení: 18
            |Míst k stání: 59
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 238",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 87,
        maxRychlost = 75.kilometruZaHodinu,
        maxNaklady = 150.0.penezZaMin,
        cena = 100_000.penez,
        delka = 11.395F.metru,
        sirka = 2.500.metru,
        vydrz = 150.hours,
        popis = """
            |Ikarus 238 je typ příměstského autobusu vyráběný společností Ikarus Body and Vehicle Factory v letech 1980 až 1986. Na konci roku 1970 kladla továrna Ikarus velký důraz na to, aby sloužila požadavkům trhu rozvojových zemí, a proto v roce 1980 vyrobila svůj autobus Type 238 pro export do Afriky, především s použitím amerických komponentů. Prototyp se lišil od flotily vyráběné v sériové výrobě od roku 1982: podvozek, motor, divize dveří (1-0-1 namísto 2-0-0), množství oken, které bylo možné otevřít (na prototypu bylo možné otevřít pouze jeden ze dvou), dvojité světlomety byly opuštěny. Navzdory svému příměstskému charakteru byla v autech instalována městská sedadla a neobvykle pro rodinu 200 byly autobusy vybaveny příďovými koly. Byly vyrobeny tři podtypy (238.10, 238.20A a 238.20B), z nichž 34 bylo prodáno v letech 1984 až 1986. Většina z nich byla v Ghaně a zbytek v Gambii a lišily se pouze z hlediska sedadel (nebo designu kabiny).
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1982 – 1986
            |Délka: 11 395 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 75 km/h
            |Míst k sezení: 27
            |Míst k stání: 60
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 242",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 95,
        maxRychlost = 75.kilometruZaHodinu,
        maxNaklady = 140.0.penezZaMin,
        cena = 105_000.penez,
        delka = 11F.metru,
        sirka = 2.500.metru,
        vydrz = 100.hours,
        popis = """
            |Ikarus 242 byl experimentální autobus v roce 1960. Protože měl zadní motor, měl nižší úroveň podlahy než 260. Nebyl však sériově vyráběn, protože podle směrnice hospodářské politiky té doby bylo možné soustředit se na vývoj pouze jednoho typu, kterým byla rodina 260-280, přestože měla akcelerační a brzdné schopnosti nad 242 let a získala zlatou medaili na lipském veletrhu. Z typu byla vyrobena pouze nulová řada, jejíž jedna kopie se po zkušebních jízdách stala majetkem střední odborné školy.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1969
            |Délka: 11 000 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 75 km/h
            |Míst k sezení: 27
            |Míst k stání: 68
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 250",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 82,
        maxRychlost = 75.kilometruZaHodinu,
        maxNaklady = 120.0.penezZaMin,
        cena = 110_000.penez,
        delka = 12F.metru,
        sirka = 2.500.metru,
        vydrz = 200.hours,
        popis = """
            |Ikarus 250 je první z rodiny Ikarus 200 a byl vyráběn v letech 1970 až 1996. Různé prototypy byly vystaveny na mezinárodních výstavách, kde byly vždy vysoce ceněny. Většina modelů byla šestiválcový vznětový motor Rába-MAN D2356 HM6U. Motor měl 220 koní. V roce 2020 existoval pouze jeden veřejný autobus, Ikarus 250.68 s registračním číslem kaz-698.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1970 – 1996
            |Délka: 12 000 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 75 km/h
            |Míst k sezení: 52
            |Míst k stání: 30
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 260",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 98,
        maxRychlost = 75.kilometruZaHodinu,
        maxNaklady = 100.0.penezZaMin,
        cena = 120_000.penez,
        delka = 11F.metru,
        sirka = 2.500.metru,
        vydrz = 400.hours,
        popis = """
            |Ikarus 260 je nejúspěšnější příměstsko-městský sólový autobus továrny Ikarus Body and Vehicle Factory a je známý a používaný po celém světě. Rodina 200 autobusů byla postavena na základě plánů hlavního architekta Györgyho Bálinta, károly Oszetzkyho, jenő Mádiho a Józsefa Vargy Pappa a designéra László Finty z roku 1966. První prototyp 260 byl představen v roce 1971 a výroba začala v roce 1972 a trvala až do roku 2002. Celkem 72 547. Autobus č. 260 vyjel z továrny Ikarus v Matthiaslandu a Székesfehérváru. Jeho úspěch naznačuje skutečnost, že se dostal do velké části světa, většina z nich byla zakoupena Sovětským svazem a NDR, ale mnoho autobusů bylo také zakoupeno v Polsku a Turecku, stejně jako zakoupeno Venezuelou, Madagaskarem, Sýrií, Tchaj-wanem a Islandem a dnes je na mnoha místech považováno za nostalgický autobus po věrné rekonstrukci. BKV byla největším odběratelem v Maďarsku, v letech 1972 až 1992 zakoupila více než 2 400 nových autobusů. Prostřednictvím společností Volán bylo 260 přítomno v místní dopravě venkovských měst i v meziměstské dopravě. K jeho úspěchu přispěl mimo jiné design, opuštění střešního ohybu nebo otevíracích oken, které byly v té době obrovské, a skutečnost, že autobus byl schopen splnit řadu technických požadavků podle požadavků zákazníka. Práce řidičů byla usnadněna hydraulickým posilovačem řízení a automatickou převodovkou, ale byly také vyrobeny s manuálními převodovkami (hlavně v meziměstských verzích), zatímco levnější servis byl výhodou pro operátory. Jeho trolejová verze je 260T, která na rozdíl od kloubového nebyla vyráběna sériově, byly postaveny pouze dvě, jedna v Budapešti a druhá ve Výmaru. Jako experimentální kus ve spolupráci s MÁV vyzkoušel Ikarus také železniční autobus přestavěný z 260, který byl nakonec neúspěšně realizován. V roce 1999 se vyráběly také modely na stlačený zemní plyn (CNG), které byly dodány do tisza volán, a tehdy továrna Ikarus vyzkoušela repasovanou verzi řady 200, rodinu Classic, v rámci které byl postaven autobus Ikarus C60, ale sériová výroba byla zrušena. Kloubová verze Ikarus 260 je Ikarus 280, což bylo také velké množství typů autobusů. V roce 1966 si 200člennou rodinu vysnili hlavní architekt György Bálint, starší designéři Károly Oszetzky, Jenő Mádi a József Varga Papp a László Finta, designér Ikarus 180, 556 a 557. Zkušební kus K1 byl dokončen v roce 1970 a budapešťský dopravní podnik byl požádán, aby jej otestoval. Prototyp P1 byl testován v Sovětském svazu a prototyp P2 byl testován v Institutu pro vědecký výzkum automobilové dopravy (ATUKI). Později byl přestavěn na tropický design. Prototyp P3 byl také převeden na BKV. Ve srovnání s dříve vyrobenými modely byl rozdíl mezi opuštěním střešního ohybu a použitím obdélníkové skříně s průřezem, která umístila horní okraj oken vysoko, což upřednostňovalo stojící cestující: hovorový jazyk často odkazoval na autobusy řady 200 jako na ''''panoramatické autobusy''''. Dvojité dveře umožňovaly rychlejší výměnu cestujících s šířkou otevření 1300 mm a práci řidiče pomáhala hydromechanická automatická převodovka pražského typu (později také vyráběná s Csepelem a ZF) a hydraulické servořízení. Původní jednotky měly specifický výkon 9,92 kW/t a mohly nést 75 cestujících. V průběhu let bylo postaveno nespočet podtypů, které navrhovaly autobus podle potřeb přijímajících zemí, takže došlo k technickým rozdílům. Sériová výroba 11 metrů dlouhého modelu Ikarus 260 začala v roce 1972 a trvala až do roku 2002. Až do roku 1976 měly podtypy určené pro Maďarsko jednodílnou, vyšší přístrojovou desku a na přední stěny nejprve umístily nápis I K A R U S, později přešly na nápis ''''cord-writing'''' Ikarus, který lze vysledovat až k dohodě se srbskou továrnou Ikarus (Икарус) (dnešní Ikarbus). Další charakteristikou autobusů bylo typové číslo 260 na mřížce, které bylo často vidět a čteno jako Z60. Od roku 1975 se začala vyrábět vozidla s 1/2 dílnými posuvnými okny, ve srovnání s předchozími 1/4 dílnými posuvnými okny. Dalším rysem bylo, že až do roku 1980 bylo poslední okno na straně bez dveří nerozdělené. Až do roku 1984 byla typická trilexová kola a akordeonové dveře (často vrásčité dveře v hovorové hantýrce). Na počátku roku 1990 se objednávky společnosti Ikarus neustále snižovaly (částečně díky zániku Sovětského svazu a NDR, které byly největšími zákazníky), zatímco náklady se zvyšovaly, navíc autobusy na vývoz zůstaly doma nebo zákazník zaplatil za vyvážená vozidla až pozdě, takže továrna byla v roce 1990 prohlášena za insolventní. K poklesu přispěla i skutečnost, že Ikarus vyvinul řadu 400, ale zbývající zákazníci (včetně BKV) raději kupovali spolehlivější řadu 200. V roce 1998 továrna vyzkoušela také rodinu autobusů Ikarus Classic, ale většinou jen několik maďarských volánských firem koupilo autobusy. Repasovaná verze modelu 260 byla nakonec vyrobena pod názvem Ikarus C60. Do Československa se model 260 nedovážel, avšak verze 280 zde bývala častá. Po zkušenostech s autobusem Ikarus 280 začali českoslovenští představitelé zvažovat spolupráci mezi Škodou Ostrov a maďarskou automobilkou Ikarus na vývoji trolejbusu, který by z modelu 280 vycházel. Záměr se nakonec nezrealizoval, nicméně bylo vyrobeno několik stovek trolejbusů Ikarus 280T s různými elektrickými výzbrojemi, které jezdily jak v Maďarsku, tak v Německé demokratické republice a v Bulharsku. Inspirováni trolejbusy odvozenými od verze 280 začala společnost Ikarus roku 1974 pracovat na vývoji trolejbusu odvozeného od modelu 260, který dostal název Ikarus 260T. Oproti kloubovým trolejbusům vznikly však pouze dva tyto stroje. První (260.T1) měl karosérii z autobusu verze 260 a elektrická výzbroj pocházela z vyřazeného sovětského trolejbusu ZiU-5. Od roku 1976 vozil cestující po budapešťských ulicích a svou službu skončil o devatenáct let později roku 1995. Druhý trolejbus (Ikarus 260.T2) byl konstruován na objednávku z východního Německa, kde měli dobré zkušenosti s autobusy typů 260 a 280. Během vývoje dostal trolejbus ve srovnání se svým předchůdcem modernější vybavení, a sice tyristorovou regulaci Ganz, a upravené měl rovněž dveře (oproti standardním čtyřkřídlým měl dvoukřídlé). Jeho výkon dosahoval 150 kilowatt a návrhová maximální rychlost 60 kilometrů za hodinu. Roku 1986 se prvně představil na lipském veletrhu a následně se podrobil pečlivým testům ve Výmaru. Na základě úspěšně zvládnutých zkoušek si Německo objednalo dvanáct těchto trolejbusů, které plánovalo využít pro dopravu v Postupimi. Roku 1989 však došlo v Německé demokratické republice k politickým změnám, které znamenaly pozastavení objednávky. Poslední,72 547. kus dostal poznávací značku IDY-782 a byl převeden na Tiszu Volán.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1971 – 2002
            |Délka: 11 000 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 75 km/h
            |Míst k sezení: 22
            |Míst k stání: 76
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 261",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 110,
        maxRychlost = 75.kilometruZaHodinu,
        maxNaklady = 110.0.penezZaMin,
        cena = 125_000.penez,
        delka = 11F.metru,
        sirka = 2.500.metru,
        vydrz = 200.hours,
        popis = """
            |Ikarus 261 je maďarský autobus. Byl vyvinut v roce 1978 továrnou Ikarus v Matthiaslandu. Byl vyroben v letech 1982 a 1983. Mechanicky identický se sběrnicí Ikarus 260 je její karoserie zrcadlovým obrazem typu 260. Vzhledem k umístění motoru nebyl žádný z 261 vyráběn v třídveřové verzi.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1982 – 1983
            |Délka: 11 000 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 75 km/h
            |Míst k sezení: 29
            |Míst k stání: 81
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 272",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 102,
        maxRychlost = 75.kilometruZaHodinu,
        maxNaklady = 110.0.penezZaMin,
        cena = 130_000.penez,
        delka = 11.97F.metru,
        sirka = 2.500.metru,
        vydrz = 150.hours,
        popis = """
            |Ikarus 272 je prototyp autobusu z roku 1978 s podvozkem MAN 16240 FOC.[1] Uspořádání dveří je 4-0-4.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1977 – 1982
            |Délka: 11 970 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 75 km/h
            |Míst k sezení: 29
            |Míst k stání: 73
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 280",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 147,
        maxRychlost = 75.kilometruZaHodinu,
        maxNaklady = 100.0.penezZaMin,
        cena = 140_000.penez,
        delka = 16.5F.metru,
        sirka = 2.500.metru,
        vydrz = 444.hours,
        popis = """
            |Ikarus 280 je nejúspěšnějším městským a příměstským kloubovým autobusem používaným po celém světě v karosárně a automobilce Ikarus. Od prosince 1971 prováděl zkušební jízdy v Budapešti a oficiálně byl představen v roce 1972 a sériová výroba začala následující rok, v roce 1973 - současně s pozastavením Ikarus 180 - a trvala až do roku 2002. V letech 1970 a 1980 tvořil tento typ dvě třetiny světové výroby kloubových autobusů, celkem 60 993. Autobus č. 280 vyjel z továrny Ikarus v Matthiaslandu a Székesfehérváru. Jeho úspěch naznačuje skutečnost, že byl zakoupen mnoha zeměmi světa, včetně Sovětského svazu, Polska, Turecka, Československa, ale také dosáhl Číny, Venezuely, Tuniska, Íránu a Tanzanie. Asi polovina autobusů byla prodána do Sovětského svazu, v Maďarsku jich měla každá volánská firma menší či větší počet a největším kupcem byla BKV, která v letech 1973 až 1992 zakoupila 1680 nových autobusů a v roce 2002 dalších 17 z Miskolce. Odlišný design, opuštění střešního ohybu nebo otevírací okna, která byla v té době obrovská, významně přispěly k jeho úspěchu a skutečnosti, že autobus byl schopen splnit řadu technických požadavků podle požadavků zákazníka. Práce řidičů byla usnadněna hydraulickým posilovačem řízení a automatickou převodovkou, ale byly také vyrobeny s manuálními převodovkami (hlavně v dálkových verzích), zatímco levnější servis byl výhodou pro operátory. Díky samočinnému, tedy nucenému řízení osy ''''C'''' je schopen otáčet se ve stejné zatáčce jako jeho také vyráběná sólová verze Ikarus 260. Trolejová verze 280T, která je také schopna několika sérií, se vyráběla převážně v Budapešti, Debrecínu, Segedínu, Bulharsku a NDR, ale prototypy vozidel se také účastnily zkušebních jízd v západní Evropě a Americe. Od roku 1986 měla smíšené autobusy, od roku 1996 autobusy na čistě stlačený zemní plyn (CNG) v Hajdú a Tisze Volán. V roce 1999 Ikarus vyzkoušel také repasovanou verzi řady 200 a v rámci rodiny Classic byl postaven nástupce modelu 280, Ikarus C80, z nichž 72 vyrobily a koupily především maďarské firmy Volán. Rodinu 200 si v roce 1966 vysnili hlavní architekt György Bálint, vedoucí designéři Károly Oszetzky, Jenő Mádi a József Varga Papp a László Finta, designér Ikarus 180,556 a 557. Mezi vlastnosti série patří samonosné tělo a jedinečný design. Ve srovnání s autobusy té doby bylo opuštění střešního ohybu a obdélníkové skříně průřezu novinkou, takže horní okraj oken byl zvýšen vysoko, což upřednostňovalo stojící cestující: hovorový jazyk často označoval vozidla řady 200 jako ''''panoramatické autobusy''''. Rychlou výměnu cestujících zajišťovaly dvojdveřové dveře se šířkou otevření 1300 mm a řidiči pomáhala hydromechanická automatická převodovka typu Praga a hydraulický posilovač řízení. Později továrna také používala převodovky Csepel, ZF Ecomat a Voith a dodávala autobusy s manuální převodovkou v závislosti na objednávce. Autobus byl zpočátku postaven s rába-MAN D2156 a poté šestiválcovými sacími a turbodieselovými motory Rába D10, ale díky samonosné karoserii byly schopny splnit téměř všechny speciální požadavky, takže autobusy byly postaveny s motory MAN, Daimler, Cummins, Renault a DAF, v závislosti na požadavku kupující země. Vzhledem k tomu, že v průběhu let bylo vyrobeno nespočet modelových variant, došlo kromě motoru k dalším technickým rozdílům. Hlavní výhodou kloubové verze autobusu Ikarus 260,16,5 metru dlouhého Ikarus 280, je to, že má stejný průměr poloměru otáčení jako sólová verze, protože vozidlo je navrženo s pohonem osy B (konstrukce tažného kloubu), zatímco kola hřídele ''''C'''' jsou nuceným řízením. To mu umožňuje umístit se do míst, kde se ostatní kloubová vozidla již nemohou otáčet. Sériová výroba začala v roce 1973 a trvala až do roku 2002. Podobně jako sólová verze měly podtypy určené pro Maďarsko až do roku 1976 jednodílnou, vyšší přístrojovou desku a na přední stěny umístily nápis I K A R U S, který byl později nahrazen nápisem ''''cord-writing'''' Ikarus, který lze vysledovat až k dohodě se srbskou továrnou Ikarus (Икарус) (dnešní Ikarbus). Další charakteristikou autobusů bylo typové číslo 280 na mřížce, které bylo často vnímáno jako Z80, ale provozovatelé někdy také zobrazovali své autobusy tímto způsobem. Od roku 1975 jsou vozidla vyráběna s 1/2 dílnými posuvnými okny oproti předchozím 1/4 dílným posuvným oknům. Dalším prvkem na bezdveřové straně je poslední nerozdělené okno, které se od roku 1980 vyrábělo stejně otevíratelné jako ostatní okna. Až do roku 1984 byla charakteristická také trilexová kola a akordeonové dveře (často vrásčité dveře v hovorovém jazyce). Na počátku roku 1990, částečně díky zániku Sovětského svazu a NDR, největšího zákazníka, se objednávky Ikarus snížily, zatímco náklady vzrostly. Vzhledem k inflaci ruského rublu maďarská vláda nepovolila hotové autobusy do nástupnických států Sovětského svazu, v důsledku čehož byly v továrním dvoře seřazeny stovky hotových vozidel. Kvůli nedostatku příjmů nebo významnému zpoždění příjmů byl Icarus v roce 1990 prohlášen za insolventní. K poklesu přispěla i skutečnost, že Ikarus začal vyvíjet řadu 400 jako nástupce řady 200 pozdě, takže většina zákazníků začala nakupovat z jiných autobusových továren a zbývající zákazníci (včetně BKV) upřednostňovali spolehlivější řadu 200. V roce 1998 továrna vyzkoušela rodinu autobusů Ikarus Classic, ale nakonec bylo vyrobeno pouze 72 repasovaných verzí 280 (nazývaných Ikarus C80), které většinou koupilo jen několik maďarských společností Volán. Česká a slovenská města ráda kupovala kloubové Ikarusy, což bylo dáno především tím, že jejich domácí továrny ještě nebyly schopny vyrábět kloubové autobusy. Autobusy jezdily hlavně ve větších městech, ale i menší osady měly několik kusů a vyskytovaly se i v meziměstské dopravě. V místním provozu byl pro meziměstský provoz typický podtyp 280.08 s 1/4 částečně posunutelným oknem a 4 akordeonovými dveřmi a 280.10 akordeonovými dveřmi. Odstranění modelu Ikarus souvisí s výskytem kloubového autobusu Karosa B 741 v roce 1991. V roce 1972 pořídil první pražskou 280, která dorazila do hlavního města na zkoušky a kvůli slabému motoru neudělala dobrý dojem. V roce 1976 však byla požadována další zkušební kopie, protože nárůst osobní dopravy ospravedlňoval použití kloubových autobusů. Nová zkouška byla již úspěšná, a tak v roce 1977 objednal Pražský dopravní podnik (DPP) 20 280,08 vozů, které byly na trhu již v lednu 1978. V následujících letech přicházely nové autobusy,do roku 1985 bylo v Praze 315 skladových vozů 280. Poslední nákup nových autobusů proběhl v únoru 1991, ale v roce 1992 bylo z Pardubic zakoupeno 19 kusů z druhé ruky. Posledních 280 v oběhu bylo sešrotováno v roce 2001. Ikarus č. p. 4382 se zachoval pro potomstvo, které lze nalézt převážně v Muzeu mhd v Praze a při některých příležitostech i na ulici jako nostalgickou trasu. V Brně, to je jedno z nejmenších měst na Moravě a v Čechách, proto se mu také někdy přezdívá například skanzen, bylo dodáno 106 280,08 a 10 280,12. Autobusy přijely v letech 1980 až 1989, poslední byl sešrotován v roce 2001 a několik exemplářů bylo prodáno do Bulharska, Ruska, Maďarska a na Slovensko. Autobus č. 2090 je ve vlastnictví Technického muzea a slouží nostalgickým účelům. V letech 1977 až 1990 bylo v Ostravě uvedeno na trh 102 kloubových Ikarusů. Na přelomu tisíciletí žádný z nich neběžel, ale zachovali si číslo 280.10 číslo 4070, se kterým se lze dodnes setkat při nostalgických výletech. V České republice byl poslední Ikarus 280, který byl provozován v Táboře, vyřazen v roce 2007. Poslední vyrobený exemplář byl odeslán do Tádžikistánu a poslední z maďarských exemplářů, IAA-833, patřil do populace Kisalföld Volán.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1973 – 2002
            |Délka: 16 500 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 75 km/h
            |Míst k sezení: 36
            |Míst k stání: 111
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 281",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 133,
        maxRychlost = 75.kilometruZaHodinu,
        maxNaklady = 110.0.penezZaMin,
        cena = 145_000.penez,
        delka = 16.5F.metru,
        sirka = 2.500.metru,
        vydrz = 200.hours,
        popis = """
            |Ikarus 281 je kloubový autobus vyvinutý společností Ikarus Body and Vehicle Factory, která byla pravostranně řízenou verzí modelu Ikarus 280. Ikarus dodal v roce 1974 do Tanzanie 32 autobusů Ikarus 280.99, jejichž kokpit byl stále vlevo. Prototyp 281 byl představen v roce 1978 a do roku 1993 bylo vyrobeno celkem 165 kusů, které zakoupil Mosambik, Tanzanie a Indonésie, a výstavní kus byl také na Novém Zélandu. Vzhledem k umístění motoru nebyl žádný z 281 vyráběn ve čtyřdveřové verzi.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1978 – 1993
            |Délka: 16 500 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 75 km/h
            |Míst k sezení: 49
            |Míst k stání: 84
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 282",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 152,
        maxRychlost = 75.kilometruZaHodinu,
        maxNaklady = 110.0.penezZaMin,
        cena = 150_000.penez,
        delka = 18F.metru,
        sirka = 2.500.metru,
        vydrz = 200.hours,
        popis = """
            |Ikarus 282 je jedním z kloubových autobusů továrny Ikarus Body and Vehicle Factory, kterých bylo vyrobeno pouze 11. Autobus byl o jeden a půl metru delší než Ikarus 280 a měl ještě jedno okno na zadní části zápěstí. V BKV se inscenace natáčela v Budapešti, stejně jako v barvách Volánbusz a Szabolcs Volán. V roce 1979 se 282 zúčastnil také veletrh v Poznani. Sólová verze je Ikarus 262.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1976 – 1978
            |Délka: 18 000 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 75 km/h
            |Míst k sezení: 49
            |Míst k stání: 103
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 283",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 205,
        maxRychlost = 75.kilometruZaHodinu,
        maxNaklady = 110.0.penezZaMin,
        cena = 155_000.penez,
        delka = 18F.metru,
        sirka = 2.500.metru,
        vydrz = 190.hours,
        popis = """
            |Ikarus 283 je jedním z kloubových autobusů karoserie a automobilky Ikarus, kterých bylo vyrobeno 1003. Motor 18metrové sběrnice, stejně jako základní typ Ikarus 280, je umístěn mezi nápravou ''''A'''' a osou ''''B'''', osa ''''B'''' je poháněna, ale osa ''''C'''' není nuceným řízením, takže průměr poloměru otáčení je větší. Zadní dvojitá kola kompenzují vyšší zatížení nápravy. Druhé dveře většiny vyráběných autobusů jsou díky prostorově náročnějšímu motoru MAN před obvyklými. Sólová verze je Ikarus 263 a pravá verze je Ikarus 285.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1988 – 1997
            |Délka: 18 000 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 75 km/h
            |Míst k sezení: 45
            |Míst k stání: 160
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 293",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 229,
        maxRychlost = 75.kilometruZaHodinu,
        maxNaklady = 100.0.penezZaMin,
        cena = 250_000.penez,
        delka = 22.68F.metru,
        sirka = 2.500.metru,
        vydrz = 300.hours,
        popis = """
            |Ikarus 293 je speciální autobus Ikarus, který se pyšnil titulem ''''nejdelší maďarský autobus'''': má dvojitou kloubovou konstrukci, tj. Skládá se ze tří členů, takže má celkovou délku 24 m. Model 293 odpovídal plus jednočlenné verzi tradičních městských kloubových autobusů řady 200, která byla vyrobena v roce 1988, ale nakonec byl vyroben pouze jeden z těchto modelů, protože konstrukce s takovými rozměry nefungovala dokonale. Během zkušební jízdy si řidiči stěžovali na pomalost, výkyvy, silné zrychlení a zatáčení vozidla. Po účasti na mnoha výstavách v Maďarsku i v zahraničí byl autobus odvezen do areálu Ikarus v Matthiaslandu. V roce 1992 jej koupila teheránská společnost, která změnila jeho interiér, nahradila motor motorem MAN o objemu 280 lei a přelakovala jej. Vzhledem ke zvýšeným prostorovým nárokům nového motoru byly dopředu posunuty i druhé dveře pro cestující. Autobus pak jel ''''na vlastní nohy'''', tedy na vlastních kolech,do Teheránu, kde sloužil až do roku 2009. Fotografie objevené na konci roku 2018 ukazují, že autobus je stále neporušený a stojí v garáži teheránské dopravní společnosti.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1988
            |Délka: 22 680 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 75 km/h
            |Míst k sezení: 69
            |Míst k stání: 160
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 405",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 46,
        maxRychlost = 76.kilometruZaHodinu,
        maxNaklady = 100.0.penezZaMin,
        cena = 90_000.penez,
        delka = 7.3F.metru,
        sirka = 2.500.metru,
        vydrz = 180.hours,
        popis = """
            |Ikarus 405 je příměstsko-městský midibusový typ továrny na autobusy Ikarus v Budapešti. Byl vyvinut v roce 1995. Původně byl vybaven vznětovým motorem Perkins Phaser 135T Euro 1. Může přepravit 46 cestujících a má 16 míst k sezení.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1995
            |Délka: 7 300 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 76 km/h
            |Míst k sezení: 16
            |Míst k stání: 30
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 412",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 62,
        maxRychlost = 75.kilometruZaHodinu,
        maxNaklady = 100.0.penezZaMin,
        cena = 110_000.penez,
        delka = 12F.metru,
        sirka = 2.500.metru,
        vydrz = 180.hours,
        popis = """
            |Ikarus 412 je příměstsko-městský nízkopodlažní sólový autobus továrny Ikarus Body and Vehicle Factory. Delší verze Ikarus 411. V roce 1995 byl představen prototyp, který byl vyroben pro německý export, s motorem Mercedes-Benz OM 447 E2 s divadelní podlahovou verzí. Poprvé se objevil v Maďarsku v Győru v roce 1996, který byl vyroben s motorem Rába D10 Euro 2. Proslavil se svým vystoupením v Budapešti (1999). To může přepravit asi 60 cestujících.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1995
            |Délka: 12 000 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 75 km/h
            |Míst k sezení: 23
            |Míst k stání: 39
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 415",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 101,
        maxRychlost = 82.kilometruZaHodinu,
        maxNaklady = 99.0.penezZaMin,
        cena = 115_000.penez,
        delka = 11.6F.metru,
        sirka = 2.500.metru,
        vydrz = 170.hours,
        popis = """
            |Ikarus 415 je příměstsko-městský standardní podlahový sólový autobus továrny na autobusy Ikarus. Byl vyroben ve třech sériích.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1987 – 2002
            |Délka: 11 600 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 82 km/h
            |Míst k sezení: 23
            |Míst k stání: 78
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 417",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 168,
        maxRychlost = 90.kilometruZaHodinu,
        maxNaklady = 98.0.penezZaMin,
        cena = 155_000.penez,
        delka = 17.63F.metru,
        sirka = 2.500.metru,
        vydrz = 220.hours,
        popis = """
            |Ikarus 417 je příměstsko-městský nízkopodlažní (bez schodišťový) posuvný autobus továrny na autobusy Ikarus v Budapešti. S tímto designem byl také průkopníkem ve světě. Byl představen v roce 1994.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1995 – 2002
            |Délka: 17 630 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 90 km/h
            |Míst k sezení: 31
            |Míst k stání: 137
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 435",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 111,
        maxRychlost = 75.kilometruZaHodinu,
        maxNaklady = 99.0.penezZaMin,
        cena = 150_000.penez,
        delka = 17.85F.metru,
        sirka = 2.500.metru,
        vydrz = 200.hours,
        popis = """
            |Ikarus 435 je příměstsko-městský posuvný autobus továrny Ikarus Body and Vehicle Factory. První série byla vyrobena v roce 1985 a druhá v roce 1994 s motorem DAF LT 195 Euro 1. Prototyp vozidla sloužil místní dopravě Salgótarján až do roku 2018.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1985 – 2002
            |Délka: 17 850 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 75 km/h
            |Míst k sezení: 31
            |Míst k stání: 80
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 436",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 97,
        maxRychlost = 75.kilometruZaHodinu,
        maxNaklady = 96.0.penezZaMin,
        cena = 160_000.penez,
        delka = 18.034F.metru,
        sirka = 2.600.metru,
        vydrz = 195.hours,
        popis = """
            |Ikarus 436 je městský kloubový autobus továrny Ikarus Body and Vehicle Factory pro Spojené státy americké. Byl založen na Ikarus 435, kromě toho, že americká verze je o 10 palců širší. Jeho sólová verze je Ikarus 416, založená na Ikarus 415.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1989 – 2013
            |Délka: 18 034 mm
            |Šířka: 2 600 mm
            |Maximální rychlost: 75 km/h
            |Míst k sezení: 31
            |Míst k stání: 66
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 480",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 85,
        maxRychlost = 75.kilometruZaHodinu,
        maxNaklady = 99.0.penezZaMin,
        cena = 160_000.penez,
        delka = 11.71F.metru,
        sirka = 2.500.metru,
        vydrz = 204.hours,
        popis = """
            |Ikarus 480 je příměstsko-městský sólový autobus továrny Ikarus Body and Vehicle Factory. Vývoj byl zahájen v roce 1988 ve spolupráci se společností DAF. S použitím podvozku DAF SB 220, který šel do výroby v roce 1989, byl prototyp první verze dokončen v tomto roce a oficiálně schválen v Anglii.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1990 – 1997
            |Délka: 11 710 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 75 km/h
            |Míst k sezení: 32
            |Míst k stání: 53
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 489 Polaris",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 69,
        maxRychlost = 75.kilometruZaHodinu,
        maxNaklady = 90.0.penezZaMin,
        cena = 100_000.penez,
        delka = 12F.metru,
        sirka = 2.500.metru,
        vydrz = 254.hours,
        popis = """
            |Ikarus 489 Polaris je jedním z typů podvozkových autobusů jedinečné tovární jednotky karoserie a výroby vozidel Ikarus.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 2000 – 2001
            |Délka: 12 000 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 75 km/h
            |Míst k sezení: 44
            |Míst k stání: 25
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 521",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 31,
        maxRychlost = 60.kilometruZaHodinu,
        maxNaklady = 500.0.penezZaMin,
        cena = 40_000.penez,
        delka = 6.84F.metru,
        sirka = 2.090.metru,
        vydrz = 50.hours,
        popis = """
            |Ikarus 521 je typ městského midibusu postaveného na podvozku nákladního vozidla Volkswagen LT 55 od společnosti Ikarus Body and Vehicle Factory. V roce 1989 BKV zakoupila 16 modelů, které obdržely poznávací značky BY-62-01 - BY-62-16. S modelem bylo kvůli jeho konstrukci mnoho problémů, protože nedostatek místa způsobil, že autobusy byly neustále přeplněné, možná je to tim, že je to hnus. Pravá řada sedadel se otáčela směrem do chodby a jednodveřová konstrukce také ztěžovala tok cestujících. Kromě cestujících nebyli řidiči spokojeni ani s typem: manuální převodovka ztěžovala řízení autobusů a kabiny řidiče nebyly izolovány od prostoru pro cestující, takže řidiči byli v zimě chladní. Na lince Várbusz jezdily pouze pět let a v roce 1995 byly nově vyvinuté Ikarus 405 nahrazeny BKV.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1987 – 1990
            |Délka: 6 840 mm
            |Šířka: 2 090 mm
            |Maximální rychlost: 60 km/h
            |Míst k sezení: 13
            |Míst k stání: 18
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus V127",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 69,
        maxRychlost = 75.kilometruZaHodinu,
        maxNaklady = 60.0.penezZaMin,
        cena = 130_000.penez,
        delka = 12.7F.metru,
        sirka = 2.090.metru,
        vydrz = 234.hours,
        popis = """
            |Ikarus V127 (spory v roce 2015). Od té doby jsou městské autobusy Mabi-bus Modulo M108d vyráběny v experimentální spolupráci PKD (částečně sražená souprava) v prostorách budapešťské Transport Zrt. a na výrobní lince mabi-bus (později Ikarus Unique).
            |
            |Výrobce: Ikarus, Mabi-BKV
            |Rok výroby: od roku 2013
            |Délka: 12 700 mm
            |Šířka: 2 090 mm
            |Maximální rychlost: 75 km/h
            |Míst k sezení: 29
            |Míst k stání: 40
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus V187",
        trakce = Autobus.Dieslovy,
        vyrobce = IKARUS,
        kapacita = 176,
        maxRychlost = 75.kilometruZaHodinu,
        maxNaklady = 60.0.penezZaMin,
        cena = 160_000.penez,
        delka = 18.75F.metru,
        sirka = 2.090.metru,
        vydrz = 234.hours,
        popis = """
            |V roce 2016 byl dokončen první Ikarus v187 také přezdívaný Modulo M168d, který je v podstatě modernizovanou a novou verzí verze 2010. Mají novou přední a zadní stěnu. BKV jej testovala na autobusové lince 5 od dubna do června 2016.
            |
            |Výrobce: Ikarus, Mabi-BKV
            |Rok výroby: od roku 2016
            |Délka: 18 750 mm
            |Šířka: 2 090 mm
            |Maximální rychlost: 75 km/h
            |Míst k sezení: 43
            |Míst k stání: 133
        """.trimMargin()
    ),
    TypBusu(
        model = "Volvo 7900 LAH",
        trakce = Autobus.Dieslovy,
        vyrobce = VOLVO,
        kapacita = 138,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 62.0.penezZaMin,
        cena = 225_000.penez,
        delka = 18.135F.metru,
        sirka = 2.550.metru,
        vydrz = 178.hours,
        popis = """
            |Volvo 7900 LAH je hybridní kloubový autobus, vyráběný švédskou společností Volvo v polském závodě ve Vratislavi. Volvo 7900 LAH je původně vyvinuto z modelu Volvo 7900.
            |
            |Výrobce: Volvo
            |Rok výroby: od roku 2013
            |Délka: 18 135 mm
            |Šířka: 2 550 mm
            |Hmotnost: 14 400 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 46
            |Míst k stání: 92
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 8,6 III",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 55,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 78.0.penezZaMin,
        cena = 80_000.penez,
        delka = 8.6F.metru,
        sirka = 2.400.metru,
        vydrz = 196.hours,
        popis = """
            |Solaris Urbino 8,6 (také Solaris Alpino nebo Solaris Alpino 8,6) je nízkopodlažní model městského midibusu vyráběný v letech 2006 až 2018 polskou společností Solaris Bus & Coach SA v Bolechowo-Osiedle u Poznaně. Tento model byl nejkratší v rodině Solaris Urbino a byl navržen pro provoz na tratích s malým tokem cestujících procházejícími úzkými horskými silnicemi nebo úzkými ulicemi na sídlištích nebo v centrech měst. Od samého počátku byl nabízen jako třetí generace autobusů Solaris Urbino a jeho výroba skončila stažením autobusů této generace z nabídky výrobce. Na jejím základě vznikly nízkopodlažní elektrické modely Urbino 8.9 LE a Urbino 8.9 LE.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2006 – 2018
            |Délka: 8 600 mm
            |Šířka: 2 400 mm
            |Hmotnost: 9 000 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 15
            |Míst k stání: 40
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 8,9 III LE",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 55,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 78.0.penezZaMin,
        cena = 90_000.penez,
        delka = 8.95F.metru,
        sirka = 2.400.metru,
        vydrz = 196.hours,
        popis = """
            |Solaris Urbino 8,9 LE (také Solaris Alpino 8,9 LE) je nízkopodlažní autobus střední třídy pro meziměstskou komunikaci, varianta nízkopodlažního autobusu Solaris Alpino. Od roku 2008 jej vyrábí polská společnost Solaris Bus & Coach SA z Bolechowa u Poznaně.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2008 – 2018
            |Délka: 8 950 mm
            |Šířka: 2 400 mm
            |Hmotnost: 9 500 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 27
            |Míst k stání: 28
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 9 I",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 79,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 89.0.penezZaMin,
        cena = 60_000.penez,
        delka = 9.35F.metru,
        sirka = 2.550.metru,
        vydrz = 169.hours,
        popis = """
            |Solaris Urbino 9 je nízkopodlažní autobus z řady Solaris Urbino určený pro městskou hromadnou dopravu.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2000 – 2002
            |Délka: 9 350 mm
            |Šířka: 2 550 mm
            |Hmotnost: 8 400 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 21
            |Míst k stání: 58
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 10 II",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 91,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 78.0.penezZaMin,
        cena = 70_000.penez,
        delka = 9.94F.metru,
        sirka = 2.550.metru,
        vydrz = 196.hours,
        popis = """
            |Solaris Urbino 10 II je nízkopodlažní autobus z řady Solaris Urbino, určený pro městskou hromadnou dopravu, vyráběný v letech 2002–2018 polskou společností Solaris Bus & Coach SA z Bolechowa u Poznaně.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2002 – 2005
            |Délka: 9 940 mm
            |Šířka: 2 550 mm
            |Hmotnost: 9 300 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 22
            |Míst k stání: 69
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 10 III",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 91,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 69.0.penezZaMin,
        cena = 80_000.penez,
        delka = 9.94F.metru,
        sirka = 2.550.metru,
        vydrz = 196.hours,
        popis = """
            |Solaris Urbino 10 III je nízkopodlažní autobus z řady Solaris Urbino, určený pro městskou hromadnou dopravu, vyráběný v letech 2002–2018 polskou společností Solaris Bus & Coach SA z Bolechowa u Poznaně.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2005 – 2018
            |Délka: 9 940 mm
            |Šířka: 2 550 mm
            |Hmotnost: 9 300 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 22
            |Míst k stání: 69
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 10 III CNG",
        trakce = Autobus.Zemeplynovy,
        vyrobce = SOLARIS,
        kapacita = 91,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 68.0.penezZaMin,
        cena = 95_000.penez,
        delka = 9.94F.metru,
        sirka = 2.550.metru,
        vydrz = 196.hours,
        popis = """
            |Solaris Urbino 10 III CNG je nízkopodlažní autobus z řady Solaris Urbino, určený pro městskou hromadnou dopravu, vyráběný v letech 2002–2018 polskou společností Solaris Bus & Coach SA z Bolechowa u Poznaně.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2005 – 2018
            |Délka: 9 940 mm
            |Šířka: 2 550 mm
            |Hmotnost: 9 300 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 22
            |Míst k stání: 69
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 10,5 IV",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 89,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 60.0.penezZaMin,
        cena = 90_000.penez,
        delka = 10.55F.metru,
        sirka = 2.550.metru,
        vydrz = 225.hours,
        popis = """
            |Solaris Urbino 10,5 je nízkopodlažní městský autobus, vyráběný od roku 2017 společností Solaris Bus & Coach v Bolechowo-Osiedle u Poznaně. Patří do rodiny městských autobusů Solaris Urbino. Jde o nástupce staženého modelu Solaris Urbino 10.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2017
            |Délka: 10 550 mm
            |Šířka: 2 550 mm
            |Hmotnost: 9 300 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 29
            |Míst k stání: 60
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 12 I",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 104,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 89.0.penezZaMin,
        cena = 80_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 169.hours,
        popis = """
            |Solaris Urbino 12 I byl poprvé představen na mezinárodním veletrhu v Poznani. Prototyp se mírně lišil od následných sériových modelů, byl vybaven mj obě zrcátka stažena dopředu (což je běžnější u autobusů než u městských autobusů). Některé prvky exteriéru měly oproti sériovým modelům mírně odlišný tvar.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 1999 – 2001
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 10 400 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 43
            |Míst k stání: 61
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 12 II",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 104,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 79.0.penezZaMin,
        cena = 90_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 196.hours,
        popis = """
            |Solaris Urbino 12 je nízkopodlažní městský autobus ze série Solaris Urbino vyráběný od roku 1999 polskou společností Solaris Bus & Coach v Bolechów-Osiedle u Poznaně. Druhá generace Solarisu Urbino 12 byla uvedena na trh na přelomu let 2001/2002 a vyráběla se až do roku 2006. Jednalo se o vylepšenou verzi prvního dílu, ale ve srovnání s předchozí generací nezavedla zásadní technické změny.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2002 – 2006
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 10 400 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 43
            |Míst k stání: 61
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 12 III",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 104,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 68.0.penezZaMin,
        cena = 100_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 225.hours,
        popis = """
            |V roce 2005 na mezinárodním veletrhu v Poznani představil Solaris Bus & Coach novou verzi městských autobusů Urbino 12. Třetí generace se oproti svým předchůdcům vyznačovala mnoha změnami. Změnil se vnější design, byly zavedeny ostřejší linie a hrany. Byl modernizován tvar předních a zadních světel, byly představeny nové přední a zadní stěny. Upustilo se i od zaoblené hrany střechy (díky které je možné namontovat displej na bok autobusu nad okna). Nový design byl představen také uvnitř a zcela změněna byla vnitřní dispozice. Palivová nádrž byla umístěna na podlaze za podběhem předního kola (podobně jako u první generace).
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2005 – 2017
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 10 400 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 43
            |Míst k stání: 61
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 12 IV",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 105,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 64.0.penezZaMin,
        cena = 110_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 250.hours,
        popis = """
            |Solaris Urbino 12 IV je nízkopodlažní městský autobus ze série Solaris Urbino vyráběný od roku 1999 polskou společností Solaris Bus & Coach v Bolechów-Osiedle u Poznaně. V roce 2014 byla během mezinárodního veletrhu IAA v Hannoveru 24. září představena čtvrtá generace modelu. Polský výrobce představil prototyp Solaris Urbino 12 a Urbino 18 ve verzi s konvenčním pohonem. Bylo oznámeno, že v průběhu let budou představeny další modely v nové verzi. Nové autobusy mají ve srovnání s předchozí generací odolnější, lehčí a rafinovanější design a další funkce. Byla také snížena hladina hluku a vibrace v celém vozidle. V roce 2016 byla představena nová, čtvrtá generace modelu Urbino 12 CNG. Byl založen na řešeních známých z předchozí generace.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2014
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 10 400 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 40
            |Míst k stání: 65
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 12 II LE",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 104,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 79.0.penezZaMin,
        cena = 100_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 196.hours,
        popis = """
            |Solaris Urbino 12 II LE je zvýšená verze nízkopodlažního městského autobusu ze série Solaris Urbino vyráběného od roku 1999 polskou společností Solaris Bus & Coach v Bolechów-Osiedle u Poznaně. Druhá generace Solarisu Urbino 12 byla uvedena na trh na přelomu let 2001/2002 a vyráběla se až do roku 2006. Jednalo se o vylepšenou verzi prvního dílu, ale ve srovnání s předchozí generací nezavedla zásadní technické změny.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2004 – 2006
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 10 400 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 43
            |Míst k stání: 61
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 12 III LE",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 104,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 68.0.penezZaMin,
        cena = 110_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 225.hours,
        popis = """
            |V roce 2005 na mezinárodním veletrhu v Poznani představil Solaris Bus & Coach novou verzi městských autobusů Urbino 12. Třetí generace se oproti svým předchůdcům vyznačovala mnoha změnami. Změnil se vnější design, byly zavedeny ostřejší linie a hrany. Byl modernizován tvar předních a zadních světel, byly představeny nové přední a zadní stěny. Upustilo se i od zaoblené hrany střechy (díky které je možné namontovat displej na bok autobusu nad okna). Nový design byl představen také uvnitř a zcela změněna byla vnitřní dispozice. Palivová nádrž byla umístěna na podlaze za podběhem předního kola (podobně jako u první generace). Solaris Urbino 12 III LE (anglicky low-entry) je zvýšená verze popisovaného autobusu Solaris Urbino 12 III
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2006 – 2017
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 10 400 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 43
            |Míst k stání: 61
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 12 IV LE",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 105,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 64.0.penezZaMin,
        cena = 120_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 250.hours,
        popis = """
            |Solaris Urbino 12 IV je nízkopodlažní městský autobus ze série Solaris Urbino vyráběný od roku 1999 polskou společností Solaris Bus & Coach v Bolechów-Osiedle u Poznaně. V roce 2014 byla během mezinárodního veletrhu IAA v Hannoveru 24. září představena čtvrtá generace modelu. Polský výrobce představil prototyp Solaris Urbino 12 a Urbino 18 ve verzi s konvenčním pohonem. Bylo oznámeno, že v průběhu let budou představeny další modely v nové verzi. Nové autobusy mají ve srovnání s předchozí generací odolnější, lehčí a rafinovanější design a další funkce. Byla také snížena hladina hluku a vibrace v celém vozidle. V roce 2016 byla představena nová, čtvrtá generace modelu Urbino 12 CNG. Byl založen na řešeních známých z předchozí generace. Podtyp Solaris Urbino 12 IV LE je zvýšená verze vzorového autobusu Solaris Urbino 12 IV.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2014
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 10 400 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 40
            |Míst k stání: 65
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 12 IV LE lite",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 105,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 50.0.penezZaMin,
        cena = 190_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 250.hours,
        popis = """
            |Solaris Urbino 12 IV je nízkopodlažní městský autobus ze série Solaris Urbino vyráběný od roku 1999 polskou společností Solaris Bus & Coach v Bolechów-Osiedle u Poznaně. V roce 2014 byla během mezinárodního veletrhu IAA v Hannoveru 24. září představena čtvrtá generace modelu. Polský výrobce představil prototyp Solaris Urbino 12 a Urbino 18 ve verzi s konvenčním pohonem. Bylo oznámeno, že v průběhu let budou představeny další modely v nové verzi. Nové autobusy mají ve srovnání s předchozí generací odolnější, lehčí a rafinovanější design a další funkce. Byla také snížena hladina hluku a vibrace v celém vozidle. V roce 2016 byla představena nová, čtvrtá generace modelu Urbino 12 CNG. Byl založen na řešeních známých z předchozí generace. Podtyp Solaris Urbino 12 IV LE lite je zvýšená verze vzorového autobusu Solaris Urbino 12 IV upravená pro co nejnižší spotřebu paliva.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2018
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 9 000 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 40
            |Míst k stání: 65
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 12 III Ü",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 105,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 68.0.penezZaMin,
        cena = 105_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 250.hours,
        popis = """
            |Dojíždějící model Solaris Urbino 12 Ü (z němčiny Überland - předměstský, regionální) je prostředníkem mezi typickými městskými a meziměstskými autobusy, který debutoval na IAA Nutzfahrzeuge v Hannoveru v září 2012. Na rozdíl od modelů Low Entry je téměř zcela nízkopodlažní. Výroba však skončila pouze u jednoho exempláře, který provozuje PKS Poznań.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2012
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 10 400 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 40
            |Míst k stání: 65
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 12 III CNG",
        trakce = Autobus.Zemeplynovy,
        vyrobce = SOLARIS,
        kapacita = 105,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 66.0.penezZaMin,
        cena = 120_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 250.hours,
        popis = """
            |S uvedením třetí generace autobusů Solaris Urbino 12 v roce 2005 byl představen model Solaris Urbino 12 CNG poháněný stlačeným zemním plynem nebo bioplynem.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2005 – 2016
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 10 400 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 40
            |Míst k stání: 65
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 12 IV CNG",
        trakce = Autobus.Zemeplynovy,
        vyrobce = SOLARIS,
        kapacita = 105,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 61.0.penezZaMin,
        cena = 140_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 250.hours,
        popis = """
            |Solaris Urbino 12 IV CNG je nízkopodlažní městský autobus ze série Solaris Urbino vyráběný od roku 1999 polskou společností Solaris Bus & Coach v Bolechów-Osiedle u Poznaně. V roce 2014 byla během mezinárodního veletrhu IAA v Hannoveru 24. září představena čtvrtá generace modelu. Polský výrobce představil prototyp Solaris Urbino 12 a Urbino 18 ve verzi s konvenčním pohonem. Bylo oznámeno, že v průběhu let budou představeny další modely v nové verzi. Nové autobusy mají ve srovnání s předchozí generací odolnější, lehčí a rafinovanější design a další funkce. Byla také snížena hladina hluku a vibrace v celém vozidle. V roce 2016 byla představena nová, čtvrtá generace modelu Urbino 12 CNG. Byl založen na řešeních známých z předchozí generace.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2016
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 10 400 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 40
            |Míst k stání: 65
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 12 III Hybrid",
        trakce = Autobus.Hybridni,
        vyrobce = SOLARIS,
        kapacita = 105,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 66.0.penezZaMin,
        cena = 120_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 250.hours,
        popis = """
            |Solaris byl prvním výrobcem autobusů na světě, který v roce 2006 představil sériový hybridní autobus. Jednalo se o kloubový hybridní model Urbino 18. V druhé polovině roku 2009 vznikl prototyp 12metrového modelu Solaris Urbino 12 Hybrid na základě třetí generace Urbino 12. Debutoval na veletrhu Busworld v Kortrijku v říjnu 2009. Sériová výroba začala v roce 2010.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2010 – 2016
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 10 400 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 40
            |Míst k stání: 65
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 12 IV hybrid",
        trakce = Autobus.Hybridni,
        vyrobce = SOLARIS,
        kapacita = 105,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 61.0.penezZaMin,
        cena = 140_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 250.hours,
        popis = """
            |Solaris byl prvním výrobcem autobusů na světě, který v roce 2006 představil sériový hybridní autobus. Jednalo se o kloubový hybridní model Urbino 18. V druhé polovině roku 2009 vznikl prototyp 12metrového modelu Solaris Urbino 12 Hybrid na základě třetí generace Urbino 12. Debutoval na veletrhu Busworld v Kortrijku v říjnu 2009. Sériová výroba začala v roce 2010.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2016
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 10 400 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 40
            |Míst k stání: 65
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 12 IV mild hybrid",
        trakce = Autobus.Hybridni,
        vyrobce = SOLARIS,
        kapacita = 105,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 50.0.penezZaMin,
        cena = 220_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 250.hours,
        popis = """
            |V roce 2020 byl do nabídky výrobce uveden nový mild hybridní model Urbino 12, který je vývojem technologie rekuperace energie z brzdění. Je vybaveno elektrickou pohonnou jednotkou s výkonem nižším než v případě hybridního modelu Urbino 12 a systémem akumulace energie s menší kapacitou - umožňujícím ukládat energii z brzdění již od rychlosti cca 60 km/h. Díky této technologii je autobus levnější na pořízení než hybridní model Urbino 12, ale emise výfukových plynů jsou nižší ve srovnání s modelem s tradičním pohonem pouze na spalování a nižší než požadovaná emisní norma Euro 6.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2020
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 10 400 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 40
            |Míst k stání: 65
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 12 IV hydrogen",
        trakce = Autobus.Vodikovy,
        vyrobce = SOLARIS,
        kapacita = 105,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 50.0.penezZaMin,
        cena = 410_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 250.hours,
        popis = """
            |Solaris Urbino 12 hydrogen je nízkopodlažní městský autobus z rodiny Solaris Urbino, vyráběný polskou společností Solaris Bus & Coach, poháněný elektřinou generovanou vodíkovým palivovým článkem. Byl představen v roce 2019 jako vývoj modelů Urbino 12 a jeho elektrické verze Urbino 12 electric.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2019
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 10 400 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 40
            |Míst k stání: 65
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 15 I",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 135,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 89.0.penezZaMin,
        cena = 100_000.penez,
        delka = 14.59F.metru,
        sirka = 2.550.metru,
        vydrz = 169.hours,
        popis = """
            |Solaris Urbino 15 I je nízkopodlažní městský autobus z rodiny Solaris Urbino vyráběný v letech 1999 až 2002 polskou společností Solaris Bus & Coach v Bolechów-Osiedle u Poznaně. V roce 1999 byl představen v Lodži nový typ autobusů Solaris Urbino. Jednalo se o Solaris Urbino 15 I.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 1999 – 2002
            |Délka: 14 590 mm
            |Šířka: 2 550 mm
            |Hmotnost: 12 700 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 43
            |Míst k stání: 92
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 15 II",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 135,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 79.0.penezZaMin,
        cena = 110_000.penez,
        delka = 14.59F.metru,
        sirka = 2.550.metru,
        vydrz = 196.hours,
        popis = """
            |Solaris Urbino 15 II je nízkopodlažní městský autobus z rodiny Solaris Urbino vyráběný v letech 2002 až 2005 polskou společností Solaris Bus & Coach v Bolechów-Osiedle u Poznaně. V roce 2002 byla představena nová, druhá generace autobusů Solaris Urbino 15. Jednalo se o přechodný model mezi vozidly první a třetí generace. Konstrukce vozidla byla mírně změněna, byly zavedeny drobné změny v uspořádání interiéru a technická vylepšení.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2002 – 2005
            |Délka: 14 590 mm
            |Šířka: 2 550 mm
            |Hmotnost: 12 700 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 43
            |Míst k stání: 92
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 15 III",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 135,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 68.0.penezZaMin,
        cena = 120_000.penez,
        delka = 14.59F.metru,
        sirka = 2.550.metru,
        vydrz = 225.hours,
        popis = """
            |Solaris Urbino 15 III je nízkopodlažní městský autobus z rodiny Solaris Urbino vyráběný v letech 2005 až 2018 polskou společností Solaris Bus & Coach v Bolechów-Osiedle u Poznaně. V roce 2005 byla zahájena výroba 3. generace Urbino 15 představením nové verze autobusů Solaris Urbino. Zavedené změny byly mnohem větší než v případě předchozí generace. Změnil se design přední a zadní stěny, dostaly dynamičtější tvar. V roce 2014 Solaris představil čtvrtou generaci rodiny Urbino, ale nebyla představena žádná nová verze modelu Urbino 15. Souběžně s novou generací se vyráběly autobusy třetí generace. Začátkem roku 2018 bylo oznámeno, že od letošního roku bude ukončena výroba předchozí generace a model Urbino 15 se již nebude vyrábět z důvodu klesajícího zájmu zákazníků.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2005 – 2018
            |Délka: 14 590 mm
            |Šířka: 2 550 mm
            |Hmotnost: 12 700 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 43
            |Míst k stání: 92
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 15 III LE",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 105,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 68.0.penezZaMin,
        cena = 135_000.penez,
        delka = 14.59F.metru,
        sirka = 2.550.metru,
        vydrz = 225.hours,
        popis = """
            |Solaris Urbino 15 III LE byl poprvé veřejnosti předtaven na veletrhu Autotec v brně. Solaris Urbino 15 LE konstrukčně vychází z modelu Solari Urbino 15.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2008 – 2018
            |Délka: 14 590 mm
            |Šířka: 2 550 mm
            |Hmotnost: 12 700 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 65
            |Míst k stání: 40
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 15 III CNG",
        trakce = Autobus.Zemeplynovy,
        vyrobce = SOLARIS,
        kapacita = 105,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 67.0.penezZaMin,
        cena = 140_000.penez,
        delka = 14.59F.metru,
        sirka = 2.550.metru,
        vydrz = 225.hours,
        popis = """
            |Solaris Urbino 15 III CNG je nízkopodlažní městský autobus z rodiny Solaris Urbino vyráběný v letech 2008 až 2018 polskou společností Solaris Bus & Coach v Bolechów-Osiedle u Poznaně.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2008 – 2018
            |Délka: 14 590 mm
            |Šířka: 2 550 mm
            |Hmotnost: 12 700 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 65
            |Míst k stání: 40
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 15 III LE CNG",
        trakce = Autobus.Zemeplynovy,
        vyrobce = SOLARIS,
        kapacita = 105,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 67.0.penezZaMin,
        cena = 155_000.penez,
        delka = 14.59F.metru,
        sirka = 2.550.metru,
        vydrz = 225.hours,
        popis = """
            |Solaris Urbino 15 III LE CNG je nízkopodlažní městský autobus z rodiny Solaris Urbino vyráběný v letech 2005 až 2018 polskou společností Solaris Bus & Coach v Bolechów-Osiedle u Poznaně. Tento autobus byl vyvinut primárně pro potřeby skandinávského trhu.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2008 – 2018
            |Délka: 14 590 mm
            |Šířka: 2 550 mm
            |Hmotnost: 12 700 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 65
            |Míst k stání: 40
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 18 I",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 138,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 89.0.penezZaMin,
        cena = 120_000.penez,
        delka = 18F.metru,
        sirka = 2.550.metru,
        vydrz = 169.hours,
        popis = """
            |První generace Solaris Urbino 18 I se vyráběla od druhé poloviny roku 1999. Tento model doplnil nabídku Solaris o kloubový 18metrový autobus. První sériové vozidlo bylo dodáno do PKA Gdynia v roce 1999
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 1999 – 2002
            |Délka: 18 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 15 000 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 43
            |Míst k stání: 92
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 18 II",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 138,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 79.0.penezZaMin,
        cena = 135_000.penez,
        delka = 18F.metru,
        sirka = 2.550.metru,
        vydrz = 196.hours,
        popis = """
            |Od roku 2002 se vyráběla nová, druhá generace modelu Urbino 18. Tato generace však byla pouze přechodným řešením mezi první a plánovanou třetí generací. Bylo provedeno několik technických vylepšení a mírně se změnil design autobusů.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2002 – 2005
            |Délka: 18 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 15 000 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 43
            |Míst k stání: 92
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 18 III",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 138,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 68.0.penezZaMin,
        cena = 150_000.penez,
        delka = 18F.metru,
        sirka = 2.550.metru,
        vydrz = 234.hours,
        popis = """
            |Solaris Urbino 18 III je nízkopodlažní kloubový autobus z rodiny Solaris Urbino určený pro veřejnou dopravu, vyráběný polskou společností Solaris Bus & Coach S.A. v Bolechów-Osiedle u Poznaně. Jedná se o druhý (po Urbino 12) nejprodávanější model značky Solaris. V roce 2005 Solaris vyrobil a ukázal první kopie nové, třetí generace modelu Solaris Urbino 18. V tomto případě byl zaveden větší počet změn, stylistických i strukturálních, ve srovnání s předchozími pohledy.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2005 – 2018
            |Délka: 18 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 15 000 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 44
            |Míst k stání: 94
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 18 IV",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 138,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 61.0.penezZaMin,
        cena = 165_000.penez,
        delka = 18F.metru,
        clanky = listOf(
            10.5.metru,
            7.5.metru,
        ),
        sirka = 2.550.metru,
        vydrz = 255.hours,
        popis = """
            |Solaris Urbino 18 IV je nízkopodlažní kloubový autobus z rodiny Solaris Urbino určený pro veřejnou dopravu, vyráběný polskou společností Solaris Bus & Coach S.A. v Bolechów-Osiedle u Poznaně. Jedná se o druhý (po Urbino 12) nejprodávanější model značky Solaris. Premiéra čtvrté generace Urbina 18 se konala 24. září 2014 během veletrhu IAA Nutzfahrzeuge v Hannoveru, zatímco polská premiéra se konala v listopadu během Transexpo v Kielcích. Nové autobusy se vyznačují odolnějším, lehčím a rafinovanějším designem, a to jak technicky, tak stylisticky. Hladina hluku a intenzita vibrací ve vozidle byly sníženy.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2014
            |Délka: 18 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 15 000 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 44
            |Míst k stání: 94
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 18,75",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 142,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 70.0.penezZaMin,
        cena = 160_000.penez,
        delka = 18.75F.metru,
        sirka = 2.550.metru,
        vydrz = 234.hours,
        popis = """
            |Solaris Urbino 18,75 je prodloužená verze osvědčeného autobusu Solaris Urbino 18 III.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2012 – 2013
            |Délka: 18 750 mm
            |Šířka: 2 550 mm
            |Hmotnost: 15 000 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 48
            |Míst k stání: 94
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 18 II CNG",
        trakce = Autobus.Zemeplynovy,
        vyrobce = SOLARIS,
        kapacita = 138,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 77.0.penezZaMin,
        cena = 160_000.penez,
        delka = 18F.metru,
        sirka = 2.550.metru,
        vydrz = 196.hours,
        popis = """
            |Od roku 2004 se vyráběla nová, druhá generace modelu Urbino 18 CNG. Tato generace však byla pouze přechodným řešením mezi první a plánovanou třetí generací. Bylo provedeno několik technických vylepšení a mírně se změnil design autobusů.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2004 – 2006
            |Délka: 18 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 15 000 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 43
            |Míst k stání: 92
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 18 III CNG",
        trakce = Autobus.Zemeplynovy,
        vyrobce = SOLARIS,
        kapacita = 138,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 66.0.penezZaMin,
        cena = 175_000.penez,
        delka = 18F.metru,
        sirka = 2.550.metru,
        vydrz = 225.hours,
        popis = """
            |Solaris Urbino 18 III CNG je nízkopodlažní kloubový autobus z rodiny Solaris Urbino určený pro veřejnou dopravu, vyráběný polskou společností Solaris Bus & Coach S.A. v Bolechów-Osiedle u Poznaně. Jedná se o druhý (po Urbino 12) nejprodávanější model značky Solaris. V roce 2005 Solaris vyrobil a ukázal první kopie nové, třetí generace modelu Solaris Urbino 18. V tomto případě byl zaveden větší počet změn, stylistických i strukturálních, ve srovnání s předchozími pohledy.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2006 – 2016
            |Délka: 18 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 15 000 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 44
            |Míst k stání: 94
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 18 IV CNG",
        trakce = Autobus.Zemeplynovy,
        vyrobce = SOLARIS,
        kapacita = 138,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 60.0.penezZaMin,
        cena = 190_000.penez,
        delka = 18F.metru,
        sirka = 2.550.metru,
        vydrz = 225.hours,
        popis = """
            |Solaris Urbino 18 IV CNG je nízkopodlažní kloubový autobus z rodiny Solaris Urbino určený pro veřejnou dopravu, vyráběný polskou společností Solaris Bus & Coach S.A. v Bolechów-Osiedle u Poznaně. Jedná se o druhý (po Urbino 12) nejprodávanější model značky Solaris. Premiéra čtvrté generace Urbina 18 se konala 24. září 2014 během veletrhu IAA Nutzfahrzeuge v Hannoveru, zatímco polská premiéra se konala v listopadu během Transexpo v Kielcích. Nové autobusy se vyznačují odolnějším, lehčím a rafinovanějším designem, a to jak technicky, tak stylisticky. Hladina hluku a intenzita vibrací ve vozidle byly sníženy. V roce 2016 byla představena nová, čtvrtá generace modelu Urbino 18 CNG. Byl založen na řešeních známých z předchozí generace.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2016
            |Délka: 18 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 15 000 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 44
            |Míst k stání: 94
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 18 III Hybrid",
        trakce = Autobus.Hybridni,
        vyrobce = SOLARIS,
        kapacita = 138,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 66.0.penezZaMin,
        cena = 175_000.penez,
        delka = 18F.metru,
        sirka = 2.550.metru,
        vydrz = 234.hours,
        popis = """
            |Solaris Urbino 18 III je nízkopodlažní kloubový autobus z rodiny Solaris Urbino určený pro veřejnou dopravu, vyráběný polskou společností Solaris Bus & Coach S.A. v Bolechów-Osiedle u Poznaně. Jedná se o druhý (po Urbino 12) nejprodávanější model značky Solaris. V roce 2005 Solaris vyrobil a ukázal první kopie nové, třetí generace modelu Solaris Urbino 18. V tomto případě byl zaveden větší počet změn, stylistických i strukturálních, ve srovnání s předchozími pohledy.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2006 – 2018
            |Délka: 18 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 15 000 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 44
            |Míst k stání: 94
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 18 IV hybrid",
        trakce = Autobus.Hybridni,
        vyrobce = SOLARIS,
        kapacita = 138,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 60.0.penezZaMin,
        cena = 190_000.penez,
        delka = 18F.metru,
        sirka = 2.550.metru,
        vydrz = 255.hours,
        popis = """
            |Solaris Urbino 18 IV Hybrid je nízkopodlažní kloubový autobus z rodiny Solaris Urbino určený pro veřejnou dopravu, vyráběný polskou společností Solaris Bus & Coach S.A. v Bolechów-Osiedle u Poznaně. Jedná se o druhý (po Urbino 12) nejprodávanější model značky Solaris. Premiéra čtvrté generace Urbina 18 se konala 24. září 2014 během veletrhu IAA Nutzfahrzeuge v Hannoveru, zatímco polská premiéra se konala v listopadu během Transexpo v Kielcích. Nové autobusy se vyznačují odolnějším, lehčím a rafinovanějším designem, a to jak technicky, tak stylisticky. Hladina hluku a intenzita vibrací ve vozidle byly sníženy.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2018
            |Délka: 18 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 15 000 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 44
            |Míst k stání: 94
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 18 IV plug-in hybrid",
        trakce = Autobus.Hybridni,
        vyrobce = SOLARIS,
        kapacita = 138,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 60.0.penezZaMin,
        cena = 240_000.penez,
        delka = 18F.metru,
        sirka = 2.550.metru,
        vydrz = 255.hours,
        popis = """
            |Solaris Urbino 18 IV plug-in hybrid je nízkopodlažní kloubový autobus z rodiny Solaris Urbino určený pro veřejnou dopravu, vyráběný polskou společností Solaris Bus & Coach S.A. v Bolechów-Osiedle u Poznaně. Jedná se o druhý (po Urbino 12) nejprodávanější model značky Solaris. Premiéra čtvrté generace Urbina 18 se konala 24. září 2014 během veletrhu IAA Nutzfahrzeuge v Hannoveru, zatímco polská premiéra se konala v listopadu během Transexpo v Kielcích. Nové autobusy se vyznačují odolnějším, lehčím a rafinovanějším designem, a to jak technicky, tak stylisticky. Hladina hluku a intenzita vibrací ve vozidle byly sníženy.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2018
            |Délka: 18 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 15 000 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 44
            |Míst k stání: 94
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 18 MetroStyle",
        trakce = Autobus.Dieslovy,
        vyrobce = SOLARIS,
        kapacita = 138,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 67.0.penezZaMin,
        cena = 180_000.penez,
        delka = 18F.metru,
        sirka = 2.550.metru,
        vydrz = 255.hours,
        popis = """
            |V roce 2010 Solaris získal zakázku na dodávku hybridních kloubových autobusů pro francouzského dopravce Transdev pro systém rychlé dopravy v Paříži. Autobusy byly představeny v roce 2011. Získaly zcela nový design odkazující na rodinu tramvají Solaris TraminoTo je důvod, proč byl nový Urbino 18 Hybrid MetroStyle pojmenován. Nechybí čelní sklo s jednořádkovou spodní hranou a zcela nové světlomety. Šikmá přední stěna a kryty zakrývající kola autobusu dodávají dynamický vzhled. Změn doznala i zadní stěna - světla jsou umístěna svisle, zadní okno má tvar V. Autobusy Solaris Urbino 18 MetroStyle byly poprvé dodány do Krakova v roce 2014
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2014
            |Délka: 18 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 15 000 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 44
            |Míst k stání: 94
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 18 Hybrid MetroStyle",
        trakce = Autobus.Hybridni,
        vyrobce = SOLARIS,
        kapacita = 138,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 65.0.penezZaMin,
        cena = 205_000.penez,
        delka = 18F.metru,
        sirka = 2.550.metru,
        vydrz = 255.hours,
        popis = """
            |V roce 2010 Solaris získal zakázku na dodávku hybridních kloubových autobusů pro francouzského dopravce Transdev pro systém rychlé dopravy v Paříži. Autobusy byly představeny v roce 2011. Získaly zcela nový design odkazující na rodinu tramvají Solaris TraminoTo je důvod, proč byl nový Urbino 18 Hybrid MetroStyle pojmenován. Nechybí čelní sklo s jednořádkovou spodní hranou a zcela nové světlomety. Šikmá přední stěna a kryty zakrývající kola autobusu dodávají dynamický vzhled. Změn doznala i zadní stěna - světla jsou umístěna svisle, zadní okno má tvar V.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2010
            |Délka: 18 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 15 000 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 44
            |Míst k stání: 94
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 18 IV hydrogen",
        trakce = Autobus.Vodikovy,
        vyrobce = SOLARIS,
        kapacita = 136,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 52.0.penezZaMin,
        cena = 550_000.penez,
        delka = 18F.metru,
        sirka = 2.550.metru,
        vydrz = 270.hours,
        popis = """
            |On 14 September 2022 Solaris unveiled its Urbino 18 hydrogen bus. The heart of the Urbino 18 hydrogen bus is a cutting-edge fuel cell that acts as a miniature hydrogen power plant. Hydrogen is transformed by the fuel cell into electricity which is then transferred to the driveline. Solaris’s new vehicle doesn’t feature a conventional engine compartment as it has been equipped with a modular drive system. The space thus saved allowed the bus maker to increase the vehicle’s passenger capacity. Depending on the configuration, the bus will be able to carry up to 138 passengers.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2022
            |Délka: 18 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 17 400 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 52
            |Míst k stání: 84
        """.trimMargin()
    ),
    TypBusu(
        model = "Iveco Crossway 13M",
        trakce = Autobus.Dieslovy,
        vyrobce = IVECO,
        kapacita = 59,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 77.0.penezZaMin,
        cena = 130_000.penez,
        delka = 12.962F.metru,
        sirka = 2.550.metru,
        vydrz = 188.hours,
        popis = """
            |Iveco Crossway (dříve Irisbus Crossway) je model meziměstského linkového autobusu, který vyrábí společnost Iveco Czech Republic ve Vysokém Mýtě. Produkce Crosswayů ve třech délkových verzích o délkách 10,6 m, 12 m a 12,8 m probíhala pod původním označením Irisbus Crossway od roku 2006. V roce 2014 Iveco Bus představilo novou generaci toho autobusu pod označením Iveco Crossway.
            |
            |Výrobce: Iveco Bus
            |Rok výroby: od roku 2014
            |Délka: 12 962 mm
            |Šířka: 2 550 mm
            |Hmotnost: 12 000 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 23
            |Míst k stání: 36
        """.trimMargin()
    ),
    TypBusu(
        model = "Renault Citybus 12M",
        trakce = Autobus.Dieslovy,
        vyrobce = RENAULT,
        kapacita = 99,
        maxRychlost = 70.kilometruZaHodinu,
        maxNaklady = 88.0.penezZaMin,
        cena = 90_000.penez,
        delka = 11.99F.metru,
        sirka = 2.500.metru,
        vydrz = 202.hours,
        popis = """
            |Renault Citybus 12M je model nízkopodlažního autobusu, který vyráběla společnost Karosa Vysoké Mýto ve spolupráci s firmou Renault v rámci koncernu Irisbus. Do Česka byl vždy dovezen skelet autobusu s motorem vyrobeným ve Francii, který zde byl dokončen. Důvodem bylo rozdílné clo a tedy levnější výroba a dovoz autobusu tímto způsobem. Model byl vyráběn mezi lety 1995 a 2005.
            |
            |Výrobce: Karosa Vysoké Mýto
            |Rok výroby: 1995 – 2005
            |Délka: 11 990 mm
            |Šířka: 2 500 mm
            |Hmotnost: 11 380 kg
            |Maximální rychlost: 70 km/h
            |Míst k sezení: 30
            |Míst k stání: 69
        """.trimMargin()
    ),
    TypBusu(
        model = "Irisbus Citelis 12M",
        trakce = Autobus.Dieslovy,
        vyrobce = IRISBUS,
        kapacita = 105,
        maxRychlost = 70.kilometruZaHodinu,
        maxNaklady = 81.0.penezZaMin,
        cena = 110_000.penez,
        delka = 11.99F.metru,
        sirka = 2.500.metru,
        vydrz = 232.hours,
        popis = """
            |Irisbus Citelis 12M je městský nízkopodlažní autobus vyráběný v letech 2005–2013 společností Irisbus. Citelis 12M, představený 31. března 2005, je nástupcem modelu Citybus 12M (Renault Agora). Stejně jako jeho předchůdce, i Citelis 12M byl vyráběn buď ve francouzském městě Annonay nebo v italském Valle Ufita, přičemž speciální požadavky českých a slovenských zákazníků byly řešeny ve vysokomýtském závodě Iveco Czech Republic (dříve Karosa). Od poloviny roku 2010 se výroba autobusů pro střední Evropu přesunula do Vysokého Mýta. Nahrazen byl modelem Iveco Urbanway 12M.
            |
            |Výrobce: Irisbus
            |Rok výroby: 2005 – 2013
            |Délka: 11 990 mm
            |Šířka: 2 500 mm
            |Hmotnost: 11 200 kg
            |Maximální rychlost: 70 km/h
            |Míst k sezení: 40
            |Míst k stání: 55
        """.trimMargin()
    ),
    TypBusu(
        model = "Irisbus Crossway LE 12.8M",
        trakce = Autobus.Dieslovy,
        vyrobce = IRISBUS,
        kapacita = 69,
        maxRychlost = 85.kilometruZaHodinu,
        maxNaklady = 77.0.penezZaMin,
        cena = 120_000.penez,
        delka = 12.962F.metru,
        sirka = 2.550.metru,
        vydrz = 189.hours,
        popis = """
            |Iveco Crossway (dříve Irisbus Crossway) je model meziměstského linkového autobusu, který vyrábí společnost Iveco Czech Republic ve Vysokém Mýtě. Produkce Crosswayů ve třech délkových verzích o délkách 10,6 m, 12 m a 12,8 m probíhala pod původním označením Irisbus Crossway od roku 2006. V roce 2014 Iveco Bus představilo novou generaci toho autobusu pod označením Iveco Crossway.
            |
            |Výrobce: Irisbus
            |Rok výroby: 2006 – 2014
            |Délka: 12 962 mm
            |Šířka: 2 550 mm
            |Hmotnost: 12 000 kg
            |Maximální rychlost: 85 km/h
            |Míst k sezení: 23
            |Míst k stání: 36
        """.trimMargin()
    ),
    TypBusu(
        model = "Irisbus Citelis 18M",
        trakce = Autobus.Dieslovy,
        vyrobce = IRISBUS,
        kapacita = 155,
        maxRychlost = 70.kilometruZaHodinu,
        maxNaklady = 81.0.penezZaMin,
        cena = 155_000.penez,
        delka = 17.8F.metru,
        sirka = 2.500.metru,
        vydrz = 200.hours,
        popis = """
            |Irisbus Citelis 18M je městský kloubový nízkopodlažní autobus, který byl vyráběn ve francouzském městě Annonay a později v italském Valle Ufita, přičemž speciální požadavky českých a slovenských zákazníků byly řešeny ve vysokomýtském závodě Iveco Czech Republic (dříve Karosa). Jeho nástupcem je model Iveco Urbanway 18M.
            |
            |Výrobce: Irisbus
            |Rok výroby: 2005 – 2014
            |Délka: 17 800 mm
            |Šířka: 2 500 mm
            |Hmotnost: 17 300 kg
            |Maximální rychlost: 70 km/h
            |Míst k sezení: 40
            |Míst k stání: 115
        """.trimMargin()
    ),
    TypBusu(
        model = "Heuliez GX 137",
        trakce = Autobus.Dieslovy,
        vyrobce = HEULIEZ,
        kapacita = 70,
        maxRychlost = 85.kilometruZaHodinu,
        maxNaklady = 99.0.penezZaMin,
        cena = 199_000.penez,
        delka = 9.52F.metru,
        sirka = 2.330.metru,
        vydrz = 169.hours,
        popis = """
            |Heuliez Bus GX 137 a GX 137 L jsou nízkopodlažní malorozchodné autobusy (minibus)vyráběné a uváděné na trh francouzským výrobcem Heuliez Bus, dceřinou společností skupiny Iveco Bus,od roku 2014. K dispozici byly také standardní a kloubové verze s označením GX 337 a GX 437. Jsou součástí řady Access Bus.
            |
            |Výrobce: Heuliez Bus
            |Rok výroby: 2014 - 2019
            |Délka: 9 520 mm
            |Šířka: 2 330 mm
            |Hmotnost: 10 572 kg
            |Maximální rychlost: 85 km/h
            |Míst k sezení: 20
            |Míst k stání: 50
        """.trimMargin()
    ),
    TypBusu(
        model = "Iveco Stratos LF 38",
        trakce = Autobus.Dieslovy,
        vyrobce = IVECO,
        kapacita = 38,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 95.0.penezZaMin,
        cena = 70_000.penez,
        delka = 7.765F.metru,
        sirka = 2.300.metru,
        vydrz = 144.hours,
        popis = """
            |SKD Stratos (dříve Iveco Stratos) je minibus, který byl vyráběn firmou SKD Trade v Dolních Bučicích na podvozku nákladního automobilu Iveco Daily. Do roku 2011 jej vyráběla v Čáslavi královéhradecká firma Stratos Auto (odtud název vozidla), jejíž autobusovou divizi zakoupila právě společnost SKD Trade. Minibus Stratos byl v roce 2017 nahrazen typem Dekstra.
            |
            |Výrobce: SKD
            |Rok výroby: 2012 - 2017
            |Délka: 7 765 mm
            |Šířka: 2 300 mm
            |Hmotnost: 7 000 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 14
            |Míst k stání: 24
        """.trimMargin()
    )
)

private val trolejbusy = listOf(
    TypBusu(
        model = "Škoda 1Tr",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 73,
        maxRychlost = 46.kilometruZaHodinu,
        maxNaklady = 200.0.penezZaMin,
        cena = 80_000.penez,
        delka = 10.330F.metru,
        sirka = 2.350.metru,
        vydrz = 24.hours,
        popis = """
            |Škoda 1Tr byl první trolejbus vyrobený ve Škodových závodech. Zároveň se také jednalo o jeden ze tří trolejbusů, které byly určeny pro zahájení trolejbusové dopravy v Praze.
            |
            |Výrobce: Škoda Mladá Boleslav
            |Rok výroby: 1936
            |Délka: 10 330 mm
            |Šířka: 2 350 mm
            |Hmotnost: 10 370 kg
            |Maximální rychlost: 46 km/h
            |Míst k sezení: 29
            |Míst k stání: 44
        """.trimMargin()
    ),
    TypBusu(
        model = "Praga TOT",
        trakce = Trolejbus.Obycejny,
        vyrobce = PRAGA,
        kapacita = 80,
        maxRychlost = 50.kilometruZaHodinu,
        maxNaklady = 200.0.penezZaMin,
        cena = 80_000.penez,
        delka = 10.200F.metru,
        sirka = 2.450.metru,
        vydrz = 24.hours,
        popis = """
            |Praga TOT, také označovaný jako Praga TR 4500, je typ československého trolejbusu, který společně s vozy Škoda 1Tr a Tatra 86 zahajoval trolejbusovou dopravu v Praze v roce 1936.
            |
            |Výrobce: Praga
            |Rok výroby: 1936 – 1939
            |Délka: 10 200 mm
            |Šířka: 2 450 mm
            |Hmotnost: 10 700 kg
            |Maximální rychlost: 50 km/h
            |Míst k sezení: 33
            |Míst k stání: 47
        """.trimMargin()
    ),
    TypBusu(
        model = "Tatra T 86",
        trakce = Trolejbus.Obycejny,
        vyrobce = TATRA,
        kapacita = 80,
        maxRychlost = 45.kilometruZaHodinu,
        maxNaklady = 200.0.penezZaMin,
        cena = 80_000.penez,
        delka = 10.700F.metru,
        sirka = 2.420.metru,
        vydrz = 24.hours,
        popis = """
            |Tatra 86 je typ československého trolejbusu z druhé poloviny 30. let 20. století.
            |
            |Výrobce: Tatra
            |Rok výroby: 1936 – 1939
            |Délka: 10 700 mm
            |Šířka: 2 420 mm
            |Hmotnost: 10 700 kg
            |Maximální rychlost: 45 km/h
            |Míst k sezení: 23
            |Míst k stání: 57
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 2Tr",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 74,
        maxRychlost = 50.kilometruZaHodinu,
        maxNaklady = 180.0.penezZaMin,
        cena = 85_000.penez,
        delka = 10.500F.metru,
        sirka = 2.500.metru,
        vydrz = 36.hours,
        popis = """
            |Škoda 2Tr je typ československého trolejbusu z konce 30. let 20. století, který konstrukčně vycházel z předchozího modelu Škoda 1Tr. Mechanickou část vyrobily Škodovy závody v Mladé Boleslavi, elektrickou výzbroj dodaly tytéž závody z Plzně.
            |
            |Výrobce: Škoda Mladá Boleslav
            |Rok výroby: 1938
            |Délka: 10 500 mm
            |Šířka: 2 500 mm
            |Hmotnost: 10 900 kg
            |Maximální rychlost: 50 km/h
            |Míst k sezení: 30
            |Míst k stání: 44
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 3Tr",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 80,
        maxRychlost = 45.kilometruZaHodinu,
        maxNaklady = 150.0.penezZaMin,
        cena = 90_000.penez,
        delka = 10.550F.metru,
        sirka = 2.500.metru,
        vydrz = 48.hours,
        popis = """
            |Škoda 3Tr je model československého trolejbusu, který byl vyráběn ve 40. letech 20. století firmou Škoda (během války se podnik nazýval Reichswerke Hermann Göring A.G.) v Plzni. Technicky vychází z předchozího typu Škoda 2Tr.
            |
            |Výrobce: ŠkodaPlzeň
            |Rok výroby: 1941–1948
            |Délka: 10 550 mm
            |Šířka: 2 500 mm
            |Hmotnost: 10 500 kg
            |Maximální rychlost: 45 km/h
            |Míst k sezení: 28
            |Míst k stání: 52
        """.trimMargin()
    ),
    TypBusu(
        model = "Tatra T 400",
        trakce = Trolejbus.Obycejny,
        vyrobce = TATRA,
        kapacita = 80,
        maxRychlost = 60.kilometruZaHodinu,
        maxNaklady = 150.0.penezZaMin,
        cena = 90_000.penez,
        delka = 11.380F.metru,
        sirka = 2.500.metru,
        vydrz = 48.hours,
        popis = """
            |Tatra 400 je typ československého trolejbusu, který byl vyráběn na přelomu 40. a 50. let 20. století.
            |
            |Výrobce: Tatra
            |Rok výroby: 1948–1955
            |Délka: 11 380 mm
            |Šířka: 2 500 mm
            |Hmotnost: 11 700 kg
            |Maximální rychlost: 60 km/h
            |Míst k sezení: 26
            |Míst k stání: 54
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 6Tr",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 79,
        maxRychlost = 46.kilometruZaHodinu,
        maxNaklady = 130.0.penezZaMin,
        cena = 95_000.penez,
        delka = 10.550F.metru,
        sirka = 2.500.metru,
        vydrz = 60.hours,
        popis = """
            |Škoda 6Tr je typ československého trolejbusu vyráběného na konci 40. let 20. století firmou Škoda v Plzni.
            |
            |Výrobce: Škoda Plzeň
            |Rok výroby: 1949
            |Délka: 10 550 mm
            |Šířka: 2 500 mm
            |Hmotnost: 9 600 kg
            |Maximální rychlost: 46 km/h
            |Míst k sezení: 21
            |Míst k stání: 58
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 7Tr",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 82,
        maxRychlost = 56.kilometruZaHodinu,
        maxNaklady = 100.0.penezZaMin,
        cena = 100_000.penez,
        delka = 10.700F.metru,
        sirka = 2.500.metru,
        vydrz = 120.hours,
        popis = """
            |Škoda 7Tr je model československého trolejbusu vyráběného v první polovině 50. let 20. století v plzeňském podniku Škoda (v letech 1952 až 1959 Závody Vladimíra Iljiče Lenina).
            |
            |Výrobce: Škoda Plzeň
            |Rok výroby: 1950–1955
            |Délka: 10 700 mm
            |Šířka: 2 500 mm
            |Hmotnost: 9 900 kg
            |Maximální rychlost: 56 km/h
            |Míst k sezení: 24
            |Míst k stání: 58
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 8Tr",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 78,
        maxRychlost = 60.kilometruZaHodinu,
        maxNaklady = 90.0.penezZaMin,
        cena = 105_000.penez,
        delka = 10.700F.metru,
        sirka = 2.500.metru,
        vydrz = 120.hours,
        popis = """
            |Škoda 8Tr je typ československého trolejbusu. Byl vyráběn od poloviny 50. do počátku 60. let 20. století podnikem ZVIL v Plzni a později od roku 1960 v Ostrově nad Ohří.
            |
            |Výrobce: Závody Vladimíra Iljiče Lenina Plzeň
            |Rok výroby: 1956 – 1961
            |Délka: 10 700 mm
            |Šířka: 2 500 mm
            |Hmotnost: 10 000 kg
            |Maximální rychlost: 50 km/h // ta rychlost je schválně napsaná jinak
            |Míst k sezení: 19
            |Míst k stání: 59
        """.trimMargin()
    ),
    TypBusu(
        model = "Tatra T 401",
        trakce = Trolejbus.Obycejny,
        vyrobce = TATRA,
        kapacita = 79,
        maxRychlost = 70.kilometruZaHodinu,
        maxNaklady = 90.0.penezZaMin,
        cena = 100_000.penez,
        delka = 11.950F.metru,
        sirka = 2.495.metru,
        vydrz = 96.hours,
        popis = """
            |Tatra 401 je model československého trolejbusu, který byl vyroben v jednom kuse ve druhé polovině 50. let 20. století.
            |
            |Výrobce: Tatra
            |Rok výroby: 1958
            |Délka: 11 950 mm
            |Šířka: 2 495 mm
            |Hmotnost: 11 350 kg
            |Maximální rychlost: 70 km/h
            |Míst k sezení: 26
            |Míst k stání: 53
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 9Tr",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 69,
        maxRychlost = 60.kilometruZaHodinu,
        maxNaklady = 70.0.penezZaMin,
        cena = 110_000.penez,
        delka = 11.000F.metru,
        sirka = 2.500.metru,
        vydrz = 240.hours,
        popis = """
            |Škoda 9Tr je model československého trolejbusu, který byl vyráběn od počátku 60. let 20. století více než 20 let. Výroba probíhala v závodě Škoda v Ostrově nad Ohří. Vůz 9Tr se ve své době stal převratným typem, rozšířil se do mnoha zemí světa, často bývá přirovnáván k tramvaji Tatra T3. Po sovětských ZiU-9 a ZiU-5 je třetím nejvyráběnějším trolejbusem na světě.
            |
            |Výrobce: Závody Vladimíra Iljiče Lenina Plzeň
            |Rok výroby: 1958 – 1981
            |Délka: 11 000 mm
            |Šířka: 2 500 mm
            |Hmotnost: 9 600 kg
            |Maximální rychlost: 60 km/h
            |Míst k sezení: 23
            |Míst k stání: 46
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda T 11",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 94,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 60.0.penezZaMin,
        cena = 110_000.penez,
        delka = 10.985F.metru,
        sirka = 2.500.metru,
        vydrz = 108.hours,
        popis = """
            |Škoda T 11 je model československého trolejbusu, který byl vyvinut v 60. letech 20. století v rámci unifikace vozidel MHD. Karosérie pro tyto vozy vyrobil národní podnik Karosa, elektrickou výzbroj dodala firma Škoda.
            |
            |Výrobce: Škoda Ostrov, Karosa Vysoké Mýto
            |Rok výroby: 1967
            |Délka: 10 985 mm
            |Šířka: 2 500 mm
            |Hmotnost: 8 350 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 23
            |Míst k stání: 71
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda Sanos S 115",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 115,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 60.0.penezZaMin,
        cena = 115_000.penez,
        delka = 12F.metru,
        sirka = 2.500.metru,
        vydrz = 106.hours,
        popis = """
            |Škoda-Sanos S 115 (někdy uváděn jako Škoda-Sanos 115Tr) je trolejbus, který jako prototyp vyrobil v roce 1987 podnik Škoda Ostrov v kooperaci s jugoslávskou (respektive makedonskou) společností FAS 11. Oktomvri Skopje.
            |
            |Výrobce: Škoda Ostrov, Karosa Vysoké Mýto
            |Rok výroby: 1987
            |Délka: 12 000 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 29
            |Míst k stání: 86
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda Sanos S 200",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 143,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 60.0.penezZaMin,
        cena = 115_000.penez,
        delka = 18F.metru,
        sirka = 2.500.metru,
        vydrz = 106.hours,
        popis = """
            |Škoda-Sanos S 200 (též Škoda-Sanos 01, někdy uváděn jako Škoda-Sanos 200Tr) je dvoučlánkový trolejbus, který byl v 80. letech 20. století kooperačně vyráběn československým podnikem Škoda Ostrov a jugoslávskou (respektive makedonskou) společností FAS 11. Oktromvri Skopje.
            |
            |Výrobce: Škoda Ostrov, Karosa Vysoké Mýto
            |Rok výroby: 1982 – 1987
            |Délka: 18 000 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 44
            |Míst k stání: 99
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 14Tr",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 80,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 49.0.penezZaMin,
        cena = 115_000.penez,
        delka = 11.340F.metru,
        sirka = 2.500.metru,
        vydrz = 240.hours,
        popis = """
            |Škoda 14Tr je československý trolejbus vyráběný v letech 1981 až 1998 podnikem Škoda Ostrov. Prototypy byly vyrobeny již v letech 1972 a 1974.
            |
            |Výrobce: Škoda Ostrov
            |Rok výroby: 1972 – 1998
            |Délka: 11 340 mm
            |Šířka: 2 500 mm
            |Hmotnost: 10 000 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 24
            |Míst k stání: 56
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 14TrM",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 82,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 44.0.penezZaMin,
        cena = 120_000.penez,
        delka = 11.340F.metru,
        sirka = 2.500.metru,
        vydrz = 240.hours,
        popis = """
            |Škoda 14TrM je modernizovaná verze československého trolejbusu Škoda 14Tr. Vyráběna byla v letech 1995–2004 firmou Škoda Ostrov.
            |
            |Výrobce: Škoda Ostrov
            |Rok výroby: 1995–2004
            |Délka: 11 340 mm
            |Šířka: 2 500 mm
            |Hmotnost: 10 400 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 28
            |Míst k stání: 54
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 15Tr",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 145,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 48.0.penezZaMin,
        cena = 160_000.penez,
        delka = 17.360F.metru,
        sirka = 2.500.metru,
        vydrz = 250.hours,
        popis = """
            |Škoda 15Tr je dvoučlánkový trolejbus vyráběný mezi lety 1988 a 1995 (prototypy již v první polovině 80. let) podnikem Škoda Ostrov. Od roku 1995 byla produkována modernizovaná verze Škoda 15TrM.
            |
            |Výrobce: Škoda Ostrov
            |Rok výroby: 1988 – 1995
            |Délka: 17 360 mm
            |Šířka: 2 500 mm
            |Hmotnost: 15 800 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 45
            |Míst k stání: 100
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 15TrM",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 145,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 44.0.penezZaMin,
        cena = 165_000.penez,
        delka = 17.360F.metru,
        sirka = 2.500.metru,
        vydrz = 250.hours,
        popis = """
            |Škoda 15TrM je modernizovaná verze československého dvoučlánkového trolejbusu Škoda 15Tr. Tato verze byla vyráběna od roku 1995 do roku 2004, kdy byl výrobní závod Škody Ostrov uzavřen.
            |
            |Výrobce: Škoda Ostrov
            |Rok výroby: 1995 – 2004
            |Délka: 17 360 mm
            |Šířka: 2 500 mm
            |Hmotnost: 16 400 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 45
            |Míst k stání: 100
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 17Tr",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 80,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 45.0.penezZaMin,
        cena = 120_000.penez,
        delka = 11.470F.metru,
        sirka = 2.500.metru,
        vydrz = 180.hours,
        popis = """
            |Škoda 17Tr je typ československého trolejbusu. Tento model (společně s autobusem Karosa B 831) byl vyvinut v rámci unifikace vozidel MHD společnostmi Škoda a Karosa v polovině 80. let 20. století.
            |
            |Výrobce: Škoda Ostrov
            |Rok výroby: 1987–1990
            |Délka: 11 470 mm
            |Šířka: 2 500 mm
            |Hmotnost: 10 900 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 29
            |Míst k stání: 51
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 21Tr",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 86,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 43.0.penezZaMin,
        cena = 130_000.penez,
        delka = 11.760F.metru,
        sirka = 2.500.metru,
        vydrz = 192.hours,
        popis = """
            |Škoda 21Tr je nízkopodlažní trolejbus vyráběný společností Škoda Ostrov mezi lety 1995 a 2004.
            |
            |Výrobce: Škoda Ostrov
            |Rok výroby: 1995-2004
            |Délka: 11 760 mm
            |Šířka: 2 500 mm
            |Hmotnost: 10 950 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 26
            |Míst k stání: 60
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 22Tr",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 140,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 43.0.penezZaMin,
        cena = 170_000.penez,
        delka = 18.070F.metru,
        sirka = 2.500.metru,
        vydrz = 192.hours,
        popis = """
            |Škoda 22Tr je nízkopodlažní dvoučlánkový trolejbus, který byl vyráběn v letech 2002 až 2004 firmou Škoda Ostrov. Konstrukčně z něj byl odvozen standardní trolejbus Škoda 21Tr a autobus Škoda 21Ab.
            |
            |Výrobce: Škoda Ostrov
            |Rok výroby: 2002-2004
            |Délka: 18 070 mm
            |Šířka: 2 500 mm
            |Hmotnost: 16 700 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 41
            |Míst k stání: 99
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 24Tr Irisbus Citybus",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 99,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 44.0.penezZaMin,
        cena = 140_000.penez,
        delka = 11.990F.metru,
        sirka = 2.500.metru,
        vydrz = 202.hours,
        popis = """
            |Škoda 24Tr Irisbus (pro západní Evropu Irisbus Škoda 24Tr) je nízkopodlažní trolejbus, který byl vyráběn v letech 2003–2014 společnostmi Škoda Electric (elektrická výzbroj a konečná montáž) a Irisbus (mechanická část).
            |
            |Výrobce: Škoda Electric, Irisbus
            |Rok výroby: 2004–2014
            |Délka: 11 990 mm
            |Šířka: 2 500 mm
            |Hmotnost: 11 500 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 30
            |Míst k stání: 69
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 24Tr Irisbus Citelis",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 99,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 40.0.penezZaMin,
        cena = 140_000.penez,
        delka = 11.990F.metru,
        sirka = 2.500.metru,
        vydrz = 200.hours,
        popis = """
            |Škoda 24Tr Irisbus (pro západní Evropu Irisbus Škoda 24Tr) je nízkopodlažní trolejbus, který byl vyráběn v letech 2003–2014 společnostmi Škoda Electric (elektrická výzbroj a konečná montáž) a Irisbus (mechanická část).
            |
            |Výrobce: Škoda Electric, Irisbus
            |Rok výroby: 2004–2014
            |Délka: 11 990 mm
            |Šířka: 2 500 mm
            |Hmotnost: 11 500 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 30
            |Míst k stání: 69
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 25Tr Irisbus Citybus",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 150,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 40.0.penezZaMin,
        cena = 175_000.penez,
        delka = 17.800F.metru,
        sirka = 2.500.metru,
        vydrz = 200.hours,
        popis = """
            |Škoda 25Tr Irisbus je dvoučlánkový nízkopodlažní trolejbus odvozený ze standardního typu Škoda 24Tr Irisbus. V letech 2004–2014 byl vyráběn firmami Škoda Electric (elektrická výzbroj vozů a konečná montáž) a Irisbus (mechanická část).
            |
            |Výrobce: Škoda Electric, Irisbus
            |Rok výroby: 2005 – 2014
            |Délka: 17 800 mm
            |Šířka: 2 500 mm
            |Hmotnost: 17 700 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 40
            |Míst k stání: 110
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 25Tr Irisbus Citelis",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 150,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 39.0.penezZaMin,
        cena = 180_000.penez,
        delka = 17.900F.metru,
        sirka = 2.500.metru,
        vydrz = 200.hours,
        popis = """
            |Škoda 25Tr Irisbus je dvoučlánkový nízkopodlažní trolejbus odvozený ze standardního typu Škoda 24Tr Irisbus. V letech 2004–2014 byl vyráběn firmami Škoda Electric (elektrická výzbroj vozů a konečná montáž) a Irisbus (mechanická část).
            |
            |Výrobce: Škoda Electric, Irisbus
            |Rok výroby: 2005 – 2014
            |Délka: 17 900 mm
            |Šířka: 2 500 mm
            |Hmotnost: 17 700 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 40
            |Míst k stání: 110
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 26Tr Solaris III",
        trakce = Trolejbus.Parcialni,
        vyrobce = SKODA,
        kapacita = 102,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 34.0.penezZaMin,
        cena = 120_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 242.hours,
        popis = """
            |Škoda 26Tr Solaris je nízkopodlažní trolejbus vyráběný společností Škoda Electric s využitím vozové skříně firmy Solaris Bus & Coach. V současnosti je společně s typy Škoda 30Tr SOR a Škoda 32Tr SOR jedním z modelů standardního dvounápravového trolejbusu, který Škoda nabízí.
            |
            |Výrobce: Škoda Electric, Solaris Bus & Coach
            |Rok výroby: 2010 – 2016
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 10 900 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 34
            |Míst k stání: 68
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 26Tr Solaris IV",
        trakce = Trolejbus.Parcialni,
        vyrobce = SKODA,
        kapacita = 102,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 30.0.penezZaMin,
        cena = 130_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 256.hours,
        popis = """
            |Škoda 26Tr Solaris je nízkopodlažní trolejbus vyráběný společností Škoda Electric s využitím vozové skříně firmy Solaris Bus & Coach. V současnosti je společně s typy Škoda 30Tr SOR a Škoda 32Tr SOR jedním z modelů standardního dvounápravového trolejbusu, který Škoda nabízí.
            |
            |Výrobce: Škoda Electric, Solaris Bus & Coach
            |Rok výroby: od roku 2016
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 10 900 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 34
            |Míst k stání: 68
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 27Tr Solaris III",
        trakce = Trolejbus.Parcialni,
        vyrobce = SKODA,
        kapacita = 167,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 34.0.penezZaMin,
        cena = 180_000.penez,
        delka = 18F.metru,
        sirka = 2.550.metru,
        vydrz = 242.hours,
        popis = """
            |Škoda 27Tr Solaris je kloubový trolejbus o délce 18 m. Výrobcem karoserie je polská firma Solaris Bus & Coach, dodavatelem elektrické výzbroje je plzeňská Škoda Electric, která toto vozidlo také kompletuje.
            |
            |Výrobce: Škoda Electric, Solaris bus & coach
            |Rok výroby: 2010 – 2016
            |Délka: 18 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 16 500 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 50
            |Míst k stání: 117
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 27Tr Solaris IV",
        trakce = Trolejbus.Parcialni,
        vyrobce = SKODA,
        kapacita = 167,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 30.0.penezZaMin,
        cena = 195_000.penez,
        delka = 18F.metru,
        sirka = 2.550.metru,
        vydrz = 256.hours,
        popis = """
            |Škoda 27Tr Solaris je kloubový trolejbus o délce 18 m. Výrobcem karoserie je polská firma Solaris Bus & Coach, dodavatelem elektrické výzbroje je plzeňská Škoda Electric, která toto vozidlo také kompletuje.
            |
            |Výrobce: Škoda Electric, Solaris bus & coach
            |Rok výroby: od roku 2016
            |Délka: 18 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 16 500 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 50
            |Míst k stání: 117
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 28Tr Solaris III",
        trakce = Trolejbus.Parcialni,
        vyrobce = SKODA,
        kapacita = 135,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 34.0.penezZaMin,
        cena = 145_000.penez,
        delka = 14.590F.metru,
        sirka = 2.550.metru,
        vydrz = 242.hours,
        popis = """
            |Škoda 28Tr Solaris je třínápravový trolejbus vyráběný ve spolupráci firem Škoda Electric (elektrická výzbroj a kompletace) a Solaris Bus & Coach (karoserie). Výroba probíhala v letech 2008–2015.
            |
            |Výrobce: Škoda Electric, Solaris Bus & Coach
            |Rok výroby: 2008 – 2015
            |Délka: 14 590 mm
            |Šířka: 2 550 mm
            |Hmotnost: 13 700 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 43
            |Míst k stání: 92
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 30Tr SOR",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 100,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 44.0.penezZaMin,
        cena = 160_000.penez,
        delka = 12.180F.metru,
        sirka = 2.550.metru,
        vydrz = 168.hours,
        popis = """
            |Škoda 30Tr SOR je nízkopodlažní trolejbus vyráběný firmou Škoda Electric ve spolupráci s dodavatelem karoserie SOR Libchavy (skříň autobusu typu NB 12). Z modelu 30Tr je odvozen kloubový vůz Škoda 31Tr SOR. Je to hnus.
            |
            |Výrobce: Škoda Electric, SOR Libchavy
            |Rok výroby: od roku 2011
            |Délka: 12 180 mm
            |Šířka: 2 550 mm
            |Hmotnost: 10 800 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 32
            |Míst k stání: 68
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 31Tr SOR",
        trakce = Trolejbus.Obycejny,
        vyrobce = SKODA,
        kapacita = 166,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 44.0.penezZaMin,
        cena = 200_000.penez,
        delka = 18.750F.metru,
        sirka = 2.550.metru,
        vydrz = 168.hours,
        popis = """
            |Škoda 31Tr SOR je kloubový nízkopodlažní trolejbus vyráběný ve spolupráci firem Škoda Electric (elektrická výzbroj a kompletace) a SOR Libchavy, který dodává karoserii vycházející z autobusu SOR NB 18. Je to hnus.
            |
            |Výrobce: Škoda Electric, SOR Libchavy
            |Rok výroby: od roku 2010
            |Délka: 18 750 mm
            |Šířka: 2 550 mm
            |Hmotnost: 18 800 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 52
            |Míst k stání: 114
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 32Tr SOR",
        trakce = Trolejbus.Parcialni,
        vyrobce = SKODA,
        kapacita = 95,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 29.0.penezZaMin,
        cena = 180_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 240.hours,
        popis = """
            |Škoda 32Tr SOR je nízkopodlažní trolejbus vyráběný ve spolupráci firem Škoda Electric (elektrická výzbroj a kompletace) a SOR Libchavy, která dodává karoserii vycházející z autobusu SOR NS 12.
            |
            |Výrobce: Škoda Electric, SOR Libchavy
            |Rok výroby: od roku 2018
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 10 800 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 35
            |Míst k stání: 60
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 33Tr SOR",
        trakce = Trolejbus.Parcialni,
        vyrobce = SKODA,
        kapacita = 119,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 29.0.penezZaMin,
        cena = 220_000.penez,
        delka = 18.750F.metru,
        sirka = 2.550.metru,
        vydrz = 240.hours,
        popis = """
            |Škoda 33Tr SOR je nízkopodlažní kloubový trolejbus vyráběný ve spolupráci firem Škoda Electric (elektrická výzbroj a kompletace) a SOR Libchavy, která dodává karoserii vycházející z autobusu SOR NS 18.
            |
            |Výrobce: Škoda Electric, SOR Libchavy
            |Rok výroby: od roku 2019
            |Délka: 18 750 mm
            |Šířka: 2 550 mm
            |Hmotnost: 18 800 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 47
            |Míst k stání: 73
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 35Tr Iveco",
        trakce = Trolejbus.Parcialni,
        vyrobce = SKODA,
        kapacita = 125,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 33.0.penezZaMin,
        cena = 240_000.penez,
        delka = 18.559F.metru,
        sirka = 2.550.metru,
        vydrz = 204.hours,
        popis = """
            |Škoda 35Tr je nízkopodlažní kloubový trolejbus vyráběný ve spolupráci firem Škoda Electric (elektrická výzbroj a kompletace) a Iveco Bus, které dodává karoserii vycházející z autobusu Iveco Urbanway 18M.
            |
            |Výrobce: Škoda Electric, Iveco Bus
            |Rok výroby: od roku 2020
            |Délka: 18 559 mm
            |Šířka: 2 550 mm
            |Hmotnost: 19 100 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 35
            |Míst k stání: 90
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda 36Tr TEMSA",
        trakce = Trolejbus.Parcialni,
        vyrobce = SKODA,
        kapacita = 112,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 27.0.penezZaMin,
        cena = 190_000.penez,
        delka = 12.02F.metru,
        sirka = 2.550.metru,
        vydrz = 222.hours,
        popis = """
            |Škoda 36Tr (obchodní název T'CITY) nebo také Škoda T'CITY 36Tr je nízkopodlažní trolejbus vyráběný od roku 2022 českou firmou Škoda Electric s využitím karoserie tureckého výrobce Temsa. Na stejném základě vznikl také elektrobus Škoda 36BB.
            |
            |Výrobce: Škoda Electric, Iveco Bus
            |Rok výroby: od roku 2022
            |Délka: 12 020 mm
            |Šířka: 2 550 mm
            |Hmotnost: 19 100 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 27
            |Míst k stání: 85
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 60T",
        trakce = Trolejbus.Obycejny,
        vyrobce = IKARUS,
        kapacita = 75,
        maxRychlost = 60.kilometruZaHodinu,
        maxNaklady = 140.0.penezZaMin,
        cena = 90_000.penez,
        delka = 9.4F.metru,
        sirka = 2.500.metru,
        vydrz = 110.hours,
        popis = """
            |Ikarus 60T je trolejbus založený na sběrnici Ikarus 60. Karoserie byla vyrobena v továrně Ikarus Body and Vehicle Factory a většina jejích elektrických zařízení byla použita v elektronice sešrotovaných trolejbusů MTB-82. Celkem bylo vyrobeno 157 jednotek, z nichž více než polovina byla přestavěna na přívěsné a kloubové trolejbusy. Všechny jednotky byly dodány v Budapešti v letech 1952 až 1976.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1952 – 1976
            |Délka: 9 400 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 60 km/h
            |Míst k sezení: 22
            |Míst k stání: 53
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 260T",
        trakce = Trolejbus.Obycejny,
        vyrobce = IKARUS,
        kapacita = 75,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 90.0.penezZaMin,
        cena = 100_000.penez,
        delka = 11F.metru,
        sirka = 2.500.metru,
        vydrz = 170.hours,
        popis = """
            |Trolejbus BKV Ikarus 260T je první maďarský trolejbus postavený s karoserií Ikarus řady 200. Byl dokončen v roce 1974, vozidlo bylo vybaveno ZiU-5, jednalo se o společný vývoj BKV a Ikarus. Jeho kariérní číslo bylo 600.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1974
            |Délka: 11 000 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 22
            |Míst k stání: 53
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 263T",
        trakce = Trolejbus.Obycejny,
        vyrobce = IKARUS,
        kapacita = 75,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 60.0.penezZaMin,
        cena = 100_000.penez,
        delka = 11.94F.metru,
        sirka = 2.500.metru,
        vydrz = 180.hours,
        popis = """
            |Ikarus 263 je sólový typ autobusu určený pro městskou a příměstskou dopravu v továrně Ikarus, což byla o 1 m delší verze Ikarus 260.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1988 – 2001
            |Délka: 11 940 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 22
            |Míst k stání: 53
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 280T",
        trakce = Trolejbus.Obycejny,
        vyrobce = IKARUS,
        kapacita = 144,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 75.0.penezZaMin,
        cena = 130_000.penez,
        delka = 16.5F.metru,
        sirka = 2.500.metru,
        vydrz = 176.hours,
        popis = """
            |Ikarus 280T je typ trolejbusu založený na sběrnici Ikarus 280. Karoserie byla vyrobena společností Ikarus Body and Vehicle Factory a její elektrická zařízení byla navržena různými společnostmi. Zpočátku byla použita elektronika vyřazených vozíků ZiU a nakonec byla většina nových vozíků vyrobena se zařízením vyvinutým v Ganz Electrical Works (GVM).
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1975 – 1992
            |Délka: 16 500 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 34
            |Míst k stání: 110
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 283T",
        trakce = Trolejbus.Obycejny,
        vyrobce = IKARUS,
        kapacita = 168,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 70.0.penezZaMin,
        cena = 140_000.penez,
        delka = 17.975F.metru,
        sirka = 2.500.metru,
        vydrz = 182.hours,
        popis = """
            |V Moskvě, hlavním městě Sovětského svazu, v pozdních 1980s, bylo nutné dát na trh více kapacitních trolejbusů, protože kloubové ZiU-10 nedorazily dostatečným tempem. SVARZ (Sokolnisky Vagonoremontno-stroityelnij Zavod – Sokolnik Wagon Repair and Production Plant) ve spolupráci s MTRZ (Moskovsky Trolejbusnoremontnij Zavod – Moskevský opravna trolejbusů) přestavěl kloubové autobusy Ikarus na trolejbusy. S elektrickými komponenty pocházejícími z továrny Dinamo byly nejprve přestavěny Ikarus 280, poté v roce 1991 bylo přestavěno pět autobusů Ikarus 283.00, jejich pohon byl stejný jako u trolejbusu ZiU. Celkem 58 vozíků vyrobila společnost SVARZ Ikarus, z toho pět 283T, další 280T. Vozíky byly sešrotovány v roce 2004.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár
            |Rok výroby: 1991
            |Délka: 17 975 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 37
            |Míst k stání: 131
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 411T",
        trakce = Trolejbus.Obycejny,
        vyrobce = IKARUS,
        kapacita = 61,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 60.0.penezZaMin,
        cena = 105_000.penez,
        delka = 11.07F.metru,
        sirka = 2.500.metru,
        vydrz = 182.hours,
        popis = """
            |Ikarus 411T je typ trolejbusu založeného na sběrnici Ikarus 411. Karoserie byla vyrobena společností Ikarus Body and Vehicle Factory a elektrická zařízení Kiepe a Obus.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár, Kiepe, Obus
            |Rok výroby: 1994
            |Délka: 11 070 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 25
            |Míst k stání: 36
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 412T",
        trakce = Trolejbus.Obycejny,
        vyrobce = IKARUS,
        kapacita = 62,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 50.0.penezZaMin,
        cena = 110_000.penez,
        delka = 11.88F.metru,
        sirka = 2.500.metru,
        vydrz = 194.hours,
        popis = """
            |Ikarus 412T je typ trolejbusu založený na sběrnici Ikarus 412. Karoserie byla vyrobena společností Ikarus Body and Vehicle Factory a elektrická zařízení společnostmi Ganz-Ansaldo, Kiepe a Obus.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár, Ganz-Ansaldo, Kiepe, Obus
            |Rok výroby: 1999, 2002
            |Délka: 11 880 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 27
            |Míst k stání: 35
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 415T",
        trakce = Trolejbus.Obycejny,
        vyrobce = IKARUS,
        kapacita = 106,
        maxRychlost = 60.kilometruZaHodinu,
        maxNaklady = 49.0.penezZaMin,
        cena = 110_000.penez,
        delka = 11.5F.metru,
        sirka = 2.500.metru,
        vydrz = 194.hours,
        popis = """
            |Ikarus 415T je příměstsko-městský sólový trolejbus Ikarus s nízkou podlahou. Trolejová verze Ikarus 415, jeho hlavním provozovatelem je RATB Bukurešť.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár, GVM, Astra, Dynamo
            |Rok výroby: 1997 – 2002
            |Délka: 11 500 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 60 km/h
            |Míst k sezení: 27
            |Míst k stání: 79
        """.trimMargin()
    ),
    TypBusu(
        model = "Ikarus 435T",
        trakce = Trolejbus.Obycejny,
        vyrobce = IKARUS,
        kapacita = 100,
        maxRychlost = 60.kilometruZaHodinu,
        maxNaklady = 50.0.penezZaMin,
        cena = 150_000.penez,
        delka = 17.85F.metru,
        sirka = 2.500.metru,
        vydrz = 194.hours,
        popis = """
            |Ikarus 435T (také známý jako Ikarus 435.81) je typ trolejbusu založený na autobusu typu 435 Ikarus Body and Vehicle Factory. Karoserie vyráběl Ikarus a elektrická zařízení Kiepe.
            |
            |Výrobce: Ikarus Karosszéria - és Járműgyár, Kiepe
            |Rok výroby: 1994 – 1996
            |Délka: 17 850 mm
            |Šířka: 2 500 mm
            |Maximální rychlost: 60 km/h
            |Míst k sezení: 44
            |Míst k stání: 56
        """.trimMargin()
    ),
    TypBusu(
        model = "Ekova Electron 12T",
        trakce = Trolejbus.Parcialni,
        vyrobce = EKOVA,
        kapacita = 86,
        maxRychlost = 70.kilometruZaHodinu,
        maxNaklady = 26.0.penezZaMin,
        cena = 190_000.penez,
        delka = 11.980F.metru,
        sirka = 2.550.metru,
        vydrz = 240.hours,
        popis = """
            |Ekova Electron 12T je nízkopodlažní trolejbus vyrobený českou firmou Ekova Electric v jednom prototypu v roce 2017.
            |
            |Výrobce: Škoda Ekova
            |Rok výroby: od roku 2017
            |Délka: 11 980 mm
            |Šířka: 2 550 mm
            |Hmotnost: 12 040 kg
            |Maximální rychlost: 70 km/h
            |Míst k sezení: 26
            |Míst k stání: 60
        """.trimMargin()
    ),
    TypBusu(
        model = "HESS lighTram 25",
        trakce = Trolejbus.Parcialni,
        vyrobce = HESS,
        kapacita = 213,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 25.0.penezZaMin,
        cena = 300_000.penez,
        delka = 24.700F.metru,
        sirka = 2.550.metru,
        vydrz = 240.hours,
        popis = """
            |Das lighTram ist dank seinem lokal emissionsfreien Elektroantrieb das ideale Verkehrsmittel für den hochbelasteten Stadtverkehr, es ist leistungsstark, leise, dabei sparsam im Energieverbrauch und nutzt die zurückgewonnene Bremsenergie. Mit seinem grosszügigen Fahr-, Sitz- und Einstiegskomfort kommt das lighTram® bei Verkehrsbetrieben wie bei Fahrgästen sehr gut an. Dank innovativer und patentierter Antriebstechnologie mit intelligenter Steuerung besticht es auch bei winterlichen und topografisch schwierigen Bedingungen. Dank zwei gelenkten Achsen, verfügt das 24,7 m lange Fahrzeug über die gleiche Wendigkeit wie normale Gelenkbusse.
            |
            |Hersteller: Carrosserie HESS AG
            |Herstellungsjahr: bis Jahr 2018
            |Länge: 24 700 mm
            |Breite: 2 550 mm
            |Maximale Geschwindigkeit: 65 km/h
            |Sitzplätze: 72
                "Stehplätze: 141
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Trollino 12 I",
        trakce = Trolejbus.Obycejny,
        vyrobce = SOLARIS,
        kapacita = 102,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 44.0.penezZaMin,
        cena = 100_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 169.hours,
        popis = """
            |První trolejbus Solaris byl vyroben v roce 2001 na základě městského autobusu Solaris Urbino 12. Byl montován v závodě Trobus v Gdyni (nástupce PNTKM Gdynia). Struktura pohonu byla založena na tranzistorech IGBT navržených ve spolupráci s Elektrotechnickým institutem ve Varšavě. Dne 11. března 2001 se uskutečnila oficiální premiéra prototypu trolejbusu Solaris, který se jmenoval Solaris Trollino 12T – spojením některých slov „trolejbus“ a „ Urbino “.
            |
            |Výrobce: Solaris Bus & Coach
            |Rok výroby: 2001 – 2003
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 34
            |Míst k stání: 68
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Trollino 12 II",
        trakce = Trolejbus.Obycejny,
        vyrobce = SOLARIS,
        kapacita = 102,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 39.0.penezZaMin,
        cena = 110_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 196.hours,
        popis = """
            |V září 2002 představil Solaris novou verzi rodiny městských autobusů Urbino, která zahrnovala také trolejbusy Solaris Trollino. Hlavní změny se dotkly vnějšího designu – mírně se změnil tvar předních světlometů, jinak byl uspořádán i interiér. V roce 2003 dodal Solaris společně s Ganz Transelektro 30 kloubových trolejbusů pro Řím. Tato vozidla byla poháněna asynchronním motorem poháněným pantografy a také trakčními bateriemi používanými na úsecích bez trolejbusové trakce.
            |
            |Výrobce: Solaris Bus & Coach
            |Rok výroby: 2003 – 2004
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 34
            |Míst k stání: 68
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Trollino 12 III",
        trakce = Trolejbus.Parcialni,
        vyrobce = SOLARIS,
        kapacita = 102,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 33.0.penezZaMin,
        cena = 130_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 225.hours,
        popis = """
            |V roce 2004 se uskutečnila premiéra třetí generace městských autobusů Solaris Urbino, která se vyznačovala odlišným designem a také velkými stylistickými změnami. Patřila k nim i rodina Trollino.
            |
            |Výrobce: Solaris Bus & Coach
            |Rok výroby: 2005 – 2017
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 34
            |Míst k stání: 68
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Trollino 12 IV",
        trakce = Trolejbus.Parcialni,
        vyrobce = SOLARIS,
        kapacita = 102,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 30.0.penezZaMin,
        cena = 140_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 240.hours,
        popis = """
            |V roce 2014 se uskutečnila premiéra městských autobusů Solaris Urbino 4. generace, známých jako New Urbino. Přesto se trolejbusy Solaris vyráběly ještě ve třetí generaci. První trolejbusy na bázi nové verze Urbino byly do slovenské Žiliny dodány v prosinci 2017.
            |
            |Výrobce: Solaris Bus & Coach
            |Rok výroby: od roku 2017
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 34
            |Míst k stání: 68
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Trollino 15 II",
        trakce = Trolejbus.Obycejny,
        vyrobce = SOLARIS,
        kapacita = 135,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 34.0.penezZaMin,
        cena = 135_000.penez,
        delka = 14.590F.metru,
        sirka = 2.550.metru,
        vydrz = 196.hours,
        popis = """
            |V srpnu 2003 představil Solaris první trolejbus na světě o délce 15 m - Solaris Trollino 15. Vyráběl se v Ostravě ve spolupráci s firmou Cegelec, pohon navrhla ČKD Pragoimex.
            |
            |Výrobce: Solaris Bus & Coach
            |Rok výroby: 2002 – 2007
            |Délka: 14 590 mm
            |Šířka: 2 550 mm
            |Hmotnost: 13 700 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 43
            |Míst k stání: 92
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Trollino 15 III",
        trakce = Trolejbus.Parcialni,
        vyrobce = SOLARIS,
        kapacita = 135,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 34.0.penezZaMin,
        cena = 145_000.penez,
        delka = 14.590F.metru,
        sirka = 2.550.metru,
        vydrz = 242.hours,
        popis = """
            |Škoda 28Tr Solaris je třínápravový trolejbus vyráběný ve spolupráci firem Škoda Electric (elektrická výzbroj a kompletace) a Solaris Bus & Coach (karoserie). Výroba probíhala v letech 2008–2015.
            |
            |Výrobce: Solaris Bus & Coach
            |Rok výroby: 2008 – 2015
            |Délka: 14 590 mm
            |Šířka: 2 550 mm
            |Hmotnost: 13 700 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 43
            |Míst k stání: 92
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Trollino 18 I",
        trakce = Trolejbus.Obycejny,
        vyrobce = SOLARIS,
        kapacita = 167,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 44.0.penezZaMin,
        cena = 150_000.penez,
        delka = 18F.metru,
        sirka = 2.550.metru,
        vydrz = 169.hours,
        popis = """
            |V polovině roku 2001 Solaris také vyrobil první trolejbusy Solaris Trollino 18T na základě modelu Urbino 18, které byly vyvinuty ve spolupráci s maďarskou firmou Ganz Transelektro z Budapešti.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2001 – 2002
            |Délka: 18 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 16 500 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 50
            |Míst k stání: 117
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Trollino 18 II",
        trakce = Trolejbus.Obycejny,
        vyrobce = SOLARIS,
        kapacita = 167,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 39.0.penezZaMin,
        cena = 165_000.penez,
        delka = 18F.metru,
        sirka = 2.550.metru,
        vydrz = 196.hours,
        popis = """
            |V září 2002 představil Solaris novou verzi rodiny městských autobusů Urbino, která zahrnovala také trolejbusy Solaris Trollino. Hlavní změny se dotkly vnějšího designu – mírně se změnil tvar předních světlometů, jinak byl uspořádán i interiér. V roce 2003 dodal Solaris společně s Ganz Transelektro 30 kloubových trolejbusů pro Řím.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2002 – 2008
            |Délka: 18 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 16 500 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 50
            |Míst k stání: 117
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Trollino 18 III",
        trakce = Trolejbus.Parcialni,
        vyrobce = SOLARIS,
        kapacita = 167,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 34.0.penezZaMin,
        cena = 180_000.penez,
        delka = 18F.metru,
        sirka = 2.550.metru,
        vydrz = 242.hours,
        popis = """
            |V roce 2004 se uskutečnila premiéra třetí generace městských autobusů Solaris Urbino, která se vyznačovala odlišným designem a také velkými stylistickými změnami. Patřila k nim i rodina Trollino. Od třetí generace se také změnil vzhled zeleného maskota jezevčíka na trolejbusech Trollino – dosud vypadal stejně jako na autobusech Solaris Urbino, později však přibylo „vodítko“, symbolizující sběrače proudu na střeše trolejbusu.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2008 – 2016
            |Délka: 18 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 16 500 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 50
            |Míst k stání: 117
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Trollino 18 IV",
        trakce = Trolejbus.Parcialni,
        vyrobce = SOLARIS,
        kapacita = 167,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 30.0.penezZaMin,
        cena = 195_000.penez,
        delka = 18F.metru,
        sirka = 2.550.metru,
        vydrz = 256.hours,
        popis = """
            |V roce 2014 se uskutečnila premiéra městských autobusů Solaris Urbino 4. generace, známých jako New Urbino. Přesto se trolejbusy Solaris vyráběly ještě ve třetí generaci. První trolejbusy na bázi nové verze Urbino byly do slovenské Žiliny dodány v prosinci 2017. Jednalo se o 12 kloubových trolejbusů Škoda-Solaris 27Tr a 3 kusy Škoda-Solaris 26Tr.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2016
            |Délka: 18 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 16 500 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 50
            |Míst k stání: 117
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Trollino 18 III MetroStyle",
        trakce = Trolejbus.Parcialni,
        vyrobce = SOLARIS,
        kapacita = 167,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 29.0.penezZaMin,
        cena = 210_000.penez,
        delka = 18F.metru,
        sirka = 2.550.metru,
        vydrz = 256.hours,
        popis = """
            |V roce 2012 dodal Solaris do Salcburku první Solaris Trollino 18 s pohonem Cegelec s novým designem odkazujícím na rodinu tramvají Solaris Tramino . Zvláštní pozornost byla věnována nakloněnému čelnímu sklu a také novým předním a zadním svítilnám. Tato vozidla byla pojmenována Solaris Trollino 18 MetroStyle.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2012 – 2017
            |Délka: 18 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 16 500 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 50
            |Míst k stání: 117
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Trollino 24 IV",
        trakce = Trolejbus.Parcialni,
        vyrobce = SOLARIS,
        kapacita = 207,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 30.0.penezZaMin,
        cena = 260_000.penez,
        delka = 24F.metru,
        clanky = listOf(
            10.metru,
            6.metru,
            8.metru,
        ),
        sirka = 2.550.metru,
        vydrz = 240.hours,
        popis = """
            |Solaris Trollino 24 IV je nejdelší tříčlánkový trolejbus z rodiny Solaris Trollino.
            |
            |Výrobce: Solaris Bus & Coach
            |Rok výroby: od roku 2019
            |Délka: 24 000 mm
            |Šířka: 2 550 mm
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 68
            |Míst k stání: 141
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Trollino 24 IV MetroStyle",
        trakce = Trolejbus.Parcialni,
        vyrobce = SOLARIS,
        kapacita = 207,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 22.0.penezZaMin,
        cena = 310_000.penez,
        delka = 24F.metru,
        sirka = 2.550.metru,
        vydrz = 240.hours,
        popis = """
            |Oficiální premiéra modelu, tentokrát ve verzi MetroStyle, proběhla v říjnu 2019 během veletrhu Busworld v Bruselu. Během veletrhu Poznań Arena Design 2020 obdržel cenu Top Design Award. Trollino 24 MetroStyle uskutečnil své první testovací jízdy v listopadu 2020 v Bratislavě.
            |
            |Výrobce: Solaris Bus & Coach
            |Rok výroby: od roku 2019
            |Délka: 24 000 mm
            |Šířka: 2 550 mm
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 68
            |Míst k stání: 141
        """.trimMargin()
    ),
    TypBusu(
        model = "Bogdan T601.10",
        trakce = Trolejbus.Obycejny,
        vyrobce = BOGDAN,
        kapacita = 60,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 42.0.penezZaMin,
        cena = 80_000.penez,
        delka = 10.600F.metru,
        sirka = 2.550.metru,
        vydrz = 180.hours,
        popis = """
            |Bogdan T601.10 je nejkratší trolejbus ukrajinského výrobce Bogdan
            |
            |Výrobce: Bogdan
            |Rok výroby: od roku 2010
            |Délka: 10 600 mm
            |Šířka: 2 550 mm
            |Maximální rychlost: 70 km/h
        """.trimMargin()
    ),
    TypBusu(
        model = "Bogdan T701.10",
        trakce = Trolejbus.Obycejny,
        vyrobce = BOGDAN,
        kapacita = 90,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 42.0.penezZaMin,
        cena = 120_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 180.hours,
        popis = """
            |Bogdan T701.10 je trolejbus ukrajinského výrobce Bogdan
            |
            |Výrobce: Bogdan
            |Rok výroby: od roku 2010
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Maximální rychlost: 70 km/h
        """.trimMargin()
    ),
    TypBusu(
        model = "Bogdan T801.10",
        trakce = Trolejbus.Obycejny,
        vyrobce = BOGDAN,
        kapacita = 120,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 42.0.penezZaMin,
        cena = 150_000.penez,
        delka = 15F.metru,
        sirka = 2.550.metru,
        vydrz = 180.hours,
        popis = """
            |Bogdan T801.10 je trolejbus ukrajinského výrobce Bogdan
            |
            |Výrobce: Bogdan
            |Rok výroby: od roku 2010
            |Délka: 15 000 mm
            |Šířka: 2 550 mm
            |Maximální rychlost: 70 km/h
        """.trimMargin()
    ),
    TypBusu(
        model = "Bogdan T901.10",
        trakce = Trolejbus.Obycejny,
        vyrobce = BOGDAN,
        kapacita = 150,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 42.0.penezZaMin,
        cena = 180_000.penez,
        delka = 18.700F.metru,
        sirka = 2.550.metru,
        vydrz = 180.hours,
        popis = """
            |Bogdan T901.10 je nejdelší trolejbus ukrajinského výrobce Bogdan
            |
            |Výrobce: Bogdan
            |Rok výroby: od roku 2010
            |Délka: 18 700 mm
            |Šířka: 2 550 mm
            |Maximální rychlost: 65 km/h
        """.trimMargin()
    )
)

private val elektrobusy = listOf(
    TypBusu(
        model = "Škoda 21Eb",
        trakce = Elektrobus,
        vyrobce = SKODA,
        kapacita = 70,
        maxRychlost = 70.kilometruZaHodinu,
        maxNaklady = 86.0.penezZaMin,
        cena = 120_000.penez,
        delka = 11.5F.metru,
        sirka = 2.550.metru,
        vydrz = 512.hours,
        popis = """
            |Škoda 21Eb je první elektrobus vyrobený firmou Škoda
            |
            |Výrobce: Škoda Ostrov
            |Rok výroby: 2002
            |Délka: 11 500 mm
            |Šířka: 2 550 mm
            |Hmotnost: 18 000 kg
            |Maximální rychlost: 70 km/h
            |Míst k sezení: 29
            |Míst k stání: 41
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda Perun 26BB",
        trakce = Elektrobus,
        vyrobce = SKODA,
        kapacita = 82,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 60.0.penezZaMin,
        cena = 120_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 256.hours,
        popis = """
            |Škoda Perun (typové označení Škoda 26BB) je nízkopodlažní elektrobus vyráběný od roku 2013 českou firmou Škoda Electric.
            |
            |Výrobce: Škoda Electric, Solaris bus & coach
            |Rok výroby: od roku 2013
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 27
            |Míst k stání: 55
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda Perun 29BB",
        trakce = Elektrobus,
        vyrobce = SKODA,
        kapacita = 55,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 63.0.penezZaMin,
        cena = 115_000.penez,
        delka = 8.95F.metru,
        sirka = 2.400.metru,
        vydrz = 256.hours,
        popis = """
            |Škoda 29BB je nízkopodlažní elektrobus vyráběný od roku 2018 českou firmou Škoda Electric.
            |
            |Výrobce: Škoda Electric, Solaris bus & coach
            |Rok výroby: od roku 2018
            |Délka: 8 950 mm
            |Šířka: 2 400 mm
            |Hmotnost: 14 500 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 21
            |Míst k stání: 34
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda E'City 34BB",
        trakce = Elektrobus,
        vyrobce = SKODA,
        kapacita = 75,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 99.0.penezZaMin,
        cena = 220_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 168.hours,
        popis = """
            |Škoda E'City (typové označení Škoda 34BB a Škoda 36BB) je nízkopodlažní elektrobus české firmy Škoda Electric. V roce 2017 byl vyroben jeden vůz typu 34BB, od roku 2021 probíhá produkce modelu 36BB.
            |
            |Výrobce: Škoda Electric, Heuliez Bus
            |Rok výroby: od roku 2017
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 37
            |Míst k stání: 38
        """.trimMargin()
    ),
    TypBusu(
        model = "Škoda E'City 36BB",
        trakce = Elektrobus,
        vyrobce = SKODA,
        kapacita = 69,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 90.0.penezZaMin,
        cena = 130_000.penez,
        delka = 12.095F.metru,
        sirka = 2.550.metru,
        vydrz = 168.hours,
        popis = """
            |Škoda E'City (typové označení Škoda 34BB a Škoda 36BB) je nízkopodlažní elektrobus české firmy Škoda Electric. V roce 2017 byl vyroben jeden vůz typu 34BB, od roku 2021 probíhá produkce modelu 36BB.
            |
            |Výrobce: Škoda Electric, TEMSA
            |Rok výroby: od roku 2017
            |Délka: 12 095 mm
            |Šířka: 2 550 mm
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 26
            |Míst k stání: 43
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 8,9 III LE Electric",
        trakce = Elektrobus,
        vyrobce = SOLARIS,
        kapacita = 55,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 63.0.penezZaMin,
        cena = 115_000.penez,
        delka = 8.95F.metru,
        sirka = 2.400.metru,
        vydrz = 256.hours,
        popis = """
            |Solaris Urbino 8,9 LE Electric je nízkopodlažní elektrický městský autobus, představený na podzim 2011. Vyráběla ho společnost Solaris Bus & Coach SA z Bolechowa u Poznaně. Jeho design vychází z modelu Solaris Urbino 8,9 LE. Elektrické modely Urbino 8,9 LE a 8,9 LE jsou posledními vyráběnými autobusy s karoserií Urbino 3. generace.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2011
            |Délka: 8 950 mm
            |Šířka: 2 400 mm
            |Hmotnost: 14 500 kg
            |Maximální rychlost: 50 km/h
            |Míst k sezení: 27
            |Míst k stání: 28
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 9 IV LE electric",
        trakce = Elektrobus,
        vyrobce = SOLARIS,
        kapacita = 55,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 50.0.penezZaMin,
        cena = 125_000.penez,
        delka = 8.95F.metru,
        sirka = 2.400.metru,
        vydrz = 256.hours,
        popis = """
            |Solaris Urbino 9 LE electric je nízkopodlažní elektrický městský autobus, představený na podzim 2011. Vyráběla ho společnost Solaris Bus & Coach SA z Bolechowa u Poznaně. Jeho design vychází z modelu Solaris Urbino 9 LE.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2021
            |Délka: 8 950 mm
            |Šířka: 2 400 mm
            |Hmotnost: 14 500 kg
            |Maximální rychlost: 50 km/h
            |Míst k sezení: 27
            |Míst k stání: 28
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 12 III Electric",
        trakce = Elektrobus,
        vyrobce = SOLARIS,
        kapacita = 105,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 60.0.penezZaMin,
        cena = 130_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 250.hours,
        popis = """
            |Solaris Urbino 12 electric je elektrický městský autobus, vyráběný od roku 2013 společností Solaris Bus & Coach v Bolechowo-Osiedle u Poznaně. Patří do rodiny městských autobusů Solaris Urbino.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2013 – 2016
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 10 400 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 40
            |Míst k stání: 65
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 12 IV electric",
        trakce = Elektrobus,
        vyrobce = SOLARIS,
        kapacita = 105,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 50.0.penezZaMin,
        cena = 140_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 250.hours,
        popis = """
            |V roce 2014, během zářijového veletrhu IAA, byly představeny Solaris Urbino 12 a 18 nové generace spalovacího motoru (na rozdíl od předchozích modelů byl použit termín „New Urbino“ místo „Urbino IV generace“). O rok později na veletrhu Busworld v Kortrijku představil Solaris elektrickou verzi nové verze Solaris. Nová generace poskytuje mnoho různých konfigurací v závislosti na potřebách zákazníka.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2016
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 10 400 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 40
            |Míst k stání: 65
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 12 IV LE electric",
        trakce = Elektrobus,
        vyrobce = SOLARIS,
        kapacita = 105,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 50.0.penezZaMin,
        cena = 150_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 250.hours,
        popis = """
            |V roce 2014, během zářijového veletrhu IAA, byly představeny Solaris Urbino 12 a 18 nové generace spalovacího motoru (na rozdíl od předchozích modelů byl použit termín „New Urbino“ místo „Urbino IV generace“). O rok později na veletrhu Busworld v Kortrijku představil Solaris elektrickou verzi nové verze Solaris. Nová generace poskytuje mnoho různých konfigurací v závislosti na potřebách zákazníka.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2020
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 10 400 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 40
            |Míst k stání: 65
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 15 IV LE electric",
        trakce = Elektrobus,
        vyrobce = SOLARIS,
        kapacita = 125,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 50.0.penezZaMin,
        cena = 180_000.penez,
        delka = 14.89F.metru,
        sirka = 2.550.metru,
        vydrz = 250.hours,
        popis = """
            |Solaris Urbino 15 LE je model příměstského, částečně nízkopodlažního autobusu, který v letech 2008–2018 vyráběla polská firma Solaris Bus & Coach. První informace (včetně fotografií prototypu) o modelu Urbino 15 LE byly poprvé představeny na veletrhu Autotec 2008 v Brně. Oficiální premiéra proběhla na podzim roku 2008 na motoristických výstavách IAA Nutzfahrzeuge v německém Hannoveru a Transexpo v polských Kielcích. Vůz konstrukčně vycházel z vozidel Solaris Urbino 15, které byly jedny z mála vyráběných autobusů této délky (15 m) na světě. Tyto autobusy byly hojně využívány v letech 2015 až 2021 pro meziměstskou dopravu v okolí Teplic. 2 vozy v CNG provedení zasahují do provozu i v Ústí nad Labem. Dne 20. října 2020 byla představena IV. generace Urbina 15 LE ve verzi elektrobus (s konvenčním pohonem nemá být vyráběn).
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2021
            |Délka: 14 890 mm
            |Šířka: 2 550 mm
            |Hmotnost: 12 400 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 65
            |Míst k stání: 60
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 18 III Electric",
        trakce = Elektrobus,
        vyrobce = SOLARIS,
        kapacita = 138,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 60.0.penezZaMin,
        cena = 190_000.penez,
        delka = 18F.metru,
        sirka = 2.550.metru,
        vydrz = 225.hours,
        popis = """
            |Solaris Urbino 18 electric je nízkopodlažní, kloubový městský autobus s elektrickým pohonem, vyráběný od roku 2014 polskou společností Solaris Bus & Coach v Bolechowo-Osiedle u Poznaně. Patří do rodiny městských autobusů Solaris Urbino a je vývojem kratších modelů elektrických městských autobusů výrobce - Urbino 8,9 LE electric a Urbino 12 electric.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: 2014 – 2017
            |Délka: 18 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 15 000 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 44
            |Míst k stání: 94
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 18 IV electric",
        trakce = Elektrobus,
        vyrobce = SOLARIS,
        kapacita = 138,
        maxRychlost = 80.kilometruZaHodinu,
        maxNaklady = 50.0.penezZaMin,
        cena = 205_000.penez,
        delka = 18F.metru,
        sirka = 2.550.metru,
        vydrz = 225.hours,
        popis = """
            |V roce 2014 představil Solaris novou generaci modelu Urbino 18, ale pouze ve verzi s konvenčním pohonem. V roce 2015 Solaris představil 12metrovou verzi elektrobusu 4. generace a oficiální premiéra 4. generace Solaris Urbino 18 electric proběhla na podzim 2017 na veletrhu Busworld v belgickém Kortrijku. Model je založen na řešení známý z modelů předchozí generace, byl však představen modernější design vozidla, design interiéru byl mírně změněn a některé konstrukční prvky byly zpřesněny.
            |
            |Výrobce: Solaris bus & coach
            |Rok výroby: od roku 2017
            |Délka: 18 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 15 000 kg
            |Maximální rychlost: 80 km/h
            |Míst k sezení: 44
            |Míst k stání: 94
        """.trimMargin()
    ),
    TypBusu(
        model = "Solaris Urbino 24 IV MetroStyle electric",
        trakce = Elektrobus,
        vyrobce = SOLARIS,
        kapacita = 207,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 55.0.penezZaMin,
        cena = 370_000.penez,
        delka = 24F.metru,
        sirka = 2.550.metru,
        vydrz = 240.hours,
        popis = """
            |24metrové vozidlo od Solaris bylo poprvé představeno na Busworld Europe 2019 v Bruselu. Účelem bylo vytvořit platformu pro budoucí sériovou výrobu nejen 24metrových vozidel s elektrickým nebo hybridním pohonem, ale také trolejbusů. Objednávka z Dánska je vůbec první na toto vozidlo s třemi články a elektrickým pohonem.
            |
            |Výrobce: Solaris Bus & Coach
            |Rok výroby: od roku 2021
            |Délka: 24 000 mm
            |Šířka: 2 550 mm
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 68
            |Míst k stání: 141
        """.trimMargin()
    ),
    TypBusu(
        model = "Ekova Electron 12",
        trakce = Elektrobus,
        vyrobce = EKOVA,
        kapacita = 90,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 52.0.penezZaMin,
        cena = 220_000.penez,
        delka = 11.98F.metru,
        sirka = 2.550.metru,
        vydrz = 240.hours,
        popis = """
            |Ekova Electron 12 je nízkopodlažní elektrobus vyráběný od roku 2015 českou firmou Ekova Electric. Je z něj odvozen trolejbus Ekova Electron 12T.
            |
            |Výrobce: Škoda Ekova
            |Rok výroby: od roku 2018
            |Délka: 11 980 mm
            |Šířka: 2 550 mm
            |Hmotnost: 18 000 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 30
            |Míst k stání: 60
        """.trimMargin()
    ),
    TypBusu(
        model = "SOR EBN 9,5",
        trakce = Elektrobus,
        vyrobce = SOR,
        kapacita = 69,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 80.0.penezZaMin,
        cena = 100_000.penez,
        delka = 9.79F.metru,
        sirka = 2.525.metru,
        vydrz = 160.hours,
        popis = """
            |SOR EBN 9,5 je elektrobus českého výrobce SOR Libchavy.
            |
            |Výrobce: SOR Libchavy
            |Rok výroby: od roku 2016
            |Délka: 9 790 mm
            |Šířka: 2 525 mm
            |Hmotnost: 9 650 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 26
            |Míst k stání: 43
        """.trimMargin()
    ),
    TypBusu(
        model = "SOR EBN 10,5",
        trakce = Elektrobus,
        vyrobce = SOR,
        kapacita = 85,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 80.0.penezZaMin,
        cena = 110_000.penez,
        delka = 10.37F.metru,
        sirka = 2.525.metru,
        vydrz = 160.hours,
        popis = """
            |SOR EBN 10,5 je elektrobus českého výrobce SOR Libchavy vyráběný od roku 2010. Jeho vývoj byl zahájen na přelomu let 2008 a 2009, dodavatelem elektrické výzbroje je firma Cegelec, která dále využívá subdodavatele.
            |
            |Výrobce: SOR Libchavy
            |Rok výroby: od roku 2016
            |Délka: 10 370 mm
            |Šířka: 2 525 mm
            |Hmotnost: 10 100 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 19
            |Míst k stání: 66
        """.trimMargin()
    ),
    TypBusu(
        model = "SOR NS 12 Electric",
        trakce = Elektrobus,
        vyrobce = SOR,
        kapacita = 64,
        maxRychlost = 65.kilometruZaHodinu,
        maxNaklady = 58.0.penezZaMin,
        cena = 130_000.penez,
        delka = 12F.metru,
        sirka = 2.550.metru,
        vydrz = 239.hours,
        popis = """
            |Prototyp elektrobusu SOR NS 12 Electric byl představen na veletrhu v Hannoveru v září 2016. Téhož roku byl vystaven na pražském veletrhu Czech Bus 2016, poté si vůz pronajal Dopravní podnik hl. m. Prahy. Sériová výroba začala v roce 2017. Byly a jsou dodávány do Bratislavy, Frýdku-Místku, Havířova, Karviné, Hradce Králové, Olomouce, Brašova a Zalău.
            |
            |Výrobce: SOR Libchavy
            |Rok výroby: od roku 2016
            |Délka: 12 000 mm
            |Šířka: 2 550 mm
            |Hmotnost: 12 350 kg
            |Maximální rychlost: 65 km/h
            |Míst k sezení: 29
            |Míst k stání: 35
        """.trimMargin()
    ),
)

val typyBusu = autobusy + trolejbusy + elektrobusy
