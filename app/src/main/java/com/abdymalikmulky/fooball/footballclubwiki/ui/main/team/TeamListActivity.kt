package com.abdymalikmulky.fooball.footballclubwiki.ui.main.team

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.R.color.colorAccent
import com.abdymalikmulky.fooball.footballclubwiki.data.*
import com.abdymalikmulky.fooball.footballclubwiki.data.league.League
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.MainActivity
import com.abdymalikmulky.fooball.footballclubwiki.util.gone
import com.abdymalikmulky.fooball.footballclubwiki.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout


class TeamListActivity : AppCompatActivity(), TeamContract.View {
    //Presenter
    private lateinit var teamPresenter: TeamContract.Presenter

    //Repo
    private lateinit var footballRepo: FootballRepo


    private lateinit var choosedLeague: League
    private var teams: MutableList<Team> = mutableListOf()

    private lateinit var spinnerLeague: Spinner
    public lateinit var listTeam: RecyclerView
    private lateinit var teamListTeamAdapter: TeamListAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPresenterRepo()

        linearLayout {
            val padding = 16
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(padding)
            leftPadding = dip(padding)
            rightPadding = dip(padding)

            spinnerLeague = spinner{
                id = R.id.league_spinner
            }

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listTeam = recyclerView {
                        id = R.id.team_list
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {}.lparams {
                        centerHorizontally()
                    }
                }
            }
        }


        teamPresenter.loadLeague()

        spinnerLeague.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                if(spinnerLeague.selectedItem != null) {
                    choosedLeague = spinnerLeague.selectedItem as League

                    teamPresenter.setFavoriteLeague(choosedLeague.leagueId)
                }
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {
            }
        }

        teamListTeamAdapter = TeamListAdapter(teams) {
            startActivity(intentFor<MainActivity>(getString(R.string.EXTRA_TEAM) to it))
        }
        listTeam.adapter = teamListTeamAdapter
        swipeRefresh.onRefresh {
            teamPresenter.loadTeam(choosedLeague.leagueId )
        }
    }
    private fun initPresenterRepo() {
        //Repo init
        footballRepo = FootballRepo(this)

        //Presenter init
        teamPresenter = TeamPresenter(footballRepo, this)
    }

    override fun onBackPressed() {
        alert(getString(R.string.alert_exit)) {
            yesButton {
                super.onBackPressed()
            }
            noButton {  }
        }.show()
    }

    private fun loadTeamByLeagueId(leagueId: String) {
        teamPresenter.loadTeam(leagueId)
    }

    override fun showLoading() {
        progressBar.visible()
        listTeam.gone()
    }

    override fun hideLoading() {
        progressBar.gone()
        listTeam.visible()
    }

    override fun showLeagueList(leagues: List<League>) {
        val spinnerAdapter = ArrayAdapter<League>(applicationContext, android.R.layout.simple_spinner_dropdown_item, leagues)
        spinnerAdapter .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLeague.adapter = spinnerAdapter

        choosedLeague = spinnerLeague.selectedItem as League
        loadTeamByLeagueId(choosedLeague.leagueId)
    }

    override fun showTeamList(teams: List<Team>) {
        swipeRefresh.isRefreshing = false
        this.teams.clear()
        this.teams.addAll(teams)
        teamListTeamAdapter.notifyDataSetChanged()
    }

    override fun teamFavorited(teamId: String) {
        
    }

    override fun leagueFavorited(leagueId: String) {
        loadTeamByLeagueId(leagueId)
    }

    override fun setPresenter(@NonNull presenter: TeamContract.Presenter) {
        teamPresenter = presenter
    }

    override fun showError(message: String) {

    }
}
