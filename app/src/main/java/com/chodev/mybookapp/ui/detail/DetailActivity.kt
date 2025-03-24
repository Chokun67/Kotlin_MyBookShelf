package com.chodev.mybookapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.chodev.mybookapp.R
import com.chodev.mybookapp.data.model.Volume
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

        // 1) ตั้งค่า Toolbar เป็น ActionBar หลักของ Activity
        setSupportActionBar(binding.myToolbar)

        // 2) เปิดใช้ปุ่ม Up (ปุ่มย้อนกลับ)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // อ่านข้อมูลจาก Intent
        bookId = intent.getIntExtra("BOOK_ID", -1)
        val bookTitle = intent.getStringExtra("BOOK_TITLE") ?: ""

        // 3) ตั้งชื่อเรื่องบน Toolbar
        supportActionBar?.title = bookTitle
        // (ถ้าอยากให้ตรงกลางจริง ๆ ให้ custom layout ใน Toolbar เพิ่ม หรือใช้ style แบบ Center Title)

        // เซ็ตค่า BookId ให้ ViewModel เพื่อโหลดรายการ Volume
        detailViewModel.setBookId(bookId)

        setupRecyclerView()
        observeViewModel()

        // เมื่อกดปุ่ม "เพิ่มเล่ม"
        binding.btnAddVolume.setOnClickListener {
            val volumeName = binding.etVolumeName.text.toString()
            if (volumeName.isNotEmpty()) {
                detailViewModel.addVolume(bookId, volumeName)
                binding.etVolumeName.text.clear()
            }
        }
    }

    // 4) เมื่อกดปุ่มย้อนกลับบน Toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // กดปุ่มลูกศรย้อนกลับ -> ปิด Activity
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewVolumes.layoutManager = LinearLayoutManager(this)

        volumeAdapter = VolumeAdapter { volume ->
            // เมื่อกดลบ Volume
            detailViewModel.deleteVolume(volume)
        }
        binding.recyclerViewVolumes.adapter = volumeAdapter
    }

    private fun observeViewModel() {
        detailViewModel.volumeList.observe(this) { volumes ->
            volumeAdapter.submitList(volumes)
        }
    }
}
