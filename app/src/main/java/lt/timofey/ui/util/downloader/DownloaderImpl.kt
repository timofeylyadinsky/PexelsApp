package lt.timofey.ui.util.downloader

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri
import lt.timofey.BuildConfig

class DownloaderImpl(
    private val context: Context
) : Downloader {
    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadImage(url: String?): Long {
        Log.d("!!!!", url.toString())
        if (url != null) {
            val request = DownloadManager.Request(url.toUri())
                .setMimeType("image/jpeg")
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setTitle("image.jpeg")
                .addRequestHeader("Authorization", BuildConfig.API_KEY)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "image.jpg")
            return downloadManager.enqueue(request)
        } else return -1
    }
}