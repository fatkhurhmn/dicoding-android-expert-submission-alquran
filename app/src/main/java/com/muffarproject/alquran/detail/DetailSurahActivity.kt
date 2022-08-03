package com.muffarproject.alquran.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.muffarproject.alquran.databinding.ActivityDetailSurahBinding
import com.muffarproject.alquran.R
import com.muffarproject.core.data.Resource
import com.muffarproject.core.domain.model.Surah
import com.muffarproject.core.ui.VerseAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailSurahActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {

    private lateinit var binding: ActivityDetailSurahBinding
    private val detailSurahViewModel: DetailSurahViewModel by viewModels()
    private val verseAdapter: VerseAdapter by lazy { VerseAdapter() }
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSurahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val surah = intent.getParcelableExtra<Surah>(EXTRA_SURAH)
        binding.verseListToolbar.title = surah?.name
        isFavorite = surah?.isFavorite == true

        setupToolbar()
        setupListVerse(surah)
        setupDetailSurah(surah)

    }

    private fun setupToolbar() {
        with(binding) {
            verseListToolbar.setNavigationOnClickListener {
                onBackPressed()
            }
            verseListToolbar.setOnMenuItemClickListener(this@DetailSurahActivity)
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
                    com.muffarproject.core.R.string.dummy_surah_verse,
                    surah.numberOfVerse.toString()
                )
                setStatusFavorite(isFavorite)
            }
        }
    }

    private fun setStatusFavorite(favorite: Boolean) {
        val favoriteItem =
            binding.verseListToolbar.menu.findItem(R.id.btn_add_favorite)
        if (favorite) {
            favoriteItem.icon =
                AppCompatResources.getDrawable(applicationContext, R.drawable.ic_favorite_filled)
        } else {
            favoriteItem.icon =
                AppCompatResources.getDrawable(applicationContext, R.drawable.ic_favorite_border)
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

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        val surah = intent.getParcelableExtra<Surah>(EXTRA_SURAH)
        return when (item?.itemId) {
            R.id.btn_add_favorite -> {
                if (surah != null) {
                    isFavorite = !isFavorite
                    detailSurahViewModel.setFavorite(surah, isFavorite)
                    setStatusFavorite(isFavorite)

                    if (isFavorite) {
                        showMessage(getString(R.string.message_add_favorite))
                    } else {
                        showMessage(getString(R.string.message_remove_favorite))
                    }
                }
                true
            }
            else -> false
        }
    }

    private fun showMessage(message: String) {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }

    companion object {
        const val EXTRA_SURAH = "extra surah"
    }
}