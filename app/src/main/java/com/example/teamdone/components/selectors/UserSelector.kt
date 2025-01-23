package com.example.teamdone.components.selectors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.teamdone.R
import com.example.teamdone.components.items.UserItem
import com.example.teamdone.controls.TextInput
import com.example.teamdone.data.User
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay

@Composable
fun UserSelector(
    onSelectedUsersChange: (users: ArrayList<User>) -> Unit,
    selectedUsers: List<User> = arrayListOf()
) {
    val firestore = FirebaseFirestore.getInstance()
    var users: List<User> by remember { mutableStateOf(arrayListOf()) }
    var userIdentifier by remember { mutableStateOf("") }
    val selectedUserList: ArrayList<User> = selectedUsers as ArrayList<User>
    var loading by remember { mutableStateOf(false) }

    fun fetchUsers(emailOrUsername: String) {
        loading = true
        firestore
            .collection("users")
            .where(
                Filter.and(
                    Filter.greaterThanOrEqualTo("email", emailOrUsername),
                    Filter.lessThan("email", emailOrUsername + 'z')
                ),
            )
            .limit(5)
            .get()
            .addOnSuccessListener { query ->
                users = query.documents.mapNotNull { User.fromMap(it.data) } as ArrayList<User>
                loading = false
            }
    }

    LaunchedEffect(firestore) {
        loading = true
        delay(200)
        fetchUsers("")
    }

    fun selectUser(user: User) {
        selectedUserList.add(user)
        onSelectedUsersChange(selectedUserList)
    }

    fun unselectUser(user: User) {
        selectedUserList.remove(user)
        onSelectedUsersChange(selectedUserList)
    }

    Column {
        TextInput(
            value = userIdentifier,
            onValueChange = {
                userIdentifier = it
                fetchUsers(userIdentifier)
            },
            label = "Email",
            placeholder = "Wyszukaj..."
        )
        if(loading) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = colorResource(R.color.primary)
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.padding(0.dp, 20.dp)
            ) {
                users.forEach { user ->
                    item {
                        UserItem(
                            user,
                            onSelect = {selectUser(it)},
                            onUnselect = {unselectUser(it)},
                            selected = selectedUserList
                                .contains(user),
                            selectable = true
                        )
                    }
                }
            }
        }
    }
}