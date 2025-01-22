package com.example.teamdone.data

data class Team(
    val id: String,
    val name: String,
    val description: String
) {

    companion object {
        fun fromMap(map: MutableMap<String, Any>?): Team? {

            return map?.let {
                Team(
                    id = it["id"].toString(),
                    name = it["name"].toString(),
                    description = it["description"].toString(),
                )
            }
        }
    }

}