package com.chodev.mybookapp.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.chodev.mybookapp.data.database.AppDatabase
import com.chodev.mybookapp.data.model.Volume
import com.chodev.mybookapp.data.repository.VolumeRepository
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val volumeRepository: VolumeRepository

    // จะผูก bookId -> volumeList
    private val _bookId = MutableLiveData<Int>()
    val volumeList: LiveData<List<Volume>>

    init {
        val db = AppDatabase.getInstance(application)
        volumeRepository = VolumeRepository(db.volumeDao())

        // switchMap เมื่อ bookId เปลี่ยน เราจะโหลด volumeList ใหม่
        volumeList = _bookId.switchMap { id ->
            volumeRepository.getVolumesByBookId(id)
        }
    }

    fun setBookId(id: Int) {
        _bookId.value = id
    }

    fun addVolume(bookId: Int, volumeName: String) {
        viewModelScope.launch {
            val volume = Volume(
                bookId = bookId,
                volumeName = volumeName
            )
            volumeRepository.insertVolume(volume)
        }
    }

    fun deleteVolume(volume: Volume) {
        viewModelScope.launch {
            volumeRepository.deleteVolume(volume)
        }
    }
}
