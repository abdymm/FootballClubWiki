package com.abdymalikmulky.fooball.footballclubwiki.ui.main.team

import com.abdymalikmulky.fooball.footballclubwiki.data.FootballDataSource
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballRepo
import com.abdymalikmulky.fooball.footballclubwiki.data.league.League
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team


class TeamPresenter(footballRepo: FootballRepo, teamView: TeamContract.View) : TeamContract.Presenter {


    internal var teamView: TeamContract.View
    internal var footballRepo: FootballRepo

    init {
        this.teamView = teamView
        this.footballRepo = footballRepo
    }

    override fun start() {
    }

    override fun stop() {
    }

    override fun loadLeague() {
        teamView.showLoading()
        footballRepo.loadLeague(object : FootballDataSource.LoadLeagueCallback {
            override fun onLoaded(leagues: List<League>) {
                teamView.hideLoading()
                teamView.showLeagueList(leagues)
            }

            override fun onFailed(errorMsg: String) {
                teamView.hideLoading()
                teamView.showError(errorMsg)
            }

        })
    }

    override fun loadTeam(leagueId: String) {
        teamView.showLoading()
        footballRepo.loadTeamLeague(leagueId, object : FootballDataSource.LoadTeamLeagueCallback {
            override fun onLoaded(teams: List<Team>) {
                teamView.hideLoading()
                teamView.showTeamList(teams)
            }

            override fun onFailed(errorMsg: String) {
                teamView.showError(errorMsg)
            }

        })
    }


}