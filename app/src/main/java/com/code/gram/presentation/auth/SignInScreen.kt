package com.code.gram.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.code.gram.R
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
    Column (
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Codegram",
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            color = Color.White
        )

        if (!isVisibleNickName) {
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onClickGoogleSignIn,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = TextTertiary,
                        shape = RoundedCornerShape(8.dp)
                    ),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SurfaceContainer,
                    contentColor = TextPrimary
                )
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_signup_google),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Text(
                    text = "구글 계정으로 로그인",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
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