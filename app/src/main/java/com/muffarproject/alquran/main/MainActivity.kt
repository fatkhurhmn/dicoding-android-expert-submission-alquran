package com.muffarproject.alquran.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.muffarproject.alquran.R
import com.muffarproject.alquran.search.SearchActivity
import com.muffarproject.alquran.databinding.ActivityMainBinding
import com.muffarproject.alquran.listsurah.ListSurahActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            cvReadQuran.setOnClickListener(this@MainActivity)
            cvSearchSurah.setOnClickListener(this@MainActivity)
            cvFavorite.setOnClickListener(this@MainActivity)
            cvSettings.setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.cv_read_quran -> {
                val readQuranIntent = Intent(this, ListSurahActivity::class.java)
                startActivity(readQuranIntent)
            }

            R.id.cv_search_surah -> {
                val searchIntent = Intent(this, SearchActivity::class.java)
                startActivity(searchIntent)
            }

            R.id.cv_favorite -> {
                val uri = Uri.parse("alquran://favorite")
                val favoriteIntent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(favoriteIntent)
            }

            R.id.cv_settings -> {
                Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show()
            }
        }
    }
}