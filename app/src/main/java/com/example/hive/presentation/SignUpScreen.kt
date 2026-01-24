package com.example.hive.presentation

import android.graphics.Canvas
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hive.domain.ResultState
import com.example.hive.presentation.components.BackgroundWavesSmall
import com.example.hive.presentation.components.EmailTextField
import com.example.hive.presentation.components.PasswordTextField
import com.example.hive.presentation.navigation.Screens
import com.example.hive.ui.theme.PurpleBackground
import com.example.hive.ui.theme.PurpleDark
import com.example.hive.ui.theme.PurpleLight
import com.example.hive.ui.theme.PurpleMain
import com.example.hive.viewmodel.SignUpViewModel
import java.nio.file.Files.size

@Composable
fun SignUpScreen(navController: NavController, viewModel: SignUpViewModel = hiltViewModel()) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val hidePassword = remember { mutableStateOf(true) }
    val errorPassword = remember { mutableStateOf(false) }
    val errorEmail = remember { mutableStateOf(false) }
    val errorMessagePassword = remember { mutableStateOf("") }
    val repeatPassword = remember { mutableStateOf("") }
    val hideRepeatPassword = remember { mutableStateOf(true) }
    val errorRepeatPassword = remember { mutableStateOf(false) }
    val errorMessageRepeatPassword = remember { mutableStateOf("") }
    val errorMessageEmail = remember { mutableStateOf("") }
    val signUpState by viewModel.stateSignUp.collectAsState()
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleBackground)
    ) {
        BackgroundWavesSmall()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(96.dp))

            Text(
                text = "Регистрация",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Введите почту и\nпароль",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PurpleDark
            )

            Spacer(modifier = Modifier.height(24.dp))

            EmailTextField(
                label = "Почта",
                value = email.value,
                placeholder = "example@mail.ru",
                onValueChange = { email.value = it },
                isError = errorEmail.value,
                errorMessage = errorMessageEmail.value
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordTextField(
                label = "Пароль",
                value = password.value,
                placeholder = "password123!",
                onValueChange = { password.value = it },
                hide = hidePassword,
                isError = errorPassword.value,
                errorMessage = errorMessagePassword.value
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordTextField(
                label = "Повторите пароль",
                value = repeatPassword.value,
                placeholder = "password123!",
                onValueChange = { repeatPassword.value = it },
                hide = hideRepeatPassword,
                isError = errorRepeatPassword.value,
                errorMessage = errorMessageRepeatPassword.value
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (password.value.isEmpty()) {
                        errorPassword.value = true
                        errorMessagePassword.value = "Введите пароль"
                    } else if (repeatPassword.value.isEmpty()) {
                        errorRepeatPassword.value = true
                        errorMessageRepeatPassword.value = "Повторите пароль пароль"
                    } else if (repeatPassword.value != password.value) {
                        errorPassword.value = true
                        errorRepeatPassword.value = true
                        errorMessagePassword.value = "Пароли должны совпадать"
                        errorMessageRepeatPassword.value = "Пароли должны совпадать"
                    } else if (email.value.isEmpty()) {
                        errorEmail.value = true
                        errorMessageEmail.value = "Введите почту"
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
                        errorEmail.value = true
                        errorMessageEmail.value = "Некорректная почта"
                    } else {
                        errorPassword.value = false
                        errorRepeatPassword.value = false
                        errorEmail.value = false
                        errorMessagePassword.value = ""
                        errorMessageRepeatPassword.value = ""
                        errorMessageEmail.value = ""
                        viewModel.signUp(email.value, password.value)
                    }


                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PurpleDark
                )
            ) {
                Text(
                    text = "Далее",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
    LaunchedEffect(signUpState) {
        when (signUpState) {
            is ResultState.Error -> {
                Toast.makeText(
                    context,
                    (signUpState as ResultState.Error).message,
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            is ResultState.Success -> {
                navController.navigate(Screens.CreateProfileScreen.route)
            }

            else -> Unit
        }
    }

}
