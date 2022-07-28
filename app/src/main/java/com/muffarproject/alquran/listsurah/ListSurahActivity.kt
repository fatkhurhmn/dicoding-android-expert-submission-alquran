package com.muffarproject.alquran.listsurah

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.muffarproject.alquran.databinding.ActivityListSurahBinding
import com.muffarproject.alquran.detail.DetailSurahActivity
import com.muffarproject.core.data.Resource
import com.muffarproject.core.domain.model.Surah
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
        binding.surahListToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupData() {
        listSurahViewModel.surah.observe(this) { surah ->
            when (surah) {
                is Resource.Loading -> {
                    with(binding) {
                        loadingListSurah.visibility = View.VISIBLE
                        rvSurah.visibility = View.GONE
                    }
                }
                is Resource.Success -> {
                    with(binding) {
                        loadingListSurah.visibility = View.GONE
                        rvSurah.visibility = View.VISIBLE
                    }
                    surahAdapter.submitList(surah.data)
                    setupRecyclerView()
                }
                is Resource.Error -> {
                    with(binding) {
                        loadingListSurah.visibility = View.GONE
                        tvErrorListSurah.visibility = View.VISIBLE
                        rvSurah.visibility = View.GONE
                    }
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

    private fun detailSurah(surah: Surah) {
        val detailIntent = Intent(this, DetailSurahActivity::class.java)
        detailIntent.putExtra(EXTRA_SURAH, surah)
        startActivity(detailIntent)
    }

    companion object {
        const val TAG = "ListSurahActivity"
        const val EXTRA_SURAH = "extra surah"
    }
}