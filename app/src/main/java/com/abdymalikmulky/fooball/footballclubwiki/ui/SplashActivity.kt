package com.abdymalikmulky.fooball.footballclubwiki.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballDataSource
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballRepo
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.MainActivity
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
        val splashTread: Thread
        splashTread = object : Thread() {
            override fun run() {
                try {
                    var waited = 0
                    // Splash screen pause time
                    while (waited < animationWaitingTime) {
                        Thread.sleep(100)
                        waited += 100
                    }

                    startActivity(intentFor<MainActivity>())

                } catch (e: InterruptedException) {
                    // do nothing
                } finally {
                    finish()
                }

            }
        }
        splashTread.start()

    }

    companion object {
        var animationWaitingTime: Long = 1000
    }

}
