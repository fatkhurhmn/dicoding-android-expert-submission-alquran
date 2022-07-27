package com.muffarproject.alquran.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.muffarproject.alquran.databinding.ActivityDetailSurahBinding
import com.muffarproject.alquran.listsurah.ListSurahActivity
import com.muffarproject.core.data.Resource
import com.muffarproject.core.domain.model.Surah
import com.muffarproject.core.domain.model.Verse
import com.muffarproject.core.ui.VerseAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailSurahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSurahBinding
    private val detailSurahViewModel: DetailSurahViewModel by viewModels()
    private val verseAdapter: VerseAdapter by lazy { VerseAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSurahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupData()
        binding.verseListToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupData() {
        val surah = intent.getParcelableExtra<Surah>(ListSurahActivity.EXTRA_SURAH)
        if (surah != null) {
            binding.verseListToolbar.title = surah.name
            detailSurahViewModel.setSurahNumber(surah.surahNumber)
            detailSurahViewModel.verse.observe(this) { verse ->
                when (verse) {
                    is Resource.Loading -> {
                        binding.loadingListVerse.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.loadingListVerse.visibility = View.GONE
                        verseAdapter.submitList(verse.data)
                        setupRecyclerView()
                    }
                    is Resource.Error -> {
                        binding.loadingListVerse.visibility = View.GONE
                        binding.tvErrorListVerse.visibility = View.VISIBLE
                        binding.rvVerse.visibility = View.GONE
                        Toast.makeText(this, "Error:${verse.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvVerse) {
            layoutManager = LinearLayoutManager(this@DetailSurahActivity)
            setHasFixedSize(true)
            adapter = verseAdapter
        }
    }

    companion object {
        const val TAG = "DetailSurahActivity"
    }
}