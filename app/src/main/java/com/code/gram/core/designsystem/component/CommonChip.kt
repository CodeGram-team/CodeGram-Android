package com.code.gram.core.designsystem.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.code.gram.core.designsystem.theme.PrimaryBlue
import com.code.gram.core.designsystem.theme.PrimaryBlueLight

@Composable
fun CommonChip(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = PrimaryBlueLight,
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = "#$text",
            color = PrimaryBlue,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 2.dp),

        )
    }
}