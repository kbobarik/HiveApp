package com.example.hive.presentation.createProfile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Patterns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
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
import coil.compose.AsyncImage
import com.example.hive.domain.ResultState
import com.example.hive.presentation.components.EmailTextField
import com.example.hive.presentation.components.PasswordTextField
import com.example.hive.presentation.createProfile.CreateProfileViewModel
import com.example.hive.presentation.navigation.Screens
import com.example.hive.ui.theme.BlueDarkest
import com.example.hive.ui.theme.GreyLight
import com.example.hive.ui.theme.Nimbus
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.output.ByteArrayOutputStream

@Composable
fun CreateProfileScreen(navController: NavController,viewModel: CreateProfileViewModel = hiltViewModel()) {
    val nickname = remember { mutableStateOf("") }
    val errorMessageNickname = remember { mutableStateOf("") }
    val errorNickname = remember { mutableStateOf(false) }
    val avatarUri = remember { mutableStateOf<Uri?>(null) }
    val avatar = remember { mutableStateOf(byteArrayOf()) }
    val context = LocalContext.current
    val addAvatarState by viewModel.stateAddAvatar.collectAsState()
    val addUserState by viewModel.stateAddUser.collectAsState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val imagePicker =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri == null) return@rememberLauncherForActivityResult
            avatar.value = bitmapToByteArray(context, uri)
            avatarUri.value = uri

        }
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
            text = "Create profile",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Nimbus
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Select avatar and write nickname",
            fontSize = 15.sp,
            color = GreyLight,
            fontFamily = Nimbus,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .background(BlueDarkest.copy(alpha = 0.4f))
                .border(2.dp, BlueDarkest, CircleShape)
                .clickable { imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
            contentAlignment = Alignment.Center
        ) {
            if (avatarUri.value != null) {
                AsyncImage(
                    model = avatarUri.value,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.AddAPhoto,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Выбрать",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        /// Никнейм

        Column {
            OutlinedTextField(
                value = nickname.value,
                onValueChange = { nickname.value = it },
                placeholder = {
                    Text(
                        "nickname", color = GreyLight,
                        fontFamily = Nimbus, fontWeight = FontWeight.Medium
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                textStyle = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontFamily = Nimbus,
                    fontSize = 15.sp
                ),
                isError = errorNickname.value,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BlueDarkest,
                    unfocusedBorderColor = GreyLight,
                    cursorColor = BlueDarkest
                )
            )
            if (errorNickname.value) {
                Text(
                    errorMessageNickname.value,
                    fontSize = 10.sp,
                    color = Color.Red,
                    fontFamily = Nimbus,
                    fontWeight = FontWeight.Medium
                )
            }

        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (avatar.value.isNotEmpty() && nickname.value != "") {
                    viewModel.addUser(nickname.value)
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
                text = "Create",
                fontSize = 16.sp,
                fontFamily = Nimbus,
                fontWeight = FontWeight.Bold
            )
        }

    }

    LaunchedEffect(addAvatarState) {
        when (addAvatarState) {
            is ResultState.Error -> {
                Toast.makeText(
                    context,
                    (addAvatarState as ResultState.Error).message,
                    Toast.LENGTH_LONG
                ).show()
            }

            is ResultState.Success -> {
                navController.navigate(Screens.MainScreens.route)
            }

            else -> Unit
        }
    }
    LaunchedEffect(addUserState) {
        when (addUserState) {
            is ResultState.Error -> {
                Toast.makeText(
                    context,
                    (addUserState as ResultState.Error).message,
                    Toast.LENGTH_LONG
                ).show()
            }

            is ResultState.Success -> {
                viewModel.addAvatar(avatar.value, nickname.value)
            }

            else -> Unit
        }
    }
}

fun bitmapToByteArray(context: Context, uri: Uri): ByteArray {
    val inputStream = context.contentResolver.openInputStream(uri)
    val bitmap = BitmapFactory.decodeStream(inputStream)
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
    return baos.toByteArray()
}
