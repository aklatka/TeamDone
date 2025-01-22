package com.example.teamdone.components.items

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teamdone.R
import com.example.teamdone.components.OverlappingAvatars
import com.example.teamdone.controls.PrimaryButton
import com.example.teamdone.data.Member
import com.example.teamdone.data.Team
import com.example.teamdone.services.MemberService
import com.google.firebase.auth.FirebaseAuth

@Composable
fun TeamCard(
    team: Team
) {
    var members: List<Member> by remember { mutableStateOf(arrayListOf()) }
    val user = FirebaseAuth.getInstance().currentUser

    LaunchedEffect(team) {
        MemberService
            .getInstance()
            .fetchAllForTeam(
                team.id
            ) {
                members = it
                Log.d("members", "TeamCard: ${members}")
            }
    }

    Surface(
        modifier = Modifier
            .border(1.dp, colorResource(R.color.primary), RoundedCornerShape(5.dp)),
        shape = RoundedCornerShape(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Surface(
//                color = colorResource(R.color.primary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    team.name,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier.padding(
                        top = 15.dp,
                        start = 15.dp,
                        end = 15.dp,
                        bottom = 5.dp,
                    ),
                    color = Color.Black
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxWidth()
                    .padding(
                        top = 0.dp,
                        start = 15.dp,
                        end = 15.dp,
                        bottom = 5.dp,
                    )
            ) {
                Row(
                    modifier = Modifier.wrapContentWidth()
                ) {
                    OverlappingAvatars(
                        members.map { it.user.avatarUrl },
                        overlap = 10.dp,
                        avatarSize = 30.dp
                    )
                }
                PrimaryButton("Otwórz", modifier = Modifier)
            }
        }
    }
}