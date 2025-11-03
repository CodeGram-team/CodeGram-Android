package com.code.gram.presentation.challenge.list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.code.gram.core.common.extension.noRippleClickable
import com.code.gram.core.designsystem.theme.CodeGramTheme
import com.code.gram.core.designsystem.theme.ContainerBackground
import com.code.gram.core.designsystem.theme.Error
import com.code.gram.core.designsystem.theme.ErrorDark
import com.code.gram.core.designsystem.theme.PrimaryButtonColor
import com.code.gram.core.designsystem.theme.Success
import com.code.gram.core.designsystem.theme.SuccessDark
import com.code.gram.core.designsystem.theme.Warning
import com.code.gram.core.designsystem.theme.WarningDark
import com.code.gram.presentation.challenge.model.DifficultyType

@Composable
fun ChallengeItem(
    title : String,
    problemId : Int,
    difficultyType: DifficultyType,
    color : Color = Success,
    levelBackgroundColor : Color = SuccessDark,
    modifier: Modifier = Modifier,
    onChallengeClick : () -> Unit = {}
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = ContainerBackground,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Row {
            Text(
                text = "$title (${problemId})",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = difficultyType.label,
                color = color,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier
                    .clip(
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(
                        color = levelBackgroundColor,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = color,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "도전하기",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(top = 4.dp)
                .background(
                    color = PrimaryButtonColor,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 18.dp, vertical = 4.dp)
                .align(Alignment.End)
                .noRippleClickable(onClick = onChallengeClick),
            textAlign = TextAlign.End
        )
    }

}

@Preview
@Composable
private fun ChallengeItemPreview() {
    CodeGramTheme {
        ChallengeItem(
            title = "Challenge Title",
            problemId = 1,
            difficultyType = DifficultyType.INTRODUCTORY
        )
    }
}