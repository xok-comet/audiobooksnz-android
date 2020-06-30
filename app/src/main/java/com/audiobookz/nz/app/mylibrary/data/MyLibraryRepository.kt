package com.audiobookz.nz.app.mylibrary.data

import com.audiobookz.nz.app.bookdetail.data.BookDetailRemoteDataSource
import com.audiobookz.nz.app.data.*
import com.audiobookz.nz.app.util.DOWNLOAD_COMPLETE
import io.audioengine.mobile.*
import javax.inject.Inject

class MyLibraryRepository @Inject constructor(
    private val remoteSource: MyLibraryRemoteDataSource,
    private val sessionDataDao: SessionDataDao,
    private val audioEngineDataSource: AudioEngineDataSource,
    private val bookDetailDataSource: BookDetailRemoteDataSource,
    private val localBookDataDao: LocalBookDataDao
) {
    fun getCloudBook(Page: Int, PageSize: Int) = resultFetchOnlyLiveData(
        networkCall = { remoteSource.getCloudBook(Page, PageSize) }
    )

    val getLocalbook = resultLocalGetOnlyLiveData(
        databaseQuery = { localBookDataDao.getLocalBookData() }
    )

    fun saveDetailBook(
        id: Int,
        title: String,
        licenseId: String,
        imageUrl: String?,
        authors: String,
        narrators: String
    ) = resultLocalSaveOnlyLiveData(
        saveCallResult = {
            localBookDataDao.insertLocalBookData(
                LocalBookData(
                    id,
                    title,
                    imageUrl,
                    licenseId,
                    narrators,
                    authors
                )
            )
        }
    )


    fun getSession() = resultLiveData(
        networkCall = { remoteSource.fetchSession() },
        saveCallResult = { sessionDataDao.insertSessionData(it) },
        databaseQuery = { sessionDataDao.getSessionData() }
    )

    fun downloadAudiobook(
        callback: (DownloadEvent) -> Unit,
        contentId: String,
        licenseId: String,
        title: String,
        imageUrl: String?,
        authors: String,
        narrators: String
    ) = resulObservableData(
        networkCall = audioEngineDataSource.download(contentId, licenseId),
        onDownloading = { callback(it) },
        onPartComplete = { localBookDataDao.insertLocalBookData(LocalBookData(it.content?.id?.toInt()!!,title,imageUrl,licenseId,narrators,authors))},
        onComplete = {audioEngineDataSource.notifySimpleNotification(title, DOWNLOAD_COMPLETE)},
        onDataError = {}
    )

    fun playAudioBook(
        chapterNumber: Int,
        contentId: String,
        licenseId: String,
        partNumber: Int,
        position: Long,
        callback: (PlaybackEvent) -> Unit
    ) = resulObservableData(
        networkCall = audioEngineDataSource.playAudiobook(
            contentId,
            licenseId,
            partNumber,
            chapterNumber,
            position
        ),
        onDownloading = { callback(it) },
        onPartComplete = {},
        onComplete = {},
        onDataError = {}
    )

    fun pauseAudioBook() = audioEngineDataSource.pause()

    fun resumeAudioBook() = audioEngineDataSource.resume()

    fun getChapterBooks(contentId: String, callback: (List<Chapter>) -> Unit ) =
        resulObservableData(
            networkCall = audioEngineDataSource.getChapterLists(contentId),
            onDownloading = { callback(it)},
            onPartComplete = {},
            onComplete = {},
            onDataError = {}
    )

    fun getPlayerState(callback:(PlayerState) -> Unit) =
        resulObservableData(
        networkCall = audioEngineDataSource.getPlayerState(),
        onDownloading = { callback(it)},
        onPartComplete = {},
        onComplete = {},
        onDataError = {}
    )

    fun nextChapterOfBook() = audioEngineDataSource.nextChapter()

    fun previousChapterOfBook() = audioEngineDataSource.previousChapter()

    fun setSpeed(speed:Float) = audioEngineDataSource.setSpeed(speed)

    fun isPlaying() = audioEngineDataSource.isPlaying()

    fun getPosition() = audioEngineDataSource.getPosition()

    fun seekTo(position: Long) = audioEngineDataSource.seekTo(position)

    fun getCurrentChapter() = audioEngineDataSource.getCurrentChapter()

    fun getCurrentSpeed() = audioEngineDataSource.getCurrentSpeed()

    fun getContentStatus(callback: (DownloadStatus) -> Unit, contentId: String) =
        resulObservableData(
            networkCall = audioEngineDataSource.getContentStatus(contentId),
            onDownloading = { callback(it) },
            onPartComplete = {},
            onComplete = {},
            onDataError = {}
        )

    fun deleteAudiobook(contentId: String, licenseId: String) {
        localBookDataDao.deleteById(contentId.toInt())
        audioEngineDataSource.delete(contentId, licenseId);
    }

    fun cancelDownload(downloadId: String) =
        audioEngineDataSource.cancelDownload(downloadId)

    fun getLocalBookList(callback: (List<String>) -> Unit, status: DownloadStatus) =
        resulObservableData(
            networkCall = audioEngineDataSource.getLocalBook(status),
            onDownloading = { callback(it) },
            onPartComplete = {},
            onComplete = {},
            onDataError = {}
        )

}