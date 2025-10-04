package com.code.gram.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.code.gram.R
import com.code.gram.core.designsystem.theme.PrimaryBlueDark
import com.code.gram.core.designsystem.theme.PrimaryPurpleDark
import com.code.gram.core.designsystem.theme.TextTertiary

@Composable
fun CodeGramTopBar(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "CodeGram",
            fontSize = 24.sp,
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = listOf(
                        PrimaryBlueDark,
                        PrimaryPurpleDark
                    ),
                    tileMode = TileMode.Mirror
                )
            ),
            fontWeight = FontWeight.Bold
        )

        Row {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                contentDescription = null,
                tint = TextTertiary
            )

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_favorite_border_24),
                contentDescription = null,
                tint = TextTertiary
            )
        }
    }
}

@Preview
@Composable
private fun CodeGramTopBarPreview() {
    CodeGramTopBar()
}