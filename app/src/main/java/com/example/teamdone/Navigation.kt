package com.example.teamdone

enum class Screen {
    AUTH_LOGIN,
    AUTH_SIGNUP,
    DASHBOARD
}

sealed class NavigationItem(val route: String) {
    data object AuthLogin : NavigationItem(Screen.AUTH_LOGIN.name)
    data object AuthSignup : NavigationItem(Screen.AUTH_SIGNUP.name)
    data object Dashboard : NavigationItem(Screen.DASHBOARD.name)
}