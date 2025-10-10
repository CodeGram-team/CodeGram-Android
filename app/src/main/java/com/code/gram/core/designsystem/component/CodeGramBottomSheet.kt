package com.code.gram.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.code.gram.core.designsystem.theme.background
import com.code.gram.core.designsystem.theme.textFieldBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeGramBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (sheetState: SheetState) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        containerColor = textFieldBackground,
        modifier = modifier
            .fillMaxWidth(),
        dragHandle = null,
    ) {
        content(sheetState)
    }
}