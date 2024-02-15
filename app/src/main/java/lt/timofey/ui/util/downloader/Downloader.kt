package lt.timofey.ui.util.downloader

interface Downloader {
    fun downloadImage(url: String?): Long
}