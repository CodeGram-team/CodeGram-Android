package com.code.gram.presentation.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.code.gram.core.designsystem.component.CommonTextField

@Composable
fun NickNameSignUpScreen(
    nickname: String,
    onSignUpClick: () -> Unit,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        CommonTextField(
            text = nickname,
            onTextChange = onTextChanged,
            modifier = Modifier,
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = onSignUpClick) {
            Text("회원가입")
        }
    }
}