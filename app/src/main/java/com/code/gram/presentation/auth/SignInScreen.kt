package com.code.gram.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.code.gram.R
import com.code.gram.core.designsystem.theme.SurfaceCard
import com.code.gram.core.designsystem.theme.SurfaceContainer
import com.code.gram.core.designsystem.theme.TextPrimary
import com.code.gram.core.designsystem.theme.TextTertiary

@Composable
fun SignInRoute(
    navigateUp: () -> Unit,
    navigateHome: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.authSideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect{ sideEffect ->
                when (sideEffect) {
                    AuthSideEffect.NavigateHome -> navigateHome()
                }
        }
    }

    SignInScreen(
        nickname = state.nickname,
        isVisibleNickName = state.isVisibleNickName,
        onTextChanged = {
            viewModel.onTextChanged(it)
        },
        onClickGoogleSignIn = {
            viewModel.onGoogleSignClick(context)
        },
        onSignUpClick = {
            viewModel.signUp()
        }
    )
}

@Composable
fun SignInScreen(
    nickname: String,
    isVisibleNickName: Boolean,
    onTextChanged: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onClickGoogleSignIn: () -> Unit,
) {
    var isTitleMoved by remember { mutableStateOf(false) }

    // 타이틀 이동 애니메이션
    val titleOffsetY by androidx.compose.animation.core.animateDpAsState(
        targetValue = if (isTitleMoved) 0.dp else 200.dp, // 아래에서 위로 이동
        animationSpec = androidx.compose.animation.core.tween(
            durationMillis = 800,
            easing = androidx.compose.animation.core.FastOutSlowInEasing
        ),
        label = "titleOffset"
    )

    // 1초 후 애니메이션 시작
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1000)
        isTitleMoved = true
    }

    Column (
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_title),
            contentDescription = null,
            modifier = Modifier
                .offset(y = titleOffsetY)
                .fillMaxWidth()
                .size(100.dp, 150.dp)
                .padding(top = 16.dp, start = 20.dp, end = 20.dp),
            alignment = Alignment.Center
        )

        if (!isVisibleNickName) {
            Spacer(modifier = Modifier.weight(1f))

            androidx.compose.animation.AnimatedVisibility(
                visible = isTitleMoved,
                enter = androidx.compose.animation.fadeIn(
                    animationSpec = androidx.compose.animation.core.tween(3500)
                ) + androidx.compose.animation.expandVertically(),
                exit = androidx.compose.animation.fadeOut()
            ) {
                Button(
                    onClick = onClickGoogleSignIn,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .padding(bottom = 30.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SurfaceCard,
                        contentColor = TextPrimary
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_signup_google),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Text(
                        text = "Sign In with Google",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            NickNameSignUpScreen(
                onSignUpClick = onSignUpClick,
                nickname = nickname,
                onTextChanged = onTextChanged
            )
        }
    }
}