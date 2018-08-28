package com.abdymalikmulky.fooball.footballclubwiki.ui.main.match.detail

import com.abdymalikmulky.fooball.footballclubwiki.data.FootballDataSource
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballRepo
import com.abdymalikmulky.fooball.footballclubwiki.data.event.Event
import com.abdymalikmulky.fooball.footballclubwiki.data.league.League
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team


class FixtureDetailPresenter(footballRepo: FootballRepo, fixtureDetailView: FixtureDetailContract.View) : FixtureDetailContract.Presenter {


    internal var fixtureDetailView: FixtureDetailContract.View
    internal var footballRepo: FootballRepo

    init {
        this.fixtureDetailView = fixtureDetailView
        this.footballRepo = footballRepo
    }

    override fun start() {
    }

    override fun stop() {
    }


    override fun loadTeam(teamId: String) {
        fixtureDetailView.showLoading()
        footballRepo.loadTeam(teamId, object : FootballDataSource.LoadTeamCallback {
            override fun onLoaded(team: Team) {
                fixtureDetailView.hideLoading()
                fixtureDetailView.showTeam(team)
            }

            override fun onFailed(errorMsg: String) {
                fixtureDetailView.hideLoading()
                fixtureDetailView.showError(errorMsg)
            }

        })
    }


}