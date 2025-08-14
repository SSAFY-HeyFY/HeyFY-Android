package com.ssafy.heyfy

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
internal fun MainScreen(navController: NavController) {

    BottomMenuScreen(navController = navController)
}

@Composable
fun BottomMenuScreen(navController: NavController) {
    val navMenus = listOf(
        NavigationData("Home", FaIcons.HOME),
        NavigationData("ID", FaIcons.ID),
        NavigationData("Finance", FaIcons.FINANCE),
    )

    var selectedItem by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    navMenus.forEachIndexed { index, menu ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable { selectedItem = index }
                        ) {


                            Spacer(modifier = Modifier.padding(2.dp))

                            Text(
                                text = menu.title,
                                )
                        }
                    }

                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (selectedItem) {
                0 -> HomeScreen()
                1 -> IdScreen()
                2 -> FinanceScreen()
            }
        }
    }
}

data class NavigationData(
    val title: String,
    val icon: FaIcons,
)

enum class FaIcons {
    HOME, ID, FINANCE
}
