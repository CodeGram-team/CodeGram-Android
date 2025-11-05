package com.code.gram.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.code.gram.core.designsystem.theme.background
import com.code.gram.core.common.extension.noRippleClickable
import com.code.gram.presentation.home.model.SortType

@Composable
fun SortTypeContent (
    selectSortType : (SortType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sortType = listOf<SortType>(
        SortType.Recommended,
        SortType.Latest,
        SortType.Popular,
    )

    LazyColumn(
        modifier = modifier
            .fillMaxHeight(0.7f)
            .background(background),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = sortType
        ) { index, lang ->
            SortTypeItem(
                sortType = lang,
                selectSortType = selectSortType,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 4.dp
                )
            )
        }
    }
}

@Composable
private fun SortTypeItem(
    sortType: SortType,
    selectSortType: (SortType) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = sortType.name,
        color = Color.White,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = background,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(
                horizontal = 16.dp,
                vertical = 11.dp
            )
            .noRippleClickable {
                selectSortType(sortType)
            },
        textAlign = TextAlign.Start
    )
}