package com.example.myicecream.ui.screen.notifications

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*


@Composable
fun NotificationScreen(viewModel: NotificationsViewModel) {

    val notifications by viewModel.notifications.collectAsState()
    val expandedStates by viewModel.expandedNotStates.collectAsState()

    LazyColumn {
        items(
            items = notifications,
            key = { it.idNot }
        ) { noti ->

            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { value ->
                    if (value == SwipeToDismissBoxValue.EndToStart) {
                        viewModel.deleteNotification(noti)
                        true
                    } else false
                }
            )

            SwipeToDismissBox(
                state = dismissState,
                backgroundContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.CenterEnd
                    ){}
                }
            ) {
                val isExpanded = expandedStates[noti.idNot] ?: false

                NotificationItem(
                    notification = noti,
                    isExpanded = isExpanded,
                    onClick = {
                        viewModel.onNotificationClick(noti)
                    }
                )
            }
        }
    }
}

