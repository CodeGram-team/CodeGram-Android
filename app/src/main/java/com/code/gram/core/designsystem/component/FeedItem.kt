package com.code.gram.core.designsystem.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
import com.code.gram.core.designsystem.theme.textFieldBackground
import com.code.gram.core.model.FeedModel
import com.code.gram.presentation.home.HomeState
import com.code.gram.presentation.search.state.SearchState
import com.wakaztahir.codeeditor.highlight.model.CodeLang
import com.wakaztahir.codeeditor.highlight.prettify.PrettifyParser
import com.wakaztahir.codeeditor.highlight.theme.CodeTheme
import com.wakaztahir.codeeditor.highlight.utils.parseCodeAsAnnotatedString
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FeedItem(
    item: FeedModel,
    isFlipped: Boolean,
    isPlayed: Boolean,
    nickname: String,
    parser: PrettifyParser,
    theme: CodeTheme,
    modifier: Modifier = Modifier,
    onFlipToggle: () -> Unit = {},
    onPlayToggle: () -> Unit = {},
    onInputChanged : (String) -> Unit = {},
    onSendInput : () -> Unit = {},
    onClickPlay: (FeedModel) -> Unit = {},
    onClosed: () -> Unit = {},
    homeState : HomeState? = null,
    searchState: SearchState? = null,
    onClickFavorite: () -> Unit = {},
    onClickComment: () -> Unit = {},
    onClickProfile: () -> Unit = {}
) {
    val rotationY by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        label = "rotationY",
        animationSpec = tween(
            durationMillis = 2000,
            easing = FastOutSlowInEasing
        )
    )

    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(item.authorProfileImageUrl)
        .crossfade(true)
        .size(32, 32)
        .error(R.drawable.ic_person_filled)
        .precision(Precision.INEXACT)
        .build()

    val timeAgo = getTimeAgo(item.createdAt)

    val codeResult = homeState?.codeResult ?: searchState?.codeResult ?: ""
    val userInput = homeState?.userInput ?: searchState?.userInput ?: ""


    Column(
        modifier = modifier
            .animateContentSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onClickProfile),
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
                    text = timeAgo,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Thin
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row (
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = onClickFavorite,
                ) {
                    Icon(
                        imageVector = if (item.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Options",
                        tint = Color.White
                    )
                }
                Text(
                    text = item.likesCount.toString(),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(12.dp))

                IconButton(
                    onClick = onClickComment
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_comment),
                        contentDescription = "Options",
                        tint = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))


        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp),
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onFlipToggle)
                    .graphicsLayer {
                        this.rotationY = rotationY
                        cameraDistance = 12f * density
                    },
                color = Color.Black,
                shape = RoundedCornerShape(8.dp),
                shadowElevation = 4.dp
            ) {
                if (rotationY <= 90f) {
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
                } else {
                    HomeBackBody(
                        title = item.title,
                        vibeEmojis = item.vibeEmojis,
                        modifier = Modifier
                            .graphicsLayer {
                                this.rotationY = 180f
                            }
                            .heightIn(max = 350.dp)
                            .padding(8.dp),
                    )
                }
            }

            if (!isPlayed) {
                IconButton(
                    onClick = {
                        onClickPlay(item)
                        onPlayToggle()
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow, // Play 아이콘
                        contentDescription = "Play Code",
                        tint = Color.White
                    )
                }
            }
        }

        if (item.description != null) {
            Text(
                text = item.description,
                fontSize = 12.sp,
                fontWeight = FontWeight.Thin,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 16.dp)
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

        if (isPlayed) {
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Output",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                IconButton(
                    onClick = onClosed,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(textFieldBackground, RoundedCornerShape(8.dp))
                    .padding(12.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = codeResult.ifEmpty { "실행 결과가 여기에 표시됩니다..." },
                    color = Color(0xFF00FF00), // 터미널 느낌의 초록색 텍스트
                    modifier = Modifier.align(Alignment.TopStart)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = userInput,
                    onValueChange = { onInputChanged(it) },
                    label = { Text("WebSocket Input") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )

                Button(onClick = onSendInput) {
                    Text("Send")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
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

@Composable
private fun HomeBackBody(
    title: String,
    vibeEmojis: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 뒤 표지용 고정 텍스트
            Text(
                text = "Vibe Code",
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
                modifier = Modifier.weight(1f),
                maxLines = 1,
                softWrap = false
            )

            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Options",
                tint = Color.LightGray,
                modifier = Modifier.size(16.dp)
            )
        }

        // vibeEmojis를 한 줄 문자열로 합쳐서 표시
        Text(
            text = vibeEmojis.joinToString(" "),
            fontSize = 40.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 16.dp),
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getTimeAgo(createdAt: String): String {
    return try {
        val localDateTime = LocalDateTime.parse(createdAt)

        val kstZone = ZoneId.of("Asia/Seoul")
        val createdTimeKST = localDateTime.atZone(kstZone)

        val nowKST = ZonedDateTime.now(kstZone)

        val duration = Duration.between(createdTimeKST, nowKST)

        when {
            duration.toDays() > 0 -> "${duration.toDays()}일 전"
            duration.toHours() > 0 -> "${duration.toHours()}시간 전"
            duration.toMinutes() > 0 -> "${duration.toMinutes()}분 전"
            else -> "방금 전"
        }
    } catch (e: Exception) {
        "알 수 없음"
    }
}