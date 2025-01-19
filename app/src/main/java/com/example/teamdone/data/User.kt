package com.example.teamdone.data

data class User(
    val uid: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val username: String,
    val avatarUrl: String,
) {
    fun toMap(): HashMap<String, Any> {
        return hashMapOf(
            "uid" to uid,
            "firstname" to firstname,
            "lastname" to lastname,
            "email" to email,
            "avatarUrl" to avatarUrl
        )
    }

    companion object {
        fun fromMap(map: MutableMap<String, Any>?): User? {

            return map?.let {
                User(
                    uid = it["uid"].toString(),
                    firstname = it["firstname"].toString(),
                    lastname = it["lastname"].toString(),
                    email = it["email"].toString(),
                    username = it["username"].toString(),
                    avatarUrl = it["avatarUrl"].toString()
                )
            }
        }
    }
}

