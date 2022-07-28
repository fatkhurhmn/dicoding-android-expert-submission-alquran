package com.muffarproject.alquran.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.muffarproject.alquran.R
import com.muffarproject.alquran.databinding.ActivitySearchBinding
import com.muffarproject.alquran.detail.DetailSurahActivity
import com.muffarproject.core.data.Resource
import com.muffarproject.core.domain.model.Surah
import com.muffarproject.core.ui.SurahAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private val surahAdapter: SurahAdapter by lazy { SurahAdapter(::detailSurah) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupData()
        setupSearchView()
    }

    private fun setupData() {
        searchViewModel.surahs.observe(this) { surah ->
            when (surah) {
                is Resource.Loading -> {
                    with(binding) {
                        loadingSearch.visibility = View.VISIBLE
                        rvSearchSurah.visibility = View.GONE
                        binding.tvErrorSearch.visibility = View.GONE
                    }
                }
                is Resource.Success -> {
                    with(binding) {
                        loadingSearch.visibility = View.GONE
                        binding.tvErrorSearch.visibility = View.GONE
                        rvSearchSurah.visibility = View.VISIBLE
                    }

                    if (surah.data?.isNotEmpty() == true) {
                        surahAdapter.submitList(surah.data)
                        setupRecyclerView()
                    } else {
                        with(binding) {
                            tvErrorSearch.visibility = View.VISIBLE
                            tvErrorSearch.text = getString(R.string.empty_search)
                            rvSearchSurah.visibility = View.GONE
                        }
                    }
                }
                is Resource.Error -> {
                    with(binding) {
                        loadingSearch.visibility = View.GONE
                        tvErrorSearch.visibility = View.VISIBLE
                        rvSearchSurah.visibility = View.GONE
                    }
                    Toast.makeText(this, "Error:${surah.message}", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun setupRecyclerView() {
        with(binding.rvSearchSurah) {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            setHasFixedSize(true)
            adapter = surahAdapter
        }
    }

    private fun setupSearchView() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView =
            binding.searchToolbar.menu.findItem(R.id.search_surah).actionView as SearchView

        with(searchView) {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = context.getString(R.string.keyword)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    searchViewModel.query.value = newText
                    binding.tvInstruction.visibility = View.GONE
                    return true
                }
            })
        }
    }

    private fun detailSurah(surah: Surah) {
        val detailIntent = Intent(this, DetailSurahActivity::class.java)
        detailIntent.putExtra(EXTRA_SURAH, surah)
        startActivity(detailIntent)
    }

    companion object {
        const val EXTRA_SURAH = "extra surah"
    }
}