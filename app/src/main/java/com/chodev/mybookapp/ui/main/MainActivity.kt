package com.chodev.mybookapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.chodev.mybookapp.R
import com.chodev.mybookapp.data.model.Book
import com.chodev.mybookapp.databinding.ActivityMainBinding
import com.chodev.mybookapp.ui.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ใช้ DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = mainViewModel

        setupRecyclerView()
        observeViewModel()

        binding.btnAddBook.setOnClickListener {
            // เปิด Dialog เพื่อเพิ่มหนังสือใหม่
            val dialog = EditBookDialog(this) { newTitle ->
                mainViewModel.addBook(newTitle)
            }
            dialog.show()
        }
    }

    private fun setupRecyclerView() {
        // **ต้องกำหนด LayoutManager ก่อนเสมอ**
        binding.recyclerViewBooks.layoutManager = LinearLayoutManager(this)

        bookAdapter = BookAdapter(
            onItemClick = { book ->
                // กดเข้าไปหน้าที่ 2 (DetailActivity)
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("BOOK_ID", book.id)
                intent.putExtra("BOOK_TITLE", book.title)
                startActivity(intent)
            },
            onEditClick = { book ->
                val dialog = EditBookDialog(this, book.title) { updatedTitle ->
                    mainViewModel.updateBook(book, updatedTitle)
                }
                dialog.show()
            },
            onDeleteClick = { book ->
                mainViewModel.deleteBook(book)
            }
        )

        binding.recyclerViewBooks.adapter = bookAdapter
    }

    private fun observeViewModel() {
        mainViewModel.bookList.observe(this) { books ->
            bookAdapter.submitList(books)
        }
    }
}
