package com.example.hive.presentation.allUsers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.hive.R
import com.example.hive.domain.ResultState
import com.example.hive.presentation.LoadingScreen
import com.example.hive.ui.theme.Nimbus

@Composable
fun AllUsersScreen(viewModel: AllUsersViewModel = hiltViewModel()) {
    val getAllUsersState by viewModel.stateGetUsers.collectAsState()
    val users by viewModel.users.collectAsState()
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loading)
    )
    LaunchedEffect(Unit) {
        viewModel.getAllUsers()
    }
    if (getAllUsersState is ResultState.Loading) {
        LoadingScreen()
    }
    if (getAllUsersState is ResultState.Success) {
        if (users.isEmpty()) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Здесь пока пусто, начни с кем то чат",
                    fontFamily = Nimbus,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(users) { chat ->
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val avatar = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current).data(chat.image)
                                .size(100, 100).build()
                        ).state

                        when (avatar) {
                            is AsyncImagePainter.State.Loading -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(40.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    LottieAnimation(
                                        composition = composition,
                                        iterations = LottieConstants.IterateForever,
                                        modifier = Modifier.size(40.dp)
                                    )
                                }
                            }

                            is AsyncImagePainter.State.Success -> {
                                Box(
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(40.dp)
                                        .clip(RoundedCornerShape(10.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(40.dp),
                                        painter = avatar.painter,
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop
                                    )
                                }

                            }

                            is AsyncImagePainter.State.Error -> {
                                Box(
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(40.dp)
                                        .clip(RoundedCornerShape(10.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(40.dp),
                                        painter = painterResource(R.drawable.no_image),
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }

                            else -> {}
                        }

                        Text(
                            text = chat.nickname,
                            fontWeight = FontWeight.Normal,
                            fontSize = 20.sp,
                            fontFamily = Nimbus,
                            color = Color.Black
                        )


                    }
                }

            }
        }
    }

}