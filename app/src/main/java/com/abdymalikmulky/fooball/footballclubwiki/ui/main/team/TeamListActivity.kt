package com.abdymalikmulky.fooball.footballclubwiki.ui.main.team

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.league.LeagueListFragment
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.match.FixtureFragment
import com.abdymalikmulky.fooball.footballclubwiki.util.openFragment



class TeamListActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_league)

        val leagueFragment = LeagueListFragment.newInstance()
        openFragment(this, leagueFragment, R.id.content_team_league)

    }

}
