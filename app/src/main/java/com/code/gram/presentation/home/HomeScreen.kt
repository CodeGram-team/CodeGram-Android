package com.code.gram.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.code.gram.core.designsystem.component.CodeGramTopBar
import com.code.gram.core.designsystem.component.CommentBottomDialog
import com.code.gram.core.designsystem.component.FeedItem
import com.code.gram.core.model.FeedModel
import com.code.gram.presentation.home.model.CommentUiModel
import com.example.makersassignment.core.common.util.UiState
import com.wakaztahir.codeeditor.highlight.prettify.PrettifyParser
import com.wakaztahir.codeeditor.highlight.theme.CodeTheme
import com.wakaztahir.codeeditor.highlight.theme.CodeThemeType
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val parser = remember { PrettifyParser() }
    var themeState by remember { mutableStateOf(CodeThemeType.Monokai) }
    val theme = remember(themeState) { themeState.theme() }

    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val totalItems = layoutInfo.totalItemsCount
            lastVisible != null && lastVisible >= totalItems - 1
        }.collect { reachedBottom ->
            if (reachedBottom) {
                launch {
                    viewModel.updatePage()
                }

                launch {
                    viewModel.fetchData()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }


    HomeScreen(
        paddingValues = paddingValues,
        homeState = state,
        parser = parser,
        theme = theme,
        listState = listState,
        onClickPlay = {
            viewModel.startJob(it)
        },
        onInputChanged = viewModel::onInputChanged,
        onSendInput = viewModel::sendInput,
        onClickFavorite = viewModel::fetchFavorite,
        onSendComment = viewModel::sendComment
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    homeState: HomeState,
    parser: PrettifyParser,
    theme: CodeTheme,
    listState: LazyListState,
    onClickPlay: (FeedModel) -> Unit = {},
    onInputChanged : (String) -> Unit = {},
    onSendInput : () -> Unit = {},
    onClickFavorite: (String) -> Unit = {},
    onSendComment: (String,String) -> Unit,
) {
    var flippedIndices by remember { mutableStateOf(persistentSetOf<Int>()) }
    var playedIndices by remember { mutableIntStateOf(-1) }
    var selectedPostIdForComment by remember { mutableStateOf<String?>(null) }

    val onFlipToggle: (Int) -> Unit = { index ->
        flippedIndices = if (flippedIndices.contains(index)) {
            flippedIndices.remove(index)
        } else {
            flippedIndices.add(index)
        }
    }

    val onPlayToggle: (Int) -> Unit = { index ->
        playedIndices = index
    }

    val currentFeedState = homeState.feedItem

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .padding(paddingValues)
    ) {
        CodeGramTopBar(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 16.dp)
        )

        when(currentFeedState) {
            is UiState.Success -> {
                LazyColumn (
                    state = listState,
                    contentPadding = PaddingValues(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    itemsIndexed(
                        items = currentFeedState.data,
                    ) { index, item ->
                        val isFlipped = flippedIndices.contains(index)
                        val isPlayed = index == playedIndices

                        FeedItem(
                            item = item,
                            nickname = item.authorNickname,
                            parser = parser,
                            theme = theme,
                            modifier = Modifier,
                            isFlipped = isFlipped,
                            isPlayed = isPlayed,
                            onPlayToggle = { onPlayToggle(index) },
                            onFlipToggle = { onFlipToggle(index) },
                            homeState = homeState,
                            onInputChanged = onInputChanged,
                            onSendInput = onSendInput,
                            onClickPlay = onClickPlay,
                            onClosed = {
                                playedIndices = -1
                            },
                            onClickFavorite = {
                                onClickFavorite(item.id)
                            },
                            onClickComment = {
                                selectedPostIdForComment = item.id
                            },
                        )
                    }

                    item {
                        if (homeState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .size(16.dp)
                            )
                        }
                    }
                }
            }
            is UiState.Loading -> { }
            UiState.Empty -> TODO()
            is UiState.Failure -> TODO()
        }
    }

    val commentsToShow = remember(currentFeedState, selectedPostIdForComment) {
        if (currentFeedState is UiState.Success && selectedPostIdForComment != null) {
            currentFeedState.data
                .find { it.id == selectedPostIdForComment } // ID가 일치하는 포스트를 찾고
                ?.comments                                // 그 포스트의 댓글 리스트를 반환
                ?: emptyList()                            // 못찾으면 빈 리스트
        } else {
            emptyList() // Success 상태가 아니면 빈 리스트
        }
    }

    if (selectedPostIdForComment != null) {
        CommentBottomDialog(
            userList = commentsToShow.toImmutableList(),
            onDismiss = { selectedPostIdForComment = null },
            onSendComment = { commentContent ->
                selectedPostIdForComment?.let { postId ->
                    onSendComment(commentContent, postId)
                }
            }
        )
    }
}