package com.abdymalikmulky.fooball.footballclubwiki.ui.main.team.detail

import com.abdymalikmulky.fooball.footballclubwiki.data.FootballDataSource
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballRepo
import com.abdymalikmulky.fooball.footballclubwiki.data.league.League
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import org.jetbrains.anko.toast


class TeamDetailPresenter(footballRepo: FootballRepo, teamView: TeamDetailContract.View) : TeamDetailContract.Presenter {



    internal var teamView: TeamDetailContract.View
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


    override fun loadTeam(teamId: String) {
        teamView.showLoading()

        footballRepo.loadTeam(teamId, object : FootballDataSource.LoadTeamCallback{
            override fun onLoaded(team: Team) {
                teamView.hideLoading()
                teamView.showTeam(team)
            }

            override fun onFailed(errorMsg: String) {
                teamView.hideLoading()
                teamView.showError(errorMsg)
            }

        })
    }

    override fun isTeamHasFavorite(teamId: String) {
        footballRepo.isTeamHasFavorited(teamId, object : FootballDataSource.IsTeamFavCallback {
            override fun onFavorited(isFav: Boolean) {
                teamView.isTeamFavorit(isFav)
            }

            override fun onFailed(errorMsg: String) {
                teamView.showError(errorMsg)
            }

        })
    }


}