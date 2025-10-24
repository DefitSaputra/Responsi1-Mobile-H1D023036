package com.pemob.responsi1mobileh1d023036

import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pemob.responsi1mobileh1d023036.data.model.SquadMember
import com.pemob.responsi1mobileh1d023036.ui.adapter.SquadAdapter
import java.io.Serializable

class SquadActivity : AppCompatActivity() {

    private lateinit var rvSquad: RecyclerView
    private lateinit var squadAdapter: SquadAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_squad)

        // Tombol back
        val btnBack: ImageButton = findViewById(R.id.btn_back_squad)
        btnBack.setOnClickListener {
            finish()
        }

        // Ambil daftar pemain dari Intent
        val playerList = getSquadDataFromIntent()

        if (playerList != null) {
            // Inisialisasi RecyclerView dan Adapter
            rvSquad = findViewById(R.id.rv_squad)
            rvSquad.layoutManager = LinearLayoutManager(this)
            squadAdapter = SquadAdapter(playerList)
            rvSquad.adapter = squadAdapter
        } else {
            Toast.makeText(this, "Gagal memuat data skuad", Toast.LENGTH_SHORT).show()
        }
    }

    // Fungsi aman untuk mengambil List<Serializable>
    private fun <T : Serializable?> getSerializableList(intent: Bundle, key: String, clazz: Class<T>): ArrayList<T>? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializable(key, java.util.ArrayList::class.java) as? ArrayList<T>
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializable(key) as? ArrayList<T>
        }
    }

    private fun getSquadDataFromIntent(): List<SquadMember>? {
        val bundle = intent.extras
        if (bundle != null) {
            return getSerializableList(bundle, "EXTRA_SQUAD_DATA", SquadMember::class.java)
        }
        return null
    }
}