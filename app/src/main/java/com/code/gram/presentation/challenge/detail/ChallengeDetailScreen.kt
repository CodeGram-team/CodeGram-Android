package com.code.gram.presentation.challenge.detail

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.code.gram.core.designsystem.component.CodeGramBottomSheet
import com.code.gram.core.designsystem.theme.ContainerBackground
import com.code.gram.core.designsystem.theme.Purple80
import com.code.gram.core.designsystem.theme.textFieldBackground
import com.code.gram.presentation.challenge.detail.component.MathDocumentViewer
import com.code.gram.presentation.challenge.detail.state.ChallengeDetailState
import com.code.gram.presentation.post.FullScreenCodeEditor
import com.code.gram.presentation.post.component.CodeLangContent
import com.wakaztahir.codeeditor.highlight.model.CodeLang
import com.wakaztahir.codeeditor.highlight.prettify.PrettifyParser
import com.wakaztahir.codeeditor.highlight.theme.CodeThemeType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeDetailRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    viewModel: ChallengeDetailViewModel = hiltViewModel()
) {
    val parser = remember { PrettifyParser() }
    var themeState by remember { mutableStateOf(CodeThemeType.Default) }
    val theme = remember(themeState) { themeState.theme() }
    val state by viewModel.state.collectAsStateWithLifecycle()
    var isFullScreenEditor by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isOpenBottomSheet by remember {
        mutableStateOf(false)
    }
    var isComplete by remember {
        mutableStateOf(false)
    }

    if (isComplete) {
        LaunchedEffect(key1 = Unit) {
            viewModel.postChallenge()
        }
    }

    if (isFullScreenEditor) {
        FullScreenCodeEditor(
            paddingValues = paddingValues,
            initialCode = state.code,
            parser = parser,
            theme = theme,
            language = state.codeLang ?: CodeLang.Java,
            onDone = { updatedCode ->
                viewModel.updateCode(updatedCode)
                isFullScreenEditor = false
                isComplete = true
                //viewModel.updateCode(state.code, true)
            },
            onCancel = {
                isFullScreenEditor = false
            }
        )
    } else {
        ChallengeDetailScreen(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            state = state,
            onClickEdit = {
                isFullScreenEditor = !isFullScreenEditor
            },
            sheetState = sheetState,
            onLanguageChange = viewModel::updateLanguage,
            isOpenBottomSheet = isOpenBottomSheet,
            onBottomSheetStateChange = {
                isOpenBottomSheet = it
            },
            isComplete = isComplete
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeDetailScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    onClickEdit: () -> Unit,
    state: ChallengeDetailState,
    sheetState: SheetState,
    onLanguageChange : (CodeLang) -> Unit,
    isOpenBottomSheet : Boolean,
    isComplete : Boolean,
    onBottomSheetStateChange : (Boolean) -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(paddingValues)
            .animateContentSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = navigateUp
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Text(
                text = "Challenge Detail",
                modifier = Modifier
                    .weight(1f),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = state.problemDetailUiModel?.problemId.toString(),
                color = Color.White,
                fontSize = 16.sp
            )

            Text(
                text = state.problemDetailUiModel?.difficulty?.label.toString(),
                color = state.problemDetailUiModel?.color ?: Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier
                    .clip(
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(
                        color = state.problemDetailUiModel?.levelBackgroundColor ?: Color.White,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = state.problemDetailUiModel?.color ?: Color.White,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Center
            )
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    color = ContainerBackground,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        ) {
            /*Text(
                text = state.problemDetailUiModel?.question.toString(),
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Visible
            )*/
            MathDocumentViewer(
                text = state.problemDetailUiModel?.question?.trimIndent().toString(),
                modifier = Modifier.weight(1f)
            )
        }

        if (!isComplete) {
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Language",
                    modifier = Modifier
                        .padding(start = 16.dp)
                )

                OutlinedButton(
                    onClick = {
                        onBottomSheetStateChange(true)
                    },
                    border = null,
                ) {
                    Text(
                        text = state.language,
                        modifier = Modifier,
                        color = Color.White
                    )

                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

            Button(
                onClick = onClickEdit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple80
                )
            ) {
                Text(
                    text = "작성하기",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isComplete) {
            Text(
                text = "Output",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(textFieldBackground, RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    val isSuccess = state.challengeResult?.executionTime != null
                    val textColor = if (isSuccess) Color(0xFF00FF00) else Color.Red

                    Text(
                        text = "Status: ${state.challengeResult?.status}",
                        color = textColor
                    )
                    Text(
                        text = "Failed Case: ${state.challengeResult?.failedCase ?: "-"}",
                        color = textColor
                    )
                    Text(
                        text = "Execution Time: ${state.challengeResult?.executionTime ?: "-"}",
                        color = textColor
                    )
                    Text(
                        text = state.challengeResult?.message?.ifEmpty { "실행 결과가 여기에 표시됩니다..." }
                            ?: "실행 결과가 여기에 표시됩니다.",
                        color = textColor
                    )
                }
            }
        }
    }


    if (isOpenBottomSheet) {
        CodeGramBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { onBottomSheetStateChange(false) }
        ) {
            CodeLangContent(
                selectCodeLang = {
                    onLanguageChange(it)
                    onBottomSheetStateChange(false)
                }
            )
        }
    }


}