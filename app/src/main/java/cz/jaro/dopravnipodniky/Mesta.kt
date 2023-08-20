package cz.jaro.dopravnipodniky

const val MESTA = """
Praha
Křemže
Ostrava
Plzeň
Liberec
Olomouc
České Budějovice
Hradec Králové
Ústí nad Labem
Pardubice
Zlín
Havířov
Kladno
Most
Opava
Frýdek-Místek
Jihlava
Karviná
Teplice
Chomutov
Karlovy Vary
Děčín
Jablonec nad Nisou
Mladá Boleslav
Prostějov
Přerov
Česká Lípa
Třebíč
Třinec
Tábor
Znojmo
Kolín
Příbram
Cheb
Písek
Joštín
Trutnov
Kroměříž
Orlová
Vsetín
Šumperk
Uherské Hradiště
Břeclav
Hodonín
Český Těšín
Litoměřice
Litvínov
Havlíčkův Brod
Nový Jičín
Chrudim
Krnov
Sokolov
Strakonice
Valašské Meziříčí
Klatovy
Kopřivnice
Jindřichův Hradec
Kutná Hora
Vyškov
Žďár nad Sázavou
Bohumín
Blansko
Beroun
Náchod
Mělník
Brandýs nad Labem-Stará Boleslav
Jirkov
Žatec
Kralupy nad Vltavou
Louny
Kadaň
Hranice
Otrokovice
Bílina
Benešov
Svitavy
Křemže
Jičín
Ostrov
Uherský Brod
Rožnov pod Radhoštěm
Říčany
Neratovice
Pelhřimov
Slaný
Bruntál
Rakovník
Dvůr Králové nad Labem
Česká Třebová
Varnsdorf
Nymburk
Poděbrady
Turnov
Klášterec nad Ohří
Rokycany
Ústí nad Orlicí
Hlučín
Zábřeh
Šternberk
Aš
Chodov
Tachov
Český Krumlov
Roudnice nad Labem
Mariánské Lázně
Krupka
Jaroměř
Čelákovice
Vrchlabí
Vysoké Mýto
Milovice
Boskovice
Nový Bor
Holešov
Vlašim
Velké Meziříčí
Uničov
Kyjov
Domažlice
Kuřim
Jeseník
Humpolec
Sušice
Rumburk
Rychnov nad Kněžnou
Veselí nad Moravou
Frenštát pod Radhoštěm
Prachatice
Čáslav
Litomyšl
Nové Město na Moravě
Jesenice
Králův Dvůr
Frýdlant nad Ostravicí
Přelouč
Ivančice
Lysá nad Labem
Lanškroun
Moravská Třebová
Litovel
Hlinsko
Studénka
Nové Město nad Metují
Tišnov
Mohelnice
Chotěboř
Nová Paka
Hostivice
Mnichovo Hradiště
Dobříš
Polička
Roztoky
Lovosice
Štětí
Choceň
Duchcov
Hořice
Příbor
Červený Kostelec
Semily
Milevsko
Třeboň
Bystřice pod Hostýnem
Rýmařov
Bystřice nad Pernštejnem
Lipník nad Bečvou
Týn nad Vltavou
Dubí
Nejdek
Hrádek nad Nisou
Šlapanice
Stříbro
Benátky nad Jizerou
Rychvald
Mikulov
Frýdlant
Vimperk
Bílovec
Černošice
Vratimov
Petřvald
Odry
Broumov
Moravské Budějovice
Kaplice
Dačice
Sezimovo Ústí
Napajedla
Český Brod
Přeštice
Slavkov u Brna
Úvaly
Vodňany
Nýřany
Hořovice
Soběslav
Nový Bydžov
Sedlčany
Letovice
Kraslice
Hulín
Kravaře
Dobruška
Holice
Staré Město
Blatná
Bučovice
Letohrad
Světlá nad Sázavou
Šenov
Podbořany
Křemže
Mimoň
Rosice
Veselí nad Lužnicí
Dubňany
Slavičín
Chrastava
Odolena Voda
Kostelec nad Orlicí
Dobřany
Tanvald
Mníšek pod Brdy
Hronov
Týniště nad Orlicí
Žamberk
Železný Brod
Hustopeče
Kojetín
Rousínov
Šluknov
Třebechovice pod Orebem
Týnec nad Sázavou
Moravský Krumlov
Třešť
Úpice
Vítkov
Nové Strašecí
Chlumec nad Cidlinou
Fulnek
Brumov-Bylnice
Zubří
Lomnice nad Popelkou
Kunovice
Hradec nad Moravicí
Strážnice
Hluboká nad Vltavou
Jablunkov
Jilemnice
Františkovy Lázně
Modřice
Stochov
Planá
Horní Slavkov
Trhové Sviny
Telč
Pohořelice
Velká Bíteš
Horažďovice
Polná
Česká Kamenice
Doksy
Kosmonosy
Starý Plzenec
Rudná
Holýšov
Kdyně
Bakov nad Jizerou
Třemošná
Jílové
Luhačovice
Skuteč
Česká Skalice
Horšovský Týn
Bechyně
Ledeč nad Sázavou
Vrbno pod Pradědem
Nýrsko
Valašské Klobouky
Jílové u Prahy
Meziboří
Chropyně
Vizovice
Habartov
Zruč nad Sázavou
Náměšť nad Oslavou
Unhošť
Křemže
Joštín
Pečky
Heřmanův Městec
Protivín
Bělá pod Bezdězem
Kynšperk nad Ohří
Oslavany
Postoloprty
Osek
Pacov
Vamberk
Vracov
Votice
Adamov
Lišov
Cvikov
Klimkovice
Bor
Bystřice
Bzenec
Bojkovice
Hostinné
Hluk
Chlumec
Rožmitál pod Třemšínem
Planá nad Lužnicí
Kostelec nad Labem
Uherský Ostroh
Slatiňany
Zdice
Borovany
Králíky
Nová Role
Jaroměřice nad Rokytnou
Horní Bříza
Brušperk
Blovice
Dolní Benešov
Sezemice
Police nad Metují
Jemnice
Přibyslav
Rajhrad
Mnichovice
Kamenický Šenov
Paskov
Velké Bílovice
Stráž pod Ralskem
Velešín
Větřní
Židlochovice
Kostelec nad Černými lesy
Brtnice
Smržovka
Sázava
Rájec-Jestřebí
Kamenice nad Lipou
Zbýšov
Dobřichovice
Volary
Nepomuk
Fryšták
Zlaté Hory
Lanžhot
Nové Město pod Smrkem
Lom
Lázně Bělohrad
Klecany
Benešov nad Ploučnicí
Jablonné v Podještědí
Toužim
Buštěhrad
Velké Opatovice
Řevnice
Jiříkov
Valtice
Stod
Suchdol nad Lužnicí
Lázně Bohdaneč
Zliv
Štěpánov
Březnice
České Velenice
Velká Bystřice
Libochovice
Město Albrechtice
Libčice nad Vltavou
Štramberk
Kralovice
Dobrovice
Veverská Bítýška
Krásná Lípa
Újezd u Brna
Libušín
Trmice
Staňkov
Hrušovany nad Jevišovkou
Nová Bystřice
Sadská
Košťany
Chrast
Jablonné nad Orlicí
Ždírec nad Doubravou
Žacléř
Uhlířské Janovice
Velké Pavlovice
Desná
Opočno
Loket
Velvary
Kaznějov
Břidličná
Třemošnice
Hanušovice
Podivín
Miroslav
Volyně
Hodkovice nad Mohelkou
Rtyně v Podkrkonoší
Teplá
Morkovice-Slížany
Loštice
Slušovice
Úštěk
Chvaletice
Ivanovice na Hané
Smiřice
Žirovnice
Moravský Beroun
Budišov nad Budišovkou
Rotava
Kostelec na Hané
Spálené Poříčí
Vejprty
Sedlec-Prčice
Terezín
Zákupy
Raspenava
Hrádek
Dolní Bousov
Český Dub
Kunštát
Jevíčko
Městec Králové
Rychnov u Jablonce nad Nisou
Plasy
Konice
Hejnice
Javorník
Mladá Vožice
Koryčany
Golčův Jeníkov
Kelč
Velké Hamry
Neveklov
Březová
Vyšší Brod
Luže
Nové Sedlo
Rokytnice nad Jizerou
Rudolfov
Netolice
Počátky
Chabařovice
Nové Hrady
Zbiroh
Ždánice
Klobouky u Brna
Bohušovice nad Ohří
Chýnov
Dolní Kounice
Tovačov
Karolinka
Jáchymov
Sobotka
Dašice
Meziměstí
Kryry
Nechanice
Slavonice
Janovice nad Úhlavou
Plumlov
Mirošov
Nová Včelnice
Veltrusy
Žlutice
Solnice
Horní Jiřetín
Horní Benešov
Kardašova Řečice
Žebrák
Město Touškov
Hranice
Budyně nad Ohří
Luby
Ralsko
Kopidlno
Mikulášovice
Borohrádek
Týnec nad Labem
Nový Knín
Proseč
Pyšely
Horní Jelení
Hrochův Týnec
Horní Planá
Hroznětín
Svoboda nad Úpou
Jistebnice
Hrob
Zásmuky
Skalná
Rokytnice v Orlických horách
Štíty
Velký Šenov
Němčice nad Hanou
Plesná
Třebenice
Libáň
Smečno
Bochov
Kouřim
Lučany nad Nisou
Žandov
Horní Cerekev
Vroutek
Hostomice
Radnice
Lomnice nad Lužnicí
Hrotovice
Černovice
Bělá nad Radbuzou
Seč
Hoštka
Dolní Poustevna
Vlachovo Březí
Dubá
Ronov nad Doubravou
Jesenice
Staré Město
Nasavrky
Olešnice
Švihov
Plánice
Oloví
Rožďalovice
Bavorov
Kladruby
Železná Ruda
Březová nad Svitavou
Všeruby
Mirovice
Teplice nad Metují
Jablonec nad Jizerou
Přimda
Poběžovice
Mýto
Bystré
Kožlany
Strmilov
Mšeno
Husinec
Lázně Kynžvart
Strážov
Trhový Štěpánov
Kašperské Hory
Svratka
Chřibská
Harrachov
Habry
Železnice
Kasejovice
Brandýs nad Orlicí
Rovensko pod Troskami
Vysoké nad Jizerou
Vidnava
Sedlice
Mirotice
Pilníkov
Hostouň
Žulová
Černošín
Potštát
Osečná
Verneřice
Nalžovské Hory
Úsov
Jevišovice
Manětín
Měčín
Krásná Hora nad Vltavou
Špindlerův Mlýn
Liběchov
Červená Řečice
Blšany
Bělčice
Bečov nad Teplou
Hartmanice
Bezdružice
Miletín
Abertamy
Stráž nad Nežárkou
Vysoké Veselí
Deštná
Krásno
Janské Lázně
Stárkov
Lipnice nad Sázavou
Pec pod Sněžkou
Chyše
Mašťov
Ledvice
Rabí
Úterý
Hora Svaté Kateřiny
Výsluní
Horní Blatná
Krásné Údolí
Rožmberk nad Vltavou
Andělská Hora
Janov
Rejštejn
Boží Dar
Loučná pod Klínovcem
Přebuz
Křemže
Joštín
"""