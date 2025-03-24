package com.chodev.mybookapp.data.repository

import androidx.lifecycle.LiveData
import com.chodev.mybookapp.data.dao.VolumeDao
import com.chodev.mybookapp.data.model.Volume

class VolumeRepository(private val volumeDao: VolumeDao) {

    fun getVolumesByBookId(bookId: Int): LiveData<List<Volume>> = volumeDao.getVolumesByBookId(bookId)

    suspend fun insertVolume(volume: Volume) {
        volumeDao.insertVolume(volume)
    }

    suspend fun deleteVolume(volume: Volume) {
        volumeDao.deleteVolume(volume)
    }
}
