package com.pemob.responsi1mobileh1d023036

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val btnBack: ImageButton = findViewById(R.id.btn_back_history)
        btnBack.setOnClickListener {
            finish()
        }
    }
}