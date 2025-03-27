package com.chodev.mybookapp.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chodev.mybookapp.data.database.AppDatabase
import com.chodev.mybookapp.data.model.Book
import com.chodev.mybookapp.data.repository.BookRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val bookRepository: BookRepository
    val bookList: LiveData<List<Book>>

    private val _filteredBooks = MutableLiveData<List<Book>>()
    val filteredBooks: LiveData<List<Book>> get() = _filteredBooks

    private var currentBooks: List<Book> = listOf()

    init {
        val db = AppDatabase.getInstance(application)
        val bookDao = db.bookDao()
        bookRepository = BookRepository(bookDao)
        bookList = bookRepository.getAllBooks()

        bookList.observeForever { books ->
            currentBooks = books
            _filteredBooks.value = books // เริ่มต้นแสดงทั้งหมด
        }
    }

    fun addBook(title: String) {
        viewModelScope.launch {
            val book = Book(title = title)
            bookRepository.insertBook(book)
        }
    }

    fun updateBook(book: Book, newTitle: String) {
        viewModelScope.launch {
            book.title = newTitle
            bookRepository.updateBook(book)
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch {
            bookRepository.deleteBook(book)
        }
    }

    fun filterBooks(query: String) {
        _filteredBooks.value = if (query.isBlank()) {
            currentBooks
        } else {
            currentBooks.filter { it.title.contains(query, ignoreCase = true) }
        }
    }

}
