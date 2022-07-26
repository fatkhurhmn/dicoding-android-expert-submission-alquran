package com.muffarproject.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.muffarproject.core.R
import com.muffarproject.core.databinding.SurahItemBinding
import com.muffarproject.core.domain.model.Surah

class SurahAdapter(private val onVerseClick: (String) -> Unit) :
    ListAdapter<Surah, SurahAdapter.ListViewHolder>(DIFF_CALLBACK) {

    inner class ListViewHolder(private val binding: SurahItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(surah: Surah) {
            with(binding) {
                tvSurahItemNumber.text = surah.surahNumber
                tvSurahItemName.text = surah.name
                tvSurahItemType.text = surah.type.replaceFirstChar { it.uppercase() }
                tvSurahItemVerse.text = itemView.context.getString(
                    R.string.dummy_surah_verse,
                    surah.numberOfVerse.toString()
                )
                tvSurahItemAsma.text = surah.asma
                binding.root.setOnClickListener {
                    onVerseClick(surah.surahNumber)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = SurahItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val surah = getItem(position)
        holder.bind(surah)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Surah>() {
            override fun areItemsTheSame(oldItem: Surah, newItem: Surah): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Surah, newItem: Surah): Boolean {
                return oldItem.surahNumber == newItem.surahNumber
            }

        }
    }
}