package com.example.teamdone

object NavigationHistory {
    private val routes = mutableListOf<String>()

    fun pushRoute(route: String) {
        routes.add(route)
    }

    fun popRoute(): String? {
        return if(routes.isNotEmpty()) routes.removeLastOrNull() else null
    }

    fun getPreviousRoute(): String? {
        return if(routes.size > 1) routes[routes.size - 1] else null
    }
}