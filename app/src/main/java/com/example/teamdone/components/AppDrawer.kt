package com.example.teamdone.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.teamdone.AuthorizedNavigationItem
import com.example.teamdone.NavigationItem
import com.example.teamdone.states.AppViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    navController: NavHostController,
    authNavController: NavHostController,
    formMode: Boolean = false,
    topBarTitle: String = "Pulpit",
    viewModel: AppViewModel = hiltViewModel(),
    content: @Composable () -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var title by remember { mutableStateOf(topBarTitle) }

    LaunchedEffect(topBarTitle) {
        title = topBarTitle
    }

    fun tabNavigate(
        tabTitle: String,
        destination: String
    ) {
        title = tabTitle
        navController.navigate(destination)
        scope.launch {
            drawerState.apply {
                close()
            }
        }
    }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Text("Menu", modifier = Modifier.padding(16.dp))
                HorizontalDivider()
                Column(
                    modifier = Modifier.fillMaxHeight(),
                ) {
                    NavigationDrawerItem(
                        label = {
                            Text("Pulpit")
                        },
                        selected = false,
                        onClick = {
                            tabNavigate("Pulpit", AuthorizedNavigationItem.Dashboard.route)
                        }
                    )
                    NavigationDrawerItem(
                        label = {
                            Text("Zespoły")
                        },
                        selected = false,
                        onClick = {
                            tabNavigate("Zespoły", AuthorizedNavigationItem.TeamList.route)
                        }
                    )
                    HorizontalDivider()
                    Column(
                        modifier = Modifier.padding(
                            bottom = 20.dp
                        )
                    ) {
                        NavigationDrawerItem(
                            label = {
                                Text("Ustawienia")
                            },
                            selected = false,
                            onClick = {
                                tabNavigate("Ustawienia", AuthorizedNavigationItem.Settings.route)
                            }
                        )
                        NavigationDrawerItem(
                            label = {
                                Text("Wyloguj się")
                            },
                            selected = false,
                            onClick = {
                                viewModel.logout()
                                authNavController.navigate(NavigationItem.AuthLogin.route) {
                                    popUpTo(0)
                                }
                            }
                        )
                    }
                }
            }
        },
        drawerState = drawerState,
        gesturesEnabled = !formMode
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier
                        .shadow(5.dp, shape = RoundedCornerShape(0.dp)),
                    title = {
                        Text(title)
                    },
                    navigationIcon = {
                        if(formMode) {
                            IconButton(
                                onClick = {
                                    navController.popBackStack()
                                }
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Cofnij"
                                )
                            }
                        } else {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.apply {
                                            if(isClosed) open() else close()
                                        }
                                    }
                                }
                            ) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    }
                )
            }
        ) { contentPadding ->

            Surface(
                modifier = Modifier.padding(contentPadding)
                    .padding(vertical = 0.dp)
            ) {
                content()
            }
        }
    }

}