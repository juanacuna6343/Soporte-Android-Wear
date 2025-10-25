package com.example.photorating

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.app.PendingIntent.FLAG_IMMUTABLE

class NotificationService(private val context: Context) {
    companion object {
        private const val CHANNEL_ID = "photo_rating_channel"
        private const val NOTIFICATION_ID = 1
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Photo Rating"
            val descriptionText = "Notifications for photo ratings"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showRatingNotification(raterUsername: String, photoId: String) {
        // Intent for viewing profile
        val viewProfileIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            action = "VIEW_PROFILE"
        }
        val viewProfilePendingIntent = PendingIntent.getActivity(
            context, 0, viewProfileIntent, FLAG_IMMUTABLE
        )

        // Intent for follow/unfollow action
        val followIntent = Intent(context, NotificationActionReceiver::class.java).apply {
            action = "FOLLOW_ACTION"
            putExtra("username", raterUsername)
        }
        val followPendingIntent = PendingIntent.getBroadcast(
            context, 1, followIntent, FLAG_IMMUTABLE
        )

        // Intent for viewing user profile
        val viewUserIntent = Intent(context, UserProfileActivity::class.java).apply {
            putExtra("username", raterUsername)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val viewUserPendingIntent = PendingIntent.getActivity(
            context, 2, viewUserIntent, FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("New Rating!")
            .setContentText("$raterUsername rated your photo")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(viewProfilePendingIntent)
            .addAction(
                R.drawable.ic_profile,
                "View My Profile",
                viewProfilePendingIntent
            )
            .addAction(
                R.drawable.ic_follow,
                "Follow/Unfollow",
                followPendingIntent
            )
            .addAction(
                R.drawable.ic_user,
                "View User",
                viewUserPendingIntent
            )
            .extend(
                NotificationCompat.WearableExtender()
                    .setContentIntentAvailableOffline(true)
            )
            .build()

        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, notification)
        }
    }
}