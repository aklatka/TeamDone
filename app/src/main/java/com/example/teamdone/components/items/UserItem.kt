package com.example.teamdone.components.items

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
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
import com.example.teamdone.data.User


@Composable
fun UserItem(
    user: User,
    onSelect: (user: User) -> Unit = {},
    onUnselect: (user: User) -> Unit = {},
    selected: Boolean = false,
    selectable: Boolean = false,
    actions: (@Composable () -> Unit)? = null
) {
    var checked by remember { mutableStateOf(selected) }

    Column(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageFromUrl(
                user.avatarUrl,
                imageModifier = Modifier
                    .size(35.dp)
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
                    Text(user.displayName(), fontSize = 15.sp, fontWeight = FontWeight.W500)
                    Text(user.email, fontSize = 12.sp)
                }
                Row {
                    if(selectable) {
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
                    actions?.let {
                        it()
                    }
                }
            }
        }
    }
}