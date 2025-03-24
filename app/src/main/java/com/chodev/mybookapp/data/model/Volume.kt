package com.chodev.mybookapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Volume(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val bookId: Int,   // FK เชื่อมกับ Book
    var volumeName: String
)
