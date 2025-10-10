package com.code.gram.presentation.post.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.code.gram.core.designsystem.theme.TextTertiary

@Composable
fun TagItem(
    text: String,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(color = TextTertiary, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, color = Color.White, fontSize = 14.sp)

        Spacer(modifier = Modifier.width(4.dp))

        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Remove Tag",
            modifier = Modifier
                .size(16.dp)
                .clickable(onClick = onRemove),
            tint = Color.White
        )
    }
}