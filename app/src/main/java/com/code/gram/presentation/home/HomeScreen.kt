package com.code.gram.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.code.gram.core.designsystem.component.CodeGramTopBar
import com.code.gram.core.designsystem.theme.TextTertiary
import com.code.gram.presentation.home.component.HomeFeedItem
import com.code.gram.presentation.home.component.LanguageItem
import com.example.makersassignment.core.common.util.UiState
import com.wakaztahir.codeeditor.highlight.prettify.PrettifyParser
import com.wakaztahir.codeeditor.highlight.theme.CodeTheme
import com.wakaztahir.codeeditor.highlight.theme.CodeThemeType

@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val parser = remember { PrettifyParser() }
    var themeState by remember { mutableStateOf(CodeThemeType.Monokai) }
    val theme = remember(themeState) { themeState.theme() }

    HomeScreen(
        paddingValues = paddingValues,
        state = state,
        parser = parser,
        theme = theme
    )
}

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    state: HomeState,
    parser: PrettifyParser,
    theme: CodeTheme,
) {
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

        LazyRow (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(10) {
                LanguageItem(
                    language = "K"
                )
            }
        }

        when(val state = state.feedItem) {
            is UiState.Success -> {
                LazyColumn (
                    modifier = Modifier,
                    contentPadding = PaddingValues(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(
                        items = state.data,
                    ) {
                        HomeFeedItem(
                            item = it,
                            nickname = it.authorNickname,
                            parser = parser,
                            theme = theme,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                        )
                    }
                }
            }
            is UiState.Loading -> {

            }
            UiState.Empty -> TODO()
            is UiState.Failure -> TODO()
        }
    }
}