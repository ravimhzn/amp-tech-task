package com.ravimhzn.amp.task.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ravimhzn.amp.framework.State
import com.ravimhzn.amp.task.model.AccountsResponse
import com.ravimhzn.amp.task.model.Amount
import com.ravimhzn.amp.task.model.FeedItem
import com.ravimhzn.amp.task.model.TransactionResponse
import com.ravimhzn.amp.task.navigation.AmpRoute
import com.ravimhzn.amp.task.navigation.TRANSACTION_DETAIL_KEY
import com.ravimhzn.amp.task.viewModel.MainViewModel
import com.ravimhzn.amp.ui.theme.BaseAmpTheme
import com.ravimhzn.amp.util.ACCOUNT_TYPE
import com.ravimhzn.amp.util.DimensionUtil.contentSpacing
import com.ravimhzn.amp.util.DimensionUtil.contentSpacingMedium
import com.ravimhzn.amp.util.DimensionUtil.contentSpacingSmall
import com.ravimhzn.amp.util.DimensionUtil.contentSpacingTiny
import com.ravimhzn.amp.util.REFERENCE
import com.ravimhzn.amp.util.TOOLBAR_HOME_TRANSACTION
import com.ravimhzn.amp.util.TRANSFER_FUNDS

@Composable
fun MainViewController(
    navHostController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.uiState
        .collectAsStateWithLifecycle(initialValue = State.Empty)

    LaunchedEffect(Unit) {
        viewModel.getAccountDetail()
    }


    MainView(
        navHostController,
        state,
        viewModel.accountsResponse.value,
        viewModel.transactionResponse.value,
        viewModel.errorMessage.value
    )
}

@Composable
fun MainView(
    navHostController: NavHostController,
    state: State,
    accounts: AccountsResponse?,
    transactions: TransactionResponse?,
    errorMessage: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Toolbar(TOOLBAR_HOME_TRANSACTION)

        when (state) {
            is State.Error -> {
                Text(
                    modifier = Modifier.padding(contentSpacing),
                    text = errorMessage,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            else -> {
                if (transactions == null) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .testTag("progressBar")
                        )
                    }

                } else {
                    Spacer(modifier = Modifier.height(contentSpacingMedium))
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(start = contentSpacing),
                            text = String.format(ACCOUNT_TYPE, accounts?.accounts[0]?.name),
                            style = MaterialTheme.typography.labelSmall
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            modifier = Modifier
                                .padding(end = contentSpacing)
                                .clickable {
                                    navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                        TRANSACTION_DETAIL_KEY,
                                        transactions
                                    )
                                    navHostController.navigate(AmpRoute.TransferAndSaveScreen.route)
                                },
                            text = TRANSFER_FUNDS,
                            style = MaterialTheme.typography.labelLarge,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                    Spacer(modifier = Modifier.height(contentSpacingMedium))
                    val feedList = transactions.feedItems
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(feedList.size) { index ->
                            TransactionListView(feedList[index])
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionListView(feed: FeedItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = contentSpacingSmall, horizontal = contentSpacing)
    ) {
        Text(
            text = feed.getFormattedTransactionDate(),
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = Color.Gray
        )

        Text(
            text = String.format(REFERENCE, feed.reference),
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(contentSpacingTiny))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = feed.source,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "£${feed.amount.convertMinorUnitsToPounds()}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFB32525)
            )
        }

        Spacer(modifier = Modifier.height(contentSpacingSmall))
        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
    }
}


@Preview(showBackground = true)
@Composable
fun TransactionListViewPreview() {
    BaseAmpTheme {
        TransactionListView(transactionResponse.feedItems[0])
    }
}

@Preview(showBackground = true)
@Composable
fun MainViewPreview() {

    BaseAmpTheme {
        MainView(rememberNavController(), State.Loading, null, transactionResponse, "")
    }
}

val transactionResponse =
    TransactionResponse(
        listOf(
            FeedItem(
                Amount("", 109),
                source = "Some Source",
                reference = "Some Reference",
                transactionTime = "2025-03-28T00:05:07.118Z"
            ),
            FeedItem(
                Amount("", 1090),
                source = "Some Source",
                reference = "Some Reference",
                transactionTime = "2025-03-26T00:05:07.118Z"
            )
        )
    )
