package com.abdymalikmulky.fooball.footballclubwiki.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballDataSource
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballRepo
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.MainActivity
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class SplashActivity : AppCompatActivity() {

    internal lateinit var footballRepo: FootballRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar!!.hide()

        footballRepo = FootballRepo(this)

        footballRepo.loadTeamLeague(getString(R.string.league_id), object : FootballDataSource.LoadTeamsCallback{
            override fun onLoaded(teams: List<Team>) {
                startSplash()
            }

            override fun onFailed(errorMsg: String) {
                toast(errorMsg)
                finishAffinity()
            }

        })
    }

    private fun startSplash() {
        launch {
            delay(animationWaitingTime)
            startActivity(intentFor<MainActivity>())
            finishAffinity()
        }
    }

    companion object {
        var animationWaitingTime: Long = 1000
    }

}
