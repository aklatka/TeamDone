package com.example.teamdone.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.teamdone.R

data class TabItem(
    val title: String,
    val route: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
)

@Composable
fun TabBottomBar(
    navController: NavHostController,
    tabs: List<TabItem>
) {
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier
            .shadow(5.dp, RoundedCornerShape(0.dp))
    ) {
        tabs.forEachIndexed { index, tabItem ->
            NavigationBarItem(
                selected = index == selectedTabIndex,
                onClick = {
                    navController.navigate(tabItem.route)
                    selectedTabIndex = index
                },
                icon = {
                    TabIconButton(
                        title = tabItem.title,
                        icon = tabItem.icon,
                        selectedIcon = tabItem.selectedIcon,
                        selected = index == selectedTabIndex
                    )
                },
                label = {
                    Text(tabItem.title, color = colorResource(R.color.primary))
                },
//                colors = NavigationBarItemDefaults.colors(
//                    indicatorColor = Color.Transparent
//                ),
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun TabIconButton(
    title: String,
    icon: ImageVector,
    selectedIcon: ImageVector,
    selected: Boolean = false
) {

    Icon(
        imageVector = if(selected) selectedIcon else icon,
        contentDescription = title,
        tint = colorResource(R.color.primary),
    )
}