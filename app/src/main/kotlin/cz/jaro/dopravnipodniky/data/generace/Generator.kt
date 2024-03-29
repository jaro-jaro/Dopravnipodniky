package cz.jaro.dopravnipodniky.data.generace

import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.shared.pocatecniDeatilGenerace
import cz.jaro.dopravnipodniky.shared.seznamy.MESTA
import cz.jaro.dopravnipodniky.ui.theme.Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

object Generator {

    suspend fun vygenerujMiPrvniMesto(): DopravniPodnik = withContext(Dispatchers.IO) {

        val seed = 19250533

        Generator(
            detailGenerace = pocatecniDeatilGenerace,
            tema = Theme.Jantarove,
        ) {}
    }

    suspend operator fun invoke(
        detailGenerace: DetailGenerace,
        tema: Theme = Theme.entries.random(),
        step: (Float) -> Unit,
    ) = withContext(Dispatchers.IO) {

        val (ulicove, krizovatky) = when (detailGenerace) {
            is DetailGeneraceV1 -> generatorV1(
                detailGenerace = detailGenerace,
                step = step,
            )

            is DetailGeneraceV2 -> generatorV2(
                detailGenerace = detailGenerace,
                step = step,
            )
        }

        val jmenoMesta = MESTA.trim().lines().random(Random(detailGenerace.nazevMestaSeed))

        DopravniPodnik(
            jmenoMesta = jmenoMesta,
            ulicove = ulicove,
            krizovatky = krizovatky,
            tema = tema,
            detailGenerace = detailGenerace
        )
    }
}