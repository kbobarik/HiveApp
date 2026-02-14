package com.example.hive.presentation.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hive.ui.theme.BlueDarkest
import com.example.hive.ui.theme.BlueMedium
import com.example.hive.ui.theme.GreyLight
import com.example.hive.ui.theme.Nimbus

@Composable
fun EmailTextField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMessage: String
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    placeholder, color = GreyLight,
                    fontFamily = Nimbus, fontWeight = FontWeight.Medium
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            textStyle = TextStyle(fontWeight = FontWeight.Medium, fontFamily = Nimbus, fontSize = 15.sp),
            isError = isError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlueDarkest,
                unfocusedBorderColor = GreyLight,
                cursorColor = BlueDarkest
            )
        )
        if (isError) {
            Text(
                errorMessage,
                fontSize = 10.sp,
                color = Color.Red,
                fontFamily = Nimbus,
                fontWeight = FontWeight.Medium
            )
        }

    }
}
