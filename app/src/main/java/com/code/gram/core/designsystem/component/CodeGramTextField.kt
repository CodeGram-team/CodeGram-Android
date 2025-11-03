package com.code.gram.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun CodeGramTextField(
    text : String,
    placeholder : String,
    type : String = "title",
    onTextChange : (String) -> Unit,
) {
    val textStyle = when (type) {
        "content" -> TextStyle(fontSize = 14.sp, color = Color.White)
        else -> TextStyle(fontSize = 18.sp, color = Color.White)
    }

    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        textStyle = textStyle,
        modifier = Modifier
            .fillMaxWidth(),
        decorationBox = { innerTextField ->
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (text.isBlank()) {
                        Text(
                            text = placeholder,
                            modifier = Modifier
                                .fillMaxWidth(),
                            color = Color.LightGray
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}