package com.ssafy.navigation

import com.ssafy.navigation.DestinationType.HOME
import com.ssafy.navigation.DestinationType.ID
import com.ssafy.navigation.DestinationType.FINANCE

sealed class Destination(protected val route: String, vararg params: String) {

    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }

    sealed class NoArgumentsDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
    }

    data object Home : NoArgumentsDestination(HOME)

    data object Id : NoArgumentsDestination(ID)

    data object Finance : NoArgumentsDestination(FINANCE)
}


private fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)

    params.forEach {
        it.second?.toString()?.let { arg ->
            builder.append("/$arg")
        }
    }

    return builder.toString()
}
