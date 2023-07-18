package cz.jaro.dopravnipodniky.other

import cz.jaro.dopravnipodniky.classes.TypBusu
import cz.jaro.dopravnipodniky.other.Podtyp.*
import cz.jaro.dopravnipodniky.other.Trakce.*
import cz.jaro.dopravnipodniky.other.Vyrobce.*


object TypyBusu {
    // upravovat max hodin a nasobitel nakladuu

    private val autobusy = listOf(

        TypBusu(
            "Škoda 706 RTO MTZ", DIESELOVY, AUTOBUS, SKODA,
            63, 65, 250.0, 100_000, 10.81F, 140, "Škoda 706 RTO (rámový trambusový osobní) je typ československého autobusu, který byl vyráběn národním podnikem Karosa mezi lety 1958 a 1972 (první prototyp již roku 1956). Odvozen byl z nákladního automobilu Škoda 706 RT. Ve výrobě byl nahrazen autobusy Karosa řady Š. V Polsku byl ale licenčně vyráběn pod označeními Jelcz 272 a Jelcz 041 až do roku 1977. MTZ - městský autobus pro tuzemské zákazníky. Má dvoje skládací elektropneumaticky ovládané dvojkřídlové dveře. Modifikace MTZ byla dodávána všem dopravním podnikům v Československu a byla primárně určena pro provoz v rámci městské hromadné dopravy. Některé autobusy (dle provozovatele) byly také vybaveny místem pro průvodčího. V Karose bylo vyrobeno celkem 14 451 vozů 706 RTO. K tomu je ale potřeba připočítat ještě autobusy a karoserie vyrobené v SVA Holýšov a v LIAZu Rýnovice.\n" +
                    "\n" +
                    "Výrobce: Karosa Vysoké Mýto\n" +
                    "Rok výroby: 1956 – 1972\n" +
                    "Délka: 10 810 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 8 590 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 20\n" +
                    "Míst k stání: 43"
        ),
        TypBusu(
            "Škoda 706 RTO MEX", DIESELOVY, AUTOBUS, SKODA,
            63, 65, 248.0, 100_000, 10.81F, 140, "Škoda 706 RTO (rámový trambusový osobní) je typ československého autobusu, který byl vyráběn národním podnikem Karosa mezi lety 1958 a 1972 (první prototyp již roku 1956). Odvozen byl z nákladního automobilu Škoda 706 RT. Ve výrobě byl nahrazen autobusy Karosa řady Š. V Polsku byl ale licenčně vyráběn pod označeními Jelcz 272 a Jelcz 041 až do roku 1977. MEX - městský autobus určený pro export. Varianta MEX se příliš nelišila od MTZ. Rozdíly byly pouze v detailech (např. u vozů pro Kubu to byly masivní nárazníky), některé vozy MEX dokonce jezdily i v československých městech. V Karose bylo vyrobeno celkem 14 451 vozů 706 RTO. K tomu je ale potřeba připočítat ještě autobusy a karoserie vyrobené v SVA Holýšov a v LIAZu Rýnovice.\n" +
                    "\n" +
                    "Výrobce: Karosa Vysoké Mýto\n" +
                    "Rok výroby: 1956 – 1972\n" +
                    "Délka: 10 810 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 8 590 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 20\n" +
                    "Míst k stání: 43"
        ),
        TypBusu(
            "Škoda 706 RTO-K", DIESELOVY, AUTOBUS, SKODA,
            105, 65, 248.0, 150_000, 16.12F, 140, "Škoda 706 RTO-K je model československého kloubového (článkového) autobusu, který byl vyroben v jednom exempláři národním podnikem Karosa Vysoké Mýto v roce 1960 (označení Škoda pochází od výrobce motoru). Vůz byl odvozen ze známého standardního autobusu Škoda 706 RTO. Po neúspěchu výroby kloubových „erťáků“ v ČSSR se začaly v hojném počtu vyrábět v Polsku (podobně jako standardní vozy 706 RTO). Zde byly vyráběny nejprve jako Jelcz AP 02 (o kousek delší než 706 RTO-K) a poté ve verzi Jelcz AP 021 (o něco kratší než 706 RTO-K) až do roku 1975. Nejednalo se o licenční výrobu, ale vlastní vývoj Poláků.\n" +
                    "\n" +
                    "Výrobce: Karosa Vysoké Mýto\n" +
                    "Rok výroby: 1960\n" +
                    "Délka: 16 120 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 12 165 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 64\n" +
                    "Míst k stání: 41"
        ),
        TypBusu(
            "Karosa ŠM 11", DIESELOVY, AUTOBUS, KAROSA,
            96, 70, 220.0, 90_000, 10.985F, 150, "Karosa ŠM 11 je model městského autobusu vyráběného národním podnikem Karosa mezi lety 1964 a 1981. Je to nástupce městské modifikace vozu Škoda 706 RTO. ŠM 11 je standardní dvounápravový autobus určený především pro městskou hromadnou dopravu (Škoda městský – označení Škoda pochází od výrobce motorů pro celou karosáckou řadu Š).\n" +
                    "\n" +
                    "Výrobce: Karosa Vysoké Mýto\n" +
                    "Rok výroby: 1964 – 1981\n" +
                    "Délka: 10 985 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 7 800 kg\n" +
                    "Maximální rychlost: 70 km/h\n" +
                    "Míst k sezení: 29\n" +
                    "Míst k stání: 67"
        ),
        TypBusu(
            "Karosa ŠM 16,5", DIESELOVY, AUTOBUS, KAROSA,
            150, 70, 220.0, 120_000, 16.66F, 150, "Karosa ŠM 16,5 je městský kloubový autobus řady Š od národního podniku Karosa Vysoké Mýto ze 60. let 20. století. Vůz ŠM 16,5 byl nástupcem modelu Škoda 706 RTO-K, který byl vyroben pouze v jednom prototypu.\n" +
                    "\n" +
                    "Výrobce: Karosa Vysoké Mýto\n" +
                    "Rok výroby: 1966 – 1969\n" +
                    "Délka: 16 660 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 11 800 kg\n" +
                    "Maximální rychlost: 70 km/h\n" +
                    "Míst k sezení: 50\n" +
                    "Míst k stání: 100"
        ),
        TypBusu(
            "Karosa B 731", DIESELOVY, AUTOBUS, KAROSA,
            94, 70, 200.0, 90_000, 11.055F, 324, "Karosa B 731 je model městského autobusu, který vyráběla společnost Karosa Vysoké Mýto v letech 1981–1996, první prototypy vznikly v roce 1974. Jde o nástupce úspěšného autobusu Karosa ŠM 11, jehož výroba byla ukončena v roce 1981.\n" +
                    "\n" +
                    "Výrobce: Karosa Vysoké Mýto\n" +
                    "Rok výroby: 1974 – 1996\n" +
                    "Délka: 11 055 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 9 400 kg\n" +
                    "Maximální rychlost: 70 km/h\n" +
                    "Míst k sezení: 31\n" +
                    "Míst k stání: 63"
        ),
        TypBusu(
            "Karosa B 732", DIESELOVY, AUTOBUS, KAROSA,
            94, 70, 180.0, 100_000, 11.055F, 232, "Karosa B 732 je model příměstského a městského třídveřového autobusu, který vyráběla společnost Karosa Vysoké Mýto v letech 1983 až 1997.\n" +
                    "\n" +
                    "Výrobce: Karosa Vysoké Mýto\n" +
                    "Rok výroby: 1983 – 1997\n" +
                    "Délka: 11 055 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 9 400 kg\n" +
                    "Maximální rychlost: 70 km/h\n" +
                    "Míst k sezení: 31\n" +
                    "Míst k stání: 63"
        ),
        TypBusu(
            "Karosa B 741", DIESELOVY, AUTOBUS, KAROSA,
            150, 70, 150.0, 150_000, 17.355F, 324, "Karosa B 741 je městský kloubový autobus vyráběný společností Karosa Vysoké Mýto v letech 1991 až 1997. V mnohých městech vystřídal starší, již nevyhovující maďarské vozy typu Ikarus 280. V součastnosti tento typ autobusu již v Česká republice v pravidelném provozu nepotkáme, proto je tento autobus již zařazen mezi autobusy muzejní.\n" +
                    "\n" +
                    "Výrobce: Karosa Vysoké Mýto\n" +
                    "Rok výroby: 1991 – 1997\n" +
                    "Délka: 17 355 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 14 000 kg\n" +
                    "Maximální rychlost: 70 km/h\n" +
                    "Míst k sezení: 42\n" +
                    "Míst k stání: 108"
        ),
        TypBusu(
            "Karosa B 831", DIESELOVY, AUTOBUS, // je to velkej hnus
            KAROSA, 103, 70, 200.0, 105_000, 11.485F,
            17 * 7,
            "Karosa B 831 je městský autobus, který byl vyroben ve třech kusech národním podnikem Karosa Vysoké Mýto mezi lety 1987 a 1989. Mělo se jednat o nástupce typu Karosa B 731 a spolu s trolejbusem Škoda 17Tr se autobus B 831 měl stát součástí nové unifikované řady silničních vozidel pro městskou hromadnou dopravu. V současnosti existuje jediný vůz B 831, který je jako historický exponát v majetku Technického muzea v Brně.\n" +
                    "\n" +
                    "Výrobce: Karosa Vysoké Mýto\n" +
                    "Rok výroby: 1987 – 1989\n" +
                    "Délka: 11 485 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 9 400 kg\n" +
                    "Maximální rychlost: 70 km/h\n" +
                    "Míst k sezení: 31\n" +
                    "Míst k stání: 72"
        ),
        TypBusu(
            "Karosa B 832", DIESELOVY, AUTOBUS, KAROSA,
            94, 70, 180.0, 110_000, 11.055F, 280, "Karosa B 832 je model městského autobusu, který vyráběla společnost Karosa Vysoké Mýto v letech 1997 až 1999. Autobus B 832 je konstrukčně téměř shodný s vozem B 732. Jedná se o dvounápravový autobus s hranatou, polosamonosnou karoserií panelové konstrukce a motorem nacházejícím se za zadní nápravou. Designově je stejný jako B 732.\n" +
                    "\n" +
                    "Výrobce: Karosa Vysoké Mýto\n" +
                    "Rok výroby: 1997 – 1999\n" +
                    "Délka: 11 055 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 9 500 kg\n" +
                    "Maximální rychlost: 70 km/h\n" +
                    "Míst k sezení: 31\n" +
                    "Míst k stání: 63"
        ),
        TypBusu(
            "Karosa B 841", DIESELOVY, AUTOBUS, KAROSA,
            150, 70, 120.0, 160_000, 17.355F, 200, "Karosa B 841 je model městského kloubového autobusu, který vyráběla společnost Karosa Vysoké Mýto v letech 1997 až 1999.\n" +
                    "\n" +
                    "Výrobce: Karosa Vysoké Mýto\n" +
                    "Rok výroby: 1997 – 1999\n" +
                    "Délka: 17 355 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 13 700 kg\n" +
                    "Maximální rychlost: 70 km/h\n" +
                    "Míst k sezení: 42\n" +
                    "Míst k stání: 108"
        ),
        TypBusu(
            "Karosa B 931", DIESELOVY, AUTOBUS, KAROSA,
            94, 70, 99.0, 115_000, 11.345F, 256, "Karosa B 931 je model městského autobusu, který vyráběla společnost Karosa Vysoké Mýto mezi lety 1995 a 2002 (od roku 1999 v upravené variantě B 931E). Jedná se o nástupce klasického městského typu Karosa B 731.\n" +
                    "\n" +
                    "Výrobce: Karosa Vysoké Mýto\n" +
                    "Rok výroby: 1995 – 2002\n" +
                    "Délka: 11 345 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 10 100 kg\n" +
                    "Maximální rychlost: 70 km/h\n" +
                    "Míst k sezení: 31\n" +
                    "Míst k stání: 63"
        ),
        TypBusu(
            "Karosa B 932", DIESELOVY, AUTOBUS, KAROSA,
            94, 70, 98.0, 120_000, 11.345F, 256, "Karosa B 932 je model městského autobusu, který vyráběla společnost Karosa Vysoké Mýto v letech 1997 až 2002. Jde o nástupce typu Karosa B 732.\n" +
                    "\n" +
                    "Výrobce: Karosa Vysoké Mýto\n" +
                    "Rok výroby: 1997 – 2002\n" +
                    "Délka: 11 345 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 10 200 kg\n" +
                    "Maximální rychlost: 70 km/h\n" +
                    "Míst k sezení: 31\n" +
                    "Míst k stání: 63"
        ),
        TypBusu(
            "Karosa B 941", DIESELOVY, AUTOBUS, KAROSA,
            160, 70, 99.0, 170_000, 17.615F, 256, "Karosa B 941 je model městského kloubového autobusu, který vyráběla společnost Karosa Vysoké Mýto v letech 1995 až 2001. Inovovaná varianta vozidla, vyráběná od roku 1999, je označena B 941E. Autobus B 941 je nástupcem typu B 741, ve výrobě byl nahrazen typem B 961 v roce 2002.\n" +
                    "\n" +
                    "Výrobce: Karosa Vysoké Mýto\n" +
                    "Rok výroby: 1995 – 2001\n" +
                    "Délka: 17 615 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 14 400 kg\n" +
                    "Maximální rychlost: 70 km/h\n" +
                    "Míst k sezení: 42\n" +
                    "Míst k stání: 118"
        ),
        TypBusu(
            "Karosa B 951", DIESELOVY, AUTOBUS, KAROSA,
            99, 70, 92.0, 125_000, 11.32F, 248, "Karosa B 951 je městský autobus, který vyráběla společnost Karosa v letech 2001 až 2007 (ve variantě B 951E od roku 2003). Jedná se o nástupce typu B 931.\n" +
                    "\n" +
                    "Výrobce: Karosa Vysoké Mýto\n" +
                    "Rok výroby: 2001 – 2007\n" +
                    "Délka: 11 320 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 10 200 kg\n" +
                    "Maximální rychlost: 70 km/h\n" +
                    "Míst k sezení: 31\n" +
                    "Míst k stání: 68"
        ),
        TypBusu(
            "Karosa B 952", DIESELOVY, AUTOBUS, KAROSA,
            99, 70, 89.0, 130_000, 11.32F, 251, "Karosa B 952 je model městského autobusu, který vyráběla Karosa Vysoké Mýto v letech 2002 až 2006, od roku 2003 ale pouze v inovované verzi B 952E. Vůz B 952 je nástupcem typu B 932.\n" +
                    "\n" +
                    "Výrobce: Karosa Vysoké Mýto\n" +
                    "Rok výroby: 2002 – 2006\n" +
                    "Délka: 11 320 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 10 200 kg\n" +
                    "Maximální rychlost: 70 km/h\n" +
                    "Míst k sezení: 31\n" +
                    "Míst k stání: 68"
        ),
        TypBusu(
            "Karosa B 961", DIESELOVY, AUTOBUS, KAROSA,
            167, 70, 89.0, 180_000, 17.59F, 251, "Karosa B 961 je městský kloubový autobus, který vyráběla společnost Karosa Vysoké Mýto v letech 2002 až 2006 (od roku 2003 pak v inovované verzi označené B 961E). B 961 je nástupce typu B 941.\n" +
                    "\n" +
                    "Výrobce: Karosa Vysoké Mýto\n" +
                    "Rok výroby: 2000 – 2006\n" +
                    "Délka: 17 590 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 14 400 kg\n" +
                    "Maximální rychlost: 70 km/h\n" +
                    "Míst k sezení: 45\n" +
                    "Míst k stání: 122"
        ),
        TypBusu(
            "Ikarus 30", DIESELOVY, AUTOBUS, IKARUS,
            40, 60, 300.0, 60_000, 8.4F, 100, "První exemplář Ikarus 30 opustil brány továrny Matthiasland 24. února 1951. Symbolicky začala pro maďarský průmysl nová éra. Kázmér Schmiedt a jeho tým byli zodpovědní za návrh autobusu. Ve srovnání s Modelem Tr 3.5 se kapacita vozidla výrazně zvýšila a může být vybavena sedadly 30 + 1 v dálkovém provedení.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1951 – 1957\n" +
                    "Délka: 8 400 mm\n" +
                    "Šířka: 2 300 mm\n" +
                    "Hmotnost: 9 070 kg\n" +
                    "Maximální rychlost: 60 km/h\n" +
                    "Míst k sezení: 30\n" +
                    "Míst k stání: 10"
        ),
        TypBusu(
            "Ikarus 31", DIESELOVY, AUTOBUS, IKARUS,
            45, 60, 250.0, 70_000, 8.54F, 120, "Bezprostředním předchůdcem konstrukce je Ikarus 30, který zdědil své hlavní technické vlastnosti a siluetu. Ikarus 30 byl vyroben v roce 1951 a používán pro městské i dálkové účely, ale byl téměř zcela nevhodný pro první úkol. Kromě vysoké pořizovací ceny a výrobních nákladů byla kapacita stojícího cestujícího velmi nízká. To motivovalo inženýry společnosti Ikarus, aby začali navrhovat vozidlo, které by nahradilo model 30.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1956 – 1965\n" +
                    "Délka: 8 540 mm\n" +
                    "Šířka: 2 420 mm\n" +
                    "Hmotnost: 9 370 kg\n" +
                    "Maximální rychlost: 60 km/h\n" +
                    "Míst k sezení: 35\n" +
                    "Míst k stání: 10"
        ),
        TypBusu(
            "Ikarus 60", DIESELOVY, AUTOBUS, IKARUS,
            60, 60, 290.0, 65_000, 9.45F, 110, "Podle cílů prvního pětiletého plánu byl autobus Ikarus 60 navržen společností Csepel-Steyr v roce 1951 v továrně Ikarus v Matthiaslandu. Trolejbusová verze je Ikarus 60T.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1952 – 1959\n" +
                    "Délka: 9 450 mm\n" +
                    "Šířka: 2 420 mm\n" +
                    "Maximální rychlost: 60 km/h\n" +
                    "Míst k sezení: 24\n" +
                    "Míst k stání: 36"
        ),
        TypBusu(
            "Ikarus 66", DIESELOVY, AUTOBUS, IKARUS,
            80, 65, 200.0, 80_000, 11.33F, 130, "Ikarus 66 je městský-příměstský autobus typu továrny Ikarus. Jeho obvyklá přezdívka je \"faros\" (česky dřevěné vlákno), pravděpodobně inspirované uspořádáním motoru vzadu vozidla a doprovodným charakteristickým motorovým stanem vyčnívajícím ze zadní části karoserie vozidla. Většina z 9 260 vyrobených kusů byla exportována do Německé demokratické republiky a s přibližně 2 700 vozidly prodanými společnostem Volán v Maďarsku to byl jeden z definujících typů autobusové dopravy v letech 1960 a 1970.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1952 – 1973\n" +
                    "Délka: 11 330 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 13 000 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 34\n" +
                    "Míst k stání: 46"
        ),
        TypBusu(
            "Ikarus 180", DIESELOVY, AUTOBUS, IKARUS,
            186, 65, 190.0, 110_000, 16.5F, 140, "Ikarus 180 je první série příměstsko-městských kloubových autobusů vyráběných společností Ikarus Body and Vehicle Factory. Sólové verze jsou typy 556 a 557. První kopie (prototyp) byla vydána v roce 1961, vozidlo bylo později ve vlastnictví FAÜ a poté MÁVAUT v Pécsi a nakonec Hotelbusz (GA-79-65). Autobus byl ve výrobě od roku 1966 do roku 1973. Celkem bylo v sérii vyrobeno 7 802 autobusů, z toho 520 pro domácí účely, z toho 416 pro hlavní město. Poslední maďarská 180 byla stažena z oběhu BKV v roce 1980.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1966 – 1973\n" +
                    "Délka: 16 500 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 36\n" +
                    "Míst k stání: 150"
        ),
        TypBusu(
            "Ikarus 190", DIESELOVY, AUTOBUS, IKARUS,
            105, 65, 170.0, 90_000, 11F, 150, "Ikarus 190 byl typ autobusu vyvinutý pro příměstský systém továrny Ikarus, který byl přímo v souladu s přísnými německými normami.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1973 – 1977\n" +
                    "Délka: 11 000 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 16 000 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 45\n" +
                    "Míst k stání: 60"
        ),
        TypBusu(
            "Ikarus 211", DIESELOVY, AUTOBUS, IKARUS,
            60, 70, 170.0, 70_000, 8.5F, 150, "Ikarus 211 byl největší midi autobus v továrně Ikarus. Společným rysem odpružených vozidel vyráběných v Székesfehérváru je motor IFA (s výkonem 92 kW/125 k) a převodovka 5+1.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1973 – 1990\n" +
                    "Délka: 8 500 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 9 000 kg\n" +
                    "Maximální rychlost: 70 km/h\n" +
                    "Míst k sezení: 31\n" +
                    "Míst k stání: 29"
        ),
        TypBusu(
            "Ikarus 216", DIESELOVY, AUTOBUS, IKARUS,
            66, 70, 170.0, 75_000, 8.96F, 150, "Ikarus 216 je typ Ikarus sotva známý v Maďarsku. V letech 1989 až 1990 bylo do Kuvajtu vyvezeno vyšší množství tohoto typu. Na první pohled se může zdát jako první člen 200 kloubového autobusu sólo.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1985 – 1993\n" +
                    "Délka: 8 960 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 9 610 kg\n" +
                    "Maximální rychlost: 70 km/h\n" +
                    "Míst k sezení: 35\n" +
                    "Míst k stání: 31"
        ),
        TypBusu(
            "Ikarus 222", DIESELOVY, AUTOBUS, IKARUS,
            77, 78, 160.0, 80_000, 9.445F, 100, "Ikarus 222, sólový městský a příměstský autobus vyvinutý továrnou Ikarus v roce 1975. Nebyl sériově vyráběn, byly vyrobeny pouze 3 prototypy, které obdržely číslování K1, K2 a K3.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1975 – 1979\n" +
                    "Délka: 9 445 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 78 km/h\n" +
                    "Míst k sezení: 18\n" +
                    "Míst k stání: 59"
        ),
        TypBusu(
            "Ikarus 238", DIESELOVY, AUTOBUS, IKARUS,
            87, 75, 150.0, 100_000, 11.395F, 150, "Ikarus 238 je typ příměstského autobusu vyráběný společností Ikarus Body and Vehicle Factory v letech 1980 až 1986. Na konci roku 1970 kladla továrna Ikarus velký důraz na to, aby sloužila požadavkům trhu rozvojových zemí, a proto v roce 1980 vyrobila svůj autobus Type 238 pro export do Afriky, především s použitím amerických komponentů. Prototyp se lišil od flotily vyráběné v sériové výrobě od roku 1982: podvozek, motor, divize dveří (1-0-1 namísto 2-0-0), množství oken, které bylo možné otevřít (na prototypu bylo možné otevřít pouze jeden ze dvou), dvojité světlomety byly opuštěny. Navzdory svému příměstskému charakteru byla v autech instalována městská sedadla a neobvykle pro rodinu 200 byly autobusy vybaveny příďovými koly. Byly vyrobeny tři podtypy (238.10, 238.20A a 238.20B), z nichž 34 bylo prodáno v letech 1984 až 1986. Většina z nich byla v Ghaně a zbytek v Gambii a lišily se pouze z hlediska sedadel (nebo designu kabiny).\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1982 – 1986\n" +
                    "Délka: 11 395 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 75 km/h\n" +
                    "Míst k sezení: 27\n" +
                    "Míst k stání: 60"
        ),
        TypBusu(
            "Ikarus 242", DIESELOVY, AUTOBUS, IKARUS,
            95, 75, 140.0, 105_000, 11F, 100, "Ikarus 242 byl experimentální autobus v roce 1960. Protože měl zadní motor, měl nižší úroveň podlahy než 260. Nebyl však sériově vyráběn, protože podle směrnice hospodářské politiky té doby bylo možné soustředit se na vývoj pouze jednoho typu, kterým byla rodina 260-280, přestože měla akcelerační a brzdné schopnosti nad 242 let a získala zlatou medaili na lipském veletrhu. Z typu byla vyrobena pouze nulová řada, jejíž jedna kopie se po zkušebních jízdách stala majetkem střední odborné školy.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1969\n" +
                    "Délka: 11 000 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 75 km/h\n" +
                    "Míst k sezení: 27\n" +
                    "Míst k stání: 68"
        ),
        TypBusu(
            "Ikarus 250", DIESELOVY, AUTOBUS, IKARUS,
            82, 75, 120.0, 110_000, 12F, 200, "Ikarus 250 je první z rodiny Ikarus 200 a byl vyráběn v letech 1970 až 1996. Různé prototypy byly vystaveny na mezinárodních výstavách, kde byly vždy vysoce ceněny. Většina modelů byla šestiválcový vznětový motor Rába-MAN D2356 HM6U. Motor měl 220 koní. V roce 2020 existoval pouze jeden veřejný autobus, Ikarus 250.68 s registračním číslem kaz-698.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1970 – 1996\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 75 km/h\n" +
                    "Míst k sezení: 52\n" +
                    "Míst k stání: 30"
        ),
        TypBusu(
            "Ikarus 260", DIESELOVY, AUTOBUS, IKARUS,
            98, 75, 100.0, 120_000, 11F, 400, "Ikarus 260 je nejúspěšnější příměstsko-městský sólový autobus továrny Ikarus Body and Vehicle Factory a je známý a používaný po celém světě. Rodina 200 autobusů byla postavena na základě plánů hlavního architekta Györgyho Bálinta, károly Oszetzkyho, jenő Mádiho a Józsefa Vargy Pappa a designéra László Finty z roku 1966. První prototyp 260 byl představen v roce 1971 a výroba začala v roce 1972 a trvala až do roku 2002. Celkem 72 547. Autobus č. 260 vyjel z továrny Ikarus v Matthiaslandu a Székesfehérváru. Jeho úspěch naznačuje skutečnost, že se dostal do velké části světa, většina z nich byla zakoupena Sovětským svazem a NDR, ale mnoho autobusů bylo také zakoupeno v Polsku a Turecku, stejně jako zakoupeno Venezuelou, Madagaskarem, Sýrií, Tchaj-wanem a Islandem a dnes je na mnoha místech považováno za nostalgický autobus po věrné rekonstrukci. BKV byla největším odběratelem v Maďarsku, v letech 1972 až 1992 zakoupila více než 2 400 nových autobusů. Prostřednictvím společností Volán bylo 260 přítomno v místní dopravě venkovských měst i v meziměstské dopravě. K jeho úspěchu přispěl mimo jiné design, opuštění střešního ohybu nebo otevíracích oken, které byly v té době obrovské, a skutečnost, že autobus byl schopen splnit řadu technických požadavků podle požadavků zákazníka. Práce řidičů byla usnadněna hydraulickým posilovačem řízení a automatickou převodovkou, ale byly také vyrobeny s manuálními převodovkami (hlavně v meziměstských verzích), zatímco levnější servis byl výhodou pro operátory. Jeho trolejová verze je 260T, která na rozdíl od kloubového nebyla vyráběna sériově, byly postaveny pouze dvě, jedna v Budapešti a druhá ve Výmaru. Jako experimentální kus ve spolupráci s MÁV vyzkoušel Ikarus také železniční autobus přestavěný z 260, který byl nakonec neúspěšně realizován. V roce 1999 se vyráběly také modely na stlačený zemní plyn (CNG), které byly dodány do tisza volán, a tehdy továrna Ikarus vyzkoušela repasovanou verzi řady 200, rodinu Classic, v rámci které byl postaven autobus Ikarus C60, ale sériová výroba byla zrušena. Kloubová verze Ikarus 260 je Ikarus 280, což bylo také velké množství typů autobusů. V roce 1966 si 200člennou rodinu vysnili hlavní architekt György Bálint, starší designéři Károly Oszetzky, Jenő Mádi a József Varga Papp a László Finta, designér Ikarus 180, 556 a 557. Zkušební kus K1 byl dokončen v roce 1970 a budapešťský dopravní podnik byl požádán, aby jej otestoval. Prototyp P1 byl testován v Sovětském svazu a prototyp P2 byl testován v Institutu pro vědecký výzkum automobilové dopravy (ATUKI). Později byl přestavěn na tropický design. Prototyp P3 byl také převeden na BKV. Ve srovnání s dříve vyrobenými modely byl rozdíl mezi opuštěním střešního ohybu a použitím obdélníkové skříně s průřezem, která umístila horní okraj oken vysoko, což upřednostňovalo stojící cestující: hovorový jazyk často odkazoval na autobusy řady 200 jako na \"panoramatické autobusy\". Dvojité dveře umožňovaly rychlejší výměnu cestujících s šířkou otevření 1300 mm a práci řidiče pomáhala hydromechanická automatická převodovka pražského typu (později také vyráběná s Csepelem a ZF) a hydraulické servořízení. Původní jednotky měly specifický výkon 9,92 kW/t a mohly nést 75 cestujících. V průběhu let bylo postaveno nespočet podtypů, které navrhovaly autobus podle potřeb přijímajících zemí, takže došlo k technickým rozdílům. Sériová výroba 11 metrů dlouhého modelu Ikarus 260 začala v roce 1972 a trvala až do roku 2002. Až do roku 1976 měly podtypy určené pro Maďarsko jednodílnou, vyšší přístrojovou desku a na přední stěny nejprve umístily nápis I K A R U S, později přešly na nápis \"cord-writing\" Ikarus, který lze vysledovat až k dohodě se srbskou továrnou Ikarus (Икарус) (dnešní Ikarbus). Další charakteristikou autobusů bylo typové číslo 260 na mřížce, které bylo často vidět a čteno jako Z60. Od roku 1975 se začala vyrábět vozidla s 1/2 dílnými posuvnými okny, ve srovnání s předchozími 1/4 dílnými posuvnými okny. Dalším rysem bylo, že až do roku 1980 bylo poslední okno na straně bez dveří nerozdělené. Až do roku 1984 byla typická trilexová kola a akordeonové dveře (často vrásčité dveře v hovorové hantýrce). Na počátku roku 1990 se objednávky společnosti Ikarus neustále snižovaly (částečně díky zániku Sovětského svazu a NDR, které byly největšími zákazníky), zatímco náklady se zvyšovaly, navíc autobusy na vývoz zůstaly doma nebo zákazník zaplatil za vyvážená vozidla až pozdě, takže továrna byla v roce 1990 prohlášena za insolventní. K poklesu přispěla i skutečnost, že Ikarus vyvinul řadu 400, ale zbývající zákazníci (včetně BKV) raději kupovali spolehlivější řadu 200. V roce 1998 továrna vyzkoušela také rodinu autobusů Ikarus Classic, ale většinou jen několik maďarských volánských firem koupilo autobusy. Repasovaná verze modelu 260 byla nakonec vyrobena pod názvem Ikarus C60. Do Československa se model 260 nedovážel, avšak verze 280 zde bývala častá. Po zkušenostech s autobusem Ikarus 280 začali českoslovenští představitelé zvažovat spolupráci mezi Škodou Ostrov a maďarskou automobilkou Ikarus na vývoji trolejbusu, který by z modelu 280 vycházel. Záměr se nakonec nezrealizoval, nicméně bylo vyrobeno několik stovek trolejbusů Ikarus 280T s různými elektrickými výzbrojemi, které jezdily jak v Maďarsku, tak v Německé demokratické republice a v Bulharsku. Inspirováni trolejbusy odvozenými od verze 280 začala společnost Ikarus roku 1974 pracovat na vývoji trolejbusu odvozeného od modelu 260, který dostal název Ikarus 260T. Oproti kloubovým trolejbusům vznikly však pouze dva tyto stroje. První (260.T1) měl karosérii z autobusu verze 260 a elektrická výzbroj pocházela z vyřazeného sovětského trolejbusu ZiU-5. Od roku 1976 vozil cestující po budapešťských ulicích a svou službu skončil o devatenáct let později roku 1995. Druhý trolejbus (Ikarus 260.T2) byl konstruován na objednávku z východního Německa, kde měli dobré zkušenosti s autobusy typů 260 a 280. Během vývoje dostal trolejbus ve srovnání se svým předchůdcem modernější vybavení, a sice tyristorovou regulaci Ganz, a upravené měl rovněž dveře (oproti standardním čtyřkřídlým měl dvoukřídlé). Jeho výkon dosahoval 150 kilowatt a návrhová maximální rychlost 60 kilometrů za hodinu. Roku 1986 se prvně představil na lipském veletrhu a následně se podrobil pečlivým testům ve Výmaru. Na základě úspěšně zvládnutých zkoušek si Německo objednalo dvanáct těchto trolejbusů, které plánovalo využít pro dopravu v Postupimi. Roku 1989 však došlo v Německé demokratické republice k politickým změnám, které znamenaly pozastavení objednávky. Poslední, 72 547. kus dostal poznávací značku IDY-782 a byl převeden na Tiszu Volán.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1971 – 2002\n" +
                    "Délka: 11 000 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 75 km/h\n" +
                    "Míst k sezení: 22\n" +
                    "Míst k stání: 76"
        ),
        TypBusu(
            "Ikarus 261", DIESELOVY, AUTOBUS, IKARUS,
            110, 75, 110.0, 125_000, 11F, 200, "Ikarus 261 je maďarský autobus. Byl vyvinut v roce 1978 továrnou Ikarus v Matthiaslandu. Byl vyroben v letech 1982 a 1983. Mechanicky identický se sběrnicí Ikarus 260 je její karoserie zrcadlovým obrazem typu 260. Vzhledem k umístění motoru nebyl žádný z 261 vyráběn v třídveřové verzi.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1982 – 1983\n" +
                    "Délka: 11 000 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 75 km/h\n" +
                    "Míst k sezení: 29\n" +
                    "Míst k stání: 81"
        ),
        TypBusu(
            "Ikarus 272", DIESELOVY, AUTOBUS, IKARUS,
            102, 75, 110.0, 130_000, 11.97F, 150, "Ikarus 272 je prototyp autobusu z roku 1978 s podvozkem MAN 16240 FOC.[1] Uspořádání dveří je 4-0-4.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1977 – 1982\n" +
                    "Délka: 11 970 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 75 km/h\n" +
                    "Míst k sezení: 29\n" +
                    "Míst k stání: 73"
        ),
        TypBusu(
            "Ikarus 280", DIESELOVY, AUTOBUS, IKARUS,
            147, 75, 100.0, 140_000, 16.5F, 444, "Ikarus 280 je nejúspěšnějším městským a příměstským kloubovým autobusem používaným po celém světě v karosárně a automobilce Ikarus. Od prosince 1971 prováděl zkušební jízdy v Budapešti a oficiálně byl představen v roce 1972 a sériová výroba začala následující rok, v roce 1973 - současně s pozastavením Ikarus 180 - a trvala až do roku 2002. V letech 1970 a 1980 tvořil tento typ dvě třetiny světové výroby kloubových autobusů, celkem 60 993. Autobus č. 280 vyjel z továrny Ikarus v Matthiaslandu a Székesfehérváru. Jeho úspěch naznačuje skutečnost, že byl zakoupen mnoha zeměmi světa, včetně Sovětského svazu, Polska, Turecka, Československa, ale také dosáhl Číny, Venezuely, Tuniska, Íránu a Tanzanie. Asi polovina autobusů byla prodána do Sovětského svazu, v Maďarsku jich měla každá volánská firma menší či větší počet a největším kupcem byla BKV, která v letech 1973 až 1992 zakoupila 1680 nových autobusů a v roce 2002 dalších 17 z Miskolce. Odlišný design, opuštění střešního ohybu nebo otevírací okna, která byla v té době obrovská, významně přispěly k jeho úspěchu a skutečnosti, že autobus byl schopen splnit řadu technických požadavků podle požadavků zákazníka. Práce řidičů byla usnadněna hydraulickým posilovačem řízení a automatickou převodovkou, ale byly také vyrobeny s manuálními převodovkami (hlavně v dálkových verzích), zatímco levnější servis byl výhodou pro operátory. Díky samočinnému, tedy nucenému řízení osy \"C\" je schopen otáčet se ve stejné zatáčce jako jeho také vyráběná sólová verze Ikarus 260. Trolejová verze 280T, která je také schopna několika sérií, se vyráběla převážně v Budapešti, Debrecínu, Segedínu, Bulharsku a NDR, ale prototypy vozidel se také účastnily zkušebních jízd v západní Evropě a Americe. Od roku 1986 měla smíšené autobusy, od roku 1996 autobusy na čistě stlačený zemní plyn (CNG) v Hajdú a Tisze Volán. V roce 1999 Ikarus vyzkoušel také repasovanou verzi řady 200 a v rámci rodiny Classic byl postaven nástupce modelu 280, Ikarus C80, z nichž 72 vyrobily a koupily především maďarské firmy Volán. Rodinu 200 si v roce 1966 vysnili hlavní architekt György Bálint, vedoucí designéři Károly Oszetzky, Jenő Mádi a József Varga Papp a László Finta, designér Ikarus 180, 556 a 557. Mezi vlastnosti série patří samonosné tělo a jedinečný design. Ve srovnání s autobusy té doby bylo opuštění střešního ohybu a obdélníkové skříně průřezu novinkou, takže horní okraj oken byl zvýšen vysoko, což upřednostňovalo stojící cestující: hovorový jazyk často označoval vozidla řady 200 jako \"panoramatické autobusy\". Rychlou výměnu cestujících zajišťovaly dvojdveřové dveře se šířkou otevření 1300 mm a řidiči pomáhala hydromechanická automatická převodovka typu Praga a hydraulický posilovač řízení. Později továrna také používala převodovky Csepel, ZF Ecomat a Voith a dodávala autobusy s manuální převodovkou v závislosti na objednávce. Autobus byl zpočátku postaven s rába-MAN D2156 a poté šestiválcovými sacími a turbodieselovými motory Rába D10, ale díky samonosné karoserii byly schopny splnit téměř všechny speciální požadavky, takže autobusy byly postaveny s motory MAN, Daimler, Cummins, Renault a DAF, v závislosti na požadavku kupující země. Vzhledem k tomu, že v průběhu let bylo vyrobeno nespočet modelových variant, došlo kromě motoru k dalším technickým rozdílům. Hlavní výhodou kloubové verze autobusu Ikarus 260, 16,5 metru dlouhého Ikarus 280, je to, že má stejný průměr poloměru otáčení jako sólová verze, protože vozidlo je navrženo s pohonem osy B (konstrukce tažného kloubu), zatímco kola hřídele \"C\" jsou nuceným řízením. To mu umožňuje umístit se do míst, kde se ostatní kloubová vozidla již nemohou otáčet. Sériová výroba začala v roce 1973 a trvala až do roku 2002. Podobně jako sólová verze měly podtypy určené pro Maďarsko až do roku 1976 jednodílnou, vyšší přístrojovou desku a na přední stěny umístily nápis I K A R U S, který byl později nahrazen nápisem \"cord-writing\" Ikarus, který lze vysledovat až k dohodě se srbskou továrnou Ikarus (Икарус) (dnešní Ikarbus). Další charakteristikou autobusů bylo typové číslo 280 na mřížce, které bylo často vnímáno jako Z80, ale provozovatelé někdy také zobrazovali své autobusy tímto způsobem. Od roku 1975 jsou vozidla vyráběna s 1/2 dílnými posuvnými okny oproti předchozím 1/4 dílným posuvným oknům. Dalším prvkem na bezdveřové straně je poslední nerozdělené okno, které se od roku 1980 vyrábělo stejně otevíratelné jako ostatní okna. Až do roku 1984 byla charakteristická také trilexová kola a akordeonové dveře (často vrásčité dveře v hovorovém jazyce). Na počátku roku 1990, částečně díky zániku Sovětského svazu a NDR, největšího zákazníka, se objednávky Ikarus snížily, zatímco náklady vzrostly. Vzhledem k inflaci ruského rublu maďarská vláda nepovolila hotové autobusy do nástupnických států Sovětského svazu, v důsledku čehož byly v továrním dvoře seřazeny stovky hotových vozidel. Kvůli nedostatku příjmů nebo významnému zpoždění příjmů byl Icarus v roce 1990 prohlášen za insolventní. K poklesu přispěla i skutečnost, že Ikarus začal vyvíjet řadu 400 jako nástupce řady 200 pozdě, takže většina zákazníků začala nakupovat z jiných autobusových továren a zbývající zákazníci (včetně BKV) upřednostňovali spolehlivější řadu 200. V roce 1998 továrna vyzkoušela rodinu autobusů Ikarus Classic, ale nakonec bylo vyrobeno pouze 72 repasovaných verzí 280 (nazývaných Ikarus C80), které většinou koupilo jen několik maďarských společností Volán. Česká a slovenská města ráda kupovala kloubové Ikarusy, což bylo dáno především tím, že jejich domácí továrny ještě nebyly schopny vyrábět kloubové autobusy. Autobusy jezdily hlavně ve větších městech, ale i menší osady měly několik kusů a vyskytovaly se i v meziměstské dopravě. V místním provozu byl pro meziměstský provoz typický podtyp 280.08 s 1/4 částečně posunutelným oknem a 4 akordeonovými dveřmi a 280.10 akordeonovými dveřmi. Odstranění modelu Ikarus souvisí s výskytem kloubového autobusu Karosa B 741 v roce 1991. V roce 1972 pořídil první pražskou 280, která dorazila do hlavního města na zkoušky a kvůli slabému motoru neudělala dobrý dojem. V roce 1976 však byla požadována další zkušební kopie, protože nárůst osobní dopravy ospravedlňoval použití kloubových autobusů. Nová zkouška byla již úspěšná, a tak v roce 1977 objednal Pražský dopravní podnik (DPP) 20 280,08 vozů, které byly na trhu již v lednu 1978. V následujících letech přicházely nové autobusy, do roku 1985 bylo v Praze 315 skladových vozů 280. Poslední nákup nových autobusů proběhl v únoru 1991, ale v roce 1992 bylo z Pardubic zakoupeno 19 kusů z druhé ruky. Posledních 280 v oběhu bylo sešrotováno v roce 2001. Ikarus č. p. 4382 se zachoval pro potomstvo, které lze nalézt převážně v Muzeu mhd v Praze a při některých příležitostech i na ulici jako nostalgickou trasu. V Brně, to je jedno z nejmenších měst na Moravě a v Čechách, proto se mu také někdy přezdívá například skanzen, bylo dodáno 106 280,08 a 10 280,12. Autobusy přijely v letech 1980 až 1989, poslední byl sešrotován v roce 2001 a několik exemplářů bylo prodáno do Bulharska, Ruska, Maďarska a na Slovensko. Autobus č. 2090 je ve vlastnictví Technického muzea a slouží nostalgickým účelům. V letech 1977 až 1990 bylo v Ostravě uvedeno na trh 102 kloubových Ikarusů. Na přelomu tisíciletí žádný z nich neběžel, ale zachovali si číslo 280.10 číslo 4070, se kterým se lze dodnes setkat při nostalgických výletech. V České republice byl poslední Ikarus 280, který byl provozován v Táboře, vyřazen v roce 2007. Poslední vyrobený exemplář byl odeslán do Tádžikistánu a poslední z maďarských exemplářů, IAA-833, patřil do populace Kisalföld Volán.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1973 – 2002\n" +
                    "Délka: 16 500 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 75 km/h\n" +
                    "Míst k sezení: 36\n" +
                    "Míst k stání: 111"
        ),
        TypBusu(
            "Ikarus 281", DIESELOVY, AUTOBUS, IKARUS,
            133, 75, 110.0, 145_000, 16.5F, 200, "Ikarus 281 je kloubový autobus vyvinutý společností Ikarus Body and Vehicle Factory, která byla pravostranně řízenou verzí modelu Ikarus 280. Ikarus dodal v roce 1974 do Tanzanie 32 autobusů Ikarus 280.99, jejichž kokpit byl stále vlevo. Prototyp 281 byl představen v roce 1978 a do roku 1993 bylo vyrobeno celkem 165 kusů, které zakoupil Mosambik, Tanzanie a Indonésie, a výstavní kus byl také na Novém Zélandu. Vzhledem k umístění motoru nebyl žádný z 281 vyráběn ve čtyřdveřové verzi.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1978 – 1993\n" +
                    "Délka: 16 500 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 75 km/h\n" +
                    "Míst k sezení: 49\n" +
                    "Míst k stání: 84"
        ),
        TypBusu(
            "Ikarus 282", DIESELOVY, AUTOBUS, IKARUS,
            152, 75, 110.0, 150_000, 18F, 200, "Ikarus 282 je jedním z kloubových autobusů továrny Ikarus Body and Vehicle Factory, kterých bylo vyrobeno pouze 11. Autobus byl o jeden a půl metru delší než Ikarus 280 a měl ještě jedno okno na zadní části zápěstí. V BKV se inscenace natáčela v Budapešti, stejně jako v barvách Volánbusz a Szabolcs Volán. V roce 1979 se 282 zúčastnil také veletrh v Poznani. Sólová verze je Ikarus 262.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1976 – 1978\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 75 km/h\n" +
                    "Míst k sezení: 49\n" +
                    "Míst k stání: 103"
        ),
        TypBusu(
            "Ikarus 283", DIESELOVY, AUTOBUS, IKARUS,
            205, 75, 110.0, 155_000, 18F, 190, "Ikarus 283 je jedním z kloubových autobusů karoserie a automobilky Ikarus, kterých bylo vyrobeno 1003. Motor 18metrové sběrnice, stejně jako základní typ Ikarus 280, je umístěn mezi nápravou \"A\" a osou \"B\", osa \"B\" je poháněna, ale osa \"C\" není nuceným řízením, takže průměr poloměru otáčení je větší. Zadní dvojitá kola kompenzují vyšší zatížení nápravy. Druhé dveře většiny vyráběných autobusů jsou díky prostorově náročnějšímu motoru MAN před obvyklými. Sólová verze je Ikarus 263 a pravá verze je Ikarus 285.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1988 – 1997\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 75 km/h\n" +
                    "Míst k sezení: 45\n" +
                    "Míst k stání: 160"
        ),
        TypBusu(
            "Ikarus 293", DIESELOVY, AUTOBUS, IKARUS,
            229, 75, 100.0, 250_000, 22.68F, 300, "Ikarus 293 je speciální autobus Ikarus, který se pyšnil titulem \"nejdelší maďarský autobus\": má dvojitou kloubovou konstrukci, tj. Skládá se ze tří členů, takže má celkovou délku 24 m. Model 293 odpovídal plus jednočlenné verzi tradičních městských kloubových autobusů řady 200, která byla vyrobena v roce 1988, ale nakonec byl vyroben pouze jeden z těchto modelů, protože konstrukce s takovými rozměry nefungovala dokonale. Během zkušební jízdy si řidiči stěžovali na pomalost, výkyvy, silné zrychlení a zatáčení vozidla. Po účasti na mnoha výstavách v Maďarsku i v zahraničí byl autobus odvezen do areálu Ikarus v Matthiaslandu. V roce 1992 jej koupila teheránská společnost, která změnila jeho interiér, nahradila motor motorem MAN o objemu 280 lei a přelakovala jej. Vzhledem ke zvýšeným prostorovým nárokům nového motoru byly dopředu posunuty i druhé dveře pro cestující. Autobus pak jel \"na vlastní nohy\", tedy na vlastních kolech, do Teheránu, kde sloužil až do roku 2009. Fotografie objevené na konci roku 2018 ukazují, že autobus je stále neporušený a stojí v garáži teheránské dopravní společnosti.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1988\n" +
                    "Délka: 22 680 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 75 km/h\n" +
                    "Míst k sezení: 69\n" +
                    "Míst k stání: 160"
        ),
        TypBusu(
            "Ikarus 405", DIESELOVY, AUTOBUS, IKARUS,
            46, 76, 100.0, 90_000, 7.3F, 180, "Ikarus 405 je příměstsko-městský midibusový typ továrny na autobusy Ikarus v Budapešti. Byl vyvinut v roce 1995. Původně byl vybaven vznětovým motorem Perkins Phaser 135T Euro 1. Může přepravit 46 cestujících a má 16 míst k sezení.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1995\n" +
                    "Délka: 7 300 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 76 km/h\n" +
                    "Míst k sezení: 16\n" +
                    "Míst k stání: 30"
        ),
        TypBusu(
            "Ikarus 412", DIESELOVY, AUTOBUS, IKARUS,
            62, 75, 100.0, 110_000, 12F, 180, "Ikarus 412 je příměstsko-městský nízkopodlažní sólový autobus továrny Ikarus Body and Vehicle Factory. Delší verze Ikarus 411. V roce 1995 byl představen prototyp, který byl vyroben pro německý export, s motorem Mercedes-Benz OM 447 E2 s divadelní podlahovou verzí. Poprvé se objevil v Maďarsku v Győru v roce 1996, který byl vyroben s motorem Rába D10 Euro 2. Proslavil se svým vystoupením v Budapešti (1999). To může přepravit asi 60 cestujících.\n" +
                "\n" +
                "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                "Rok výroby: 1995\n" +
                "Délka: 12 000 mm\n" +
                "Šířka: 2 500 mm\n" +
                "Maximální rychlost: 75 km/h\n" +
                "Míst k sezení: 23\n" +
                "Míst k stání: 39"
        ),
        TypBusu(
            "Ikarus 415", DIESELOVY, AUTOBUS, IKARUS,
            101, 82, 99.0, 115_000, 11.6F, 170, "Ikarus 415 je příměstsko-městský standardní podlahový sólový autobus továrny na autobusy Ikarus. Byl vyroben ve třech sériích.\n" +
                "\n" +
                "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                "Rok výroby: 1987 – 2002\n" +
                "Délka: 11 600 mm\n" +
                "Šířka: 2 500 mm\n" +
                "Maximální rychlost: 82 km/h\n" +
                "Míst k sezení: 23\n" +
                "Míst k stání: 78"
        ),
        TypBusu(
            "Ikarus 417", DIESELOVY, AUTOBUS, IKARUS,
            168, 90, 98.0, 155_000, 17.63F, 220, "Ikarus 417 je příměstsko-městský nízkopodlažní (bez schodišťový) posuvný autobus továrny na autobusy Ikarus v Budapešti. S tímto designem byl také průkopníkem ve světě. Byl představen v roce 1994.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1995 – 2002\n" +
                    "Délka: 17 630 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 90 km/h\n" +
                    "Míst k sezení: 31\n" +
                    "Míst k stání: 137"
        ),
        TypBusu(
            "Ikarus 435", DIESELOVY, AUTOBUS, IKARUS,
            111, 75, 99.0, 150_000, 17.85F, 200, "Ikarus 435 je příměstsko-městský posuvný autobus továrny Ikarus Body and Vehicle Factory. První série byla vyrobena v roce 1985 a druhá v roce 1994 s motorem DAF LT 195 Euro 1. Prototyp vozidla sloužil místní dopravě Salgótarján až do roku 2018.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1985 – 2002\n" +
                    "Délka: 17 850 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 75 km/h\n" +
                    "Míst k sezení: 31\n" +
                    "Míst k stání: 80"
        ),
        TypBusu(
            "Ikarus 436", DIESELOVY, AUTOBUS, IKARUS,
            97, 75, 96.0, 160_000, 18.034F, 195, "Ikarus 436 je městský kloubový autobus továrny Ikarus Body and Vehicle Factory pro Spojené státy americké. Byl založen na Ikarus 435, kromě toho, že americká verze je o 10 palců širší. Jeho sólová verze je Ikarus 416, založená na Ikarus 415.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1989 – 2013\n" +
                    "Délka: 18 034 mm\n" +
                    "Šířka: 2 600 mm\n" +
                    "Maximální rychlost: 75 km/h\n" +
                    "Míst k sezení: 31\n" +
                    "Míst k stání: 66"
        ),
        TypBusu(
            "Ikarus 480", DIESELOVY, AUTOBUS, IKARUS,
            85, 75, 99.0, 160_000, 11.71F, 204, "Ikarus 480 je příměstsko-městský sólový autobus továrny Ikarus Body and Vehicle Factory. Vývoj byl zahájen v roce 1988 ve spolupráci se společností DAF. S použitím podvozku DAF SB 220, který šel do výroby v roce 1989, byl prototyp první verze dokončen v tomto roce a oficiálně schválen v Anglii.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1990 – 1997\n" +
                    "Délka: 11 710 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 75 km/h\n" +
                    "Míst k sezení: 32\n" +
                    "Míst k stání: 53"
        ),
        TypBusu(
            "Ikarus 489 Polaris", DIESELOVY, AUTOBUS, IKARUS,
            69, 75, 90.0, 100_000, 12F, 254, "Ikarus 489 Polaris je jedním z typů podvozkových autobusů jedinečné tovární jednotky karoserie a výroby vozidel Ikarus.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 2000 – 2001\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 75 km/h\n" +
                    "Míst k sezení: 44\n" +
                    "Míst k stání: 25"
        ),
        TypBusu(
            "Ikarus 521", DIESELOVY, AUTOBUS, IKARUS,
            31, 60, 500.0, 40_000, 6.84F, 50, "Ikarus 521 je typ městského midibusu postaveného na podvozku nákladního vozidla Volkswagen LT 55 od společnosti Ikarus Body and Vehicle Factory. V roce 1989 BKV zakoupila 16 modelů, které obdržely poznávací značky BY-62-01 - BY-62-16. S modelem bylo kvůli jeho konstrukci mnoho problémů, protože nedostatek místa způsobil, že autobusy byly neustále přeplněné, možná je to tim, že je to hnus. Pravá řada sedadel se otáčela směrem do chodby a jednodveřová konstrukce také ztěžovala tok cestujících. Kromě cestujících nebyli řidiči spokojeni ani s typem: manuální převodovka ztěžovala řízení autobusů a kabiny řidiče nebyly izolovány od prostoru pro cestující, takže řidiči byli v zimě chladní. Na lince Várbusz jezdily pouze pět let a v roce 1995 byly nově vyvinuté Ikarus 405 nahrazeny BKV.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1987 – 1990\n" +
                    "Délka: 6 840 mm\n" +
                    "Šířka: 2 090 mm\n" +
                    "Maximální rychlost: 60 km/h\n" +
                    "Míst k sezení: 13\n" +
                    "Míst k stání: 18"
        ),
        TypBusu(
            "Ikarus V127", DIESELOVY, AUTOBUS, IKARUS,
            69, 75, 60.0, 130_000, 12.7F, 234, "Ikarus V127 (spory v roce 2015). Od té doby jsou městské autobusy Mabi-bus Modulo M108d vyráběny v experimentální spolupráci PKD (částečně sražená souprava) v prostorách budapešťské Transport Zrt. a na výrobní lince mabi-bus (později Ikarus Unique).\n" +
                    "\n" +
                    "Výrobce: Ikarus, Mabi-BKV\n" +
                    "Rok výroby: od roku 2013\n" +
                    "Délka: 12 700 mm\n" +
                    "Šířka: 2 090 mm\n" +
                    "Maximální rychlost: 75 km/h\n" +
                    "Míst k sezení: 29\n" +
                    "Míst k stání: 40"
        ),
        TypBusu(
            "Ikarus V187", DIESELOVY, AUTOBUS, IKARUS,
            176, 75, 60.0, 160_000, 18.75F, 234, "V roce 2016 byl dokončen první Ikarus v187 také přezdívaný Modulo M168d, který je v podstatě modernizovanou a novou verzí verze 2010. Mají novou přední a zadní stěnu. BKV jej testovala na autobusové lince 5 od dubna do června 2016.\n" +
                    "\n" +
                    "Výrobce: Ikarus, Mabi-BKV\n" +
                    "Rok výroby: od roku 2016\n" +
                    "Délka: 18 750 mm\n" +
                    "Šířka: 2 090 mm\n" +
                    "Maximální rychlost: 75 km/h\n" +
                    "Míst k sezení: 43\n" +
                    "Míst k stání: 133"
        ),
        TypBusu(
            "Volvo 7900 LAH", DIESELOVY, AUTOBUS, VOLVO,
            138, 80, 62.0, 225_000, 18.135F, 178, "Volvo 7900 LAH je hybridní kloubový autobus, vyráběný švédskou společností Volvo v polském závodě ve Vratislavi. Volvo 7900 LAH je původně vyvinuto z modelu Volvo 7900.\n" +
                    "\n" +
                    "Výrobce: Volvo\n" +
                    "Rok výroby: od roku 2013\n" +
                    "Délka: 18 135 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 14 400 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 46\n" +
                    "Míst k stání: 92"
        ),
        TypBusu(
            "Solaris Urbino 8,6 III", DIESELOVY, AUTOBUS, SOLARIS,
            55, 80, 78.0, 80_000, 8.6F, 196, "Solaris Urbino 8,6 (také Solaris Alpino nebo Solaris Alpino 8,6) je nízkopodlažní model městského midibusu vyráběný v letech 2006 až 2018 polskou společností Solaris Bus & Coach SA v Bolechowo-Osiedle u Poznaně. Tento model byl nejkratší v rodině Solaris Urbino a byl navržen pro provoz na tratích s malým tokem cestujících procházejícími úzkými horskými silnicemi nebo úzkými ulicemi na sídlištích nebo v centrech měst. Od samého počátku byl nabízen jako třetí generace autobusů Solaris Urbino a jeho výroba skončila stažením autobusů této generace z nabídky výrobce. Na jejím základě vznikly nízkopodlažní elektrické modely Urbino 8.9 LE a Urbino 8.9 LE.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2006 – 2018\n" +
                    "Délka: 8 600 mm\n" +
                    "Šířka: 2 400 mm\n" +
                    "Hmotnost: 9 000 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 15\n" +
                    "Míst k stání: 40"
        ),
        TypBusu(
            "Solaris Urbino 8,9 III LE", DIESELOVY, AUTOBUS, SOLARIS,
            55, 80, 78.0, 90_000, 8.95F, 196, "Solaris Urbino 8,9 LE (také Solaris Alpino 8,9 LE) je nízkopodlažní autobus střední třídy pro meziměstskou komunikaci, varianta nízkopodlažního autobusu Solaris Alpino. Od roku 2008 jej vyrábí polská společnost Solaris Bus & Coach SA z Bolechowa u Poznaně.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2008 – 2018\n" +
                    "Délka: 8 950 mm\n" +
                    "Šířka: 2 400 mm\n" +
                    "Hmotnost: 9 500 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 27\n" +
                    "Míst k stání: 28"
        ),
        TypBusu(
            "Solaris Urbino 9 I", DIESELOVY, AUTOBUS, SOLARIS,
            79, 80, 89.0, 60_000, 9.35F, 169, "Solaris Urbino 9 je nízkopodlažní autobus z řady Solaris Urbino určený pro městskou hromadnou dopravu.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2000 – 2002\n" +
                    "Délka: 9 350 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 8 400 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 21\n" +
                    "Míst k stání: 58"
        ),
        TypBusu(
            "Solaris Urbino 10 II", DIESELOVY, AUTOBUS, SOLARIS,
            91, 80, 78.0, 70_000, 9.94F, 196, "Solaris Urbino 10 II je nízkopodlažní autobus z řady Solaris Urbino, určený pro městskou hromadnou dopravu, vyráběný v letech 2002–2018 polskou společností Solaris Bus & Coach SA z Bolechowa u Poznaně.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2002 – 2005\n" +
                    "Délka: 9 940 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 9 300 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 22\n" +
                    "Míst k stání: 69"
        ),
        TypBusu(
            "Solaris Urbino 10 III", DIESELOVY, AUTOBUS, SOLARIS,
            91, 80, 69.0, 80_000, 9.94F, 196, "Solaris Urbino 10 III je nízkopodlažní autobus z řady Solaris Urbino, určený pro městskou hromadnou dopravu, vyráběný v letech 2002–2018 polskou společností Solaris Bus & Coach SA z Bolechowa u Poznaně.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2005 – 2018\n" +
                    "Délka: 9 940 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 9 300 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 22\n" +
                    "Míst k stání: 69"
        ),
        TypBusu(
            "Solaris Urbino 10 III CNG", ZEMEPLYNOVY, AUTOBUS, SOLARIS,
            91, 80, 68.0, 95_000, 9.94F, 196, "Solaris Urbino 10 III CNG je nízkopodlažní autobus z řady Solaris Urbino, určený pro městskou hromadnou dopravu, vyráběný v letech 2002–2018 polskou společností Solaris Bus & Coach SA z Bolechowa u Poznaně.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2005 – 2018\n" +
                    "Délka: 9 940 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 9 300 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 22\n" +
                    "Míst k stání: 69"
        ),
        TypBusu(
            "Solaris Urbino 10,5 IV", DIESELOVY, AUTOBUS, SOLARIS,
            89, 80, 60.0, 90_000, 10.55F, 225, "Solaris Urbino 10,5 je nízkopodlažní městský autobus, vyráběný od roku 2017 společností Solaris Bus & Coach v Bolechowo-Osiedle u Poznaně. Patří do rodiny městských autobusů Solaris Urbino. Jde o nástupce staženého modelu Solaris Urbino 10.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: od roku 2017\n" +
                    "Délka: 10 550 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 9 300 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 29\n" +
                    "Míst k stání: 60"
        ),
        TypBusu(
            "Solaris Urbino 12 I", DIESELOVY, AUTOBUS, SOLARIS,
            104, 80, 89.0, 80_000, 12F, 169, "Solaris Urbino 12 I byl poprvé představen na mezinárodním veletrhu v Poznani. Prototyp se mírně lišil od následných sériových modelů, byl vybaven mj obě zrcátka stažena dopředu (což je běžnější u autobusů než u městských autobusů). Některé prvky exteriéru měly oproti sériovým modelům mírně odlišný tvar.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 1999 – 2001\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 10 400 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 43\n" +
                    "Míst k stání: 61"
        ),
        TypBusu(
            "Solaris Urbino 12 II", DIESELOVY, AUTOBUS, SOLARIS,
            104, 80, 79.0, 90_000, 12F, 196, "Solaris Urbino 12 je nízkopodlažní městský autobus ze série Solaris Urbino vyráběný od roku 1999 polskou společností Solaris Bus & Coach v Bolechów-Osiedle u Poznaně. Druhá generace Solarisu Urbino 12 byla uvedena na trh na přelomu let 2001/2002 a vyráběla se až do roku 2006. Jednalo se o vylepšenou verzi prvního dílu, ale ve srovnání s předchozí generací nezavedla zásadní technické změny.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2002 – 2006\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 10 400 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 43\n" +
                    "Míst k stání: 61"
        ),
        TypBusu(
            "Solaris Urbino 12 III", DIESELOVY, AUTOBUS, SOLARIS,
            104, 80, 68.0, 100_000, 12F, 225, "V roce 2005 na mezinárodním veletrhu v Poznani představil Solaris Bus & Coach novou verzi městských autobusů Urbino 12. Třetí generace se oproti svým předchůdcům vyznačovala mnoha změnami. Změnil se vnější design, byly zavedeny ostřejší linie a hrany. Byl modernizován tvar předních a zadních světel, byly představeny nové přední a zadní stěny. Upustilo se i od zaoblené hrany střechy (díky které je možné namontovat displej na bok autobusu nad okna). Nový design byl představen také uvnitř a zcela změněna byla vnitřní dispozice. Palivová nádrž byla umístěna na podlaze za podběhem předního kola (podobně jako u první generace).\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2005 – 2017\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 10 400 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 43\n" +
                    "Míst k stání: 61"
        ),
        TypBusu(
            "Solaris Urbino 12 IV", DIESELOVY, AUTOBUS, SOLARIS,
            105, 80, 64.0, 110_000, 12F, 250, "Solaris Urbino 12 IV je nízkopodlažní městský autobus ze série Solaris Urbino vyráběný od roku 1999 polskou společností Solaris Bus & Coach v Bolechów-Osiedle u Poznaně. V roce 2014 byla během mezinárodního veletrhu IAA v Hannoveru 24. září představena čtvrtá generace modelu. Polský výrobce představil prototyp Solaris Urbino 12 a Urbino 18 ve verzi s konvenčním pohonem. Bylo oznámeno, že v průběhu let budou představeny další modely v nové verzi. Nové autobusy mají ve srovnání s předchozí generací odolnější, lehčí a rafinovanější design a další funkce. Byla také snížena hladina hluku a vibrace v celém vozidle. V roce 2016 byla představena nová, čtvrtá generace modelu Urbino 12 CNG. Byl založen na řešeních známých z předchozí generace.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: od roku 2014\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 10 400 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 40\n" +
                    "Míst k stání: 65"
        ),
        TypBusu(
            "Solaris Urbino 12 II LE", DIESELOVY, AUTOBUS, SOLARIS,
            104, 80, 79.0, 100_000, 12F, 196, "Solaris Urbino 12 II LE je zvýšená verze nízkopodlažního městského autobusu ze série Solaris Urbino vyráběného od roku 1999 polskou společností Solaris Bus & Coach v Bolechów-Osiedle u Poznaně. Druhá generace Solarisu Urbino 12 byla uvedena na trh na přelomu let 2001/2002 a vyráběla se až do roku 2006. Jednalo se o vylepšenou verzi prvního dílu, ale ve srovnání s předchozí generací nezavedla zásadní technické změny.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2004 – 2006\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 10 400 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 43\n" +
                    "Míst k stání: 61"
        ),
        TypBusu(
            "Solaris Urbino 12 III LE", DIESELOVY, AUTOBUS, SOLARIS,
            104, 80, 68.0, 110_000, 12F, 225, "V roce 2005 na mezinárodním veletrhu v Poznani představil Solaris Bus & Coach novou verzi městských autobusů Urbino 12. Třetí generace se oproti svým předchůdcům vyznačovala mnoha změnami. Změnil se vnější design, byly zavedeny ostřejší linie a hrany. Byl modernizován tvar předních a zadních světel, byly představeny nové přední a zadní stěny. Upustilo se i od zaoblené hrany střechy (díky které je možné namontovat displej na bok autobusu nad okna). Nový design byl představen také uvnitř a zcela změněna byla vnitřní dispozice. Palivová nádrž byla umístěna na podlaze za podběhem předního kola (podobně jako u první generace). Solaris Urbino 12 III LE (anglicky low-entry) je zvýšená verze popisovaného autobusu Solaris Urbino 12 III\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2006 – 2017\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 10 400 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 43\n" +
                    "Míst k stání: 61"
        ),
        TypBusu(
            "Solaris Urbino 12 IV LE", DIESELOVY, AUTOBUS, SOLARIS,
            105, 80, 64.0, 120_000, 12F, 250, "Solaris Urbino 12 IV je nízkopodlažní městský autobus ze série Solaris Urbino vyráběný od roku 1999 polskou společností Solaris Bus & Coach v Bolechów-Osiedle u Poznaně. V roce 2014 byla během mezinárodního veletrhu IAA v Hannoveru 24. září představena čtvrtá generace modelu. Polský výrobce představil prototyp Solaris Urbino 12 a Urbino 18 ve verzi s konvenčním pohonem. Bylo oznámeno, že v průběhu let budou představeny další modely v nové verzi. Nové autobusy mají ve srovnání s předchozí generací odolnější, lehčí a rafinovanější design a další funkce. Byla také snížena hladina hluku a vibrace v celém vozidle. V roce 2016 byla představena nová, čtvrtá generace modelu Urbino 12 CNG. Byl založen na řešeních známých z předchozí generace. Podtyp Solaris Urbino 12 IV LE je zvýšená verze vzorového autobusu Solaris Urbino 12 IV.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: od roku 2014\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 10 400 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 40\n" +
                    "Míst k stání: 65"
        ),
        TypBusu(
            "Solaris Urbino 12 IV LE lite", DIESELOVY, AUTOBUS, SOLARIS,
            105, 80, 50.0, 190_000, 12F, 250, "Solaris Urbino 12 IV je nízkopodlažní městský autobus ze série Solaris Urbino vyráběný od roku 1999 polskou společností Solaris Bus & Coach v Bolechów-Osiedle u Poznaně. V roce 2014 byla během mezinárodního veletrhu IAA v Hannoveru 24. září představena čtvrtá generace modelu. Polský výrobce představil prototyp Solaris Urbino 12 a Urbino 18 ve verzi s konvenčním pohonem. Bylo oznámeno, že v průběhu let budou představeny další modely v nové verzi. Nové autobusy mají ve srovnání s předchozí generací odolnější, lehčí a rafinovanější design a další funkce. Byla také snížena hladina hluku a vibrace v celém vozidle. V roce 2016 byla představena nová, čtvrtá generace modelu Urbino 12 CNG. Byl založen na řešeních známých z předchozí generace. Podtyp Solaris Urbino 12 IV LE lite je zvýšená verze vzorového autobusu Solaris Urbino 12 IV upravená pro co nejnižší spotřebu paliva.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: od roku 2018\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 9 000 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 40\n" +
                    "Míst k stání: 65"
        ),
        TypBusu(
            "Solaris Urbino 12 III Ü", DIESELOVY, AUTOBUS, SOLARIS,
            105, 80, 68.0, 105_000, 12F, 250, "Dojíždějící model Solaris Urbino 12 Ü (z němčiny Überland - předměstský, regionální) je prostředníkem mezi typickými městskými a meziměstskými autobusy, který debutoval na IAA Nutzfahrzeuge v Hannoveru v září 2012. Na rozdíl od modelů Low Entry je téměř zcela nízkopodlažní. Výroba však skončila pouze u jednoho exempláře, který provozuje PKS Poznań.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2012\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 10 400 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 40\n" +
                    "Míst k stání: 65"
        ),
        TypBusu(
            "Solaris Urbino 12 III CNG", ZEMEPLYNOVY, AUTOBUS, SOLARIS,
            105, 80, 66.0, 120_000, 12F, 250, "S uvedením třetí generace autobusů Solaris Urbino 12 v roce 2005 byl představen model Solaris Urbino 12 CNG poháněný stlačeným zemním plynem nebo bioplynem.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2005 – 2016\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 10 400 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 40\n" +
                    "Míst k stání: 65"
        ),
        TypBusu(
            "Solaris Urbino 12 IV CNG", ZEMEPLYNOVY, AUTOBUS, SOLARIS,
            105, 80, 61.0, 140_000, 12F, 250, "Solaris Urbino 12 IV CNG je nízkopodlažní městský autobus ze série Solaris Urbino vyráběný od roku 1999 polskou společností Solaris Bus & Coach v Bolechów-Osiedle u Poznaně. V roce 2014 byla během mezinárodního veletrhu IAA v Hannoveru 24. září představena čtvrtá generace modelu. Polský výrobce představil prototyp Solaris Urbino 12 a Urbino 18 ve verzi s konvenčním pohonem. Bylo oznámeno, že v průběhu let budou představeny další modely v nové verzi. Nové autobusy mají ve srovnání s předchozí generací odolnější, lehčí a rafinovanější design a další funkce. Byla také snížena hladina hluku a vibrace v celém vozidle. V roce 2016 byla představena nová, čtvrtá generace modelu Urbino 12 CNG. Byl založen na řešeních známých z předchozí generace.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: od roku 2016\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 10 400 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 40\n" +
                    "Míst k stání: 65"
        ),
        TypBusu(
            "Solaris Urbino 12 III Hybrid", HYBRIDNI, AUTOBUS, SOLARIS,
            105, 80, 66.0, 120_000, 12F, 250, "Solaris byl prvním výrobcem autobusů na světě, který v roce 2006 představil sériový hybridní autobus. Jednalo se o kloubový hybridní model Urbino 18. V druhé polovině roku 2009 vznikl prototyp 12metrového modelu Solaris Urbino 12 Hybrid na základě třetí generace Urbino 12. Debutoval na veletrhu Busworld v Kortrijku v říjnu 2009. Sériová výroba začala v roce 2010.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2010 – 2016\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 10 400 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 40\n" +
                    "Míst k stání: 65"
        ),
        TypBusu(
            "Solaris Urbino 12 IV hybrid", HYBRIDNI, AUTOBUS, SOLARIS,
            105, 80, 61.0, 140_000, 12F, 250, "Solaris byl prvním výrobcem autobusů na světě, který v roce 2006 představil sériový hybridní autobus. Jednalo se o kloubový hybridní model Urbino 18. V druhé polovině roku 2009 vznikl prototyp 12metrového modelu Solaris Urbino 12 Hybrid na základě třetí generace Urbino 12. Debutoval na veletrhu Busworld v Kortrijku v říjnu 2009. Sériová výroba začala v roce 2010.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: od roku 2016\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 10 400 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 40\n" +
                    "Míst k stání: 65"
        ),
        TypBusu(
            "Solaris Urbino 12 IV mild hybrid", HYBRIDNI, AUTOBUS, SOLARIS,
            105, 80, 50.0, 220_000, 12F, 250, "V roce 2020 byl do nabídky výrobce uveden nový mild hybridní model Urbino 12, který je vývojem technologie rekuperace energie z brzdění. Je vybaveno elektrickou pohonnou jednotkou s výkonem nižším než v případě hybridního modelu Urbino 12 a systémem akumulace energie s menší kapacitou - umožňujícím ukládat energii z brzdění již od rychlosti cca 60 km/h. Díky této technologii je autobus levnější na pořízení než hybridní model Urbino 12, ale emise výfukových plynů jsou nižší ve srovnání s modelem s tradičním pohonem pouze na spalování a nižší než požadovaná emisní norma Euro 6.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: od roku 2020\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 10 400 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 40\n" +
                    "Míst k stání: 65"
        ),
        TypBusu(
            "Solaris Urbino 12 IV hydrogen", VODIKOVY, AUTOBUS, SOLARIS,
            105, 80, 50.0, 410_000, 12F, 250, "Solaris Urbino 12 hydrogen je nízkopodlažní městský autobus z rodiny Solaris Urbino, vyráběný polskou společností Solaris Bus & Coach, poháněný elektřinou generovanou vodíkovým palivovým článkem. Byl představen v roce 2019 jako vývoj modelů Urbino 12 a jeho elektrické verze Urbino 12 electric.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: od roku 2019\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 10 400 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 40\n" +
                    "Míst k stání: 65"
        ),
        TypBusu(
            "Solaris Urbino 15 I", DIESELOVY, AUTOBUS, SOLARIS,
            135, 80, 89.0, 100_000, 14.59F, 169, "Solaris Urbino 15 I je nízkopodlažní městský autobus z rodiny Solaris Urbino vyráběný v letech 1999 až 2002 polskou společností Solaris Bus & Coach v Bolechów-Osiedle u Poznaně. V roce 1999 byl představen v Lodži nový typ autobusů Solaris Urbino. Jednalo se o Solaris Urbino 15 I.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 1999 – 2002\n" +
                    "Délka: 14 590 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 12 700 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 43\n" +
                    "Míst k stání: 92"
        ),
        TypBusu(
            "Solaris Urbino 15 II", DIESELOVY, AUTOBUS, SOLARIS,
            135, 80, 79.0, 110_000, 14.59F, 196, "Solaris Urbino 15 II je nízkopodlažní městský autobus z rodiny Solaris Urbino vyráběný v letech 2002 až 2005 polskou společností Solaris Bus & Coach v Bolechów-Osiedle u Poznaně. V roce 2002 byla představena nová, druhá generace autobusů Solaris Urbino 15. Jednalo se o přechodný model mezi vozidly první a třetí generace. Konstrukce vozidla byla mírně změněna, byly zavedeny drobné změny v uspořádání interiéru a technická vylepšení.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2002 – 2005\n" +
                    "Délka: 14 590 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 12 700 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 43\n" +
                    "Míst k stání: 92"
        ),
        TypBusu(
            "Solaris Urbino 15 III", DIESELOVY, AUTOBUS, SOLARIS,
            135, 80, 68.0, 120_000, 14.59F, 225, "Solaris Urbino 15 III je nízkopodlažní městský autobus z rodiny Solaris Urbino vyráběný v letech 2005 až 2018 polskou společností Solaris Bus & Coach v Bolechów-Osiedle u Poznaně. V roce 2005 byla zahájena výroba 3. generace Urbino 15 představením nové verze autobusů Solaris Urbino. Zavedené změny byly mnohem větší než v případě předchozí generace. Změnil se design přední a zadní stěny, dostaly dynamičtější tvar. V roce 2014 Solaris představil čtvrtou generaci rodiny Urbino, ale nebyla představena žádná nová verze modelu Urbino 15. Souběžně s novou generací se vyráběly autobusy třetí generace. Začátkem roku 2018 bylo oznámeno, že od letošního roku bude ukončena výroba předchozí generace a model Urbino 15 se již nebude vyrábět z důvodu klesajícího zájmu zákazníků.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2005 – 2018\n" +
                    "Délka: 14 590 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 12 700 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 43\n" +
                    "Míst k stání: 92"
        ),
        TypBusu(
            "Solaris Urbino 15 III LE", DIESELOVY, AUTOBUS, SOLARIS,
            105, 80, 68.0, 135_000, 14.59F, 225, "Solaris Urbino 15 III LE byl poprvé veřejnosti předtaven na veletrhu Autotec v brně. Solaris Urbino 15 LE konstrukčně vychází z modelu Solari Urbino 15.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2008 – 2018\n" +
                    "Délka: 14 590 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 12 700 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 65\n" +
                    "Míst k stání: 40"
        ),
        TypBusu(
            "Solaris Urbino 15 III CNG", ZEMEPLYNOVY, AUTOBUS, SOLARIS,
            105, 80, 67.0, 140_000, 14.59F, 225, "Solaris Urbino 15 III CNG je nízkopodlažní městský autobus z rodiny Solaris Urbino vyráběný v letech 2008 až 2018 polskou společností Solaris Bus & Coach v Bolechów-Osiedle u Poznaně.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2008 – 2018\n" +
                    "Délka: 14 590 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 12 700 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 65\n" +
                    "Míst k stání: 40"
        ),
        TypBusu(
            "Solaris Urbino 15 III LE CNG", ZEMEPLYNOVY, AUTOBUS, SOLARIS,
            105, 80, 67.0, 155_000, 14.59F, 225, "Solaris Urbino 15 III LE CNG je nízkopodlažní městský autobus z rodiny Solaris Urbino vyráběný v letech 2005 až 2018 polskou společností Solaris Bus & Coach v Bolechów-Osiedle u Poznaně. Tento autobus byl vyvinut primárně pro potřeby skandinávského trhu.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2008 – 2018\n" +
                    "Délka: 14 590 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 12 700 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 65\n" +
                    "Míst k stání: 40"
        ),
        TypBusu(
            "Solaris Urbino 18 I", DIESELOVY, AUTOBUS, SOLARIS,
            138, 80, 89.0, 120_000, 18F, 169, "První generace Solaris Urbino 18 I se vyráběla od druhé poloviny roku 1999. Tento model doplnil nabídku Solaris o kloubový 18metrový autobus. První sériové vozidlo bylo dodáno do PKA Gdynia v roce 1999\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 1999 – 2002\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 15 000 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 43\n" +
                    "Míst k stání: 92"
        ),
        TypBusu(
            "Solaris Urbino 18 II", DIESELOVY, AUTOBUS, SOLARIS,
            138, 80, 79.0, 135_000, 18F, 196, "Od roku 2002 se vyráběla nová, druhá generace modelu Urbino 18. Tato generace však byla pouze přechodným řešením mezi první a plánovanou třetí generací. Bylo provedeno několik technických vylepšení a mírně se změnil design autobusů.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2002 – 2005\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 15 000 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 43\n" +
                    "Míst k stání: 92"
        ),
        TypBusu(
            "Solaris Urbino 18 III", DIESELOVY, AUTOBUS, SOLARIS,
            138, 80, 68.0, 150_000, 18F, 234, "Solaris Urbino 18 III je nízkopodlažní kloubový autobus z rodiny Solaris Urbino určený pro veřejnou dopravu, vyráběný polskou společností Solaris Bus & Coach S.A. v Bolechów-Osiedle u Poznaně. Jedná se o druhý (po Urbino 12) nejprodávanější model značky Solaris. V roce 2005 Solaris vyrobil a ukázal první kopie nové, třetí generace modelu Solaris Urbino 18. V tomto případě byl zaveden větší počet změn, stylistických i strukturálních, ve srovnání s předchozími pohledy.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2005 – 2018\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 15 000 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 44\n" +
                    "Míst k stání: 94"
        ),
        TypBusu(
            "Solaris Urbino 18 IV", DIESELOVY, AUTOBUS, SOLARIS,
            138, 80, 61.0, 165_000, 18F, 255, "Solaris Urbino 18 IV je nízkopodlažní kloubový autobus z rodiny Solaris Urbino určený pro veřejnou dopravu, vyráběný polskou společností Solaris Bus & Coach S.A. v Bolechów-Osiedle u Poznaně. Jedná se o druhý (po Urbino 12) nejprodávanější model značky Solaris. Premiéra čtvrté generace Urbina 18 se konala 24. září 2014 během veletrhu IAA Nutzfahrzeuge v Hannoveru, zatímco polská premiéra se konala v listopadu během Transexpo v Kielcích. Nové autobusy se vyznačují odolnějším, lehčím a rafinovanějším designem, a to jak technicky, tak stylisticky. Hladina hluku a intenzita vibrací ve vozidle byly sníženy.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: od roku 2014\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 15 000 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 44\n" +
                    "Míst k stání: 94"
        ),
        TypBusu(
            "Solaris Urbino 18,75", DIESELOVY, AUTOBUS, SOLARIS,
            142, 80, 70.0, 160_000, 18.75F, 234, "Solaris Urbino 18,75 je prodloužená verze osvědčeného autobusu Solaris Urbino 18 III.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2012 – 2013\n" +
                    "Délka: 18 750 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 15 000 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 48\n" +
                    "Míst k stání: 94"
        ),
        TypBusu(
            "Solaris Urbino 18 II CNG", ZEMEPLYNOVY, AUTOBUS, SOLARIS,
            138, 80, 77.0, 160_000, 18F, 196, "Od roku 2004 se vyráběla nová, druhá generace modelu Urbino 18 CNG. Tato generace však byla pouze přechodným řešením mezi první a plánovanou třetí generací. Bylo provedeno několik technických vylepšení a mírně se změnil design autobusů.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2004 – 2006\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 15 000 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 43\n" +
                    "Míst k stání: 92"
        ),
        TypBusu(
            "Solaris Urbino 18 III CNG", ZEMEPLYNOVY, AUTOBUS, SOLARIS,
            138, 80, 66.0, 175_000, 18F, 225, "Solaris Urbino 18 III CNG je nízkopodlažní kloubový autobus z rodiny Solaris Urbino určený pro veřejnou dopravu, vyráběný polskou společností Solaris Bus & Coach S.A. v Bolechów-Osiedle u Poznaně. Jedná se o druhý (po Urbino 12) nejprodávanější model značky Solaris. V roce 2005 Solaris vyrobil a ukázal první kopie nové, třetí generace modelu Solaris Urbino 18. V tomto případě byl zaveden větší počet změn, stylistických i strukturálních, ve srovnání s předchozími pohledy.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2006 – 2016\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 15 000 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 44\n" +
                    "Míst k stání: 94"
        ),
        TypBusu(
            "Solaris Urbino 18 IV CNG", ZEMEPLYNOVY, AUTOBUS, SOLARIS,
            138, 80, 60.0, 190_000, 18F, 225, "Solaris Urbino 18 IV CNG je nízkopodlažní kloubový autobus z rodiny Solaris Urbino určený pro veřejnou dopravu, vyráběný polskou společností Solaris Bus & Coach S.A. v Bolechów-Osiedle u Poznaně. Jedná se o druhý (po Urbino 12) nejprodávanější model značky Solaris. Premiéra čtvrté generace Urbina 18 se konala 24. září 2014 během veletrhu IAA Nutzfahrzeuge v Hannoveru, zatímco polská premiéra se konala v listopadu během Transexpo v Kielcích. Nové autobusy se vyznačují odolnějším, lehčím a rafinovanějším designem, a to jak technicky, tak stylisticky. Hladina hluku a intenzita vibrací ve vozidle byly sníženy. V roce 2016 byla představena nová, čtvrtá generace modelu Urbino 18 CNG. Byl založen na řešeních známých z předchozí generace.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: od roku 2016\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 15 000 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 44\n" +
                    "Míst k stání: 94"
        ),
        TypBusu(
            "Solaris Urbino 18 III Hybrid", HYBRIDNI, AUTOBUS, SOLARIS,
            138, 80, 66.0, 175_000, 18F, 234, "Solaris Urbino 18 III je nízkopodlažní kloubový autobus z rodiny Solaris Urbino určený pro veřejnou dopravu, vyráběný polskou společností Solaris Bus & Coach S.A. v Bolechów-Osiedle u Poznaně. Jedná se o druhý (po Urbino 12) nejprodávanější model značky Solaris. V roce 2005 Solaris vyrobil a ukázal první kopie nové, třetí generace modelu Solaris Urbino 18. V tomto případě byl zaveden větší počet změn, stylistických i strukturálních, ve srovnání s předchozími pohledy.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2006 – 2018\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 15 000 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 44\n" +
                    "Míst k stání: 94"
        ),
        TypBusu(
            "Solaris Urbino 18 IV hybrid", HYBRIDNI, AUTOBUS, SOLARIS,
            138, 80, 60.0, 190_000, 18F, 255, "Solaris Urbino 18 IV Hybrid je nízkopodlažní kloubový autobus z rodiny Solaris Urbino určený pro veřejnou dopravu, vyráběný polskou společností Solaris Bus & Coach S.A. v Bolechów-Osiedle u Poznaně. Jedná se o druhý (po Urbino 12) nejprodávanější model značky Solaris. Premiéra čtvrté generace Urbina 18 se konala 24. září 2014 během veletrhu IAA Nutzfahrzeuge v Hannoveru, zatímco polská premiéra se konala v listopadu během Transexpo v Kielcích. Nové autobusy se vyznačují odolnějším, lehčím a rafinovanějším designem, a to jak technicky, tak stylisticky. Hladina hluku a intenzita vibrací ve vozidle byly sníženy.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: od roku 2018\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 15 000 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 44\n" +
                    "Míst k stání: 94"
        ),
        TypBusu(
            "Solaris Urbino 18 IV plug-in hybrid", HYBRIDNI, AUTOBUS, SOLARIS,
            138, 80, 60.0, 240_000, 18F, 255, "Solaris Urbino 18 IV plug-in hybrid je nízkopodlažní kloubový autobus z rodiny Solaris Urbino určený pro veřejnou dopravu, vyráběný polskou společností Solaris Bus & Coach S.A. v Bolechów-Osiedle u Poznaně. Jedná se o druhý (po Urbino 12) nejprodávanější model značky Solaris. Premiéra čtvrté generace Urbina 18 se konala 24. září 2014 během veletrhu IAA Nutzfahrzeuge v Hannoveru, zatímco polská premiéra se konala v listopadu během Transexpo v Kielcích. Nové autobusy se vyznačují odolnějším, lehčím a rafinovanějším designem, a to jak technicky, tak stylisticky. Hladina hluku a intenzita vibrací ve vozidle byly sníženy.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: od roku 2018\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 15 000 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 44\n" +
                    "Míst k stání: 94"
        ),
        TypBusu(
            "Solaris Urbino 18 MetroStyle", DIESELOVY, AUTOBUS, SOLARIS,
            138, 80, 67.0, 180_000, 18F, 255, "V roce 2010 Solaris získal zakázku na dodávku hybridních kloubových autobusů pro francouzského dopravce Transdev pro systém rychlé dopravy v Paříži. Autobusy byly představeny v roce 2011. Získaly zcela nový design odkazující na rodinu tramvají Solaris TraminoTo je důvod, proč byl nový Urbino 18 Hybrid MetroStyle pojmenován. Nechybí čelní sklo s jednořádkovou spodní hranou a zcela nové světlomety. Šikmá přední stěna a kryty zakrývající kola autobusu dodávají dynamický vzhled. Změn doznala i zadní stěna - světla jsou umístěna svisle, zadní okno má tvar V. Autobusy Solaris Urbino 18 MetroStyle byly poprvé dodány do Krakova v roce 2014\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: od roku 2014\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 15 000 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 44\n" +
                    "Míst k stání: 94"
        ),
        TypBusu(
            "Solaris Urbino 18 Hybrid MetroStyle", HYBRIDNI, AUTOBUS, SOLARIS,
            138, 80, 65.0, 205_000, 18F, 255, "V roce 2010 Solaris získal zakázku na dodávku hybridních kloubových autobusů pro francouzského dopravce Transdev pro systém rychlé dopravy v Paříži. Autobusy byly představeny v roce 2011. Získaly zcela nový design odkazující na rodinu tramvají Solaris TraminoTo je důvod, proč byl nový Urbino 18 Hybrid MetroStyle pojmenován. Nechybí čelní sklo s jednořádkovou spodní hranou a zcela nové světlomety. Šikmá přední stěna a kryty zakrývající kola autobusu dodávají dynamický vzhled. Změn doznala i zadní stěna - světla jsou umístěna svisle, zadní okno má tvar V.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: od roku 2010\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 15 000 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 44\n" +
                    "Míst k stání: 94"
        ),
        TypBusu(
            "Iveco Crossway 13M", DIESELOVY, AUTOBUS, IVECO,
            59, 80, 77.0, 130_000, 12.962F, 188, "Iveco Crossway (dříve Irisbus Crossway) je model meziměstského linkového autobusu, který vyrábí společnost Iveco Czech Republic ve Vysokém Mýtě. Produkce Crosswayů ve třech délkových verzích o délkách 10,6 m, 12 m a 12,8 m probíhala pod původním označením Irisbus Crossway od roku 2006. V roce 2014 Iveco Bus představilo novou generaci toho autobusu pod označením Iveco Crossway.\n" +
                    "\n" +
                    "Výrobce: Iveco Bus\n" +
                    "Rok výroby: od roku 2014\n" +
                    "Délka: 12 962 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 12 000 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 23\n" +
                    "Míst k stání: 36"
        ),
        TypBusu(
            "Renault Citybus 12M", DIESELOVY, AUTOBUS, RENAULT,
            99, 70, 88.0, 90_000, 11.99F, 202, "Renault Citybus 12M je model nízkopodlažního autobusu, který vyráběla společnost Karosa Vysoké Mýto ve spolupráci s firmou Renault v rámci koncernu Irisbus. Do Česka byl vždy dovezen skelet autobusu s motorem vyrobeným ve Francii, který zde byl dokončen. Důvodem bylo rozdílné clo a tedy levnější výroba a dovoz autobusu tímto způsobem. Model byl vyráběn mezi lety 1995 a 2005.\n" +
                    "\n" +
                    "Výrobce: Karosa Vysoké Mýto\n" +
                    "Rok výroby: 1995 – 2005\n" +
                    "Délka: 11 990 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 11 380 kg\n" +
                    "Maximální rychlost: 70 km/h\n" +
                    "Míst k sezení: 30\n" +
                    "Míst k stání: 69"
        ),
        TypBusu(
            "Irisbus Citelis 12M", DIESELOVY, AUTOBUS, IRISBUS,
            105, 70, 81.0, 110_000, 11.99F, 232, "Irisbus Citelis 12M je městský nízkopodlažní autobus vyráběný v letech 2005–2013 společností Irisbus. Citelis 12M, představený 31. března 2005, je nástupcem modelu Citybus 12M (Renault Agora). Stejně jako jeho předchůdce, i Citelis 12M byl vyráběn buď ve francouzském městě Annonay nebo v italském Valle Ufita, přičemž speciální požadavky českých a slovenských zákazníků byly řešeny ve vysokomýtském závodě Iveco Czech Republic (dříve Karosa). Od poloviny roku 2010 se výroba autobusů pro střední Evropu přesunula do Vysokého Mýta. Nahrazen byl modelem Iveco Urbanway 12M.\n" +
                    "\n" +
                    "Výrobce: Irisbus\n" +
                    "Rok výroby: 2005 – 2013\n" +
                    "Délka: 11 990 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 11 200 kg\n" +
                    "Maximální rychlost: 70 km/h\n" +
                    "Míst k sezení: 40\n" +
                    "Míst k stání: 55"
        ),
        TypBusu(
            "Irisbus Crossway LE 12.8M", DIESELOVY, AUTOBUS, IRISBUS,
            69, 85, 77.0, 120_000, 12.962F, 189, "Iveco Crossway (dříve Irisbus Crossway) je model meziměstského linkového autobusu, který vyrábí společnost Iveco Czech Republic ve Vysokém Mýtě. Produkce Crosswayů ve třech délkových verzích o délkách 10,6 m, 12 m a 12,8 m probíhala pod původním označením Irisbus Crossway od roku 2006. V roce 2014 Iveco Bus představilo novou generaci toho autobusu pod označením Iveco Crossway.\n" +
                    "\n" +
                    "Výrobce: Irisbus\n" +
                    "Rok výroby: 2006 – 2014\n" +
                    "Délka: 12 962 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 12 000 kg\n" +
                    "Maximální rychlost: 85 km/h\n" +
                    "Míst k sezení: 23\n" +
                    "Míst k stání: 36"
        ),
        TypBusu(
            "Irisbus Citelis 18M", DIESELOVY, AUTOBUS, IRISBUS,
            155, 70, 81.0, 155_000, 17.8F, 200, "Irisbus Citelis 18M je městský kloubový nízkopodlažní autobus, který byl vyráběn ve francouzském městě Annonay a později v italském Valle Ufita, přičemž speciální požadavky českých a slovenských zákazníků byly řešeny ve vysokomýtském závodě Iveco Czech Republic (dříve Karosa). Jeho nástupcem je model Iveco Urbanway 18M.\n" +
                    "\n" +
                    "Výrobce: Irisbus\n" +
                    "Rok výroby: 2005 – 2014\n" +
                    "Délka: 17 800 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 17 300 kg\n" +
                    "Maximální rychlost: 70 km/h\n" +
                    "Míst k sezení: 40\n" +
                    "Míst k stání: 115"
        ),
        TypBusu(
            "Heuliez GX 137", DIESELOVY, AUTOBUS, HEULIEZ,
            70, 85, 99.0, 199_000, 9.52F, 169, "Heuliez Bus GX 137 a GX 137 L jsou nízkopodlažní malorozchodné autobusy (minibus)vyráběné a uváděné na trh francouzským výrobcem Heuliez Bus, dceřinou společností skupiny Iveco Bus,od roku 2014. K dispozici byly také standardní a kloubové verze s označením GX 337 a GX 437. Jsou součástí řady Access Bus.\n" +
                    "\n" +
                    "Výrobce: Heuliez Bus\n" +
                    "Rok výroby: 2014 - 2019\n" +
                    "Délka: 9 520 mm\n" +
                    "Šířka: 2 330 mm\n" +
                    "Hmotnost: 10 572 kg\n" +
                    "Maximální rychlost: 85 km/h\n" +
                    "Míst k sezení: 20\n" +
                    "Míst k stání: 50"
        ),
        TypBusu(
            "Iveco Stratos LF 38", DIESELOVY, AUTOBUS, IVECO,
            38, 80, 95.0, 70_000, 7.765F, 144, "SKD Stratos (dříve Iveco Stratos) je minibus, který byl vyráběn firmou SKD Trade v Dolních Bučicích na podvozku nákladního automobilu Iveco Daily. Do roku 2011 jej vyráběla v Čáslavi královéhradecká firma Stratos Auto (odtud název vozidla), jejíž autobusovou divizi zakoupila právě společnost SKD Trade. Minibus Stratos byl v roce 2017 nahrazen typem Dekstra.\n" +
                    "\n" +
                    "Výrobce: SKD\n" +
                    "Rok výroby: 2012 - 2017\n" +
                    "Délka: 7 765 mm\n" +
                    "Šířka: 2 300 mm\n" +
                    "Hmotnost: 7 000 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 14\n" +
                    "Míst k stání: 24"
        )
    )

    private val trolejbusy = listOf(
        TypBusu(
            "Škoda 1Tr", OBYCEJNY, TROLEJBUS, SKODA,
            73, 46, 200.0, 80_000, 10.330F, 24, "Škoda 1Tr byl první trolejbus vyrobený ve Škodových závodech. Zároveň se také jednalo o jeden ze tří trolejbusů, které byly určeny pro zahájení trolejbusové dopravy v Praze.\n" +
                    "\n" +
                    "Výrobce: Škoda Mladá Boleslav\n" +
                    "Rok výroby: 1936\n" +
                    "Délka: 10 330 mm\n" +
                    "Šířka: 2 350 mm\n" +
                    "Hmotnost: 10 370 kg\n" +
                    "Maximální rychlost: 46 km/h\n" +
                    "Míst k sezení: 29\n" +
                    "Míst k stání: 44"
        ),
        TypBusu(
            "Praga TOT", OBYCEJNY, TROLEJBUS, PRAGA,
            80, 50, 200.0, 80_000, 10.200F, 24, "Praga TOT, také označovaný jako Praga TR 4500, je typ československého trolejbusu, který společně s vozy Škoda 1Tr a Tatra 86 zahajoval trolejbusovou dopravu v Praze v roce 1936.\n" +
                    "\n" +
                    "Výrobce: Praga\n" +
                    "Rok výroby: 1936 – 1939\n" +
                    "Délka: 10 200 mm\n" +
                    "Šířka: 2 450 mm\n" +
                    "Hmotnost: 10 700 kg\n" +
                    "Maximální rychlost: 50 km/h\n" +
                    "Míst k sezení: 33\n" +
                    "Míst k stání: 47"
        ),
        TypBusu(
            "Tatra T 86", OBYCEJNY, TROLEJBUS, TATRA,
            80, 45, 200.0, 80_000, 10.700F, 24, "Tatra 86 je typ československého trolejbusu z druhé poloviny 30. let 20. století.\n" +
                    "\n" +
                    "Výrobce: Tatra\n" +
                    "Rok výroby: 1936 – 1939\n" +
                    "Délka: 10 700 mm\n" +
                    "Šířka: 2 420 mm\n" +
                    "Hmotnost: 10 700 kg\n" +
                    "Maximální rychlost: 45 km/h\n" +
                    "Míst k sezení: 23\n" +
                    "Míst k stání: 57"
        ),
        TypBusu(
            "Škoda 2Tr", OBYCEJNY, TROLEJBUS, SKODA,
            74, 50, 180.0, 85_000, 10.500F, 36, "Škoda 2Tr je typ československého trolejbusu z konce 30. let 20. století, který konstrukčně vycházel z předchozího modelu Škoda 1Tr. Mechanickou část vyrobily Škodovy závody v Mladé Boleslavi, elektrickou výzbroj dodaly tytéž závody z Plzně.\n" +
                    "\n" +
                    "Výrobce: Škoda Mladá Boleslav\n" +
                    "Rok výroby: 1938\n" +
                    "Délka: 10 500 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 10 900 kg\n" +
                    "Maximální rychlost: 50 km/h\n" +
                    "Míst k sezení: 30\n" +
                    "Míst k stání: 44"
        ),
        TypBusu(
            "Škoda 3Tr", OBYCEJNY, TROLEJBUS, SKODA,
            80, 45, 150.0, 90_000, 10.550F, 48, "Škoda 3Tr je model československého trolejbusu, který byl vyráběn ve 40. letech 20. století firmou Škoda (během války se podnik nazýval Reichswerke Hermann Göring A.G.) v Plzni. Technicky vychází z předchozího typu Škoda 2Tr.\n" +
                    "\n" +
                    "Výrobce: ŠkodaPlzeň\n" +
                    "Rok výroby: 1941–1948\n" +
                    "Délka: 10 550 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 10 500 kg\n" +
                    "Maximální rychlost: 45 km/h\n" +
                    "Míst k sezení: 28\n" +
                    "Míst k stání: 52"
        ),
        TypBusu(
            "Tatra T 400", OBYCEJNY, TROLEJBUS, TATRA,
            80, 60, 150.0, 90_000, 11.380F, 48, "Tatra 400 je typ československého trolejbusu, který byl vyráběn na přelomu 40. a 50. let 20. století.\n" +
                    "\n" +
                    "Výrobce: Tatra\n" +
                    "Rok výroby: 1948–1955\n" +
                    "Délka: 11 380 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 11 700 kg\n" +
                    "Maximální rychlost: 60 km/h\n" +
                    "Míst k sezení: 26\n" +
                    "Míst k stání: 54"
        ),
        TypBusu(
            "Škoda 6Tr", OBYCEJNY, TROLEJBUS, SKODA,
            79, 46, 130.0, 95_000, 10.550F, 60, "Škoda 6Tr je typ československého trolejbusu vyráběného na konci 40. let 20. století firmou Škoda v Plzni.\n" +
                    "\n" +
                    "Výrobce: Škoda Plzeň\n" +
                    "Rok výroby: 1949\n" +
                    "Délka: 10 550 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 9 600 kg\n" +
                    "Maximální rychlost: 46 km/h\n" +
                    "Míst k sezení: 21\n" +
                    "Míst k stání: 58"
        ),
        TypBusu(
            "Škoda 7Tr", OBYCEJNY, TROLEJBUS, SKODA,
            82, 56, 100.0, 100_000, 10.700F, 120, "Škoda 7Tr je model československého trolejbusu vyráběného v první polovině 50. let 20. století v plzeňském podniku Škoda (v letech 1952 až 1959 Závody Vladimíra Iljiče Lenina).\n" +
                    "\n" +
                    "Výrobce: Škoda Plzeň\n" +
                    "Rok výroby: 1950–1955\n" +
                    "Délka: 10 700 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 9 900 kg\n" +
                    "Maximální rychlost: 56 km/h\n" +
                    "Míst k sezení: 24\n" +
                    "Míst k stání: 58"
        ),
        TypBusu(
            "Škoda 8Tr", OBYCEJNY, TROLEJBUS, SKODA,
            78, 60, 90.0, 105_000, 10.700F, 120, "Škoda 8Tr je typ československého trolejbusu. Byl vyráběn od poloviny 50. do počátku 60. let 20. století podnikem ZVIL v Plzni a později od roku 1960 v Ostrově nad Ohří.\n" +
                    "\n" +
                    "Výrobce: Závody Vladimíra Iljiče Lenina Plzeň\n" +
                    "Rok výroby: 1956 – 1961\n" +
                    "Délka: 10 700 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 10 000 kg\n" +
                    "Maximální rychlost: 50 km/h\n" + // ta rychlost je schválně napsaná jinak
                    "Míst k sezení: 19\n" +
                    "Míst k stání: 59"
        ),
        TypBusu(
            "Tatra T 401", OBYCEJNY, TROLEJBUS, TATRA,
            79, 70, 90.0, 100_000, 11.950F, 96, "Tatra 401 je model československého trolejbusu, který byl vyroben v jednom kuse ve druhé polovině 50. let 20. století.\n" +
                    "\n" +
                    "Výrobce: Tatra\n" +
                    "Rok výroby: 1958\n" +
                    "Délka: 11 950 mm\n" +
                    "Šířka: 2 495 mm\n" +
                    "Hmotnost: 11 350 kg\n" +
                    "Maximální rychlost: 70 km/h\n" +
                    "Míst k sezení: 26\n" +
                    "Míst k stání: 53"
        ),
        TypBusu(
            "Škoda 9Tr", OBYCEJNY, TROLEJBUS, SKODA,
            69, 60, 70.0, 110_000, 11.000F, 240, "Škoda 9Tr je model československého trolejbusu, který byl vyráběn od počátku 60. let 20. století více než 20 let. Výroba probíhala v závodě Škoda v Ostrově nad Ohří. Vůz 9Tr se ve své době stal převratným typem, rozšířil se do mnoha zemí světa, často bývá přirovnáván k tramvaji Tatra T3. Po sovětských ZiU-9 a ZiU-5 je třetím nejvyráběnějším trolejbusem na světě.\n" +
                    "\n" +
                    "Výrobce: Závody Vladimíra Iljiče Lenina Plzeň\n" +
                    "Rok výroby: 1958 – 1981\n" +
                    "Délka: 11 000 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 9 600 kg\n" +
                    "Maximální rychlost: 60 km/h\n" +
                    "Míst k sezení: 23\n" +
                    "Míst k stání: 46"
        ),
        TypBusu(
            "Škoda T 11", OBYCEJNY, TROLEJBUS, SKODA,
            94, 65, 60.0, 110_000, 10.985F, 108, "Škoda T 11 je model československého trolejbusu, který byl vyvinut v 60. letech 20. století v rámci unifikace vozidel MHD. Karosérie pro tyto vozy vyrobil národní podnik Karosa, elektrickou výzbroj dodala firma Škoda.\n" +
                    "\n" +
                    "Výrobce: Škoda Ostrov, Karosa Vysoké Mýto\n" +
                    "Rok výroby: 1967\n" +
                    "Délka: 10 985 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 8 350 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 23\n" +
                    "Míst k stání: 71"
        ),
        TypBusu(
            "Škoda Sanos S 115", OBYCEJNY, TROLEJBUS, SKODA,
            115, 65, 60.0, 115_000, 12F, 106, "Škoda-Sanos S 115 (někdy uváděn jako Škoda-Sanos 115Tr) je trolejbus, který jako prototyp vyrobil v roce 1987 podnik Škoda Ostrov v kooperaci s jugoslávskou (respektive makedonskou) společností FAS 11. Oktomvri Skopje.\n" +
                    "\n" +
                    "Výrobce: Škoda Ostrov, Karosa Vysoké Mýto\n" +
                    "Rok výroby: 1987\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 29\n" +
                    "Míst k stání: 86"
        ),
        TypBusu(
            "Škoda Sanos S 200", OBYCEJNY, TROLEJBUS, SKODA,
            143, 65, 60.0, 115_000, 18F, 106, "Škoda-Sanos S 200 (též Škoda-Sanos 01, někdy uváděn jako Škoda-Sanos 200Tr) je dvoučlánkový trolejbus, který byl v 80. letech 20. století kooperačně vyráběn československým podnikem Škoda Ostrov a jugoslávskou (respektive makedonskou) společností FAS 11. Oktromvri Skopje.\n" +
                    "\n" +
                    "Výrobce: Škoda Ostrov, Karosa Vysoké Mýto\n" +
                    "Rok výroby: 1982 – 1987\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 44\n" +
                    "Míst k stání: 99"
        ),
        TypBusu(
            "Škoda 14Tr", OBYCEJNY, TROLEJBUS, SKODA,
            80, 65, 49.0, 115_000, 11.340F, 240, "Škoda 14Tr je československý trolejbus vyráběný v letech 1981 až 1998 podnikem Škoda Ostrov. Prototypy byly vyrobeny již v letech 1972 a 1974.\n" +
                    "\n" +
                    "Výrobce: Škoda Ostrov\n" +
                    "Rok výroby: 1972 – 1998\n" +
                    "Délka: 11 340 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 10 000 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 24\n" +
                    "Míst k stání: 56"
        ),
        TypBusu(
            "Škoda 14TrM", OBYCEJNY, TROLEJBUS, SKODA,
            82, 65, 44.0, 120_000, 11.340F, 240, "Škoda 14TrM je modernizovaná verze československého trolejbusu Škoda 14Tr. Vyráběna byla v letech 1995–2004 firmou Škoda Ostrov.\n" +
                    "\n" +
                    "Výrobce: Škoda Ostrov\n" +
                    "Rok výroby: 1995–2004\n" +
                    "Délka: 11 340 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 10 400 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 28\n" +
                    "Míst k stání: 54"
        ),
        TypBusu(
            "Škoda 15Tr", OBYCEJNY, TROLEJBUS, SKODA,
            145, 65, 48.0, 160_000, 17.360F, 250, "Škoda 15Tr je dvoučlánkový trolejbus vyráběný mezi lety 1988 a 1995 (prototypy již v první polovině 80. let) podnikem Škoda Ostrov. Od roku 1995 byla produkována modernizovaná verze Škoda 15TrM.\n" +
                    "\n" +
                    "Výrobce: Škoda Ostrov\n" +
                    "Rok výroby: 1988 – 1995\n" +
                    "Délka: 17 360 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 15 800 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 45\n" +
                    "Míst k stání: 100"
        ),
        TypBusu(
            "Škoda 15TrM", OBYCEJNY, TROLEJBUS, SKODA,
            145, 65, 44.0, 165_000, 17.360F, 250, "Škoda 15TrM je modernizovaná verze československého dvoučlánkového trolejbusu Škoda 15Tr. Tato verze byla vyráběna od roku 1995 do roku 2004, kdy byl výrobní závod Škody Ostrov uzavřen.\n" +
                    "\n" +
                    "Výrobce: Škoda Ostrov\n" +
                    "Rok výroby: 1995 – 2004\n" +
                    "Délka: 17 360 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 16 400 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 45\n" +
                    "Míst k stání: 100"
        ),
        TypBusu(
            "Škoda 17Tr", OBYCEJNY, TROLEJBUS, SKODA,
            80, 65, 45.0, 120_000, 11.470F, 180, "Škoda 17Tr je typ československého trolejbusu. Tento model (společně s autobusem Karosa B 831) byl vyvinut v rámci unifikace vozidel MHD společnostmi Škoda a Karosa v polovině 80. let 20. století.\n" +
                    "\n" +
                    "Výrobce: Škoda Ostrov\n" +
                    "Rok výroby: 1987–1990\n" +
                    "Délka: 11 470 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 10 900 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 29\n" +
                    "Míst k stání: 51"
        ),
        TypBusu(
            "Škoda 21Tr", OBYCEJNY, TROLEJBUS, SKODA,
            86, 65, 43.0, 130_000, 11.760F, 192, "Škoda 21Tr je nízkopodlažní trolejbus vyráběný společností Škoda Ostrov mezi lety 1995 a 2004.\n" +
                    "\n" +
                    "Výrobce: Škoda Ostrov\n" +
                    "Rok výroby: 1995-2004\n" +
                    "Délka: 11 760 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 10 950 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 26\n" +
                    "Míst k stání: 60"
        ),
        TypBusu(
            "Škoda 22Tr", OBYCEJNY, TROLEJBUS, SKODA,
            140, 65, 43.0, 170_000, 18.070F, 192, "Škoda 22Tr je nízkopodlažní dvoučlánkový trolejbus, který byl vyráběn v letech 2002 až 2004 firmou Škoda Ostrov. Konstrukčně z něj byl odvozen standardní trolejbus Škoda 21Tr a autobus Škoda 21Ab.\n" +
                    "\n" +
                    "Výrobce: Škoda Ostrov\n" +
                    "Rok výroby: 2002-2004\n" +
                    "Délka: 18 070 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 16 700 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 41\n" +
                    "Míst k stání: 99"
        ),
        TypBusu(
            "Škoda 24Tr Irisbus Citybus", OBYCEJNY, TROLEJBUS, SKODA,
            99, 65, 44.0, 140_000, 11.990F, 202, "Škoda 24Tr Irisbus (pro západní Evropu Irisbus Škoda 24Tr) je nízkopodlažní trolejbus, který byl vyráběn v letech 2003–2014 společnostmi Škoda Electric (elektrická výzbroj a konečná montáž) a Irisbus (mechanická část).\n" +
                    "\n" +
                    "Výrobce: Škoda Electric, Irisbus\n" +
                    "Rok výroby: 2004–2014\n" +
                    "Délka: 11 990 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 11 500 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 30\n" +
                    "Míst k stání: 69"
        ),
        TypBusu(
            "Škoda 24Tr Irisbus Citelis", OBYCEJNY, TROLEJBUS, SKODA,
            99, 65, 40.0, 140_000, 11.990F, 200, "Škoda 24Tr Irisbus (pro západní Evropu Irisbus Škoda 24Tr) je nízkopodlažní trolejbus, který byl vyráběn v letech 2003–2014 společnostmi Škoda Electric (elektrická výzbroj a konečná montáž) a Irisbus (mechanická část).\n" +
                    "\n" +
                    "Výrobce: Škoda Electric, Irisbus\n" +
                    "Rok výroby: 2004–2014\n" +
                    "Délka: 11 990 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 11 500 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 30\n" +
                    "Míst k stání: 69"
        ),
        TypBusu(
            "Škoda 25Tr Irisbus Citybus", OBYCEJNY, TROLEJBUS, SKODA,
            150, 65, 40.0, 175_000, 17.800F, 200, "Škoda 25Tr Irisbus je dvoučlánkový nízkopodlažní trolejbus odvozený ze standardního typu Škoda 24Tr Irisbus. V letech 2004–2014 byl vyráběn firmami Škoda Electric (elektrická výzbroj vozů a konečná montáž) a Irisbus (mechanická část).\n" +
                    "\n" +
                    "Výrobce: Škoda Electric, Irisbus\n" +
                    "Rok výroby: 2005 – 2014\n" +
                    "Délka: 17 800 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 17 700 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 40\n" +
                    "Míst k stání: 110"
        ),
        TypBusu(
            "Škoda 25Tr Irisbus Citelis", OBYCEJNY, TROLEJBUS, SKODA,
            150, 65, 39.0, 180_000, 17.900F, 200, "Škoda 25Tr Irisbus je dvoučlánkový nízkopodlažní trolejbus odvozený ze standardního typu Škoda 24Tr Irisbus. V letech 2004–2014 byl vyráběn firmami Škoda Electric (elektrická výzbroj vozů a konečná montáž) a Irisbus (mechanická část).\n" +
                    "\n" +
                    "Výrobce: Škoda Electric, Irisbus\n" +
                    "Rok výroby: 2005 – 2014\n" +
                    "Délka: 17 900 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Hmotnost: 17 700 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 40\n" +
                    "Míst k stání: 110"
        ),
        TypBusu(
            "Škoda 26Tr Solaris III", PARCIALNI, TROLEJBUS, SKODA,
            102, 65, 34.0, 120_000, 12F, 242, "Škoda 26Tr Solaris je nízkopodlažní trolejbus vyráběný společností Škoda Electric s využitím vozové skříně firmy Solaris Bus & Coach. V současnosti je společně s typy Škoda 30Tr SOR a Škoda 32Tr SOR jedním z modelů standardního dvounápravového trolejbusu, který Škoda nabízí.\n" +
                    "\n" +
                    "Výrobce: Škoda Electric, Solaris Bus & Coach\n" +
                    "Rok výroby: 2010 – 2016\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 10 900 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 34\n" +
                    "Míst k stání: 68"
        ),
        TypBusu(
            "Škoda 26Tr Solaris IV", PARCIALNI, TROLEJBUS, SKODA,
            102, 65, 30.0, 130_000, 12F, 256, "Škoda 26Tr Solaris je nízkopodlažní trolejbus vyráběný společností Škoda Electric s využitím vozové skříně firmy Solaris Bus & Coach. V současnosti je společně s typy Škoda 30Tr SOR a Škoda 32Tr SOR jedním z modelů standardního dvounápravového trolejbusu, který Škoda nabízí.\n" +
                    "\n" +
                    "Výrobce: Škoda Electric, Solaris Bus & Coach\n" +
                    "Rok výroby: od roku 2016\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 10 900 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 34\n" +
                    "Míst k stání: 68"
        ),
        TypBusu(
            "Škoda 27Tr Solaris III", PARCIALNI, TROLEJBUS, SKODA,
            167, 65, 34.0, 180_000, 18F, 242, "Škoda 27Tr Solaris je kloubový trolejbus o délce 18 m. Výrobcem karoserie je polská firma Solaris Bus & Coach, dodavatelem elektrické výzbroje je plzeňská Škoda Electric, která toto vozidlo také kompletuje.\n" +
                    "\n" +
                    "Výrobce: Škoda Electric, Solaris bus & coach\n" +
                    "Rok výroby: 2010 – 2016\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 16 500 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 50\n" +
                    "Míst k stání: 117"
        ),
        TypBusu(
            "Škoda 27Tr Solaris IV", PARCIALNI, TROLEJBUS, SKODA,
            167, 65, 30.0, 195_000, 18F, 256, "Škoda 27Tr Solaris je kloubový trolejbus o délce 18 m. Výrobcem karoserie je polská firma Solaris Bus & Coach, dodavatelem elektrické výzbroje je plzeňská Škoda Electric, která toto vozidlo také kompletuje.\n" +
                    "\n" +
                    "Výrobce: Škoda Electric, Solaris bus & coach\n" +
                    "Rok výroby: od roku 2016\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 16 500 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 50\n" +
                    "Míst k stání: 117"
        ),
        TypBusu(
            "Škoda 28Tr Solaris III", PARCIALNI, TROLEJBUS, SKODA,
            135, 65, 34.0, 145_000, 14.590F, 242, "Škoda 28Tr Solaris je třínápravový trolejbus vyráběný ve spolupráci firem Škoda Electric (elektrická výzbroj a kompletace) a Solaris Bus & Coach (karoserie). Výroba probíhala v letech 2008–2015.\n" +
                    "\n" +
                    "Výrobce: Škoda Electric, Solaris Bus & Coach\n" +
                    "Rok výroby: 2008 – 2015\n" +
                    "Délka: 14 590 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 13 700 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 43\n" +
                    "Míst k stání: 92"
        ),
        TypBusu(
            "Škoda 30Tr SOR", OBYCEJNY, TROLEJBUS, SKODA,
            100, 65, 44.0, 160_000, 12.180F, 168, "Škoda 30Tr SOR je nízkopodlažní trolejbus vyráběný firmou Škoda Electric ve spolupráci s dodavatelem karoserie SOR Libchavy (skříň autobusu typu NB 12). Z modelu 30Tr je odvozen kloubový vůz Škoda 31Tr SOR. Je to hnus.\n" +
                    "\n" +
                    "Výrobce: Škoda Electric, SOR Libchavy\n" +
                    "Rok výroby: od roku 2011\n" +
                    "Délka: 12 180 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 10 800 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 32\n" +
                    "Míst k stání: 68"
        ),
        TypBusu(
            "Škoda 31Tr SOR", OBYCEJNY, TROLEJBUS, SKODA,
            166, 65, 44.0, 200_000, 18.750F, 168, "Škoda 31Tr SOR je kloubový nízkopodlažní trolejbus vyráběný ve spolupráci firem Škoda Electric (elektrická výzbroj a kompletace) a SOR Libchavy, který dodává karoserii vycházející z autobusu SOR NB 18. Je to hnus.\n" +
                    "\n" +
                    "Výrobce: Škoda Electric, SOR Libchavy\n" +
                    "Rok výroby: od roku 2010\n" +
                    "Délka: 18 750 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 18 800 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 52\n" +
                    "Míst k stání: 114"
        ),
        TypBusu(
            "Škoda 32Tr SOR", PARCIALNI, TROLEJBUS, SKODA,
            95, 65, 29.0, 180_000, 12F, 240, "Škoda 32Tr SOR je nízkopodlažní trolejbus vyráběný ve spolupráci firem Škoda Electric (elektrická výzbroj a kompletace) a SOR Libchavy, která dodává karoserii vycházející z autobusu SOR NS 12.\n" +
                    "\n" +
                    "Výrobce: Škoda Electric, SOR Libchavy\n" +
                    "Rok výroby: od roku 2018\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 10 800 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 35\n" +
                    "Míst k stání: 60"
        ),
        TypBusu(
            "Škoda 33Tr SOR", PARCIALNI, TROLEJBUS, SKODA,
            119, 65, 29.0, 220_000, 18.750F, 240, "Škoda 33Tr SOR je nízkopodlažní kloubový trolejbus vyráběný ve spolupráci firem Škoda Electric (elektrická výzbroj a kompletace) a SOR Libchavy, která dodává karoserii vycházející z autobusu SOR NS 18.\n" +
                    "\n" +
                    "Výrobce: Škoda Electric, SOR Libchavy\n" +
                    "Rok výroby: od roku 2019\n" +
                    "Délka: 18 750 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 18 800 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 47\n" +
                    "Míst k stání: 73"
        ),
        TypBusu(
            "Škoda 35Tr Iveco", PARCIALNI, TROLEJBUS, SKODA,
            125, 65, 33.0, 240_000, 18.559F, 204, "Škoda 35Tr je nízkopodlažní kloubový trolejbus vyráběný ve spolupráci firem Škoda Electric (elektrická výzbroj a kompletace) a Iveco Bus, které dodává karoserii vycházející z autobusu Iveco Urbanway 18M.\n" +
                    "\n" +
                    "Výrobce: Škoda Electric, Iveco Bus\n" +
                    "Rok výroby: od roku 2020\n" +
                    "Délka: 18 559 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 19 100 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 35\n" +
                    "Míst k stání: 90"
        ),
        TypBusu(
            "Škoda 36Tr TEMSA", PARCIALNI, TROLEJBUS, SKODA,
            112, 65, 27.0, 190_000, 12.02F, 222, "Škoda 36Tr (obchodní název T'CITY) nebo také Škoda T'CITY 36Tr je nízkopodlažní trolejbus vyráběný od roku 2022 českou firmou Škoda Electric s využitím karoserie tureckého výrobce Temsa. Na stejném základě vznikl také elektrobus Škoda 36BB.n" +
                    "\n" +
                    "Výrobce: Škoda Electric, Iveco Bus\n" +
                    "Rok výroby: od roku 2022\n" +
                    "Délka: 12 020 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 19 100 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 27\n" +
                    "Míst k stání: 85"
        ),
        TypBusu(
            "Ikarus 60T", OBYCEJNY, TROLEJBUS, IKARUS,
            75, 60, 140.0, 90_000, 9.4F, 110, "Ikarus 60T je trolejbus založený na sběrnici Ikarus 60. Karoserie byla vyrobena v továrně Ikarus Body and Vehicle Factory a většina jejích elektrických zařízení byla použita v elektronice sešrotovaných trolejbusů MTB-82. Celkem bylo vyrobeno 157 jednotek, z nichž více než polovina byla přestavěna na přívěsné a kloubové trolejbusy. Všechny jednotky byly dodány v Budapešti v letech 1952 až 1976.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1952 – 1976\n" +
                    "Délka: 9 400 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 60 km/h\n" +
                    "Míst k sezení: 22\n" +
                    "Míst k stání: 53"
        ),
        TypBusu(
            "Ikarus 260T", OBYCEJNY, TROLEJBUS, IKARUS,
            75, 65, 90.0, 100_000, 11F, 170, "Trolejbus BKV Ikarus 260T je první maďarský trolejbus postavený s karoserií Ikarus řady 200. Byl dokončen v roce 1974, vozidlo bylo vybaveno ZiU-5, jednalo se o společný vývoj BKV a Ikarus. Jeho kariérní číslo bylo 600.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1974\n" +
                    "Délka: 11 000 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 22\n" +
                    "Míst k stání: 53"
        ),
        TypBusu(
            "Ikarus 263T", OBYCEJNY, TROLEJBUS, IKARUS,
            75, 65, 60.0, 100_000, 11.94F, 180, "Ikarus 263 je sólový typ autobusu určený pro městskou a příměstskou dopravu v továrně Ikarus, což byla o 1 m delší verze Ikarus 260.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1988 – 2001\n" +
                    "Délka: 11 940 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 22\n" +
                    "Míst k stání: 53"
        ),
        TypBusu(
            "Ikarus 280T", OBYCEJNY, TROLEJBUS, IKARUS,
            144, 65, 75.0, 130_000, 16.5F, 176, "Ikarus 280T je typ trolejbusu založený na sběrnici Ikarus 280. Karoserie byla vyrobena společností Ikarus Body and Vehicle Factory a její elektrická zařízení byla navržena různými společnostmi. Zpočátku byla použita elektronika vyřazených vozíků ZiU a nakonec byla většina nových vozíků vyrobena se zařízením vyvinutým v Ganz Electrical Works (GVM).\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1975 – 1992\n" +
                    "Délka: 16 500 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 34\n" +
                    "Míst k stání: 110"
        ),
        TypBusu(
            "Ikarus 283T", OBYCEJNY, TROLEJBUS, IKARUS,
            168, 65, 70.0, 140_000, 17.975F, 182, "V Moskvě, hlavním městě Sovětského svazu, v pozdních 1980s, bylo nutné dát na trh více kapacitních trolejbusů, protože kloubové ZiU-10 nedorazily dostatečným tempem. SVARZ (Sokolnisky Vagonoremontno-stroityelnij Zavod – Sokolnik Wagon Repair and Production Plant) ve spolupráci s MTRZ (Moskovsky Trolejbusnoremontnij Zavod – Moskevský opravna trolejbusů) přestavěl kloubové autobusy Ikarus na trolejbusy. S elektrickými komponenty pocházejícími z továrny Dinamo byly nejprve přestavěny Ikarus 280, poté v roce 1991 bylo přestavěno pět autobusů Ikarus 283.00, jejich pohon byl stejný jako u trolejbusu ZiU. Celkem 58 vozíků vyrobila společnost SVARZ Ikarus, z toho pět 283T, další 280T. Vozíky byly sešrotovány v roce 2004.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár\n" +
                    "Rok výroby: 1991\n" +
                    "Délka: 17 975 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 37\n" +
                    "Míst k stání: 131"
        ),
        TypBusu(
            "Ikarus 411T", OBYCEJNY, TROLEJBUS, IKARUS,
            61, 65, 60.0, 105_000, 11.07F, 182, "Ikarus 411T je typ trolejbusu založeného na sběrnici Ikarus 411. Karoserie byla vyrobena společností Ikarus Body and Vehicle Factory a elektrická zařízení Kiepe a Obus.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár, Kiepe, Obus\n" +
                    "Rok výroby: 1994\n" +
                    "Délka: 11 070 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 25\n" +
                    "Míst k stání: 36"
        ),
        TypBusu(
            "Ikarus 412T", OBYCEJNY, TROLEJBUS, IKARUS,
            62, 65, 50.0, 110_000, 11.88F, 194, "Ikarus 412T je typ trolejbusu založený na sběrnici Ikarus 412. Karoserie byla vyrobena společností Ikarus Body and Vehicle Factory a elektrická zařízení společnostmi Ganz-Ansaldo, Kiepe a Obus.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár, Ganz-Ansaldo, Kiepe, Obus\n" +
                    "Rok výroby: 1999, 2002\n" +
                    "Délka: 11 880 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 27\n" +
                    "Míst k stání: 35"
        ),
        TypBusu(
            "Ikarus 415T", OBYCEJNY, TROLEJBUS, IKARUS,
            106, 60, 49.0, 110_000, 11.5F, 194, "Ikarus 415T je příměstsko-městský sólový trolejbus Ikarus s nízkou podlahou. Trolejová verze Ikarus 415, jeho hlavním provozovatelem je RATB Bukurešť.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár, GVM, Astra, Dynamo\n" +
                    "Rok výroby: 1997 – 2002\n" +
                    "Délka: 11 500 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 60 km/h\n" +
                    "Míst k sezení: 27\n" +
                    "Míst k stání: 79"
        ),
        TypBusu(
            "Ikarus 435T", OBYCEJNY, TROLEJBUS, IKARUS,
            100, 60, 50.0, 150_000, 17.85F, 194, "Ikarus 435T (také známý jako Ikarus 435.81) je typ trolejbusu založený na autobusu typu 435 Ikarus Body and Vehicle Factory. Karoserie vyráběl Ikarus a elektrická zařízení Kiepe.\n" +
                    "\n" +
                    "Výrobce: Ikarus Karosszéria - és Járműgyár, Kiepe\n" +
                    "Rok výroby: 1994 – 1996\n" +
                    "Délka: 17 850 mm\n" +
                    "Šířka: 2 500 mm\n" +
                    "Maximální rychlost: 60 km/h\n" +
                    "Míst k sezení: 44\n" +
                    "Míst k stání: 56"
        ),
        TypBusu(
            "Ekova Electron 12T", PARCIALNI, TROLEJBUS, EKOVA,
            86, 70, 26.0, 190_000, 11.980F, 240, "Ekova Electron 12T je nízkopodlažní trolejbus vyrobený českou firmou Ekova Electric v jednom prototypu v roce 2017.\n" +
                    "\n" +
                    "Výrobce: Škoda Ekova\n" +
                    "Rok výroby: od roku 2017\n" +
                    "Délka: 11 980 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 12 040 kg\n" +
                    "Maximální rychlost: 70 km/h\n" +
                    "Míst k sezení: 26\n" +
                    "Míst k stání: 60"
        ),
        TypBusu(
            "HESS lighTram 25", PARCIALNI, TROLEJBUS, HESS,
            213, 65, 25.0, 300_000, 24.700F, 240, "Das lighTram ist dank seinem lokal emissionsfreien Elektroantrieb das ideale Verkehrsmittel für den hochbelasteten Stadtverkehr, es ist leistungsstark, leise, dabei sparsam im Energieverbrauch und nutzt die zurückgewonnene Bremsenergie. Mit seinem grosszügigen Fahr-, Sitz- und Einstiegskomfort kommt das lighTram® bei Verkehrsbetrieben wie bei Fahrgästen sehr gut an. Dank innovativer und patentierter Antriebstechnologie mit intelligenter Steuerung besticht es auch bei winterlichen und topografisch schwierigen Bedingungen. Dank zwei gelenkten Achsen, verfügt das 24,7 m lange Fahrzeug über die gleiche Wendigkeit wie normale Gelenkbusse.\n" +
                    "\n" +
                    "Hersteller: Carrosserie HESS AG\n" +
                    "Herstellungsjahr: bis Jahr 2018\n" +
                    "Länge: 24 700 mm\n" +
                    "Breite: 2 550 mm\n" +
                    "Maximale Geschwindigkeit: 65 km/h\n" +
                    "Sitzplätze: 72\n" +
                    "Stehplätze: 141"
        ),
        TypBusu(
            "Solaris Trollino 12 I", OBYCEJNY, TROLEJBUS, SOLARIS,
            102, 65, 44.0, 100_000, 12F, 169, "První trolejbus Solaris byl vyroben v roce 2001 na základě městského autobusu Solaris Urbino 12. Byl montován v závodě Trobus v Gdyni (nástupce PNTKM Gdynia). Struktura pohonu byla založena na tranzistorech IGBT navržených ve spolupráci s Elektrotechnickým institutem ve Varšavě. Dne 11. března 2001 se uskutečnila oficiální premiéra prototypu trolejbusu Solaris, který se jmenoval Solaris Trollino 12T – spojením některých slov „trolejbus“ a „ Urbino “.\n" +
                    "\n" +
                    "Výrobce: Solaris Bus & Coach\n" +
                    "Rok výroby: 2001 – 2003\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 34\n" +
                    "Míst k stání: 68"
        ),
        TypBusu(
            "Solaris Trollino 12 II", OBYCEJNY, TROLEJBUS, SOLARIS,
            102, 65, 39.0, 110_000, 12F, 196, "V září 2002 představil Solaris novou verzi rodiny městských autobusů Urbino, která zahrnovala také trolejbusy Solaris Trollino. Hlavní změny se dotkly vnějšího designu – mírně se změnil tvar předních světlometů, jinak byl uspořádán i interiér. V roce 2003 dodal Solaris společně s Ganz Transelektro 30 kloubových trolejbusů pro Řím. Tato vozidla byla poháněna asynchronním motorem poháněným pantografy a také trakčními bateriemi používanými na úsecích bez trolejbusové trakce.\n" +
                    "\n" +
                    "Výrobce: Solaris Bus & Coach\n" +
                    "Rok výroby: 2003 – 2004\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 34\n" +
                    "Míst k stání: 68"
        ),
        TypBusu(
            "Solaris Trollino 12 III", PARCIALNI, TROLEJBUS, SOLARIS,
            102, 65, 33.0, 130_000, 12F, 225, "V roce 2004 se uskutečnila premiéra třetí generace městských autobusů Solaris Urbino, která se vyznačovala odlišným designem a také velkými stylistickými změnami. Patřila k nim i rodina Trollino.\n" +
                    "\n" +
                    "Výrobce: Solaris Bus & Coach\n" +
                    "Rok výroby: 2005 – 2017\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 34\n" +
                    "Míst k stání: 68"
        ),
        TypBusu(
            "Solaris Trollino 12 IV", PARCIALNI, TROLEJBUS, SOLARIS,
            102, 65, 30.0, 140_000, 12F, 240, "V roce 2014 se uskutečnila premiéra městských autobusů Solaris Urbino 4. generace, známých jako New Urbino. Přesto se trolejbusy Solaris vyráběly ještě ve třetí generaci. První trolejbusy na bázi nové verze Urbino byly do slovenské Žiliny dodány v prosinci 2017.\n" +
                    "\n" +
                    "Výrobce: Solaris Bus & Coach\n" +
                    "Rok výroby: od roku 2017\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 34\n" +
                    "Míst k stání: 68"
        ),
        TypBusu(
            "Solaris Trollino 15 II", OBYCEJNY, TROLEJBUS, SOLARIS,
            135, 65, 34.0, 135_000, 14.590F, 196, "V srpnu 2003 představil Solaris první trolejbus na světě o délce 15 m - Solaris Trollino 15. Vyráběl se v Ostravě ve spolupráci s firmou Cegelec, pohon navrhla ČKD Pragoimex.\n" +
                    "\n" +
                    "Výrobce: Solaris Bus & Coach\n" +
                    "Rok výroby: 2002 – 2007\n" +
                    "Délka: 14 590 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 13 700 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 43\n" +
                    "Míst k stání: 92"
        ),
        TypBusu(
            "Solaris Trollino 15 III", PARCIALNI, TROLEJBUS, SOLARIS,
            135, 65, 34.0, 145_000, 14.590F, 242, "Škoda 28Tr Solaris je třínápravový trolejbus vyráběný ve spolupráci firem Škoda Electric (elektrická výzbroj a kompletace) a Solaris Bus & Coach (karoserie). Výroba probíhala v letech 2008–2015.\n" +
                    "\n" +
                    "Výrobce: Solaris Bus & Coach\n" +
                    "Rok výroby: 2008 – 2015\n" +
                    "Délka: 14 590 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 13 700 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 43\n" +
                    "Míst k stání: 92"
        ),
        TypBusu(
            "Solaris Trollino 18 I", OBYCEJNY, TROLEJBUS, SOLARIS,
            167, 65, 44.0, 150_000, 18F, 169, "V polovině roku 2001 Solaris také vyrobil první trolejbusy Solaris Trollino 18T na základě modelu Urbino 18, které byly vyvinuty ve spolupráci s maďarskou firmou Ganz Transelektro z Budapešti.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2001 – 2002\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 16 500 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 50\n" +
                    "Míst k stání: 117"
        ),
        TypBusu(
            "Solaris Trollino 18 II", OBYCEJNY, TROLEJBUS, SOLARIS,
            167, 65, 39.0, 165_000, 18F, 196, "V září 2002 představil Solaris novou verzi rodiny městských autobusů Urbino, která zahrnovala také trolejbusy Solaris Trollino. Hlavní změny se dotkly vnějšího designu – mírně se změnil tvar předních světlometů, jinak byl uspořádán i interiér. V roce 2003 dodal Solaris společně s Ganz Transelektro 30 kloubových trolejbusů pro Řím.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2002 – 2008\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 16 500 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 50\n" +
                    "Míst k stání: 117"
        ),
        TypBusu(
            "Solaris Trollino 18 III", PARCIALNI, TROLEJBUS, SOLARIS,
            167, 65, 34.0, 180_000, 18F, 242, "V roce 2004 se uskutečnila premiéra třetí generace městských autobusů Solaris Urbino, která se vyznačovala odlišným designem a také velkými stylistickými změnami. Patřila k nim i rodina Trollino. Od třetí generace se také změnil vzhled zeleného maskota jezevčíka na trolejbusech Trollino – dosud vypadal stejně jako na autobusech Solaris Urbino, později však přibylo „vodítko“, symbolizující sběrače proudu na střeše trolejbusu.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2008 – 2016\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 16 500 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 50\n" +
                    "Míst k stání: 117"
        ),
        TypBusu(
            "Solaris Trollino 18 IV", PARCIALNI, TROLEJBUS, SOLARIS,
            167, 65, 30.0, 195_000, 18F, 256, "V roce 2014 se uskutečnila premiéra městských autobusů Solaris Urbino 4. generace, známých jako New Urbino. Přesto se trolejbusy Solaris vyráběly ještě ve třetí generaci. První trolejbusy na bázi nové verze Urbino byly do slovenské Žiliny dodány v prosinci 2017. Jednalo se o 12 kloubových trolejbusů Škoda-Solaris 27Tr a 3 kusy Škoda-Solaris 26Tr.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: od roku 2016\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 16 500 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 50\n" +
                    "Míst k stání: 117"
        ),
        TypBusu(
            "Solaris Trollino 18 III MetroStyle", PARCIALNI, TROLEJBUS, SOLARIS,
            167, 65, 29.0, 210_000, 18F, 256, "V roce 2012 dodal Solaris do Salcburku první Solaris Trollino 18 s pohonem Cegelec s novým designem odkazujícím na rodinu tramvají Solaris Tramino . Zvláštní pozornost byla věnována nakloněnému čelnímu sklu a také novým předním a zadním svítilnám. Tato vozidla byla pojmenována Solaris Trollino 18 MetroStyle.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2012 – 2017\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 16 500 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 50\n" +
                    "Míst k stání: 117"
        ),
        TypBusu(
            "Solaris Trollino 24 IV", PARCIALNI, TROLEJBUS, SOLARIS,
            207, 65, 30.0, 260_000, 24F, 240, "Solaris Trollino 24 IV je nejdelší tříčlánkový trolejbus z rodiny Solaris Trollino.\n" +
                    "\n" +
                    "Výrobce: Solaris Bus & Coach\n" +
                    "Rok výroby: od roku 2019\n" +
                    "Délka: 24 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 68\n" +
                    "Míst k stání: 141"
        ),
        TypBusu(
            "Solaris Trollino 24 IV MetroStyle", PARCIALNI, TROLEJBUS, SOLARIS,
            207, 65, 22.0, 310_000, 24F, 240, "Oficiální premiéra modelu, tentokrát ve verzi MetroStyle, proběhla v říjnu 2019 během veletrhu Busworld v Bruselu. Během veletrhu Poznań Arena Design 2020 obdržel cenu Top Design Award. Trollino 24 MetroStyle uskutečnil své první testovací jízdy v listopadu 2020 v Bratislavě.\n" +
                    "\n" +
                    "Výrobce: Solaris Bus & Coach\n" +
                    "Rok výroby: od roku 2019\n" +
                    "Délka: 24 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 68\n" +
                    "Míst k stání: 141"
        ),
        TypBusu(
            "Bogdan T601.10", OBYCEJNY, TROLEJBUS, BOGDAN,
            60, 65, 42.0, 80_000, 10.600F, 180, "Bogdan T601.10 je nejkratší trolejbus ukrajinského výrobce Bogdan\n" +
                    "\n" +
                    "Výrobce: Bogdan\n" +
                    "Rok výroby: od roku 2010\n" +
                    "Délka: 10 600 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Maximální rychlost: 70 km/h\n"
        ),
        TypBusu(
            "Bogdan T701.10", OBYCEJNY, TROLEJBUS, BOGDAN,
            90, 65, 42.0, 120_000, 12F, 180, "Bogdan T701.10 je trolejbus ukrajinského výrobce Bogdan\n" +
                    "\n" +
                    "Výrobce: Bogdan\n" +
                    "Rok výroby: od roku 2010\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Maximální rychlost: 70 km/h\n"
        ),
        TypBusu(
            "Bogdan T801.10", OBYCEJNY, TROLEJBUS, BOGDAN,
            120, 65, 42.0, 150_000, 15F, 180, "Bogdan T801.10 je trolejbus ukrajinského výrobce Bogdan\n" +
                    "\n" +
                    "Výrobce: Bogdan\n" +
                    "Rok výroby: od roku 2010\n" +
                    "Délka: 15 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Maximální rychlost: 70 km/h\n"
        ),
        TypBusu(
            "Bogdan T901.10", OBYCEJNY, TROLEJBUS, BOGDAN,
            150, 65, 42.0, 180_000, 18.700F, 180, "Bogdan T901.10 je nejdelší trolejbus ukrajinského výrobce Bogdan\n" +
                    "\n" +
                    "Výrobce: Bogdan\n" +
                    "Rok výroby: od roku 2010\n" +
                    "Délka: 18 700 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Maximální rychlost: 65 km/h\n"
        )
    )

    private val elektrobusy = listOf(
        TypBusu(
            "Škoda 21Eb", OBYCEJNY, ELEKTROBUS, SKODA,
            70, 70, 86.0, 120_000, 11.5F, 512, "Škoda 21Eb je první elektrobus vyrobený firmou Škoda\n" +
                    "\n" +
                    "Výrobce: Škoda Ostrov\n" +
                    "Rok výroby: 2002\n" +
                    "Délka: 11 500 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 18 000 kg" +
                    "Maximální rychlost: 70 km/h\n" +
                    "Míst k sezení: 29\n" +
                    "míst k stání: 41"
        ),
        TypBusu(
            "Škoda Perun 26BB", OBYCEJNY, ELEKTROBUS, SKODA,
            82, 65, 60.0, 120_000, 12F, 256, "Škoda Perun (typové označení Škoda 26BB) je nízkopodlažní elektrobus vyráběný od roku 2013 českou firmou Škoda Electric.\n" +
                    "\n" +
                    "Výrobce: Škoda Electric, Solaris bus & coach\n" +
                    "Rok výroby: od roku 2013\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 27\n" +
                    "míst k stání: 55"
        ),
        TypBusu(
            "Škoda Perun 29BB", OBYCEJNY, ELEKTROBUS, SKODA,
            55, 65, 63.0, 115_000, 8.95F, 256, "Škoda 29BB je nízkopodlažní elektrobus vyráběný od roku 2018 českou firmou Škoda Electric.\n" +
                    "\n" +
                    "Výrobce: Škoda Electric, Solaris bus & coach\n" +
                    "Rok výroby: od roku 2018\n" +
                    "Délka: 8 950 mm\n" +
                    "Šířka: 2 400 mm\n" +
                    "Hmotnost: 14 500 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 21\n" +
                    "Míst k stání: 34"
        ),
        TypBusu(
            "Škoda E'City 34BB", OBYCEJNY, ELEKTROBUS, SKODA,
            75, 65, 99.0, 220_000, 12F, 168, "Škoda E'City (typové označení Škoda 34BB a Škoda 36BB) je nízkopodlažní elektrobus české firmy Škoda Electric. V roce 2017 byl vyroben jeden vůz typu 34BB, od roku 2021 probíhá produkce modelu 36BB.\n" +
                    "\n" +
                    "Výrobce: Škoda Electric, Heuliez Bus\n" +
                    "Rok výroby: od roku 2017\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 37\n" +
                    "Míst k stání: 38"
        ),
        TypBusu(
            "Škoda E'City 36BB", OBYCEJNY, ELEKTROBUS, SKODA,
            69, 65, 90.0, 130_000, 12.095F, 168, "Škoda E'City (typové označení Škoda 34BB a Škoda 36BB) je nízkopodlažní elektrobus české firmy Škoda Electric. V roce 2017 byl vyroben jeden vůz typu 34BB, od roku 2021 probíhá produkce modelu 36BB.\n" +
                    "\n" +
                    "Výrobce: Škoda Electric, TEMSA\n" +
                    "Rok výroby: od roku 2017\n" +
                    "Délka: 12 095 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 26\n" +
                    "Míst k stání: 43"
        ),
        TypBusu(
            "Solaris Urbino 8,9 III LE Electric", OBYCEJNY, ELEKTROBUS, SOLARIS,
            55, 65, 63.0, 115_000, 8.95F, 256, "Solaris Urbino 8,9 LE Electric je nízkopodlažní elektrický městský autobus, představený na podzim 2011. Vyráběla ho společnost Solaris Bus & Coach SA z Bolechowa u Poznaně. Jeho design vychází z modelu Solaris Urbino 8,9 LE. Elektrické modely Urbino 8,9 LE a 8,9 LE jsou posledními vyráběnými autobusy s karoserií Urbino 3. generace.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: od roku 2011\n" +
                    "Délka: 8 950 mm\n" +
                    "Šířka: 2 400 mm\n" +
                    "Hmotnost: 14 500 kg\n" +
                    "Maximální rychlost: 50 km/h\n" +
                    "Míst k sezení: 27\n" +
                    "Míst k stání: 28"
        ),
        TypBusu(
            "Solaris Urbino 9 IV LE electric", OBYCEJNY, ELEKTROBUS, SOLARIS,
            55, 65, 50.0, 125_000, 8.95F, 256, "Solaris Urbino 9 LE electric je nízkopodlažní elektrický městský autobus, představený na podzim 2011. Vyráběla ho společnost Solaris Bus & Coach SA z Bolechowa u Poznaně. Jeho design vychází z modelu Solaris Urbino 9 LE.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: od roku 2021\n" +
                    "Délka: 8 950 mm\n" +
                    "Šířka: 2 400 mm\n" +
                    "Hmotnost: 14 500 kg\n" +
                    "Maximální rychlost: 50 km/h\n" +
                    "Míst k sezení: 27\n" +
                    "Míst k stání: 28"
        ),
        TypBusu(
            "Solaris Urbino 12 III Electric", OBYCEJNY, ELEKTROBUS, SOLARIS,
            105, 80, 60.0, 130_000, 12F, 250, "Solaris Urbino 12 electric je elektrický městský autobus, vyráběný od roku 2013 společností Solaris Bus & Coach v Bolechowo-Osiedle u Poznaně. Patří do rodiny městských autobusů Solaris Urbino.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: od roku 2013 – 2016\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 10 400 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 40\n" +
                    "Míst k stání: 65"
        ),
        TypBusu(
            "Solaris Urbino 12 IV electric", OBYCEJNY, ELEKTROBUS, SOLARIS,
            105, 80, 50.0, 140_000, 12F, 250, "V roce 2014, během zářijového veletrhu IAA, byly představeny Solaris Urbino 12 a 18 nové generace spalovacího motoru (na rozdíl od předchozích modelů byl použit termín „New Urbino“ místo „Urbino IV generace“). O rok později na veletrhu Busworld v Kortrijku představil Solaris elektrickou verzi nové verze Solaris. Nová generace poskytuje mnoho různých konfigurací v závislosti na potřebách zákazníka.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: od roku 2016\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 10 400 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 40\n" +
                    "Míst k stání: 65"
        ),
        TypBusu(
            "Solaris Urbino 12 IV LE electric", OBYCEJNY, ELEKTROBUS, SOLARIS,
            105, 80, 50.0, 150_000, 12F, 250, "V roce 2014, během zářijového veletrhu IAA, byly představeny Solaris Urbino 12 a 18 nové generace spalovacího motoru (na rozdíl od předchozích modelů byl použit termín „New Urbino“ místo „Urbino IV generace“). O rok později na veletrhu Busworld v Kortrijku představil Solaris elektrickou verzi nové verze Solaris. Nová generace poskytuje mnoho různých konfigurací v závislosti na potřebách zákazníka.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: od roku 2020\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 10 400 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 40\n" +
                    "Míst k stání: 65"
        ),
        TypBusu(
            "Solaris Urbino 18 III Electric", OBYCEJNY, ELEKTROBUS, SOLARIS,
            138, 80, 60.0, 190_000, 18F, 225, "Solaris Urbino 18 electric je nízkopodlažní, kloubový městský autobus s elektrickým pohonem, vyráběný od roku 2014 polskou společností Solaris Bus & Coach v Bolechowo-Osiedle u Poznaně. Patří do rodiny městských autobusů Solaris Urbino a je vývojem kratších modelů elektrických městských autobusů výrobce - Urbino 8,9 LE electric a Urbino 12 electric.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: 2014 – 2017\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 15 000 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 44\n" +
                    "Míst k stání: 94"
        ),
        TypBusu(
            "Solaris Urbino 18 IV electric", OBYCEJNY, ELEKTROBUS, SOLARIS,
            138, 80, 50.0, 205_000, 18F, 225, "V roce 2014 představil Solaris novou generaci modelu Urbino 18, ale pouze ve verzi s konvenčním pohonem. V roce 2015 Solaris představil 12metrovou verzi elektrobusu 4. generace a oficiální premiéra 4. generace Solaris Urbino 18 electric proběhla na podzim 2017 na veletrhu Busworld v belgickém Kortrijku. Model je založen na řešení známý z modelů předchozí generace, byl však představen modernější design vozidla, design interiéru byl mírně změněn a některé konstrukční prvky byly zpřesněny.\n" +
                    "\n" +
                    "Výrobce: Solaris bus & coach\n" +
                    "Rok výroby: od roku 2017\n" +
                    "Délka: 18 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 15 000 kg\n" +
                    "Maximální rychlost: 80 km/h\n" +
                    "Míst k sezení: 44\n" +
                    "Míst k stání: 94"
        ),
        TypBusu(
            "Solaris Urbino 24 IV MetroStyle electric", OBYCEJNY, ELEKTROBUS, SOLARIS,
            207, 65, 55.0, 370_000, 24F, 240, "24metrové vozidlo od Solaris bylo poprvé představeno na Busworld Europe 2019 v Bruselu. Účelem bylo vytvořit platformu pro budoucí sériovou výrobu nejen 24metrových vozidel s elektrickým nebo hybridním pohonem, ale také trolejbusů. Objednávka z Dánska je vůbec první na toto vozidlo s třemi články a elektrickým pohonem.\n" +
                    "\n" +
                    "Výrobce: Solaris Bus & Coach\n" +
                    "Rok výroby: od roku 2021\n" +
                    "Délka: 24 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 68\n" +
                    "Míst k stání: 141"
        ),
        TypBusu(
            "Ekova Electron 12", OBYCEJNY, ELEKTROBUS, EKOVA,
            90, 65, 52.0, 220_000, 11.98F, 240, "Ekova Electron 12 je nízkopodlažní elektrobus vyráběný od roku 2015 českou firmou Ekova Electric. Je z něj odvozen trolejbus Ekova Electron 12T.\n" +
                    "\n" +
                    "Výrobce: Škoda Ekova\n" +
                    "Rok výroby: od roku 2018\n" +
                    "Délka: 11 980 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 18 000 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 30\n" +
                    "Míst k stání: 60"
        ),
        TypBusu(
            "SOR EBN 9,5", OBYCEJNY, ELEKTROBUS, SOR,
            69, 65, 80.0, 100_000, 9.79F, 160, "SOR EBN 9,5 je elektrobus českého výrobce SOR Libchavy.\n" +
                    "\n" +
                    "Výrobce: SOR Libchavy\n" +
                    "Rok výroby: od roku 2016\n" +
                    "Délka: 9 790 mm\n" +
                    "Šířka: 2 525 mm\n" +
                    "Hmotnost: 9 650 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 26\n" +
                    "Míst k stání: 43"
        ),
        TypBusu(
            "SOR EBN 10,5", OBYCEJNY, ELEKTROBUS, SOR,
            85, 65, 80.0, 110_000, 10.37F, 160, "SOR EBN 10,5 je elektrobus českého výrobce SOR Libchavy vyráběný od roku 2010. Jeho vývoj byl zahájen na přelomu let 2008 a 2009, dodavatelem elektrické výzbroje je firma Cegelec, která dále využívá subdodavatele.\n" +
                    "\n" +
                    "Výrobce: SOR Libchavy\n" +
                    "Rok výroby: od roku 2016\n" +
                    "Délka: 10 370 mm\n" +
                    "Šířka: 2 525 mm\n" +
                    "Hmotnost: 10 100 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 19\n" +
                    "Míst k stání: 66"
        ),
        TypBusu(
            "SOR NS 12 Electric", OBYCEJNY, ELEKTROBUS, SOR,
            64, 65, 58.0, 130_000, 12F, 239, "Prototyp elektrobusu SOR NS 12 Electric byl představen na veletrhu v Hannoveru v září 2016. Téhož roku byl vystaven na pražském veletrhu Czech Bus 2016, poté si vůz pronajal Dopravní podnik hl. m. Prahy. Sériová výroba začala v roce 2017. Byly a jsou dodávány do Bratislavy, Frýdku-Místku, Havířova, Karviné, Hradce Králové, Olomouce, Brašova a Zalău.\n" +
                    "\n" +
                    "Výrobce: SOR Libchavy\n" +
                    "Rok výroby: od roku 2016\n" +
                    "Délka: 12 000 mm\n" +
                    "Šířka: 2 550 mm\n" +
                    "Hmotnost: 12 350 kg\n" +
                    "Maximální rychlost: 65 km/h\n" +
                    "Míst k sezení: 29\n" +
                    "Míst k stání: 35"
        ),
    )

    val typy = autobusy + trolejbusy + elektrobusy
}
