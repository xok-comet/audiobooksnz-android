package com.audiobookz.nz.app.mylibrary.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LocalBookDataDao {

    @Transaction
    @Query("SELECT * FROM localbook ")
    fun getLocalBookData(): LiveData<List<LocalBookData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalBookData(localBookData: LocalBookData)

    @Query("DELETE FROM localbook WHERE id = :id")
    fun deleteById(id:Int)
}