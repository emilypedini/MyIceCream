package com.example.myicecream.ui.screen.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myicecream.data.database.NotificationEntity
import com.example.myicecream.data.repositories.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotificationsViewModel(private val notificationRepository: NotificationRepository,
    private val userId: Int): ViewModel() {

    private val _expandedNotStates =
        MutableStateFlow<Map<Int, Boolean>>(emptyMap())

    val expandedNotStates = _expandedNotStates.asStateFlow()


    val notifications = notificationRepository.getNotifications(userId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    val unreadNotificationCount = notificationRepository.getUnreadCount(userId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(),
            0)


    fun onNotificationClick(notification: NotificationEntity) {
        val current = _expandedNotStates.value[notification.idNot] ?: false

        _expandedNotStates.value =
            _expandedNotStates.value.toMutableMap().apply {
                put(notification.idNot, !current)
            }

        if (!notification.isRead && !current) {
            viewModelScope.launch {
                notificationRepository.markNotiAsRead(notification.idNot)
            }
        }
    }

    fun deleteNotification(notification: NotificationEntity) {
        viewModelScope.launch {
            notificationRepository.deleteNotification(notification.idNot)
        }
    }
}