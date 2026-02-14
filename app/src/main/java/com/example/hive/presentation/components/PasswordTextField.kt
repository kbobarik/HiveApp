package com.example.hive.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Message
import com.example.hive.R
import com.example.hive.ui.theme.BlueDarkest
import com.example.hive.ui.theme.BlueMedium
import com.example.hive.ui.theme.GreyLight
import com.example.hive.ui.theme.Nimbus

@Composable
fun PasswordTextField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    hide: MutableState<Boolean>,
    isError: Boolean,
    errorMessage: String
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    placeholder,
                    color = GreyLight,
                    fontFamily = Nimbus,
                    fontWeight = FontWeight.Medium
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            textStyle = TextStyle(fontWeight = FontWeight.Medium, fontFamily = Nimbus, fontSize = 15.sp),
            visualTransformation = if (hide.value) PasswordVisualTransformation() else VisualTransformation.None,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            isError = isError,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlueDarkest,
                unfocusedBorderColor = GreyLight,
                cursorColor = BlueMedium
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(if (hide.value) R.drawable.eye else R.drawable.close_eye),
                    "",
                    tint = GreyLight,
                    modifier = Modifier.clickable {
                        hide.value = !hide.value
                    }
                )

            }
        )
        if (isError) {
            Text(
                errorMessage,
                fontSize = 13.sp,
                color = Color.Red,
                fontFamily = Nimbus,
                fontWeight = FontWeight.Medium
            )
        }

    }
}
