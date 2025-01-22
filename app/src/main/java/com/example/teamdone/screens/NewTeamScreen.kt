package com.example.teamdone.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.teamdone.components.selectors.UserSelector
import com.example.teamdone.controls.PrimaryButton
import com.example.teamdone.controls.SecondaryButton
import com.example.teamdone.controls.TextInput

@Composable
fun NewTeamScreen(
    navController: NavHostController
) {
    var currentStep by remember { mutableIntStateOf(0) }

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var memberUids: List<String> by remember { mutableStateOf(listOf()) }

    var userIdentifier by remember { mutableStateOf("") }

    val steps = listOf("Opis zespołu", "Uczestnicy")

    @Composable
    fun DetailsForm() {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TextInput(
                value = name,
                onValueChange = {name = it},
                label = "Nazwa",
            )
            TextInput(
                value = description,
                onValueChange = {description = it},
                label = "Opis",
                lines = 5
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        AnimatedContent(
            targetState = currentStep,
            transitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth } // Przesunięcie z prawej strony
                ) togetherWith  slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth } // Przesunięcie w lewo
                )
            },
            label = ""
        ) { step ->
            Column {
                Text(
                    steps[step],
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp
                )
                HorizontalDivider(Modifier.padding(0.dp, 10.dp))

                when(step) {
                    0 -> DetailsForm()
                    1 -> UserSelector(
                        onSelectedUsersChange = {
                            memberUids = it.map { u ->
                                u.uid
                            }
                        }
                    )
                    else -> DetailsForm()
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            if(currentStep > 0) {
                SecondaryButton(
                    "Cofnij",
                    onClick = {
                        if(currentStep > 0) currentStep--
                    },
                    modifier = Modifier.fillMaxWidth(0.5f)
                )
            }
            PrimaryButton(
                "Dalej",
                onClick = {
                    if(currentStep < steps.size - 1) currentStep++
                },
                enabled = currentStep < steps.size - 1,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun TeamDetailsForm() {

}