package com.example.hive.presentation

import android.util.Patterns
import android.widget.Toast
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.hive.domain.ResultState
import com.example.hive.presentation.components.BackgroundWavesSmall
import com.example.hive.presentation.components.EmailTextField
import com.example.hive.presentation.components.PasswordTextField
import com.example.hive.ui.theme.PurpleBackground
import com.example.hive.ui.theme.PurpleDark
import com.example.hive.viewmodel.LoginViewModel
import com.example.hive.viewmodel.SignUpViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val hidePassword = remember { mutableStateOf(true) }
    val errorPassword = remember { mutableStateOf(false) }
    val errorEmail = remember { mutableStateOf(false) }
    val errorMessagePassword = remember { mutableStateOf("") }
    val errorRepeatPassword = remember { mutableStateOf(false) }
    val errorMessageRepeatPassword = remember { mutableStateOf("") }
    val errorMessageEmail = remember { mutableStateOf("") }
    val context = LocalContext.current
    val signInState by viewModel.stateSignIn.collectAsState()
    val flagSignIn = remember { mutableStateOf(false) }
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
                text = "Войти",
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

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (password.value.isEmpty()) {
                        errorPassword.value = true
                        errorMessagePassword.value = "Введите пароль"
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
                        viewModel.signIn(email.value, password.value)
                        flagSignIn.value = true
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
                    text = "Войти",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    LaunchedEffect(signInState) {
        when (signInState) {
            is ResultState.Error -> {
                Toast.makeText(
                    context,
                    (signInState as ResultState.Error).message,
                    Toast.LENGTH_LONG
                ).show()
            }

            is ResultState.Success -> {
                Toast.makeText(
                    context,
                    (signInState as ResultState.Success).message,
                    Toast.LENGTH_LONG
                ).show()
            }

            else -> Unit
        }
    }
}