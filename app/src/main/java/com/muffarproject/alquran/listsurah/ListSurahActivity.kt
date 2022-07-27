package com.muffarproject.alquran.listsurah

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.muffarproject.alquran.databinding.ActivityListSurahBinding
import com.muffarproject.core.data.Resource
import com.muffarproject.core.ui.SurahAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListSurahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListSurahBinding
    private val listSurahViewModel: ListSurahViewModel by viewModels()
    private val surahAdapter: SurahAdapter by lazy { SurahAdapter(::detailSurah) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListSurahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupData()
        setupRecyclerView()
        binding.surahListToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupData() {
        listSurahViewModel.surah.observe(this) { surah ->
            when (surah) {
                is Resource.Loading -> {
                    binding.loadingListSurah.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.loadingListSurah.visibility = View.GONE
                    surahAdapter.submitList(surah.data)
                }
                is Resource.Error -> {
                    binding.loadingListSurah.visibility = View.GONE
                    binding.tvErrorListSurah.visibility = View.VISIBLE
                    Toast.makeText(this, "Error:${surah.message}", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun setupRecyclerView() {
        with(binding.rvSurah) {
            layoutManager = LinearLayoutManager(this@ListSurahActivity)
            setHasFixedSize(true)
            adapter = surahAdapter
        }
    }

    private fun detailSurah(surahNumber: String) {
        Toast.makeText(this, surahNumber, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TAG = "ListSurahActivity"
    }
}