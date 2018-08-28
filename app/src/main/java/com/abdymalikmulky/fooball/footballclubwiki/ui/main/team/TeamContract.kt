package com.abdymalikmulky.fooball.footballclubwiki.ui.main.team

import com.abdymalikmulky.fooball.footballclubwiki.data.league.League
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.polri.sipp.app.ui.BasePresenter
import com.polri.sipp.app.ui.BaseView


interface TeamContract {

    interface View : BaseView<Presenter> {
        fun showLoading()
        fun hideLoading()

        fun showLeagueList(leagues: List<League>)
        fun showTeamList(teams: List<Team>)

    }
    interface Presenter : BasePresenter {
        fun loadLeague()
        fun loadTeam(leagueId: String)
    }

}