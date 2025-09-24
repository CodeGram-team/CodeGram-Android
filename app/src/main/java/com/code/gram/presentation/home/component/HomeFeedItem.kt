package com.code.gram.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.code.gram.core.designsystem.component.CommonChip
import com.code.gram.core.designsystem.theme.PrimaryBlueLight
import com.code.gram.presentation.home.model.FeedModel
import com.wakaztahir.codeeditor.highlight.model.CodeLang
import com.wakaztahir.codeeditor.highlight.prettify.PrettifyParser
import com.wakaztahir.codeeditor.highlight.theme.CodeTheme
import com.wakaztahir.codeeditor.highlight.utils.parseCodeAsAnnotatedString


@Composable
fun HomeFeedItem(
    item: FeedModel,
    nickname: String,
    parser: PrettifyParser,
    theme: CodeTheme,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        color = PrimaryBlueLight,
                        shape = CircleShape
                    )
            )

            Column {
                Text(
                    text = nickname,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "2시간 전",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Thin
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = Color.Black,
            shape = RoundedCornerShape(8.dp),
            shadowElevation = 4.dp
        ) {
            HomeFeedBody(
                code = item.code.trimIndent(),
                language = item.language,
                parser = parser,
                theme = theme,
                modifier = Modifier
                    .padding(8.dp)
            )
        }

        LazyRow (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            items(item.tags.size) {
                CommonChip(
                    text = item.tags[it],
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun HomeFeedBody(
    code : String,
    language : CodeLang,
    parser: PrettifyParser,
    theme: CodeTheme,
    modifier: Modifier = Modifier,
) {
    val annotatedString = remember(code, language) {
        parseCodeAsAnnotatedString(
            parser = parser,
            theme = theme,
            lang = language,
            code = code
        )
    }

    Column (
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = language.name,
                color = Color.LightGray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Code Options",
                tint = Color.LightGray,
                modifier = Modifier.size(16.dp)
            )
        }

        Text(
            text = annotatedString,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp, vertical = 8.dp),
            fontSize = 13.sp,
            lineHeight = 16.sp
        )
    }
}