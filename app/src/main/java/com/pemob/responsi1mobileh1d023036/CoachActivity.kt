package com.pemob.responsi1mobileh1d023036

import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pemob.responsi1mobileh1d023036.data.model.Coach

class CoachActivity : AppCompatActivity() {
    private lateinit var tvCoachName: TextView
    private lateinit var tvCoachDob: TextView
    private lateinit var tvCoachNationality: TextView
    private lateinit var btnBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coach)

        tvCoachName = findViewById(R.id.tv_coach_name)
        tvCoachDob = findViewById(R.id.tv_coach_dob)
        tvCoachNationality = findViewById(R.id.tv_coach_nationality)
        btnBack = findViewById(R.id.btn_back_coach)

        btnBack.setOnClickListener {
            finish()
        }

        val coachData = getCoachDataFromIntent()

        if (coachData != null) {
            populateCoachData(coachData)
        } else {
            Toast.makeText(this, "Gagal memuat data pelatih", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCoachDataFromIntent(): Coach? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("EXTRA_COACH_DATA", Coach::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("EXTRA_COACH_DATA") as? Coach
        }
    }
    private fun populateCoachData(coach: Coach) {
        tvCoachName.text = coach.name ?: "Nama Tidak Tersedia"
        tvCoachDob.text = coach.dateOfBirth ?: "Tanggal Lahir Tidak Tersedia"
        tvCoachNationality.text = coach.nationality ?: "Kebangsaan Tidak Tersedia"
    }
}