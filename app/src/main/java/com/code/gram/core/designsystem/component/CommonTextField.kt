package com.code.gram.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CommonTextField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Gray,
    isPasswordVisible: Boolean = false,
    isEnable: Boolean = true,
    onClickTextField : () -> Unit = {},
    placeHolder: String = "닉네임을 작성해주세요",
    suffix: (@Composable () -> Unit)? = null,
    prefix: (@Composable () -> Unit)? = null,
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(12.dp)
            .clickable(
                enabled = !isEnable,
                onClick = onClickTextField
            ),
        visualTransformation = VisualTransformation.None,
        singleLine = true,
        enabled = isEnable,
        textStyle = TextStyle(
            color = Color.White,
            fontWeight = FontWeight.Bold
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                prefix?.invoke()

                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (text.isEmpty()) {
                        Text(
                            text = placeHolder,
                            color = Color.LightGray,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    innerTextField()
                }
                suffix?.invoke()
            }
        }
    )
}

@Preview
@Composable
private fun CommonTextFieldPreview() {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column {
        CommonTextField(
            text = id,
            onTextChange = { id = it },
            modifier = Modifier,
        )

        CommonTextField(
            text = password,
            onTextChange = { password = it },
            modifier = Modifier,
            suffix = { }
        )
    }
}