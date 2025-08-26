package com.ssafy.common.manager

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import timber.log.Timber


class FCMTokenManagerImpl(): FCMTokenManager {

    override suspend fun getToken(): String? {
        return try {
            val token = FirebaseMessaging.getInstance().token.await()
            token
        } catch (e: Exception) {
            Timber.e(e, "Failed to get FCM token")
            null
        }
    }

    override fun deleteToken() {
        try {
            FirebaseMessaging.getInstance().deleteToken()
        } catch (e: Exception) {
            Timber.e(e, "Failed to delete FCM token")
        }
    }

    override suspend fun subscribeToTopic(topic: String) {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(topic).await()
            Timber.d("Subscribed to topic: $topic")
        } catch (e: Exception) {
            Timber.e(e, "Failed to subscribe to topic: $topic")
        }
    }

    override suspend fun unsubscribeFromTopic(topic: String) {
        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(topic).await()
            Timber.d("Unsubscribed from topic: $topic")
        } catch (e: Exception) {
            Timber.e(e, "Failed to unsubscribe from topic: $topic")
        }
    }
}
