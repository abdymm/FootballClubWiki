package com.abdymalikmulky.fooball.footballclubwiki.ui.main.team

import com.abdymalikmulky.fooball.footballclubwiki.data.FootballDataSource
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballRepo
import com.abdymalikmulky.fooball.footballclubwiki.data.league.League
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.times
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.verify
import java.util.ArrayList

class TeamPresenterTest {
    private lateinit var LEAGUES: ArrayList<League>
    private lateinit var TEAMS: ArrayList<Team>
    private val LEAGUE_ID = "4328"

    @Mock
    private lateinit var footballRepo: FootballRepo

    @Mock
    private lateinit var teamView: TeamContract.View

    private lateinit var teamPresenter: TeamPresenter

    @Before
    fun setupTasksPresenter() {
        MockitoAnnotations.initMocks(this)

        teamPresenter = TeamPresenter(footballRepo, teamView)


        TEAMS = ArrayList()
        TEAMS.add(Team("1", "PSSI", "PSSI MAJU", "logo1"))
        TEAMS.add(Team("2", "PSSI2", "PSSI MAJU2", "logo2"))
        TEAMS.add(Team("3", "PSSI3", "PSSI MAJU3", "logo3"))

        LEAGUES = ArrayList()
        LEAGUES.add(League("1", "LIGA 1"))
        LEAGUES.add(League("1", "LIGA 2"))
    }

    @Test
    fun createPresenter_setsThePresenterToView() {
        // Get a reference to the class under test
        teamPresenter = TeamPresenter(footballRepo, teamView)

        // Then the presenter is set to the view
        verify<TeamContract.View>(teamView).setPresenter(teamPresenter)
    }

    @Test
    fun loadAllLeagues() {
        teamPresenter.loadLeague()

        argumentCaptor<FootballDataSource.LoadLeagueCallback>().apply {
            verify(teamView).showLoading()
            verify(footballRepo).loadLeague(capture())
            this.firstValue.onLoaded(LEAGUES)
            verify(teamView).hideLoading()

            argumentCaptor<List<League>>().apply {
                verify(teamView).showLeagueList(this.capture())
                assertEquals(2, firstValue.size)
            }

        }

    }

    @Test
    fun loadAllTeamsByLeague() {
        teamPresenter.loadTeam(LEAGUE_ID)

        //Null Safety of Kotlin Issue However, in Mockito, both anyObject() and any() will return Null in it’s verification function when being used.
        //so i use 3rd party library to handle this issue https://github.com/nhaarman/mockito-kotlin
        argumentCaptor<FootballDataSource.LoadTeamsCallback>().apply {

            verify(teamView).showLoading()
            verify(footballRepo).loadTeamLeague(any(), capture())
            this.firstValue.onLoaded(TEAMS)
            verify(teamView).hideLoading()

            argumentCaptor<List<Team>>().apply {
                verify(teamView).showTeamList(this.capture())
                assertEquals(3, firstValue.size)
            }

        }

    }

    @Test
    fun setFavoriteLeague() {
        teamPresenter.setFavoriteLeague(LEAGUE_ID)

        //Null Safety of Kotlin Issue However, in Mockito, both anyObject() and any() will return Null in it’s verification function when being used.
        //so i use 3rd party library to handle this issue https://github.com/nhaarman/mockito-kotlin
        argumentCaptor<FootballDataSource.SetFavoriteLeagueCallback>().apply {

            verify(footballRepo).setFavoriteLeague(any(), capture())
            this.firstValue.onSet(LEAGUE_ID)

            argumentCaptor<String>().apply {
                verify(teamView).leagueFavorited(this.capture())
                assertEquals(LEAGUE_ID, firstValue)
            }

        }

    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }
    private fun <T> uninitialized(): T = null as T
}