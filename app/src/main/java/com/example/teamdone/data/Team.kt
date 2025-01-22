package com.example.teamdone.data

data class Team(
    val id: String,
    val name: String,
    val description: String,
    val memberUids: List<String>,
) {

    companion object {
        fun fromMap(map: MutableMap<String, Any>?): Team? {

            return map?.let {
                val memberUids: List<String> = listOf(it["membersUids"]).map { item ->
                    item.toString()
                }

                Team(
                    id = it["id"].toString(),
                    name = it["name"].toString(),
                    description = it["description"].toString(),
                    memberUids = memberUids
                )
            }
        }
    }

}