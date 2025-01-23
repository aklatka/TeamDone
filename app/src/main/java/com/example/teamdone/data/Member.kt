package com.example.teamdone.data

data class Member(
    val user: User,
    val inviteAccepted: Boolean,
    val inviteStatus: String = INVITE_STATUS_PENDING,
    val teamId: String,
    val owner: Boolean = false
) {
    companion object {
        const val INVITE_STATUS_REJECTED = "rejected"
        const val INVITE_STATUS_ACCEPTED = "accepted"
        const val INVITE_STATUS_PENDING = "pending"
    }
}