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
    private val _bookId = MutableLiveData<Int>()
    private val volumeList: LiveData<List<Volume>>

    private val _filteredVolumeList = MutableLiveData<List<Volume>>()
    val filteredVolumeList: LiveData<List<Volume>> get() = _filteredVolumeList

    private var currentVolumes: List<Volume> = listOf()

    init {
        val db = AppDatabase.getInstance(application)
        volumeRepository = VolumeRepository(db.volumeDao())

        volumeList = _bookId.switchMap { id ->
            volumeRepository.getVolumesByBookId(id)
        }

        // observe volumeList to keep original data
        volumeList.observeForever { volumes ->
            currentVolumes = volumes
            _filteredVolumeList.value = volumes
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

    fun filterVolumes(query: String) {
        _filteredVolumeList.value = if (query.isBlank()) {
            currentVolumes
        } else {
            currentVolumes.filter {
                it.volumeName.contains(query, ignoreCase = true)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        volumeList.removeObserver { }
    }
}
