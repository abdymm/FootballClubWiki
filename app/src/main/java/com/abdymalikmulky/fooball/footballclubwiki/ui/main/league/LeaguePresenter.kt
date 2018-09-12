package com.abdymalikmulky.fooball.footballclubwiki.ui.main.league

import com.abdymalikmulky.fooball.footballclubwiki.data.FootballDataSource
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballRepo
import com.abdymalikmulky.fooball.footballclubwiki.data.league.League


class LeaguePresenter(footballRepo: FootballRepo, teamView: LeagueContract.View) : LeagueContract.Presenter {


    internal var leagueView: LeagueContract.View
    internal var footballRepo: FootballRepo

    init {
        this.leagueView = teamView
        this.footballRepo = footballRepo

        this.leagueView.setPresenter(this)
    }

    override fun start() {
    }

    override fun stop() {
    }

    override fun loadLeague() {
        leagueView.showLoading()
        footballRepo.loadLeague(object : FootballDataSource.LoadLeagueCallback {
            override fun onLoaded(leagues: List<League>) {
                leagueView.hideLoading()
                val filteredLeagues = leagues.filter {
                    it.sportType.equals("Soccer")
                }
                leagueView.showLeagueList(filteredLeagues)
            }

            override fun onFailed(errorMsg: String) {
                leagueView.hideLoading()
                leagueView.showError(errorMsg)
            }

        })
    }



    override fun setFavoriteLeague(leagueId: String) {
        footballRepo.setFavoriteLeague(leagueId, object : FootballDataSource.SetFavoriteLeagueCallback{
            override fun onSet(leagueId: String) {
                leagueView.showTeamPage(leagueId)
            }

            override fun onFailed(errorMsg: String) {
                leagueView.showError(errorMsg)
            }

        })
    }

}