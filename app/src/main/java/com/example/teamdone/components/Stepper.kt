package com.example.teamdone.components


import android.annotation.SuppressLint
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
import coil.compose.AsyncImagePainter
import com.example.teamdone.controls.PrimaryButton
import com.example.teamdone.controls.SecondaryButton

@SuppressLint("MutableCollectionMutableState")
@Composable
fun Stepper(
    steps: List<String>,
    valid: Boolean = true,
    finishButtonText: String = "Utwórz",
    loading: Boolean = false,
    onFinish: () -> Unit = {},
    header: (@Composable () -> Unit)? = null,
    content: @Composable (step: Int) -> Unit,
) {
    var currentStep by remember { mutableIntStateOf(0) }
    var isNextStep by remember { mutableStateOf(true) }

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
                if(isNextStep) {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth } // Przesunięcie z prawej strony
                    ) togetherWith  slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth } // Przesunięcie w lewo
                    )
                } else {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth } // Przesunięcie z prawej strony
                    ) togetherWith  slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth } // Przesunięcie w lewo
                    )
                }
            },
            label = ""
        ) { step ->
            Column {
                if(steps.isNotEmpty()) {
                    Text(
                        steps[step],
                        fontWeight = FontWeight.W600,
                        fontSize = 20.sp
                    )
                }
                header?.let {
                    it()
                }
                HorizontalDivider(Modifier.padding(0.dp, 10.dp))

                content(step)
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
                        if(currentStep > 0) {
                            isNextStep = false
                            currentStep--
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.5f)
                )
            }
            PrimaryButton(
                if(currentStep == steps.size - 1 || steps.isEmpty()) finishButtonText else "Dalej",
                onClick = {
                    if(currentStep < steps.size - 1) {
                        isNextStep = true
                        currentStep++
                    } else {
                        onFinish()
                    }
                },
                loading = loading,
                enabled = valid,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
