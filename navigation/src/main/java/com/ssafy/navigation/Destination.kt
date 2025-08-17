package com.ssafy.navigation

import com.ssafy.navigation.DestinationType.CARD_DETAIL
import com.ssafy.navigation.DestinationType.LOGIN
import com.ssafy.navigation.DestinationType.MAIN
import com.ssafy.navigation.DestinationType.SIGN_UP
import com.ssafy.navigation.DestinationType.SPLASH

sealed class Destination(protected val route: String, vararg params: String) {

    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }

    sealed class NoArgumentsDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
    }

    data object Splash : NoArgumentsDestination(SPLASH)

    data object Login : NoArgumentsDestination(LOGIN)

    data object SignUp : NoArgumentsDestination(SIGN_UP)

    data object Main : NoArgumentsDestination(MAIN)

    data object CardDetail : NoArgumentsDestination(CARD_DETAIL)
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
