package com.code.gram.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.code.gram.core.designsystem.component.CodeGramTopBar
import com.code.gram.core.designsystem.theme.TextTertiary
import com.code.gram.presentation.home.component.HomeFeedItem
import com.code.gram.presentation.home.component.LanguageItem

@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
) {
    HomeScreen(
        paddingValues = paddingValues
    )
}

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        CodeGramTopBar(
            modifier = Modifier
                .padding(8.dp)
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(),
            thickness = 1.dp,
            color = TextTertiary
        )

        Column (
            modifier = Modifier
                .padding(16.dp)
        ) {
            LazyRow (
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(10) {
                    LanguageItem(
                        language = "K"
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                thickness = 1.dp,
                color = TextTertiary
            )

            LazyColumn {
                items(10) {
                    HomeFeedItem(
                        nickname = "test"
                    )
                }
            }
        }
    }
}