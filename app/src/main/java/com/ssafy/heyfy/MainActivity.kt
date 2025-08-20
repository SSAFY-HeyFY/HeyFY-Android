package com.ssafy.heyfy

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ssafy.account.AccountScreen
import com.ssafy.card.CardDetailScreen
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.exchange.ExchangeScreen
import com.ssafy.login.LoginScreen
import com.ssafy.mento_club.MentoClubScreen
import com.ssafy.navigation.Destination
import com.ssafy.navigation.HeyFYNavHost
import com.ssafy.navigation.NavigationIntent
import com.ssafy.navigation.heyFYComposable
import com.ssafy.navigation.heyFYComposableWithFade
import com.ssafy.send_money.SendMoneyScreen
import com.ssafy.splash.SplashScreen
import com.ssafy.success.SuccessScreen
import com.ssafy.tips.TipsScreen
import com.ssafy.transaction.TransactionScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED // 세로 모드 고정
        initTimberLog()
        enableEdgeToEdge()

        setContent {
            HeyFYScreen()
        }
    }

    private fun initTimberLog() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    @Composable
    private fun HeyFYScreen(
        heyFYViewModel: HeyFYViewModel = hiltViewModel(),
    ) {
        val navController = rememberNavController()

        NavigationEffects(
            navigationChannel = heyFYViewModel.navigationChannel,
            navHostController = navController
        )

        HeyFYTheme {
            HeyFYNavHost(
                navController = navController,
                startDestination = Destination.Splash,
                modifier = Modifier.background(Color.White)
            ) {
                heyFYComposableWithFade(Destination.Main) {
                    MainScreen()
                }

                heyFYComposable(Destination.CardDetail) {
                    CardDetailScreen()
                }

                heyFYComposableWithFade(Destination.Splash) {
                    SplashScreen()
                }

                heyFYComposableWithFade(Destination.Login) {
                    LoginScreen()
                }

                heyFYComposable(Destination.Account) {
                    AccountScreen()
                }

                heyFYComposable(Destination.SendMoney) {
                    SendMoneyScreen()
                }
                heyFYComposable(Destination.Transaction) {
                    TransactionScreen()
                }

                heyFYComposable(Destination.MentoClub) {
                    MentoClubScreen()
                }

                heyFYComposable(Destination.Success) {
                    SuccessScreen()
                }

                heyFYComposable(Destination.Exchange) {
                    ExchangeScreen()
                }

                heyFYComposable(Destination.Tips) {
                    TipsScreen()
                }
            }
        }
    }

    @Composable
    private fun NavigationEffects(
        navigationChannel: Channel<NavigationIntent>,
        navHostController: NavHostController,
    ) {
        val activity = (LocalContext.current as? Activity)
        LaunchedEffect(activity, navHostController, navigationChannel) {
            navigationChannel.receiveAsFlow().collect { intent ->
                if (activity == null || activity.isFinishing) {
                    return@collect
                }

                when (intent) {
                    is NavigationIntent.NavigateBack -> {
                        navigateBackToRoute(intent, navHostController)
                    }

                    is NavigationIntent.NavigateTo -> {
                        navigateToRoute(intent, navHostController)
                    }
                }
            }
        }
    }

    private fun navigateBackToRoute(
        intent: NavigationIntent.NavigateBack,
        navHostController: NavHostController,
    ) {
        intent.route?.let { route ->
            navHostController.popBackStack(route, intent.inclusive)
        } ?: navHostController.popBackStack()
    }

    private fun navigateToRoute(
        intent: NavigationIntent.NavigateTo,
        navHostController: NavHostController,
    ) {
        navHostController.navigate(intent.route) {
            launchSingleTop = intent.isSingleTop

            if (intent.isBackStackCleared) {
                popUpTo(0) { inclusive = intent.inclusive }
            } else {
                intent.popUpToRoute?.let { popUpToRoute ->
                    popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                }
            }
        }
    }
}
