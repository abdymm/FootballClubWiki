package com.abdymalikmulky.fooball.footballclubwiki.ui.main.team

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import org.jetbrains.anko.*
import android.view.MenuItem
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballDataSource
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballLocal
import org.jetbrains.anko.design.snackbar


class TeamDetailActivity : AppCompatActivity() {

    internal lateinit var menu: Menu

    internal lateinit var team: Team

    internal lateinit var footballLocal: FootballLocal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        footballLocal = FootballLocal(this)

        team = intent.extras.get(getString(R.string.EXTRA_TEAM)) as Team

        ClubDetailActivityUI(team).setContentView(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        this.menu = menu
        inflater.inflate(R.menu.team, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.menu_fav -> {
                val unfaveMenu = this.menu.findItem(R.id.menu_unfav)
                unfaveMenu.isVisible = true
                item.isVisible = false


                setFavoriteTeam(true, team.teamId)
                return true
            }
            R.id.menu_unfav -> {
                val faveMenu = this.menu.findItem(R.id.menu_fav)
                faveMenu.isVisible = true
                item.isVisible = false

                setFavoriteTeam(false, team.teamId)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun setFavoriteTeam(favorite: Boolean, teamId: String) {
        footballLocal.setFavoriteTeam(favorite, team.teamId, object: FootballDataSource.SetFavoriteTeamCallback{
            override fun onSavedTeam(teamId: String) {
            }

            override fun onFailed(errorMsg: String) {
                toast(errorMsg)
            }

        })
    }
}

