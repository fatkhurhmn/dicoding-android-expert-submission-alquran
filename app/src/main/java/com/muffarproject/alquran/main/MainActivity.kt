package com.muffarproject.alquran.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.muffarproject.alquran.R
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
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.cv_read_quran -> {
                val readQuranIntent = Intent(this, ListSurahActivity::class.java)
                startActivity(readQuranIntent)
            }
        }
    }
}