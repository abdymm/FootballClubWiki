package com.abdymalikmulky.fooball.footballclubwiki.ui.main.match

import android.util.Log
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

    override fun loadFavoriteEvent(leagueId: String) {
        var eventCollection = ArrayList<Event>()

        fixtureView.showLoading()

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
                                fixtureView.hideLoading()
                                fixtureView.showFixtureList(eventFiltered)


                            }

                            override fun onFailed(errorMsg: String) {
                                fixtureView.hideLoading()
                                fixtureView.showError(errorMsg)
                            }

                        })

                    }

                    override fun onFailed(errorMsg: String) {
                        fixtureView.hideLoading()
                        fixtureView.showError(errorMsg)
                    }

                })
            }

            override fun onFailed(errorMsg: String) {
                fixtureView.hideLoading()
                fixtureView.showError(errorMsg)
            }

        })


    }

}