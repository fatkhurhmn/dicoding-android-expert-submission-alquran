package com.muffarproject.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muffarproject.core.databinding.VerseItemBinding
import com.muffarproject.core.domain.model.Verse

class VerseAdapter : ListAdapter<Verse, VerseAdapter.ListViewHolder>(DIFF_CALLBACK) {

    inner class ListViewHolder(private val binding: VerseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(verse: Verse) {
            with(binding) {
                tvVerseItemNumber.text = verse.number
                tvVerseItemArabic.text = verse.arabic
                tvVerseItemLatin.text = HtmlCompat.fromHtml(verse.latin, HtmlCompat.FROM_HTML_MODE_LEGACY)
                tvVerseItemMeaning.text = verse.meaning
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = VerseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val verse = getItem(position)
        holder.bind(verse)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Verse>() {
            override fun areItemsTheSame(oldItem: Verse, newItem: Verse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Verse, newItem: Verse): Boolean {
                return oldItem.number == newItem.number
            }

        }
    }
}