package cz.cvut.fel.zan.movielibrary.ui.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import cz.cvut.fel.zan.movielibrary.MainActivity
import cz.cvut.fel.zan.movielibrary.R

class NotificationUtils : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val movieTitle = intent.getStringExtra("movie_title") ?: return
        showMovieAddedNotification(context, movieTitle)
    }
}

fun scheduleMovieNotification(
    context: Context,
    movieTitle: String
) {
    val intent = Intent(context, NotificationUtils::class.java).apply {
        putExtra("movie_title", movieTitle)
        action = "cz.cvut.fel.zan.MOVIE_NOTIFICATION_${movieTitle.hashCode()}"
    }
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        movieTitle.hashCode(),
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (!alarmManager.canScheduleExactAlarms()) {
            Log.w("NotificationUtils", "Cannot schedule exact alarms")
            return
        }
    }
    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        System.currentTimeMillis(),
        pendingIntent
    )
}

fun showMovieAddedNotification(
    context: Context,
    movieTitle: String
) {
    if (!hasNotificationPermission(context)) {
        Log.w("NotificationUtils", "Notification permission not granted")
        return
    }
    try {
        // Create a notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "movie_added_channel",
                "Add a new movie",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notification for newly added movie"
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        // Create a notification
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("open_movie", movieTitle)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            movieTitle.hashCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notification = NotificationCompat
            .Builder(context, "movie_added_channel")
            .setSmallIcon(R.drawable.popcorn)
            .setContentTitle("New movie added to library")
            .setContentText("$movieTitle is newly added to library!!!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setFullScreenIntent(pendingIntent, true)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .build()
        // Display the notification
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify((movieTitle + System.currentTimeMillis()).hashCode(), notification)
    } catch (e: SecurityException) {
        Log.e("NotificationUtils","Something wrong: ${e.message}")
    }
}

private fun hasNotificationPermission(context: Context) : Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        NotificationManagerCompat.from(context).areNotificationsEnabled()
    } else {
        true
    }
}
