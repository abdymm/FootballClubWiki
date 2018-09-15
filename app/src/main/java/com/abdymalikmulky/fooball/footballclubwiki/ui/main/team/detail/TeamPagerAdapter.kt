package com.abdymalikmulky.fooball.footballclubwiki.ui.main.team.detail

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.team.detail.squad.TeamSquadFragment

class TeamPagerAdapter(team: Team, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    var team: Team

    init {
        this.team = team
    }

    override fun getItem(position: Int): Fragment? = when (position) {
        0 -> TeamOverviewFragment.newInstance(team)
        1 -> TeamSquadFragment.newInstance(team)
        2 -> TeamStadiumFragment.newInstance(team)
        else -> null
    }

    override fun getPageTitle(position: Int): CharSequence = when (position) {
        0 -> "Overview"
        1 -> "Squad"
        2 -> "Stadium"
        else -> ""
    }

    override fun getCount(): Int = 3
}