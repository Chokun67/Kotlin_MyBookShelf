package com.chodev.mybookapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chodev.mybookapp.data.model.Volume

@Dao
interface VolumeDao {
    @Query("SELECT * FROM Volume WHERE bookId = :bookId")
    fun getVolumesByBookId(bookId: Int): LiveData<List<Volume>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVolume(volume: Volume)

    @Delete
    suspend fun deleteVolume(volume: Volume)
}
