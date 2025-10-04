package com.code.gram.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.code.gram.core.designsystem.component.CommonTextField
import com.code.gram.core.designsystem.theme.itemBackground
import com.example.makersassignment.core.common.extension.noRippleClickable

@Composable
fun SearchRoute(
    paddingValues: PaddingValues,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SearchScreen(
        paddingValues = paddingValues,
        query = state.searchText,
        onTextChange = viewModel::updateSearchQuery
    )
}

@Composable
fun SearchScreen(
    paddingValues: PaddingValues,
    query : String,
    onTextChange: (String) -> Unit = {},
) {
    var selectedFilter by remember {
        mutableIntStateOf(0)
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        CommonTextField(
            text = query,
            onTextChange = onTextChange,
            modifier = Modifier
                .background(
                    color = itemBackground,
                    shape = RoundedCornerShape(8.dp)
                ),
            placeHolder = "Search",
            prefix = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }
        )

        Spacer(modifier = Modifier.padding(8.dp))

        LazyRow (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(10) {
                FilterItem(
                    filter = "Filter $it",
                    index = it,
                    selected = it == selectedFilter,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    },
                    onClick = {
                        selectedFilter = it
                    }
                )
            }
        }
    }
}

@Composable
private fun FilterItem(
    filter : String,
    index : Int,
    selected : Boolean = false,
    icon : @Composable () -> Unit = {},
    onClick : (Int) -> Unit = {}
) {
    val backgroundColor = if (selected) {
        itemBackground
    } else {
        MaterialTheme.colorScheme.surface
    }

    Row (
        modifier = Modifier
            .padding(4.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon.invoke()

        Text(
            text = filter,
            modifier = Modifier
                .padding(4.dp)
                .noRippleClickable {
                    onClick(index)
                }
        )
    }
}