package com.ssafy.heyfy

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.finance.FinanceScreen
import com.ssafy.home.HomeScreen
import com.ssafy.id.IdScreen

@Composable
internal fun MainScreen(
    viewModel: MainViewModel = hiltViewModel<MainViewModel>()
) {

    val selectedItem by viewModel.selectedItem.collectAsStateWithLifecycle()

    BottomMenuScreen(
        navMenus = viewModel.navMenus,
        selectedItem = selectedItem,
        onItemSelected = viewModel::updateSelectedItem
    )
}

@Composable
private fun BottomMenuScreen(
    navMenus: List<NavigationData>,
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .systemBarsPadding(),

        bottomBar = {
            BottomNavigationBar(
                menus = navMenus,
                selectedIndex = selectedItem,
                onItemSelected = { onItemSelected(it) }
            )
        },
        containerColor = Color.White,
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

@Composable
private fun BottomNavigationBar(
    menus: List<NavigationData>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
) {
    BottomAppBar(
        modifier = Modifier
            .border(1.dp, Color(0xFFE5E7EB)),
        containerColor = Color.White,
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            menus.forEachIndexed { index, menu ->
                val isSelected = selectedIndex == index
                NavigationItem(
                    title = menu.title,
                    iconRes = menu.icon,
                    isSelected = isSelected,
                    onClick = { onItemSelected(index) }
                )
            }
        }
    }
}

@Composable
private fun NavigationItem(
    title: String,
    @DrawableRes iconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val color = if (isSelected) Color(0xFF9333EA) else Color(0xFF9CA3AF)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(vertical = 16.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = color
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            style = HeyFYTheme.typography.bodyS2,
            color = color,
        )
    }
}

data class NavigationData(
    val title: String,
    @DrawableRes val icon: Int,
)
