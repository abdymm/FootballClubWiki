package com.abdymalikmulky.fooball.footballclubwiki.ui.main.match

import com.abdymalikmulky.fooball.footballclubwiki.data.FootballDataSource
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballRepo
import com.abdymalikmulky.fooball.footballclubwiki.data.event.Event
import com.abdymalikmulky.fooball.footballclubwiki.data.league.League
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team


class FixturePresenter(footballRepo: FootballRepo, fixtureView: FixtureContract.View) : FixtureContract.Presenter {



    internal var fixtureView: FixtureContract.View
    internal var footballRepo: FootballRepo

    init {
        this.fixtureView = fixtureView
        this.footballRepo = footballRepo
    }

    override fun start() {
    }

    override fun stop() {
    }

    override fun loadEvent(isPast: Boolean, leagueId: String) {
        fixtureView.showLoading()
        footballRepo.loadEventLeague(isPast, leagueId, object : FootballDataSource.LoadEventLeagueCallback {
            override fun onLoaded(events: List<Event>) {
                fixtureView.hideLoading()
                fixtureView.showFixtureList(events)
            }

            override fun onFailed(errorMsg: String) {
                fixtureView.hideLoading()
                fixtureView.showError(errorMsg)
            }

        })
    }


}