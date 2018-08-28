package com.abdymalikmulky.fooball.footballclubwiki.ui.main.match

import com.abdymalikmulky.fooball.footballclubwiki.data.event.Event
import com.abdymalikmulky.fooball.footballclubwiki.data.league.League
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.polri.sipp.app.ui.BasePresenter
import com.polri.sipp.app.ui.BaseView


interface FixtureContract {

    interface View : BaseView<Presenter> {
        fun showLoading()
        fun hideLoading()

        fun showFixtureList(events: List<Event>)

    }
    interface Presenter : BasePresenter {
        fun loadEvent(isPast: Boolean, leagueId: String)
    }

}