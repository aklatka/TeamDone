package com.example.teamdone.components.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teamdone.R
import com.example.teamdone.data.Member
import com.example.teamdone.ui.theme.ErrorColor
import com.example.teamdone.ui.theme.PrimaryColor
import com.example.teamdone.ui.theme.SuccessColor

@Composable
fun MemberItem(
    member: Member,
    onAddTask: (member: Member) -> Unit = {},
    onDelete: (member: Member) -> Unit = {},
) {
    var inviteStatusMessage by remember { mutableStateOf("") }
    var inviteStatusColor by remember { mutableStateOf(Color.Black) }

    LaunchedEffect(member) {
        when(member.inviteStatus) {
            Member.INVITE_STATUS_PENDING -> {
                inviteStatusColor = PrimaryColor
                inviteStatusMessage = "Wysłano zaproszenie"
            }
            Member.INVITE_STATUS_REJECTED -> {
                inviteStatusColor = ErrorColor
                inviteStatusMessage = "Odrzucono zaproszenie"
            }
            else -> {
                inviteStatusColor = Color.Transparent
                inviteStatusMessage = ""
            }
        }
    }

    Column(
        modifier = Modifier.padding(bottom = 10.dp)
    ) {
        UserItem(
            member.user,
            actions = {
                if(member.inviteAccepted) {
                    IconButton(
                        onClick = {
                            onAddTask(member)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddTask,
                            contentDescription = "Przypisz zadanie",
                            tint = colorResource(R.color.primary)
                        )
                    }
                    IconButton(
                        onClick = {
                            onDelete(member)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = "Usuń uczestnika",
                            tint = Color.Red
                        )
                    }
                }
            }
        )
        Row(
            modifier = Modifier.padding(horizontal = 50.dp)
        ) {
           if(!member.inviteAccepted) {
               Text(
                   inviteStatusMessage,
                   fontSize = 12.sp,
                   color = inviteStatusColor
               )
           }
        }
    }
}