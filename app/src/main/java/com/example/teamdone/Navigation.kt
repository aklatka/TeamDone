package com.example.teamdone

enum class Screen {
    AUTH_LOGIN,
    AUTH_SIGNUP,
    MAIN,
    DASHBOARD,
    SETTINGS
}

sealed class NavigationItem(val route: String) {
    data object AuthLogin : NavigationItem(Screen.AUTH_LOGIN.name)
    data object AuthSignup : NavigationItem(Screen.AUTH_SIGNUP.name)
    data object Main : NavigationItem(Screen.MAIN.name)
}

sealed class AuthorizedNavigationItem(val route: String) {
    data object Dashboard : AuthorizedNavigationItem(Screen.DASHBOARD.name)
    data object Settings : AuthorizedNavigationItem(Screen.SETTINGS.name)
}