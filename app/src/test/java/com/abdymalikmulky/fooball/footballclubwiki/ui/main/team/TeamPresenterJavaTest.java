package com.abdymalikmulky.fooball.footballclubwiki.ui.main.team;

import com.abdymalikmulky.fooball.footballclubwiki.data.FootballDataSource;
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballRepo;
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

public class TeamPresenterJavaTest {
    private static ArrayList<Team> TEAMS;
    private static String LEAGUE_ID = "4328";

    @Mock
    private FootballRepo footballRepo;

    @Mock
    private TeamContract.View teamView;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<FootballDataSource.LoadTeamsCallback> loadTeamsCallbackCaptor;

    private TeamPresenter teamPresenter;

    @Before
    public void setupTasksPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        teamPresenter = new TeamPresenter(footballRepo, teamView);

        // We start the tasks to 3, with one active and two completed
        TEAMS = new ArrayList<>();
        TEAMS.add(new Team("1", "PSSI", "PSSI MAJU", "logo1"));
        TEAMS.add(new Team("2", "PSSI2", "PSSI MAJU2", "logo2"));
        TEAMS.add(new Team("3", "PSSI3", "PSSI MAJU3", "logo3"));

    }

    @Test
    public void createPresenter_setsThePresenterToView() {
        // Get a reference to the class under test
        teamPresenter = new TeamPresenter(footballRepo, teamView);

        // Then the presenter is set to the view
        verify(teamView).setPresenter(teamPresenter);
    }

    @Test
    public void loadAllTasksFromRepositoryAndLoadIntoView() {
        // Given an initialized TasksPresenter with initialized tasks
        // When loading of Tasks is requested
        teamPresenter.loadTeam(LEAGUE_ID);

        // Callback is captured and invoked with stubbed tasks
        verify(footballRepo).loadTeamLeague((String) any(), loadTeamsCallbackCaptor.capture());
        loadTeamsCallbackCaptor.getValue().onLoaded(TEAMS);

        verify(teamView).showLoading();
        ArgumentCaptor<List> showTasksArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(teamView).showTeamList(showTasksArgumentCaptor.capture());
        assertTrue(showTasksArgumentCaptor.getValue().size() == 3);
        verify(teamView).hideLoading();

    }

}
