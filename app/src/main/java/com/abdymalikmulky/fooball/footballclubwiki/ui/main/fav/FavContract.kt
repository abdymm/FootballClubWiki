package com.abdymalikmulky.fooball.footballclubwiki.ui.main.fav

import com.abdymalikmulky.fooball.footballclubwiki.data.event.Event
import com.abdymalikmulky.fooball.footballclubwiki.data.league.League
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.polri.sipp.app.ui.BasePresenter
import com.polri.sipp.app.ui.BaseView


interface FavContract {

    interface View : BaseView<Presenter> {
        fun showLoading()
        fun hideLoading()

        fun showFixtureList(events: List<Event>)
        fun showTeamList(teams: List<Team>)

    }
    interface Presenter : BasePresenter {
        fun loadFavoriteEvents(leagueId: String)
        fun loadFavoriteTeams()
    }

}