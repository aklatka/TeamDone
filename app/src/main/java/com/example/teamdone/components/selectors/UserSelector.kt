package com.example.teamdone.components.selectors

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teamdone.R
import com.example.teamdone.components.ImageFromUrl
import com.example.teamdone.controls.TextInput
import com.example.teamdone.data.User
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun UserSelector(
    onSelectedUsersChange: (users: ArrayList<User>) -> Unit
) {
    val firestore = FirebaseFirestore.getInstance()
    var users: List<User> by remember { mutableStateOf(arrayListOf()) }
    var userIdentifier by remember { mutableStateOf("") }
    val selectedUsers: ArrayList<User> = arrayListOf()

    fun fetchUsers(emailOrUsername: String) {
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
            }
    }

    fun selectUser(user: User) {
        selectedUsers.add(user)
        onSelectedUsersChange(selectedUsers)
    }

    fun unselectUser(user: User) {
        selectedUsers.remove(user)
        onSelectedUsersChange(selectedUsers)
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
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.padding(0.dp, 20.dp)
        ) {
            users.forEach { user ->
                item {
                    UserItem(
                        user,
                        onSelect = {selectUser(it)},
                        onUnselect = {unselectUser(it)}
                    )
                }
            }
        }
    }
}

@Composable
fun UserItem(
    user: User,
    onSelect: (user: User) -> Unit,
    onUnselect: (user: User) -> Unit,
) {
    var checked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
    ) {
        Row() {
            ImageFromUrl(
                user.avatarUrl,
                imageModifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Black, CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(user.displayName(), fontSize = 20.sp, fontWeight = FontWeight.Medium)
                    Text(user.email, fontSize = 14.sp)
                }
                Row {
                    IconToggleButton(
                        checked = checked,
                        onCheckedChange = {
                            checked = it

                            if(checked) {
                                onSelect(user)
                            } else {
                                onUnselect(user)
                            }
                        }
                    ) {
                        Icon(
                            imageVector =
                                if(checked) Icons.Default.Remove
                                else Icons.Default.Add,
                            contentDescription = "",
                            tint = if(!checked) colorResource(R.color.primary) else Color.Red
                        )
                    }
                }
            }
        }
    }
}