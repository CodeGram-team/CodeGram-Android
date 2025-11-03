package com.code.gram.presentation.search.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.code.gram.presentation.search.model.SearchQueryUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchQueryBottomSheet(
    searchQuery: SearchQueryUiModel,
    onValueChange: (SearchQueryUiModel) -> Unit,
    onDismiss: () -> Unit,
    onSearch: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scrollState = rememberScrollState()

    var vibeInput by remember { mutableStateOf("") }
    var tagInput by remember { mutableStateOf("") }
    var queryInput by remember { mutableStateOf(searchQuery.query ?: "") }
    var languageInput by remember { mutableStateOf(searchQuery.language ?: "") }
    var showEmojiInfoDialog by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .verticalScroll(scrollState)
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onDismiss
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close"
                    )
                }

                Text(
                    text = "검색 조건 입력",
                    style = MaterialTheme.typography.titleLarge
                )

                Row {
                    IconButton(
                        onClick = onSearch
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        onClick = {
                            vibeInput = ""
                            tagInput = ""
                            queryInput = ""
                            languageInput = ""
                            onValueChange(SearchQueryUiModel())

                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Clear",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // 🎭 Vibe Emoji 입력
            OutlinedTextField(
                value = vibeInput,
                onValueChange = { vibeInput = it },
                label = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Vibe Emoji 입력 (예: 🔥)")
                        IconButton(
                            onClick = { showEmojiInfoDialog = true },
                            modifier = Modifier.padding(start = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "지원 이모지 안내",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                trailingIcon = {
                    IconButton(onClick = {
                        val text = vibeInput.trim()
                        if (text.isNotEmpty() && text !in searchQuery.vibeEmojis) {
                            val newList = searchQuery.vibeEmojis + text
                            onValueChange(searchQuery.copy(vibeEmojis = newList))
                            vibeInput = ""
                        }
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Vibe")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            if (searchQuery.vibeEmojis.isNotEmpty()) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(searchQuery.vibeEmojis) { emoji ->
                        AssistChip(
                            onClick = {},
                            label = { Text(emoji) },
                            trailingIcon = {
                                IconButton(onClick = {
                                    val newList = searchQuery.vibeEmojis - emoji
                                    onValueChange(searchQuery.copy(vibeEmojis = newList))
                                }) {
                                    Icon(Icons.Default.Close, contentDescription = "Remove")
                                }
                            }
                        )
                    }
                }
            }

            // 💬 Query 입력
            OutlinedTextField(
                value = queryInput,
                onValueChange = {
                    queryInput = it
                    onValueChange(searchQuery.copy(query = it.takeIf { it.isNotBlank() }))
                },
                label = { Text("검색어 (제목, 설명, 태그)") },
                modifier = Modifier.fillMaxWidth()
            )

            // 💻 Language 입력
            OutlinedTextField(
                value = languageInput,
                onValueChange = {
                    languageInput = it
                    onValueChange(searchQuery.copy(language = it.takeIf { it.isNotBlank() }))
                },
                label = { Text("언어 입력 (예: Kotlin)") },
                modifier = Modifier.fillMaxWidth()
            )

            // 🏷 Tags 입력
            OutlinedTextField(
                value = tagInput,
                onValueChange = { tagInput = it },
                label = { Text("태그 입력") },
                trailingIcon = {
                    IconButton(onClick = {
                        val text = tagInput.trim()
                        if (text.isNotEmpty() && text !in searchQuery.tags) {
                            val newList = searchQuery.tags + text
                            onValueChange(searchQuery.copy(tags = newList))
                            tagInput = ""
                        }
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Add tag")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            if (searchQuery.tags.isNotEmpty()) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(searchQuery.tags) { tag ->
                        AssistChip(
                            onClick = {},
                            label = { Text(tag) },
                            trailingIcon = {
                                IconButton(onClick = {
                                    val newList = searchQuery.tags - tag
                                    onValueChange(searchQuery.copy(tags = newList))
                                }) {
                                    Icon(Icons.Default.Close, contentDescription = "Remove")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
    if (showEmojiInfoDialog) {
        AlertDialog(
            onDismissRequest = { showEmojiInfoDialog = false },
            title = { Text("사용 가능한 이모지 목록 / 2개까지 가능해요") },
            text = {
                Column {
                    Text("🔥  집중 / 열정 / 도전")
                    Text("💡  아이디어 / 창의력")
                    Text("💻  개발 / 코딩 관련")
                    Text("🌱  성장 / 학습")
                }
            },
            confirmButton = {
                TextButton(onClick = { showEmojiInfoDialog = false }) {
                    Text("확인")
                }
            }
        )
    }
}

@Preview
@Composable
private fun SearchQueryBottomSheetPreview() {
    SearchQueryBottomSheet(
        searchQuery = SearchQueryUiModel(),
        onValueChange = {},
        onSearch = {},
        onDismiss = {}
    )
}