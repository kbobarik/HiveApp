package com.example.hive.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.hive.domain.ResultState
import com.example.hive.presentation.components.BackgroundWavesSmall
import com.example.hive.ui.theme.PurpleBackground
import com.example.hive.ui.theme.PurpleDark
import com.example.hive.viewmodel.CreateProfileViewModel
import com.example.hive.viewmodel.LoginViewModel
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.output.ByteArrayOutputStream

@Composable
fun CreateProfileScreen(viewModel: CreateProfileViewModel = hiltViewModel()) {
    val nickname = remember { mutableStateOf("") }
    val avatarUri = remember { mutableStateOf<Uri?>(null) }
    val avatar = remember { mutableStateOf(byteArrayOf()) }
    val context = LocalContext.current
    val addAvatarState by viewModel.stateAddAvatar.collectAsState()
    val addUserState by viewModel.stateAddUser.collectAsState()

    val imagePicker =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri == null) return@rememberLauncherForActivityResult
            avatar.value = bitmapToByteArray(context, uri)
            avatarUri.value = uri

        }

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
                text = "Создание профиля",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Введите никнейм\nи выберите аватар",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = PurpleDark
            )

            Spacer(modifier = Modifier.height(32.dp))

            /// Аватар
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape)
                    .background(PurpleDark.copy(alpha = 0.4f))
                    .border(2.dp, PurpleDark, CircleShape)
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
            OutlinedTextField(
                value = nickname.value,
                onValueChange = { nickname.value = it },
                label = { Text("Никнейм") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PurpleDark,
                    unfocusedBorderColor = PurpleDark.copy(alpha = 0.6f),
                    focusedLabelColor = PurpleDark,
                    cursorColor = PurpleDark
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if(avatar.value.isNotEmpty() && nickname.value !=""){
                       val result =  viewModel.checkValidNickname(nickname.value)
                        if(!result){
                            viewModel.addAvatar(avatar.value,nickname.value)
                        }
                    }
                },
                enabled = nickname.value.isNotBlank() && avatarUri.value != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PurpleDark
                )
            ) {
                Text(
                    text = "Продолжить",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
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
                viewModel.addUser(nickname.value)
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
                Toast.makeText(
                    context,
                    (addUserState as ResultState.Success).message,
                    Toast.LENGTH_LONG
                ).show()
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
