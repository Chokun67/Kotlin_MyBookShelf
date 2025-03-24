package com.chodev.mybookapp.ui.main

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.chodev.mybookapp.databinding.DialogEditBookBinding

class EditBookDialog(
    context: Context,
    private val initialTitle: String? = null,
    private val onSave: (String) -> Unit
) : Dialog(context) {

    private lateinit var binding: DialogEditBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogEditBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialTitle?.let {
            binding.etBookTitle.setText(it)
        }

        binding.btnSave.setOnClickListener {
            val newTitle = binding.etBookTitle.text.toString()
            if (newTitle.isNotEmpty()) {
                onSave(newTitle)
                dismiss()
            }
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }
}
