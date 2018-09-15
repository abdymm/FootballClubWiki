package com.abdymalikmulky.fooball.footballclubwiki.ui.main.fav

import android.util.Log
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballDataSource
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballRepo
import com.abdymalikmulky.fooball.footballclubwiki.data.event.Event
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team


class FavPresenter(footballRepo: FootballRepo, fixtureView: FavContract.View) : FavContract.Presenter {

    internal var favView: FavContract.View
    internal var footballRepo: FootballRepo

    init {
        this.favView = fixtureView
        this.footballRepo = footballRepo

        this.favView.setPresenter(this)
    }

    override fun start() {
    }

    override fun stop() {
    }


    override fun loadFavoriteTeams() {
        favView.showLoading()
        footballRepo.loadFavoriteTeam(object : FootballDataSource.LoadFavoriteTeamsCallback{
            override fun onLoaded(teams: List<Team>) {
                favView.hideLoading()
                Log.d("DataTeam", teams.toString())
                favView.showTeamList(teams)
            }

            override fun onFailed(errorMsg: String) {
                favView.showError(errorMsg)
            }

        })
    }


    override fun loadFavoriteEvents(leagueId: String) {
        var eventCollection = ArrayList<Event>()

        favView.showLoading()

        footballRepo.loadFavoriteEvent(object : FootballDataSource.LoadFavEventLeagueCallback{
            override fun onLoaded(eventIds: ArrayList<String>) {

                //Load event past
                footballRepo.loadEventLeague(true, leagueId, object : FootballDataSource.LoadEventLeagueCallback {
                    override fun onLoaded(events: List<Event>) {

                        eventCollection.addAll(events)

                        //Load event next
                        footballRepo.loadEventLeague(false, leagueId, object : FootballDataSource.LoadEventLeagueCallback {
                            override fun onLoaded(events: List<Event>) {

                                eventCollection.addAll(events)

                                val eventFiltered = ArrayList<Event>()

                                for (eventItem in eventCollection) {
                                    if(eventIds.contains(eventItem.idEvent)) {
                                        eventFiltered.add(eventItem)
                                    }
                                }
                                Log.d("EVENTIDS", eventIds.toString())
                                favView.hideLoading()
                                favView.showFixtureList(eventFiltered)


                            }

                            override fun onFailed(errorMsg: String) {
                                favView.hideLoading()
                                favView.showError(errorMsg)
                            }

                        })

                    }

                    override fun onFailed(errorMsg: String) {
                        favView.hideLoading()
                        favView.showError(errorMsg)
                    }

                })
            }

            override fun onFailed(errorMsg: String) {
                favView.hideLoading()
                favView.showError(errorMsg)
            }

        })


    }

}