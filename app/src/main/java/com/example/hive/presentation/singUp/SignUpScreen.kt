package com.example.hive.presentation.singUp

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hive.domain.ResultState
import com.example.hive.presentation.LoadingScreen
import com.example.hive.presentation.components.EmailTextField
import com.example.hive.presentation.components.PasswordTextField
import com.example.hive.presentation.navigation.Screens
import com.example.hive.presentation.singUp.SignUpViewModel
import com.example.hive.ui.theme.BlueDarkest
import com.example.hive.ui.theme.BlueMedium
import com.example.hive.ui.theme.GreyLight
import com.example.hive.ui.theme.Nimbus

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
    val loadingFlag = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

     Box(Modifier.fillMaxSize()){
         Column(
             modifier = Modifier
                 .fillMaxSize()
                 .background(Color.White)
                 .padding(horizontal = 24.dp, vertical = 30.dp)
                 .windowInsetsPadding(WindowInsets.navigationBars)
                 .pointerInput(Unit) {
                     detectTapGestures(onTap = {
                         keyboardController?.hide()
                         focusManager.clearFocus()
                     })
                 },
         ) {

             Spacer(modifier = Modifier.height(32.dp))

             // 🔹 Title
             Text(
                 text = "Sign up",
                 fontSize = 30.sp,
                 fontWeight = FontWeight.Bold,
                 fontFamily = Nimbus
             )

             Spacer(modifier = Modifier.height(6.dp))

             Text(
                 text = "Create an account to get started",
                 fontSize = 15.sp,
                 color = GreyLight,
                 fontFamily = Nimbus,
                 fontWeight = FontWeight.Medium
             )

             Spacer(modifier = Modifier.height(32.dp))

             Text(
                 text = "Email Address",
                 fontSize = 15.sp,
                 fontWeight = FontWeight.Bold,
                 fontFamily = Nimbus
             )

             Spacer(modifier = Modifier.height(6.dp))

             EmailTextField(
                 value = email.value,
                 onValueChange = { email.value = it },
                 placeholder = "email",
                 isError = errorEmail.value,
                 errorMessage = errorMessageEmail.value
             )

             Spacer(modifier = Modifier.height(20.dp))

             // 🔒 Password
             Text(
                 text = "Password",
                 fontSize = 15.sp,
                 fontWeight = FontWeight.Bold,
                 fontFamily = Nimbus
             )

             Spacer(modifier = Modifier.height(6.dp))

             PasswordTextField(
                 value = password.value,
                 onValueChange = { password.value = it },
                 placeholder = "password",
                 hide = hidePassword,
                 isError = errorPassword.value,
                 errorMessage = errorMessagePassword.value

             )

             Spacer(modifier = Modifier.height(16.dp))

             // 🔒 Confirm password
             PasswordTextField(
                 value = repeatPassword.value,
                 onValueChange = { repeatPassword.value = it },
                 placeholder = "confirm password",
                 hide = hideRepeatPassword,
                 isError = errorRepeatPassword.value,
                 errorMessage = errorMessageRepeatPassword.value

             )
             Spacer(modifier = Modifier.weight(1f))

             Button(
                 onClick = {
                     errorPassword.value = false
                     errorEmail.value = false
                     errorRepeatPassword.value = false
                     if (email.value.isEmpty()) {
                         errorEmail.value = true
                         errorMessageEmail.value = "Введите почту"
                     }
                     if (!Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
                         errorEmail.value = true
                         errorMessageEmail.value = "Некорректная почта"
                     }
                     if (password.value.isEmpty()) {
                         errorPassword.value = true
                         errorMessagePassword.value = "Введите пароль"
                     }
                     if (repeatPassword.value.isEmpty()) {
                         errorRepeatPassword.value = true
                         errorMessageRepeatPassword.value = "Повторите пароль"
                     }
                     if (password.value != repeatPassword.value) {
                         errorRepeatPassword.value = true
                         errorPassword.value = true
                         errorMessagePassword.value = "Пароли должны совпадать"
                         errorMessageRepeatPassword.value = "Пароли должны совпадать"
                     }
                     if (!errorEmail.value && !errorPassword.value && !errorRepeatPassword.value) {
                         errorMessagePassword.value = ""
                         errorMessageEmail.value = ""
                         errorMessageRepeatPassword.value = ""
                         viewModel.signUp(email.value, password.value)
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
                     text = "Sign Up",
                     fontSize = 16.sp,
                     fontFamily = Nimbus,
                     fontWeight = FontWeight.Bold
                 )
             }

             LaunchedEffect(signUpState) {
                 when (signUpState) {
                     is ResultState.Error -> {
                         loadingFlag.value = false
                         Toast.makeText(
                             context,
                             (signUpState as ResultState.Error).message,
                             Toast.LENGTH_LONG
                         ).show()
                     }

                     is ResultState.Success -> {
                         loadingFlag.value = false
                         navController.navigate(Screens.CreateProfileScreen.route){
                             popUpTo(0)
                         }
                     }

                     is ResultState.Loading -> {
                         loadingFlag.value = true
                     }
                     else -> Unit
                 }
             }


         }
         if (loadingFlag.value){
             LoadingScreen()
         }
     }

}
