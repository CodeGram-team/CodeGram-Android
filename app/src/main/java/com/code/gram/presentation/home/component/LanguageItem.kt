package com.code.gram.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.code.gram.core.designsystem.theme.PrimaryBlue
import com.code.gram.core.designsystem.theme.PrimaryPurple
import com.code.gram.core.designsystem.theme.TextInverse

@Composable
fun LanguageItem(
    language: String,
    modifier: Modifier = Modifier,
) {
    val brush = Brush.linearGradient(listOf(PrimaryBlue, PrimaryPurple))

    Box(
        modifier = modifier
            .clip(CircleShape)
            .size(48.dp)
            .background(
                brush = brush,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = language,
            fontWeight = FontWeight.Bold,
            color = TextInverse
        )
    }
}

@Preview
@Composable
private fun LanguageItemPreview() {
    LanguageItem(
        language = "K"
    )
}