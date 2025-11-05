package com.code.gram.presentation.post

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.code.gram.core.designsystem.component.CodeGramBottomSheet
import com.code.gram.core.designsystem.component.CodeGramTextField
import com.code.gram.core.designsystem.theme.Error
import com.code.gram.core.designsystem.theme.ErrorDark
import com.code.gram.core.designsystem.theme.ErrorLight
import com.code.gram.core.designsystem.theme.Success
import com.code.gram.core.designsystem.theme.SuccessDark
import com.code.gram.core.designsystem.theme.SuccessLight
import com.code.gram.core.designsystem.theme.textFieldBackground
import com.code.gram.presentation.post.component.CodeLangContent
import com.code.gram.presentation.post.component.TagItem
import com.wakaztahir.codeeditor.highlight.model.CodeLang
import com.wakaztahir.codeeditor.highlight.prettify.PrettifyParser
import com.wakaztahir.codeeditor.highlight.theme.CodeTheme
import com.wakaztahir.codeeditor.highlight.theme.CodeThemeType
import com.wakaztahir.codeeditor.highlight.utils.parseCodeAsAnnotatedString
import kotlinx.coroutines.launch

@Composable
fun PostRoute(
    paddingValues: PaddingValues,
    navigateToHome: () -> Unit,
    viewModel: PostViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    val parser = remember { PrettifyParser() }
    var themeState by remember { mutableStateOf(CodeThemeType.Default) }
    val theme = remember(themeState) { themeState.theme() }

    PostScreen(
        paddingValues = paddingValues,
        state = state,
        parser = parser,
        theme = theme,
        onTitleChange = viewModel::updateTitle,
        onContentChange = viewModel::updateContent,
        onLanguageChange = viewModel::updateLanguage,
        onCodeChange = viewModel::updateCode,
        /*onTagAdd = viewModel::addTag,
        onTagRemove = viewModel::removeTag*/
        onTagChange = viewModel::updateTag,
        onClickComplete = {
            viewModel.startJob()
            viewModel.updateComplete()
        },
        onPostComplete = {
            if (state.post.title.isNotEmpty() && state.post.description.isNotEmpty() && state.post.code.isNotEmpty()) {
                viewModel.postComplete()
            } else {
                Toast.makeText(context, "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        },
        connectWebSocket = viewModel::startJob,
        onSendInput = viewModel::sendInput,
        onInputChanged = viewModel::onInputChanged,
        navigateToHome = {
            viewModel.clearData()
            navigateToHome
        },
        showToast = {
            Toast.makeText(context, "코드를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    paddingValues: PaddingValues,
    state: PostState,
    parser: PrettifyParser,
    theme: CodeTheme,
    onTitleChange : (String) -> Unit,
    onContentChange : (String) -> Unit,
    onLanguageChange : (CodeLang) -> Unit,
    onCodeChange : (String) -> Unit,
    onTagChange : (List<String>) -> Unit,
    onClickComplete: () -> Unit,
    onPostComplete: () -> Unit,
    connectWebSocket : () -> Unit,
    onSendInput : () -> Unit,
    onInputChanged : (String) -> Unit,
    showToast : () -> Unit = {},
    navigateToHome : () -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isFullScreenEditor by remember { mutableStateOf(false) }
    var isOpenBottomSheet by remember {
        mutableStateOf(false)
    }

    if (isFullScreenEditor) {
        FullScreenCodeEditor(
            paddingValues = paddingValues,
            initialCode = state.post.code,
            parser = parser,
            theme = theme,
            language = state.post.codeLang ?: CodeLang.Java,
            onDone = { updatedCode ->
                if (updatedCode != "") {
                    onCodeChange(updatedCode)
                    isFullScreenEditor = false
                } else {
                    showToast()
                }
            },
            onCancel = {
                isFullScreenEditor = false
            }
        )
    } else {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "새 게시물",
                    modifier = Modifier
                        .padding(vertical = 16.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Row {
                    TextButton(
                        onClick = { }
                    ) {
                        Text(
                            text = "공유",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF7C9BFF)
                        )
                    }

                    TextButton(
                        onClick = if (!state.isSuccess) {
                            onPostComplete
                        } else if (state.isComplete) {
                            navigateToHome
                        } else {
                            onClickComplete
                        }
                    ) {
                        Text(
                            // 완료일 때는 서버에 postcode함 / 실행 때는 서버에 올릴 필요없이
                            text = if (!state.isSuccess) {
                                "완료"
                            } else if (state.isComplete) {
                                "종료"
                            } else {
                                "실행"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF7C9BFF)
                        )
                    }
                }
            }

            CodeGramTextField(
                text = state.post.title,
                placeholder = "제목을 입력해주세요.",
                onTextChange = onTitleChange
            )

            Spacer(modifier = Modifier.height(16.dp))

            CodeGramTextField(
                text = state.post.description,
                placeholder = "내용을 입력해주세요.",
                type = "content",
                onTextChange = onContentChange
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row (
               modifier = Modifier
                   .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Language",
                    modifier = Modifier
                )

                OutlinedButton(
                    onClick = {
                        isOpenBottomSheet = true
                    },
                    border = null,
                ) {
                    Text(
                        text = state.post.language,
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

            Spacer(modifier = Modifier.height(16.dp))

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = textFieldBackground,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable(onClick = {
                        isFullScreenEditor = !isFullScreenEditor
                    })
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                )

                Text(
                    text = if (state.post.code.isEmpty()) "코드 작성하기" else "작성 완료"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = if (state.error.isNotEmpty()) {
                            ErrorDark
                        } else {
                            SuccessDark
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(
                            color = if (state.error.isNotEmpty()) {
                                Error
                            } else {
                                Success
                            },
                            shape = CircleShape
                        )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = state.error.ifEmpty {
                        "검사 통과"
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = if (state.error.isNotEmpty()) {
                        ErrorLight
                    } else {
                        SuccessLight
                    },
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TagEditor(
                modifier = Modifier
                    .fillMaxWidth(),
                tags = state.post.tags,
                onTagsChanged = onTagChange
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "힌트: 'Java' 드롭다운은 언어 토글(탭/선택), 칩은 상태 표시 용도입니다.",
                fontSize = 10.sp,
                color = Color.White
            )

            if (state.isSuccess) {
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
                    Text(
                        text = state.codeResult.ifEmpty { "실행 결과가 여기에 표시됩니다..." },
                        color = Color(0xFF00FF00), // 터미널 느낌의 초록색 텍스트
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = state.userInput,
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

                Button(
                    onClick = { connectWebSocket() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Connect & Run")
                }
            }
        }
    }

    if (isOpenBottomSheet) {
        CodeGramBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { isOpenBottomSheet = false }
        ) {
            CodeLangContent(
                selectCodeLang = {
                    onLanguageChange(it)
                    isOpenBottomSheet = false
                }
            )
        }
    }
}

@Composable
fun FullScreenCodeEditor(
    paddingValues: PaddingValues,
    initialCode: String,
    parser: PrettifyParser,
    theme: CodeTheme,
    language: CodeLang,
    onDone: (String) -> Unit,
    onCancel: () -> Unit
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val density = LocalDensity.current

    var lineTops by remember { mutableStateOf(emptyArray<Float>()) }

    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                annotatedString = parseCodeAsAnnotatedString(
                    parser = parser,
                    theme = theme,
                    lang = language,
                    code = initialCode
                )
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.clickable(onClick = onCancel)
            )

            Text(text = "코드 에디터", color = Color.White)

            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.clickable { onDone(textFieldValue.text) }
            )
        }

        // Editor Body
        Row(
            modifier = Modifier
                .weight(1f, fill = false)
                .background(color = textFieldBackground, shape = RoundedCornerShape(8.dp))
                .verticalScroll(scrollState)
        ) {
            // Line Numbers
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .padding(horizontal = 4.dp)
            ) {
                lineTops.forEachIndexed { index, top ->
                    Text(
                        text = (index + 1).toString(),
                        color = Color.White,
                        modifier = Modifier.offset(y = with(density) { top.toDp() })
                    )
                }
            }

            // Code Input
            BasicTextField(
                value = textFieldValue,
                onValueChange = { newValue ->
                    textFieldValue = newValue.copy(
                        annotatedString = parseCodeAsAnnotatedString(
                            parser = parser,
                            theme = theme,
                            lang = CodeLang.Java,
                            code = newValue.text
                        )
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .bringIntoViewRequester(bringIntoViewRequester)
                    .padding(8.dp),
                onTextLayout = { textLayoutResult ->
                    // 줄 번호 계산
                    lineTops = Array(textLayoutResult.lineCount) { textLayoutResult.getLineTop(it) }

                    val cursorRect = textLayoutResult.getCursorRect(textFieldValue.selection.end)
                    coroutineScope.launch {
                        bringIntoViewRequester.bringIntoView(cursorRect)
                    }
                }
            )
        }
    }
}


@Composable
fun TagEditor(
    modifier: Modifier = Modifier,
    tags: List<String>,
    onTagsChanged: (List<String>) -> Unit
) {
    var currentInput by remember { mutableStateOf("") }

    // 백스페이스 키로 마지막 태그를 삭제하는 로직
    /*val onBackspacePressed = {
        if (currentInput.isEmpty() && tags.isNotEmpty()) {
            onTagsChanged(tags.dropLast(1))
        }
    }*/

    BasicTextField(
        value = currentInput,
        onValueChange = {
            // 스페이스바를 누르면 태그 추가
            if (it.endsWith(" ")) {
                val newTag = it.trim()
                if (newTag.isNotBlank() && !tags.contains(newTag)) {
                    onTagsChanged(tags + newTag)
                }
                currentInput = "" // 입력창 비우기
            } else {
                currentInput = it
            }
        },
        modifier = modifier
            .background(color = textFieldBackground, shape = RoundedCornerShape(8.dp))
            /*.onKeyEvent {
                if (it.type == KeyEventType.KeyUp && it.key == Key.Backspace) {
                    onBackspacePressed()
                    true // 이벤트 소비
                } else {
                    false
                }
            },*/,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                val newTag = currentInput.trim()
                if (newTag.isNotBlank() && !tags.contains(newTag)) {
                    onTagsChanged(tags + newTag)
                }
                currentInput = "" // 입력창 비우기
            }
        ),
        decorationBox = { innerTextField ->
            // FlowRow를 사용하여 태그가 많아지면 자동 줄바꿈
            FlowRow(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 기존 태그들을 표시
                tags.forEach { tag ->
                    TagItem(
                        text = tag,
                        onRemove = {
                            onTagsChanged(tags - tag)
                        }
                    )
                }
                // 텍스트 입력창
                Box(modifier = Modifier.align(Alignment.CenterVertically)) {
                    if (tags.isEmpty() && currentInput.isEmpty()) {
                        Text("#태그를 입력하세요", color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        }
    )
}