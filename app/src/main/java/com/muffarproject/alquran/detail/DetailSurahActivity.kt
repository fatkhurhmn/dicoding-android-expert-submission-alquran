package com.muffarproject.alquran.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.muffarproject.alquran.databinding.ActivityDetailSurahBinding
import com.muffarproject.alquran.listsurah.ListSurahActivity
import com.muffarproject.core.data.Resource
import com.muffarproject.core.domain.model.Surah
import com.muffarproject.core.ui.VerseAdapter
import com.muffarproject.core.R
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

        val surah = intent.getParcelableExtra<Surah>(ListSurahActivity.EXTRA_SURAH)
        binding.verseListToolbar.title = surah?.name


        setupListVerse(surah)
        setupDetailSurah(surah)
        binding.verseListToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupDetailSurah(surah: Surah?) {
        binding.apply {
            if (surah != null) {
                tvDetailSurahNumber.text = surah.surahNumber
                tvDetailSurahName.text = surah.name
                tvDetailSurahMeaning.text = surah.meaning
                tvDetailType.text = surah.type.replaceFirstChar { it.uppercase() }
                tvDetailNumberOfVerse.text = getString(
                    R.string.dummy_surah_verse,
                    surah.numberOfVerse.toString()
                )
            }
        }
    }

    private fun setupListVerse(surah: Surah?) {
        if (surah != null) {
            detailSurahViewModel.getVerse(surah.surahNumber).observe(this) { verse ->
                when (verse) {
                    is Resource.Loading -> {
                        with(binding) {
                            loadingListVerse.visibility = View.VISIBLE
                            rvVerse.visibility = View.GONE
                        }
                    }
                    is Resource.Success -> {
                        with(binding) {
                            loadingListVerse.visibility = View.GONE
                            rvVerse.visibility = View.VISIBLE
                        }
                        verseAdapter.submitList(verse.data)
                        setupRecyclerView()
                    }
                    is Resource.Error -> {
                        with(binding) {
                            loadingListVerse.visibility = View.GONE
                            tvErrorListVerse.visibility = View.VISIBLE
                            rvVerse.visibility = View.GONE
                        }
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