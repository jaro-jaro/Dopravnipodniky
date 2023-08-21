package cz.jaro.dopravnipodniky.shared

import cz.jaro.dopravnipodniky.R

sealed interface StavTutorialu {
    data object Nic : StavTutorialu

    sealed class Tutorialujeme(
        val text: Int,
        val dalsi: StavTutorialu,
    ) : StavTutorialu {
        data object Uvod : Tutorialujeme(
            text = R.string.t1,
            dalsi = Linky,
        )
        data object Linky :  Tutorialujeme(
            text = R.string.t2,
            dalsi = Zastavky,
        )
        data object Zastavky :  Tutorialujeme(
            text = R.string.t3,
            dalsi = Garaz,
        )
        data object Garaz :  Tutorialujeme(
            text = R.string.t4,
            dalsi = Obchod,
        )
        data object Obchod :  Tutorialujeme(
            text = R.string.t5,
            dalsi = Vypraveni,
        )
        data object Vypraveni :  Tutorialujeme(
            text = R.string.t6,
            dalsi = Nic,
        )
        data object NovejDp :  Tutorialujeme(
            text = R.string.t7,
            dalsi = Nic,
        )
    }
}