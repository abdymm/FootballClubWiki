package com.abdymalikmulky.fooball.footballclubwiki.ui.main.team

import com.abdymalikmulky.fooball.footballclubwiki.data.FootballDataSource
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballRepo
import com.abdymalikmulky.fooball.footballclubwiki.data.league.League
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import org.jetbrains.anko.toast


class TeamPresenter(footballRepo: FootballRepo, teamView: TeamContract.View) : TeamContract.Presenter {



    internal var teamView: TeamContract.View
    internal var footballRepo: FootballRepo

    init {
        this.teamView = teamView
        this.footballRepo = footballRepo

        this.teamView.setPresenter(this)
    }

    override fun start() {
    }

    override fun stop() {
    }


    override fun loadTeam(leagueId: String) {
        teamView.showLoading()
        footballRepo.loadTeamLeague(leagueId, object : FootballDataSource.LoadTeamsCallback {
            override fun onLoaded(teams: List<Team>) {
                teamView.hideLoading()
                teamView.showTeamList(teams)
            }

            override fun onFailed(errorMsg: String) {
                teamView.showError(errorMsg)
            }

        })
    }

    override fun setFavoriteTeam(teamId: String) {
        footballRepo.setFavoriteTeam(true, teamId, object: FootballDataSource.SetFavoriteTeamCallback{
            override fun onSavedTeam(teamId: String) {
                teamView.teamFavorited(teamId)
            }

            override fun onFailed(errorMsg: String) {
                teamView.showError(errorMsg)
            }

        })
    }

}