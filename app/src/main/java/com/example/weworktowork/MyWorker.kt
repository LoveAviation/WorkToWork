package com.example.weworktowork

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class MyWorker(appContext: Context, workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val CHANNEL_ID = "CHANNEL_ID"
        const val NOTIFICATION_ID = 1
    }

    override suspend fun doWork(): Result {
        setForeground(createForegroundInfo())

        for (i in 1..10000) {
            delay(1)
            if (isStopped) {
                updateNotification("Worker Stopped")
                return Result.success()
            }
        }

        updateNotification("Worker Finished")
        return Result.success()
    }

    private fun createForegroundInfo(): ForegroundInfo {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Worker Notification Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val notification = createNotification("Worker Started")
        return ForegroundInfo(NOTIFICATION_ID, notification)
    }


    private fun createNotification(contentText: String): Notification {
        return NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle("MyWorker")
            .setContentText(contentText)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    private fun updateNotification(contentText: String) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = createNotification(contentText)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}