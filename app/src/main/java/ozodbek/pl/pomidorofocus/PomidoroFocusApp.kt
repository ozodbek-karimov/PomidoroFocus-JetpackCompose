package ozodbek.pl.pomidorofocus

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import ozodbek.pl.pomidorofocus.utils.NotificationConstants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PomidoroFocusApp : Application() {

	private val notificationManager by lazy { getSystemService<NotificationManager>() }
	@RequiresApi(Build.VERSION_CODES.O)
	override fun onCreate() {
		super.onCreate()

		val channelId = NotificationConstants.CHANNEL_ID
		val channelName = NotificationConstants.CHANNEL_NAME
		val channelDescription = NotificationConstants.CHANNEL_DESCRIPTION

		val channel = NotificationChannel(
			channelId,
			channelName,
			NotificationManager.IMPORTANCE_HIGH
		).apply {
			description = channelDescription
			setShowBadge(false)
			lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
		}
		notificationManager?.createNotificationChannel(channel)
	}
}