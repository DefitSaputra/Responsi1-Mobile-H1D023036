package com.pemob.responsi1mobileh1d023036

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ScrollView // <-- Ubah dari RelativeLayout ke ScrollView jika perlu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
// import CardView tidak lagi dipakai di sini, tapi di dalam <include>
import com.bumptech.glide.Glide
import com.pemob.responsi1mobileh1d023036.ui.viewmodel.TeamViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: TeamViewModel by viewModels()
    private lateinit var progressBar: ProgressBar
    private lateinit var contentView: ScrollView
    private lateinit var ivClubCrest: ImageView
    private lateinit var tvClubName: TextView
    private lateinit var menuHistory: View
    private lateinit var menuCoach: View
    private lateinit var menuSquad: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progress_bar)
        contentView = findViewById(R.id.content_view)
        ivClubCrest = findViewById(R.id.iv_club_crest)
        tvClubName = findViewById(R.id.tv_club_name)

        menuHistory = findViewById(R.id.menu_history)
        menuCoach = findViewById(R.id.menu_coach)
        menuSquad = findViewById(R.id.menu_squad)

        viewModel.fetchTeamData()

        observeViewModel()

        setupMenuContent()
        setupNavigation()
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
                contentView.visibility = View.INVISIBLE
            } else {
                progressBar.visibility = View.GONE
                contentView.visibility = View.VISIBLE
            }
        }

        viewModel.teamData.observe(this) { team ->
            tvClubName.text = team.name
            Glide.with(this)
                .load(team.crest)
                .placeholder(R.drawable.ogc_nice_logo)
                .into(ivClubCrest)
        }

        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            progressBar.visibility = View.GONE
        }
    }

    private fun setupMenuContent() {
        // Menu 1: History
        val iconHistory: ImageView = menuHistory.findViewById(R.id.iv_menu_icon)
        val titleHistory: TextView = menuHistory.findViewById(R.id.tv_menu_title)
        iconHistory.setImageResource(R.drawable.ic_soccer)
        titleHistory.text = "Club History"

        // Menu 2: Coach
        val iconCoach: ImageView = menuCoach.findViewById(R.id.iv_menu_icon)
        val titleCoach: TextView = menuCoach.findViewById(R.id.tv_menu_title)
        iconCoach.setImageResource(R.drawable.ic_coach)
        titleCoach.text = "Head Coach"

        // Menu 3: Squad
        val iconSquad: ImageView = menuSquad.findViewById(R.id.iv_menu_icon)
        val titleSquad: TextView = menuSquad.findViewById(R.id.tv_menu_title)
        iconSquad.setImageResource(R.drawable.ic_team)
        titleSquad.text = "Team Squad"
    }

    private fun setupNavigation() {
        menuHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        menuCoach.setOnClickListener {
            val teamData = viewModel.teamData.value
            if (teamData != null) {
                val coach = teamData.coach
                val intent = Intent(this, CoachActivity::class.java)
                intent.putExtra("EXTRA_COACH_DATA", coach)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Data belum siap, coba lagi.", Toast.LENGTH_SHORT).show()
            }
        }

        menuSquad.setOnClickListener {
            val teamData = viewModel.teamData.value
            if (teamData != null) {
                val squadList = teamData.squad
                val intent = Intent(this, SquadActivity::class.java)
                intent.putExtra("EXTRA_SQUAD_DATA", ArrayList(squadList))
                startActivity(intent)
            } else {
                Toast.makeText(this, "Data belum siap, coba lagi.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}