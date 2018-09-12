package com.abdymalikmulky.fooball.footballclubwiki.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballDataSource
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballRepo
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.MainActivity
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.team.TeamListActivity
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class SplashActivity : AppCompatActivity() {

    internal lateinit var footballRepo: FootballRepo

    internal lateinit var leagueId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar!!.hide()

        footballRepo = FootballRepo(this)

        footballRepo.loadFavoriteLeague(object : FootballDataSource.LoadFavoriteLeagueCallback{
            override fun onLoad(leagueId: String) {
                setLeagueId(leagueId)
            }

            override fun onFailed(errorMsg: String) {
                toast(errorMsg)
                finishAffinity()
            }
        })
    }

    private fun startSplash(leagueId: String) {
        launch {
            delay(animationWaitingTime)
            if(leagueId.equals("")) {
                startActivity(intentFor<TeamListActivity>())
            } else {
                startActivity(intentFor<MainActivity>())
            }

            finishAffinity()
        }
    }

    fun setLeagueId(leagueId: String) {
        this.leagueId = leagueId
        startSplash(leagueId)
    }

    companion object {
        var animationWaitingTime: Long = 1000
    }

}
