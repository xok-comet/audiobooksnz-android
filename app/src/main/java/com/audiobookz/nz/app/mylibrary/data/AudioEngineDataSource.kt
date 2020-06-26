package com.audiobookz.nz.app.mylibrary.data

import com.audiobookz.nz.app.api.BookEngineService
import com.audiobookz.nz.app.api.NotificationService
import io.audioengine.mobile.DownloadStatus
import javax.inject.Inject

class AudioEngineDataSource @Inject constructor(private val bookEngineService: BookEngineService, private val notificationService: NotificationService) {

    fun download(contentId: String, licenseId: String) = bookEngineService.download(contentId,licenseId)
    fun delete(contentId: String, licenseId: String) = bookEngineService.delete(contentId, licenseId)
    fun cancelDownload(contentId: String, licenseId: String) = bookEngineService.cancelDownload(contentId, licenseId)
    fun notifySimpleNotification(title:String, body:String)=notificationService.simple(title,body)
    fun getLocalBook(status: DownloadStatus)= bookEngineService.getLocalBook(status)
    fun getContentStatus(contentId :String) = bookEngineService.contentStatus(contentId)
    fun playAudiobook(contentId:String,licenseId: String, partNumber: Int,chapterNumber:Int,position:Long)= bookEngineService.play(contentId,licenseId,partNumber,chapterNumber,position)
    fun pause() = bookEngineService.pausePlay()
    fun resume() = bookEngineService.resumePlay()
    fun nextChapter() = bookEngineService.nextChapter()
    fun previousChapter() = bookEngineService.previousChapter()
    fun setSpeed(speed:Float) = bookEngineService.setSpeed(speed)
    fun getChapterLists(contentId :String) = bookEngineService.getChapterLists(contentId)
    fun isPlaying() = bookEngineService.isPlaying()
    fun getPosition() = bookEngineService.getPosition()
    fun seekTo(position: Long) = bookEngineService.seekTo(position)
    fun getPlayerState() = bookEngineService.getPlayerState()
}