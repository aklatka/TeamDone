package com.example.teamdone

enum class Screen {
    AUTH_LOGIN,
    AUTH_SIGNUP,
    AUTHORIZED,
    DASHBOARD,
    SETTINGS,
    TEAM_LIST,
    NEW_TEAM,
    FORM
}

sealed class NavigationItem(val route: String) {
    data object AuthLogin : NavigationItem(Screen.AUTH_LOGIN.name)
    data object AuthSignup : NavigationItem(Screen.AUTH_SIGNUP.name)
    data object Authorized : NavigationItem(Screen.AUTHORIZED.name)
}

sealed class AuthorizedNavigationItem(val route: String) {
    data object Dashboard : AuthorizedNavigationItem(Screen.DASHBOARD.name)
    data object Settings : AuthorizedNavigationItem(Screen.SETTINGS.name)
    data object TeamList : AuthorizedNavigationItem(Screen.TEAM_LIST.name)
    data object NewTeam : AuthorizedNavigationItem(Screen.NEW_TEAM.name)
}