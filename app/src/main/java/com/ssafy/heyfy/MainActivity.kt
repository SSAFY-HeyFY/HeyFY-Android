package com.ssafy.heyfy

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ssafy.account.AccountScreen
import com.ssafy.auth.AuthScreen
import com.ssafy.card.CardDetailScreen
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.exchange.ExchangeScreen
import com.ssafy.heyfy.manager.FCMTokenManager
import com.ssafy.heyfy.manager.NotificationPermissionMonitor
import com.ssafy.heyfy.utils.NotificationPermissionUtil
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
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var fcmTokenManager: FCMTokenManager
    private lateinit var notificationPermissionMonitor: NotificationPermissionMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED // 세로 모드 고정
        initTimberLog()
        enableEdgeToEdge()
        initDependencies()
        initFCM()

        setContent {
            HeyFYScreen()
        }
    }

    private fun initTimberLog() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initDependencies() {
        fcmTokenManager = FCMTokenManager()
        notificationPermissionMonitor = NotificationPermissionMonitor()
    }

    private fun initFCM() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!NotificationPermissionUtil.hasNotificationPermission(this)) {
                NotificationPermissionUtil.requestNotificationPermission(this) { isGranted ->
                    if (isGranted) {
                        Timber.d("Notification permission granted")
                    } else {
                        Timber.w("Notification permission denied")
                    }
                }
            } else {
                Timber.d("Notification permission already granted")
            }
        }
    }

    @Composable
    private fun HeyFYScreen(
        heyFYViewModel: HeyFYViewModel = hiltViewModel(),
    ) {
        val navController = rememberNavController()

        val hasNotificationPermission by notificationPermissionMonitor
            .observePermissionState(LocalContext.current)
            .collectAsState(initial = false)

        LaunchedEffect(hasNotificationPermission) {
            if (hasNotificationPermission) {
                val token = fcmTokenManager.getToken()
                token?.let {
                    Timber.d("FCM Token initialized: $it")
                    // TODO: Send token to your server
                }
            } else {
                fcmTokenManager.deleteToken()
                // TODO : Send token to your server
            }
        }

        NavigationEffects(
            navigationChannel = heyFYViewModel.navigationChannel,
            navHostController = navController
        )

        HeyFYTheme {
            Box(modifier = Modifier.background(Color.White)) {
                HeyFYNavHost(
                    navController = navController,
                    startDestination = Destination.Splash,
                    modifier = Modifier.background(Color.Transparent)
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

                    heyFYComposable(Destination.Auth) {
                        AuthScreen()
                    }
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
