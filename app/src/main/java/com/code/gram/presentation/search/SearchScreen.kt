package com.code.gram.presentation.search

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.code.gram.core.designsystem.component.CommentBottomDialog
import com.code.gram.core.designsystem.component.CommonTextField
import com.code.gram.core.designsystem.component.FeedItem
import com.code.gram.core.designsystem.theme.textFieldBackground
import com.code.gram.core.model.FeedModel
import com.code.gram.presentation.home.model.CommentUiModel
import com.code.gram.presentation.search.component.SearchQueryBottomSheet
import com.code.gram.presentation.search.model.SearchQueryUiModel
import com.code.gram.presentation.search.state.SearchState
import com.wakaztahir.codeeditor.highlight.prettify.PrettifyParser
import com.wakaztahir.codeeditor.highlight.theme.CodeThemeType
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchRoute(
    paddingValues: PaddingValues,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SearchScreen(
        paddingValues = paddingValues,
        onSearchQueryChange = viewModel::updateSearchQuery,
        onSearchComplete = viewModel::fetchSearch,
        state = state,
        onInputChanged = viewModel::onInputChanged,
        onSendInput = viewModel::sendInput,
        onClickPlay = viewModel::startJob,
        onClickFavorite = viewModel::fetchFavorite,
        onSendComment = viewModel::sendComment
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchScreen(
    paddingValues: PaddingValues,
    state: SearchState,
    onSearchQueryChange: (SearchQueryUiModel) -> Unit = {},
    onSearchComplete: () -> Unit = {},
    onInputChanged : (String) -> Unit = {},
    onSendInput : () -> Unit = {},
    onClickPlay: (FeedModel) -> Unit = {},
    onClickFavorite: (String) -> Unit = {},
    onSendComment: (String,String) -> Unit,
) {
    var isFilterExpanded by remember { mutableStateOf(false) }
    val parser = remember { PrettifyParser() }
    var themeState by remember { mutableStateOf(CodeThemeType.Monokai) }
    val theme = remember(themeState) { themeState.theme() }
    var flippedIndices by remember { mutableStateOf(persistentSetOf<Int>()) }
    var playedIndices by remember { mutableIntStateOf(-1) }

    var isOpenDialog by remember { mutableStateOf(false) }
    var selectedComments by remember { mutableStateOf<List<CommentUiModel>>(emptyList()) }
    var clickPostId by remember { mutableStateOf("-1") }


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

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        CommonTextField(
            text = state.searchQueryUiModel.query ?: "",
            onTextChange = {

            },
            onClickTextField = {
                isFilterExpanded = true
            },
            isEnable = false,
            backgroundColor = textFieldBackground,
            placeHolder = "Search",
            prefix = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }
        )

        Spacer(modifier = Modifier.padding(8.dp))

        if (state.searchResultList.isNotEmpty()) {
            LazyColumn (
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                itemsIndexed(
                    items = state.searchResultList,
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
                        searchState = state,
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
                            clickPostId = item.id
                            selectedComments = item.comments
                            isOpenDialog = true
                        },
                    )
                }
            }
        } else {
            Text(
                text = "검색 결과가 없습니다.",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

    if (isFilterExpanded) {
        SearchQueryBottomSheet(
            searchQuery = state.searchQueryUiModel,
            onValueChange = {
                Timber.e("onValueChange ${it}")
                onSearchQueryChange(it)
            },
            onDismiss = {
                isFilterExpanded = false
            },
            onSearch = {
                isFilterExpanded = false
                onSearchComplete()
            }
        )
    }

    if (isOpenDialog) {
        CommentBottomDialog(
            userList = selectedComments.toImmutableList(),
            onDismiss = { isOpenDialog = false },
            onSendComment = {
                onSendComment(it, clickPostId)
            }
        )
    }
}