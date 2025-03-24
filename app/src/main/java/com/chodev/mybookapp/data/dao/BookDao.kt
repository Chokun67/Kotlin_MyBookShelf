package com.chodev.mybookapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chodev.mybookapp.data.model.Book

@Dao
interface BookDao {
    @Query("SELECT * FROM Book")
    fun getAllBooks(): LiveData<List<Book>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Update
    suspend fun updateBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)
}
