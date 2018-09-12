package com.abdymalikmulky.fooball.footballclubwiki.ui.main.league

import com.abdymalikmulky.fooball.footballclubwiki.data.league.League
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.polri.sipp.app.ui.BasePresenter
import com.polri.sipp.app.ui.BaseView


interface LeagueContract {

    interface View : BaseView<Presenter> {
        fun showLoading()
        fun hideLoading()

        fun showLeagueList(leagues: List<League>)

        fun showTeamPage(leagueId: String)
    }
    interface Presenter : BasePresenter {
        fun loadLeague()

        fun setFavoriteLeague(leagueId: String)
    }

}