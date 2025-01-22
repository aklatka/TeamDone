package com.example.teamdone.controls

import android.graphics.drawable.shapes.Shape
import android.text.InputType
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teamdone.R
import com.example.teamdone.ui.theme.InputBgColor
import com.example.teamdone.ui.theme.InputBorderColor

@Preview(showBackground = true)
@Composable
fun TextInput(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    label: String = "Email",
    placeholder: String = "",
    inputType: Int = InputType.TYPE_TEXT_VARIATION_NORMAL,
    valueVisible: Boolean = false,
    error: String? = null,
    lines: Int = 1,
) {
    var isFocused by remember { mutableStateOf(false) }
    var isValueVisible by remember { mutableStateOf(
        valueVisible || inputType == InputType.TYPE_TEXT_VARIATION_NORMAL
    ) }
    val focusRequester = remember { FocusRequester() }

    Column {
        Text(label, fontSize = 15.sp)
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    if (isFocused) colorResource(R.color.primary) else InputBorderColor,
                    RoundedCornerShape(5)
                )
                .background(InputBgColor, RoundedCornerShape(5))
                .focusRequester(focusRequester)
                .onFocusChanged { state ->
                    isFocused = state.isFocused
                },
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(placeholder)
            },
            minLines = lines,
            maxLines = lines,
            shape = RoundedCornerShape(5),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = InputBgColor,
                focusedContainerColor = InputBgColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            visualTransformation =
                if(isValueVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
            trailingIcon = {
                if(inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    val image = if(isValueVisible) Icons.Filled.VisibilityOff
                                else Icons.Filled.Visibility

                    val description =
                        if(isValueVisible) stringResource(R.string.hide_password)
                        else stringResource(R.string.show_password)

                    IconButton(onClick = {isValueVisible = !isValueVisible}) {
                        Icon(imageVector = image, description)
                    }
                }
            }
        )
        error?.let {
            Text(
                it,
                color = Color.Red,
                fontSize = 13.sp
            )
        }
    }
}
