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

        fun showEventFav(isFavorite: Boolean)
        fun showTeam(team: Team)
        fun showFixture(event: Event)
        fun showFavoriteMatchResponse(isFavorite: Boolean, eventId: String)
    }
    interface Presenter : BasePresenter {
        fun loadTeam(teamId: String)

        fun checkIsEventFavorited(eventId: String)

        fun favoriteMatch(isFavorite: Boolean, eventId: String)
    }

}