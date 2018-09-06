package com.abdymalikmulky.fooball.footballclubwiki.ui.main.match

import com.abdymalikmulky.fooball.footballclubwiki.data.FootballDataSource
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballRepo
import com.abdymalikmulky.fooball.footballclubwiki.data.event.Event
import com.abdymalikmulky.fooball.footballclubwiki.data.league.League
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.team.TeamContract
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.team.TeamPresenter
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import junit.framework.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.*
import java.util.ArrayList

class FixturePresenterTest {
    private lateinit var EVENTS: ArrayList<Event>
    private val LEAGUE_ID = "4328"

    @Mock
    private lateinit var footballRepo: FootballRepo

    @Mock
    private lateinit var fixtureView: FixtureContract.View

    private lateinit var fixturePresenter: FixturePresenter

    @Before
    fun setupTasksPresenter() {
        MockitoAnnotations.initMocks(this)

        fixturePresenter = FixturePresenter(footballRepo, fixtureView)


        EVENTS = ArrayList()
        EVENTS.add(Event())
        EVENTS.add(Event())
        EVENTS.add(Event())
        EVENTS.add(Event())

    }

    @Test
    fun createPresenter_setsThePresenterToView() {
        fixturePresenter = FixturePresenter(footballRepo, fixtureView)

        Mockito.verify<FixtureContract.View>(fixtureView).setPresenter(fixturePresenter)
    }
    @Test
    fun loadAllEventByLeague() {
        fixturePresenter.loadEvent(true, LEAGUE_ID)

        argumentCaptor<FootballDataSource.LoadEventLeagueCallback>().apply {
            Mockito.verify(fixtureView).showLoading()
            Mockito.verify(footballRepo).loadEventLeague(eq(true), any(), capture())
            this.firstValue.onLoaded(EVENTS)
            Mockito.verify(fixtureView).hideLoading()

            argumentCaptor<List<Event>>().apply {
                Mockito.verify(fixtureView).showFixtureList(this.capture())
                Assert.assertEquals(4, firstValue.size)
            }

        }
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }
    private fun <T> uninitialized(): T = null as T

}