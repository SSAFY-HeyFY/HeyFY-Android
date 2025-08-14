package com.ssafy.heyfy

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.common.R as commonR

@Composable
internal fun MainScreen() {
    BottomMenuScreen()
}

@Composable
private fun BottomMenuScreen() {
    val navMenus = remember {
        listOf(
            NavigationData("Home", commonR.drawable.icon_home),
            NavigationData("ID", commonR.drawable.icon_id),
            NavigationData("Finance", commonR.drawable.icon_finance),
        )
    }

    var selectedItem by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                menus = navMenus,
                selectedIndex = selectedItem,
                onItemSelected = { selectedItem = it }
            )
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

@Composable
private fun BottomNavigationBar(
    menus: List<NavigationData>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    BottomAppBar {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(1.dp, Color(0xFFE5E7EB)),
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
    onClick: () -> Unit
) {
    val color = if (isSelected) Color(0xFF9333EA) else Color(0xFF9CA3AF)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(vertical = 16.dp)
            .clickable(onClick = onClick)
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
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontFamily = FontFamily(Font(commonR.font.pretendard_regular)),
                fontWeight = FontWeight(400),
                color = color,
                textAlign = TextAlign.Center
            )
        )
    }
}

private data class NavigationData(
    val title: String,
    @DrawableRes val icon: Int,
)
