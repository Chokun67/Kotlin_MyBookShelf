package com.chodev.mybookapp.data.repository

import androidx.lifecycle.LiveData
import com.chodev.mybookapp.data.dao.BookDao
import com.chodev.mybookapp.data.model.Book

class BookRepository(private val bookDao: BookDao) {

    fun getAllBooks(): LiveData<List<Book>> = bookDao.getAllBooks()

    suspend fun insertBook(book: Book) {
        bookDao.insertBook(book)
    }

    suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }

    suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(book)
    }
}
