package cz.jaro.dopravnipodniky

sealed interface StavTutorialu {
    data object Nic : StavTutorialu

    sealed class Tutorialujeme(
        bla: String
    ) : StavTutorialu {
        data object Uvod : Tutorialujeme(bla = "bla bla")

        // TODO!!!
    }
}