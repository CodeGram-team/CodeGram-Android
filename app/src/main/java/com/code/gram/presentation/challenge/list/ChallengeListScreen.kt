package com.code.gram.presentation.challenge.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.code.gram.core.common.util.NoRippleInteractionSource
import com.code.gram.core.designsystem.component.CommonTextField
import com.code.gram.core.designsystem.theme.ContainerBackground
import com.code.gram.core.designsystem.theme.PrimaryBlue
import com.code.gram.core.designsystem.theme.Success
import com.code.gram.core.designsystem.theme.TextTertiary
import com.code.gram.core.designsystem.theme.textFieldBackground
import com.code.gram.presentation.challenge.list.component.ChallengeItem
import com.code.gram.presentation.challenge.list.model.ChallengeItemModel
import com.code.gram.presentation.challenge.model.DifficultyType
import com.code.gram.presentation.challenge.list.state.ChallengeState
import kotlinx.collections.immutable.ImmutableList
import timber.log.Timber

@Composable
fun ChallengeListRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateChallengeDetail: (Int) -> Unit,
    viewModel: ChallengeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val tabs = DifficultyType.entries

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    ChallengeListScreen(
        paddingValues = paddingValues,
        state = state,
        tabs = tabs,
        selectedTabIndex = selectedTabIndex,
        onTextChange = viewModel::updateSearchQuery,
        onTabSelected = {
            selectedTabIndex = it
            viewModel.fetchChallengeList(
                difficulty = tabs[it]
            )
        },
        onChallengeClick = navigateChallengeDetail
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeListScreen (
    paddingValues: PaddingValues,
    state: ChallengeState,
    tabs : List<DifficultyType>,
    selectedTabIndex : Int,
    onTextChange: (String) -> Unit = {},
    onTabSelected : (Int) -> Unit = {},
    onChallengeClick : (Int) -> Unit = {}
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        CommonTextField(
            text = state.query,
            onTextChange = onTextChange,
            modifier = Modifier,
            backgroundColor = textFieldBackground,
            placeHolder = "Search",
            prefix = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = ContainerBackground,
                    shape = RoundedCornerShape(16.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(3) {
                ChallengeInfoItem(
                    value = it,
                    description = when(it) {
                        0 -> "완료"

                        1 -> "포인트"

                        2 -> "정답률"

                        else -> {}
                    } as String
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        PrimaryTabRow (
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            divider = {},
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(selectedTabIndex, matchContentSize = true),
                    width = Dp.Unspecified,
                    color = PrimaryBlue
                )
            }
        ) {
            tabs.forEachIndexed { index, tab ->
                val isSelected = index == selectedTabIndex
                Tab(
                    text = {
                        Text(
                            text = tab.label,
                            color = if (isSelected) Color.White else Color.DarkGray,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    },
                    selected = isSelected,
                    onClick = { onTabSelected(index) },
                    interactionSource = remember { NoRippleInteractionSource() },
                )
            }
        }

        ChallengeList(
            challengeList = state.challengeList,
            onChallengeClick = onChallengeClick
        )
    }

}

@Composable
private fun ChallengeInfoItem(
    value : Int,
    description : String,
    modifier: Modifier = Modifier
) {
    val text = if (description == "정답률") {
        "$value%"
    } else {
        value.toString()
    }

    val color = if (description == "정답률") {
        if (value >= 50) {
            Success
        } else {
            Color.Red
        }
    } else {
        Color.White
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier,
            color = color
        )

        Text(
            text = description,
            modifier = Modifier,
            color = TextTertiary
        )
    }
}

@Composable
private fun ChallengeList(
    challengeList : ImmutableList<ChallengeItemModel>,
    onChallengeClick : (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn (
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        itemsIndexed(
            items = challengeList,
            key = { index, item ->
                item.problemId
            }
        ) { index, item ->
            ChallengeItem(
                title = item.title,
                problemId = item.problemId,
                difficultyType = item.difficulty,
                color = item.color,
                levelBackgroundColor = item.levelBackgroundColor,
                onChallengeClick = {
                    onChallengeClick(item.problemId)
                }
            )
            Timber.d("challengeList $challengeList")
            Timber.d("challengeList[index] ${challengeList[index].color}")
        }
    }
}