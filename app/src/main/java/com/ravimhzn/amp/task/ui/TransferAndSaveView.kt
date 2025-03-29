package com.ravimhzn.amp.task.ui

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ravimhzn.amp.task.model.TransactionResponse
import com.ravimhzn.amp.ui.theme.Purple40
import com.ravimhzn.amp.util.Callable
import com.ravimhzn.amp.util.DimensionUtil.buttonHeight
import com.ravimhzn.amp.util.DimensionUtil.contentSpacing
import com.ravimhzn.amp.util.DimensionUtil.contentSpacingLarge
import com.ravimhzn.amp.util.DimensionUtil.contentSpacingSmall
import com.ravimhzn.amp.util.DimensionUtil.dialogIconSize
import com.ravimhzn.amp.util.DimensionUtil.textSizeBody
import com.ravimhzn.amp.util.DimensionUtil.textSizeExtraLarge
import com.ravimhzn.amp.util.LOG_INFO
import com.ravimhzn.amp.util.Logger
import com.ravimhzn.amp.util.TOOLBAR_HOME_FUNDS_TRANSFERRED
import com.ravimhzn.amp.util.TRANSFER_FUNDS_DIALOG_BUTTON
import com.ravimhzn.amp.util.TRANSFER_FUNDS_DIALOG_DESC
import com.ravimhzn.amp.util.TRANSFER_FUNDS_DIALOG_FROM
import com.ravimhzn.amp.util.TRANSFER_FUNDS_DIALOG_TO
import com.ravimhzn.amp.util.TRANSFER_FUNDS_DIALOG_Title
import com.ravimhzn.amp.util.TRANSFER_FUNDS_FIELD_HINT
import com.ravimhzn.amp.util.TRANSFER_FUNDS_MESSAGE
import com.ravimhzn.amp.util.TRANSFER_FUNDS_MESSAGE_NO_TRANSACTION
import com.ravimhzn.amp.util.TRANSFER_FUNDS_SUCCESS
import com.ravimhzn.amp.util.TRANSFER_FUNDS_TRANSFERRED
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@Composable
fun TransferAndSaveView(navHostController: NavHostController, data: TransactionResponse?) {
    val context = LocalContext.current

    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var startDateFormatted by remember { mutableStateOf("") }
    var endDateFormatted by remember { mutableStateOf("") }
    var transferMessage by remember { mutableStateOf("") }
    var roundUpData by remember { mutableStateOf<BigDecimal>(BigDecimal.ZERO) }
    val showDialog = remember { mutableStateOf(false) }

    LaunchedEffect(startDate, endDate) {
        val start = startDate.takeIf { it.isNotEmpty() }?.let { getFormattedDate(it) }
        val end = endDate.takeIf { it.isNotEmpty() }?.let { getFormattedDate(it) }

        startDateFormatted = start ?: ""
        endDateFormatted = end ?: ""

        if (start != null && end != null) {
            val check = data?.calculateRoundUpForDateRange(startDate, endDate)
            check?.first?.let {
                roundUpData = check.second
                transferMessage = if (it > 0) {
                    String.format(TRANSFER_FUNDS_MESSAGE, check.first, check.second)
                } else {
                    TRANSFER_FUNDS_MESSAGE_NO_TRANSACTION
                }
            }

        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (showDialog.value) {
            FundsTransferredDialog(roundUpData.toString()) { showDialog.value = false }
        }

        Column(
            Modifier
                .fillMaxSize()

        ) {
            Toolbar(TOOLBAR_HOME_FUNDS_TRANSFERRED, true) {
                navHostController.popBackStack()
            }

            Box(
                Modifier
                    .fillMaxSize()
                    .padding(contentSpacing)
            ) {

                Column {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "",
                        tint = Purple40,
                        modifier = Modifier.size(dialogIconSize)
                    )
                    Spacer(modifier = Modifier.height(contentSpacingSmall))
                    Text(
                        TRANSFER_FUNDS_DIALOG_Title,
                        fontSize = textSizeExtraLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        TRANSFER_FUNDS_DIALOG_DESC,
                        fontSize = textSizeBody,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(contentSpacing))

                    Text(TRANSFER_FUNDS_DIALOG_FROM, fontWeight = FontWeight.SemiBold)

                    DateInputField(
                        value = startDateFormatted,
                        onClick = { showDatePickerDialog(context) { startDate = it } })

                    Spacer(modifier = Modifier.height(contentSpacing))

                    Text(TRANSFER_FUNDS_DIALOG_TO, fontWeight = FontWeight.SemiBold)

                    DateInputField(
                        value = endDateFormatted,
                        onClick = { showDatePickerDialog(context) { endDate = it } })

                    Spacer(modifier = Modifier.height(contentSpacingLarge))

                    if (transferMessage.isNotEmpty()) {
                        Text(
                            transferMessage,
                            fontSize = textSizeBody,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(contentSpacingLarge))
                    }

                    Button(
                        onClick = { showDialog.value = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(buttonHeight),
                        shape = RoundedCornerShape(contentSpacingSmall),
                        enabled = roundUpData != BigDecimal.ZERO
                    ) {
                        Text(
                            TRANSFER_FUNDS_DIALOG_BUTTON,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun DateInputField(value: String, onClick: () -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "",
                modifier = Modifier.clickable { onClick() }
            )
        },
        placeholder = { Text(TRANSFER_FUNDS_FIELD_HINT) }
    )
}

@Composable
fun FundsTransferredDialog(
    value: String,
    onClose: Callable<Boolean>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)), // Dims the background
        contentAlignment = Alignment.Center
    ) {
        Dialog(
            onDismissRequest = { onClose.call(true) }
        ) {
            Box(
                Modifier
                    .padding(contentSpacing)
                    .shadow(
                        contentSpacingSmall,
                        RoundedCornerShape(contentSpacingSmall)
                    )
                    .background(
                        Color.White,
                        RoundedCornerShape(contentSpacingSmall)
                    )
            ) {
                Column(Modifier.padding(contentSpacing)) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = String.format(TRANSFER_FUNDS_SUCCESS, value),
                        style = MaterialTheme.typography.titleMedium.copy(
                            textAlign = TextAlign.Center
                        )
                    )

                    Spacer(modifier = Modifier.height(contentSpacing))

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = String.format(TRANSFER_FUNDS_TRANSFERRED, value),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            textAlign = TextAlign.Center
                        )
                    )

                    Spacer(modifier = Modifier.height(contentSpacing))

                    Button(
                        onClick = { onClose.call(true) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(buttonHeight),
                        shape = RoundedCornerShape(contentSpacingSmall),
                    ) {
                        Text(
                            "Close",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

}

fun getFormattedDate(date: String): String {
    var formattedDate = ""
    val inputFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC") // Ensure correct timezone handling
        }
    val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    try {
        formattedDate =
            inputFormat.parse(date)?.let { outputFormat.format(it) } ?: ""
    } catch (e: Exception) {
        Logger.e(LOG_INFO, "Date parsing error: ${e.message}")
    }
    return formattedDate
}

fun showDatePickerDialog(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    DatePickerDialog(
        context,
        { _, year, month, day ->
            calendar.set(year, month, day)
            onDateSelected(dateFormat.format(calendar.time))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}


@Preview(showBackground = true)
@Composable
fun TransferAndSaveViewPreview() {
    TransferAndSaveView(rememberNavController(), null)
}

@Composable
@Preview
fun PreviewDialog() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        FundsTransferredDialog("10") {}
    }
}
