package com.ravimhzn.amp.task.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.ravimhzn.amp.ui.theme.BaseAmpTheme
import com.ravimhzn.amp.util.DimensionUtil.contentSpacingExtraSmall
import com.ravimhzn.amp.util.DimensionUtil.textSizeExtraLarge
import com.ravimhzn.amp.util.DimensionUtil.toolbarHeight

@Composable
fun Toolbar(
    toolbarTitle: String = "",
    displayBackIcon: Boolean = false,
    onBackClick: () -> Unit = {},
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(toolbarHeight)
                .padding(horizontal = contentSpacingExtraSmall)
        ) {
            if (displayBackIcon) {
                IconButton(modifier = Modifier.fillMaxHeight(), onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = toolbarTitle,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = textSizeExtraLarge,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        HorizontalDivider()
    }
}

@Preview
@Composable
fun DasaiToolbarPreview() {
    BaseAmpTheme {
        Column {
            Toolbar("TransactionList", false) {}
            Toolbar("Funds Transferred", true) {}
        }
    }
}
