package com.example.teamdone.data

data class Member(
    val user: User,
    val inviteAccepted: Boolean,
    val teamId: String
)