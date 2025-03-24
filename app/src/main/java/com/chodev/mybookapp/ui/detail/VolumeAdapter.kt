package com.chodev.mybookapp.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chodev.mybookapp.data.model.Volume
import com.chodev.mybookapp.databinding.ItemVolumeBinding

class VolumeAdapter(
    private val onDeleteClick: (Volume) -> Unit
) : RecyclerView.Adapter<VolumeAdapter.VolumeViewHolder>() {

    private var items: List<Volume> = emptyList()

    fun submitList(data: List<Volume>) {
        items = data
        notifyDataSetChanged()
    }

    inner class VolumeViewHolder(val binding: ItemVolumeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Volume) {
            binding.volume = item
            binding.btnDeleteVolume.setOnClickListener {
                onDeleteClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolumeViewHolder {
        val binding = ItemVolumeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VolumeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VolumeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
