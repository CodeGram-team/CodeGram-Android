package com.code.gram.presentation.challenge.component

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
import com.code.gram.presentation.challenge.EntireChallengeList
import com.code.gram.presentation.challenge.model.ChallengeItemModel
import com.code.gram.presentation.challenge.model.ChallengeLevel

@Composable
fun ChallengeItem(
    title : String,
    description : String,
    type : String,
    level : String,
    modifier: Modifier = Modifier,
    onClickChallenge : () -> Unit = {}
) {
    val levelColor = when(level) {
        ChallengeLevel.Easy.name -> Success
        ChallengeLevel.Medium.name -> Warning
        ChallengeLevel.Hard.name -> Error
        else -> Success
    }

    val levelBackgroundColor = when(level) {
        ChallengeLevel.Easy.name -> SuccessDark
        ChallengeLevel.Medium.name -> WarningDark
        ChallengeLevel.Hard.name -> ErrorDark
        else -> SuccessDark
    }

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
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = level,
                color = levelColor,
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
                        color = levelColor,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Center
            )
        }

        Text(
            text = description,
            color = Color.LightGray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = type,
            color = Color.DarkGray,
            fontSize = 12.sp
        )

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
                .noRippleClickable(onClick = onClickChallenge),
            textAlign = TextAlign.End
        )
    }

}

@Preview
@Composable
private fun ChallengeItemPreview() {
    CodeGramTheme {
        ChallengeItem(
            title = "코딩테스트",
            description = "코딩테스트를 해볼까요?",
            type = "Array",
            level = "Easy"
        )
    }
}

@Preview
@Composable
private fun ChallengeListPreview() {
    EntireChallengeList(
        challengeList = listOf(
            ChallengeItemModel(
                title = "코딩테스트",
                description = "코딩테스트를 해볼까요?",
                level = ChallengeLevel.Easy,
                type = "Array"
            ),
            ChallengeItemModel(
                title = "코딩테스트",
                description = "코딩테스트를 해볼까요?",
                level = ChallengeLevel.Easy,
                type = "Array"
            ),
            ChallengeItemModel(
                title = "코딩테스트",
                description = "코딩테스트를 해볼까요?",
                level = ChallengeLevel.Easy,
                type = "Array"
            ),
        ),
    )
}