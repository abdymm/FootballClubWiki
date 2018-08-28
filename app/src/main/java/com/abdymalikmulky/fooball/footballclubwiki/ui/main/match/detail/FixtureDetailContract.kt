package com.abdymalikmulky.fooball.footballclubwiki.ui.main.match.detail

import com.abdymalikmulky.fooball.footballclubwiki.data.event.Event
import com.abdymalikmulky.fooball.footballclubwiki.data.league.League
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.polri.sipp.app.ui.BasePresenter
import com.polri.sipp.app.ui.BaseView


interface FixtureDetailContract {

    interface View : BaseView<Presenter> {
        fun showLoading()
        fun hideLoading()

        fun showTeam(team: Team)
        fun showFixture(event: Event)
    }
    interface Presenter : BasePresenter {
        fun loadTeam(teamId: String)
    }

}