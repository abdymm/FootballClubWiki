package com.abdymalikmulky.fooball.footballclubwiki.ui.main.team

import com.abdymalikmulky.fooball.footballclubwiki.data.FootballDataSource
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballRepo
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.nhaarman.mockitokotlin2.argumentCaptor
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.verify
import java.util.ArrayList

class TeamPresenterTest {
    private lateinit var TEAMS: ArrayList<Team>
    private val LEAGUE_ID = "4328"

    @Mock
    private lateinit var footballRepo: FootballRepo

    @Mock
    private lateinit var teamView: TeamContract.View

    /**
     * [ArgumentCaptor] is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private lateinit var loadTeamsCallbackCaptor: ArgumentCaptor<FootballDataSource.LoadTeamsCallback>

    private lateinit var teamPresenter: TeamPresenter

    @Before
    fun setupTasksPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        // Get a reference to the class under test
        teamPresenter = TeamPresenter(footballRepo, teamView)


        // We start the tasks to 3, with one active and two completed
        TEAMS = ArrayList()
        TEAMS.add(Team("1", "PSSI", "PSSI MAJU", "logo1"))
        TEAMS.add(Team("2", "PSSI2", "PSSI MAJU2", "logo2"))
        TEAMS.add(Team("3", "PSSI3", "PSSI MAJU3", "logo3"))

    }

    @Test
    fun createPresenter_setsThePresenterToView() {
        // Get a reference to the class under test
        teamPresenter = TeamPresenter(footballRepo, teamView)

        // Then the presenter is set to the view
        verify<TeamContract.View>(teamView).setPresenter(teamPresenter)
    }

    @Test
    fun loadAllTasksFromRepositoryAndLoadIntoView() {
        teamPresenter.loadTeam(LEAGUE_ID)

        //Null Safety of Kotlin Issue However, in Mockito, both anyObject() and any() will return Null in it’s verification function when being used.
        //so i use 3rd party library to handle this issue https://github.com/nhaarman/mockito-kotlin

        argumentCaptor<FootballDataSource.LoadTeamsCallback>().apply {
            verify(footballRepo).loadTeamLeague(any(), capture())
            this.firstValue.onLoaded(TEAMS)
        }
        //loadTeamsCallbackCaptor.value.onLoaded(TEAMS)
        /*verify(teamView).showLoading()

        val showTasksArgumentCaptor = ArgumentCaptor.forClass(List::class.java)
        verify(teamView).showTeamList(showTasksArgumentCaptor.capture() as List<Team>)
        assertTrue(showTasksArgumentCaptor.value.size == 3)
        verify(teamView).hideLoading()*/

    }
    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }
    private fun <T> uninitialized(): T = null as T
}