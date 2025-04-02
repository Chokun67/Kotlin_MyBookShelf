package com.chodev.mybookapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Book::class,
            parentColumns = ["id"],
            childColumns = ["bookId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Volume(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val bookId: Int,   // FK เชื่อมกับ Book
    var volumeName: String
)
