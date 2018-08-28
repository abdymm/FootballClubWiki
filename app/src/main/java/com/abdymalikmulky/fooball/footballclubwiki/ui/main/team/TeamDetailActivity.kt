package com.abdymalikmulky.fooball.footballclubwiki.ui.main.team

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import org.jetbrains.anko.*

class TeamDetailActivity : AppCompatActivity() {

    internal lateinit var team: Team

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        team = intent.extras.get(getString(R.string.EXTRA_TEAM)) as Team

        ClubDetailActivityUI(team).setContentView(this)
    }
}

