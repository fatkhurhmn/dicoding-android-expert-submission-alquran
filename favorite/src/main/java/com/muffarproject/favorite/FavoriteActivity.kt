package com.muffarproject.favorite

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.muffarproject.alquran.detail.DetailSurahActivity
import com.muffarproject.alquran.di.FavoriteModuleDependencies
import com.muffarproject.core.domain.model.Surah
import com.muffarproject.core.ui.SurahAdapter
import com.muffarproject.favorite.databinding.ActivityFavoriteBinding
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val surahAdapter: SurahAdapter by lazy { SurahAdapter(::detailSurah) }

    @Inject
    lateinit var viewModelFactory: FavoriteViewModelFactory
    private val favoriteViewModel: FavoriteViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.favoriteToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        setupFavoriteSurah()
    }

    private fun setupFavoriteSurah() {
        favoriteViewModel.favorites.observe(this) { surah ->
            if (surah.isNotEmpty()) {
                binding.rvFavoriteSurah.visibility = View.VISIBLE
                binding.tvMessageFavorite.visibility = View.GONE
                surahAdapter.submitList(surah)
                setupRecyclerView()
            } else {
                binding.rvFavoriteSurah.visibility = View.GONE
                binding.tvMessageFavorite.visibility = View.VISIBLE
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvFavoriteSurah) {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(true)
            adapter = surahAdapter
        }
    }

    private fun detailSurah(surah: Surah) {
        val uri = Uri.parse("alquran://detail")
        val detailIntent = Intent(Intent.ACTION_VIEW, uri)
        detailIntent.putExtra(DetailSurahActivity.EXTRA_SURAH, surah)
        startActivity(detailIntent)
    }
}
