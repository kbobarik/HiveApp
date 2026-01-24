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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Message
import com.example.hive.R
import com.example.hive.ui.theme.PurpleDark
import com.example.hive.ui.theme.PurpleMain

@Composable
fun PasswordTextField(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    hide: MutableState<Boolean>,
    isError: Boolean,
    errorMessage: String
) {
    Column {
        Text(
            text = label,
            fontSize = 14.sp,
            color = PurpleDark
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(placeholder)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (hide.value) PasswordVisualTransformation() else VisualTransformation.None,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            isError = isError,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PurpleMain,
                unfocusedBorderColor = PurpleMain,
                cursorColor = PurpleMain
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(if (hide.value) R.drawable.eye else R.drawable.close_eye),
                    "",
                    Modifier.clickable {
                        hide.value = !hide.value
                    }
                )

            }
        )
        if (isError) {
            Text(errorMessage, fontSize = 10.sp, color = Color.Red)
        }
    }
}
