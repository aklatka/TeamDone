package com.example.teamdone

enum class Screen {
    AUTH_LOGIN,
    AUTH_SIGNUP,
    AUTHORIZED,
    DASHBOARD,
    SETTINGS,
    TEAM_LIST,
    NEW_TEAM,
    TEAM,
    TEAM_DASHBOARD,
    TEAM_TASK_LIST,
    TEAM_MEMBER_LIST,
    TEAM_ADD_TASKS,
    TEAM_ADD_TASKS_FOR_MEMBER,
    TEAM_ADD_MEMBERS
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
    data object Team : AuthorizedNavigationItem(Screen.TEAM.name)
    data object TeamDashboard : AuthorizedNavigationItem(Screen.TEAM_DASHBOARD.name)
    data object TeamTaskList : AuthorizedNavigationItem(Screen.TEAM_TASK_LIST.name)
    data object TeamAddTasks : AuthorizedNavigationItem(Screen.TEAM_ADD_TASKS.name)
    data object TeamAddTasksForMember : AuthorizedNavigationItem(Screen.TEAM_ADD_TASKS_FOR_MEMBER.name)
    data object TeamMemberList : AuthorizedNavigationItem(Screen.TEAM_MEMBER_LIST.name)
    data object TeamAddMembers : AuthorizedNavigationItem(Screen.TEAM_ADD_MEMBERS.name)
}