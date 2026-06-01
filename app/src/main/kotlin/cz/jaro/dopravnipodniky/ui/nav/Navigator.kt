package cz.jaro.dopravnipodniky.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack

interface Navigator {
    fun pop()
    fun push(route: Route)
    fun modifyBackStack(apply: NavBackStack<Route>.() -> Unit)
}

fun Navigator(backStack: NavBackStack<Route>) = object : Navigator {
    override fun modifyBackStack(apply: NavBackStack<Route>.() -> Unit) = backStack.apply()
    override fun push(route: Route) = backStack.plusAssign(route)
    override fun pop() {
        if (backStack.size > 1)
            backStack.removeLastOrNull()
    }
}

@Composable
fun rememberNavigator(backStack: NavBackStack<Route>) = remember { Navigator(backStack) }