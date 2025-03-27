package com.chodev.mybookapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.chodev.mybookapp.R
import com.chodev.mybookapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()

    private lateinit var volumeAdapter: VolumeAdapter
    private var bookId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ใช้ DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.lifecycleOwner = this
        binding.viewModel = detailViewModel

        setSupportActionBar(binding.myToolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        bookId = intent.getIntExtra("BOOK_ID", -1)
        val bookTitle = intent.getStringExtra("BOOK_TITLE") ?: ""

        supportActionBar?.title = bookTitle

        detailViewModel.setBookId(bookId)

        setupRecyclerView()
        observeViewModel()

        binding.btnAddVolume.setOnClickListener {
            val volumeName = binding.etVolumeName.text.toString()
            if (volumeName.isNotEmpty()) {
                detailViewModel.addVolume(bookId, volumeName)
                binding.etVolumeName.text.clear()
            }
        }

        binding.searchViewVolume.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                detailViewModel.filterVolumes(newText.orEmpty())
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setupRecyclerView() {
        binding.recyclerViewVolumes.layoutManager = LinearLayoutManager(this)

        volumeAdapter = VolumeAdapter { volume ->
            detailViewModel.deleteVolume(volume)
        }
        binding.recyclerViewVolumes.adapter = volumeAdapter
    }

    private fun observeViewModel() {
        detailViewModel.filteredVolumeList.observe(this) { volumes ->
            volumeAdapter.submitList(volumes)
        }
    }

}
