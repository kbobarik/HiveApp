package com.example.hive.presentation.login

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hive.R
import com.example.hive.domain.ResultState
import com.example.hive.presentation.LoadingScreen
import com.example.hive.presentation.components.EmailTextField
import com.example.hive.presentation.components.PasswordTextField
import com.example.hive.presentation.navigation.Screens
import com.example.hive.ui.theme.BlueDarkest
import com.example.hive.ui.theme.Nimbus

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel(), navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val hidePassword = remember { mutableStateOf(true) }
    val errorPassword = remember { mutableStateOf(false) }
    val errorEmail = remember { mutableStateOf(false) }
    val errorMessagePassword = remember { mutableStateOf("") }
    val errorMessageEmail = remember { mutableStateOf("") }
    val context = LocalContext.current
    val signInState by viewModel.stateSignIn.collectAsState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val loadingFlag = remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    })
                },
        ) {

            // 🔹 Верхний блок (картинка-заглушка)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .background(Color.White),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    painter = painterResource(R.drawable.background),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillWidth
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            ) {

                Text(
                    text = "Welcome!",
                    fontFamily = Nimbus,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 📧 Email
                EmailTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    placeholder = "email",
                    isError = errorEmail.value,
                    errorMessage = errorMessageEmail.value

                )

                Spacer(modifier = Modifier.height(16.dp))

                // 🔒 Password
                PasswordTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    placeholder = "password",
                    hide = hidePassword,
                    isError = errorPassword.value,
                    errorMessage = errorMessagePassword.value

                )

                Spacer(modifier = Modifier.height(8.dp))

                // ❓ Forgot password
                Text(
                    text = "Forgot password?",
                    color = BlueDarkest,
                    fontFamily = Nimbus,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 🔵 Login button
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
                            errorEmail.value = false
                            errorMessagePassword.value = ""
                            errorMessageEmail.value = ""
                            viewModel.signIn(email.value, password.value)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = BlueDarkest
                    )
                ) {
                    Text(
                        text = "Login",
                        fontSize = 16.sp,
                        fontFamily = Nimbus,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))

                // 📝 Register
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Not a member? ",
                        fontFamily = Nimbus,
                        fontWeight = FontWeight.Normal,
                        color = Color.LightGray
                    )
                    Text(
                        text = "Register now",
                        color = BlueDarkest,
                        fontFamily = Nimbus,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable { navController.navigate(Screens.SignUpScreen.route) }
                    )
                }
            }
        }

        LaunchedEffect(signInState) {
            when (signInState) {
                is ResultState.Error -> {
                    loadingFlag.value = false
                    Toast.makeText(
                        context,
                        (signInState as ResultState.Error).message,
                        Toast.LENGTH_LONG
                    ).show()
                }

                is ResultState.Success -> {
                    loadingFlag.value = false
                    navController.navigate(Screens.MainScreens.route)
                }
                is ResultState.Loading -> {
                    loadingFlag.value = true
                }
                else -> Unit
            }
        }
        if (loadingFlag.value) {
            LoadingScreen()
        }
    }
}