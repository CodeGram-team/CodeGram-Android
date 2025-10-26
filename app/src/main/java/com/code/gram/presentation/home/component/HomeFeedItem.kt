package com.code.gram.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Precision
import com.code.gram.R
import com.code.gram.core.designsystem.component.CommonChip
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
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(item.authorProfileImageUrl)
        .crossfade(true)
        .size(32, 32)
        .error(R.drawable.ic_person_filled)
        .precision(Precision.INEXACT)
        .build()

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (item.authorProfileImageUrl != null) {
                    AsyncImage(
                        model = imageRequest,
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_person_filled),
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(6.dp)
                    )
                }
            }

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
                language = if (item.language == "Java") CodeLang.Java else CodeLang.Python,
                markLanguage = item.language,
                parser = parser,
                theme = theme,
                modifier = Modifier
                    .heightIn(max = 350.dp)
                    .padding(8.dp),
                title = item.title
            )
        }

        if (item.description != null) {
            Text(
                text = item.description,
                fontSize = 12.sp,
                fontWeight = FontWeight.Thin,
                color = Color.White
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
    title : String,
    code : String,
    language : CodeLang,
    markLanguage : String,
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
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = markLanguage,
                color = Color.LightGray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = title,
                color = Color.LightGray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f),
                maxLines = 1,
                softWrap = false
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