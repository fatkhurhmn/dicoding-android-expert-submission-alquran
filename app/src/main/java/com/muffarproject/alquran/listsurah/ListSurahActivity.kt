package com.muffarproject.alquran.listsurah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.muffarproject.alquran.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListSurahActivity : AppCompatActivity() {

    private val listSurahViewModel: ListSurahViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_surah)

        listSurahViewModel.surah.observe(this){
            Log.d(TAG, "onCreate: ${it.data}")
        }
    }

    companion object {
        const val TAG = "ListSurahActivity"
    }
}