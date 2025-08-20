package com.ssafy.navigation

import com.ssafy.navigation.DestinationParamConstants.MENTO_CLUB_TYPE
import com.ssafy.navigation.DestinationType.ACCOUNT
import com.ssafy.navigation.DestinationType.CARD_DETAIL
import com.ssafy.navigation.DestinationType.EXCHANGE
import com.ssafy.navigation.DestinationType.FINANCE
import com.ssafy.navigation.DestinationType.LOGIN
import com.ssafy.navigation.DestinationType.MAIN
import com.ssafy.navigation.DestinationType.MENTO_CLUB
import com.ssafy.navigation.DestinationType.SEND_MONEY
import com.ssafy.navigation.DestinationType.SPLASH
import com.ssafy.navigation.DestinationType.SUCCESS
import com.ssafy.navigation.DestinationType.TIPS
import com.ssafy.navigation.DestinationType.TRANSACTION

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

    data object Account : NoArgumentsDestination(ACCOUNT)

    data object Main : NoArgumentsDestination(MAIN)

    data object CardDetail : NoArgumentsDestination(CARD_DETAIL)

    data object SendMoney : NoArgumentsDestination(SEND_MONEY)

    data object Transaction : NoArgumentsDestination(TRANSACTION)

    data object MentoClub : Destination(MENTO_CLUB, MENTO_CLUB_TYPE) {
        operator fun invoke(type: String): String = route.appendParams(MENTO_CLUB_TYPE to type)
    }

    data object Success : NoArgumentsDestination(SUCCESS)

    data object Finance : NoArgumentsDestination(FINANCE)

    data object Exchange : NoArgumentsDestination(EXCHANGE)

    data object Tips : NoArgumentsDestination(TIPS)
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
