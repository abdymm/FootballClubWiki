package com.abdymalikmulky.fooball.footballclubwiki.ui.main.fav


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar

import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballRepo
import com.abdymalikmulky.fooball.footballclubwiki.data.event.Event
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.match.detail.FixtureDetailActivity

import com.abdymalikmulky.fooball.footballclubwiki.util.SharedPreferenceUtil
import com.abdymalikmulky.fooball.footballclubwiki.util.gone
import com.abdymalikmulky.fooball.footballclubwiki.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.*
import android.widget.Button
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.match.FixtureAdapter
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.team.TeamListAdapter
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.team.detail.TeamDetailActivity
import com.abdymalikmulky.fooball.footballclubwiki.util.invisible
import org.jetbrains.anko.sdk25.coroutines.onClick


class FavFragment : Fragment(), FavContract.View {

    private lateinit var sharedPreferenceUtil: SharedPreferenceUtil
    private lateinit var leagueId: String

    //Presenter
    private lateinit var favPresenter: FavContract.Presenter

    //Repo
    private lateinit var footballRepo: FootballRepo

    private var events: MutableList<Event> = mutableListOf()
    private var teams: MutableList<Team> = mutableListOf()

    private lateinit var progressBar: ProgressBar

    private lateinit var listFixture: RecyclerView
    private lateinit var fixtureAdapter: FixtureAdapter
    private lateinit var swipeRefreshFixture: SwipeRefreshLayout

    private lateinit var listTeam: RecyclerView
    private lateinit var teamListAdapter: TeamListAdapter
    private lateinit var swipeRefreshTeam: SwipeRefreshLayout

    private lateinit var fixtureButton: Button
    private lateinit var teamButton: Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return UI {
            linearLayout {
                val padding = 16
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL
                topPadding = dip(padding)
                leftPadding = dip(padding)
                rightPadding = dip(padding)
                bottomPadding = dip(60)

                linearLayout {
                    lparams(width = matchParent, height = wrapContent) {
                        bottomMargin = dip(8)
                    }
                    orientation = LinearLayout.HORIZONTAL

                    fixtureButton = button {
                        isClickable = true
                        text = "FIXTURE"
                        background = ContextCompat.getDrawable(activity!!.applicationContext, R.drawable.background_ripple)
                        textColor = resources.getColor(R.color.colorPrimary, null)
                        onClick {
                            textColor = resources.getColor(R.color.colorPrimary, null)
                            teamButton.textColor = resources.getColor(R.color.colorButtonText, null)

                            swipeRefreshFixture.visible()
                            swipeRefreshTeam.gone()
                            favPresenter.loadFavoriteEvents(leagueId)
                        }
                    }.lparams(width = wrapContent, height = wrapContent, weight = 0.5f) {
                        rightMargin = dip(8)
                    }

                    teamButton = button {
                        isClickable = true
                        text = "TEAM"
                        background = ContextCompat.getDrawable(activity!!.applicationContext, R.drawable.background_ripple)
                        textColor = resources.getColor(R.color.colorButtonText, null)
                        onClick {
                            textColor = resources.getColor(R.color.colorPrimary, null)
                            fixtureButton.textColor = resources.getColor(R.color.colorButtonText, null)

                            swipeRefreshFixture.gone()
                            swipeRefreshTeam.visible()
                            favPresenter.loadFavoriteTeams()
                        }
                    }.lparams(width = wrapContent, height = wrapContent, weight = 0.5f) {
                        leftMargin = dip(8)
                    }


                }

                progressBar = progressBar {}.lparams {
                    gravity = Gravity.CENTER
                }

                swipeRefreshFixture = swipeRefreshLayout {
                    setColorSchemeResources(R.color.colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light)

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)
                        listFixture = recyclerView {
                            id = R.id.event_list
                            lparams(width = matchParent, height = wrapContent)
                            layoutManager = LinearLayoutManager(ctx)
                        }

                    }
                }

                swipeRefreshTeam = swipeRefreshLayout {
                    setColorSchemeResources(R.color.colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light)

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)
                        backgroundColor = resources.getColor(R.color.colorBackground)

                        listTeam = recyclerView {
                            id = R.id.team_list
                            lparams(width = matchParent, height = wrapContent)
                            layoutManager = LinearLayoutManager(ctx)
                            backgroundColor = resources.getColor(R.color.colorBackground)

                        }

                    }
                }



            }
        }.view

    }

    companion object {

        fun newInstance(): FavFragment {
            val fragment = FavFragment()
            return fragment
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferenceUtil = SharedPreferenceUtil(activity?.applicationContext)
        leagueId = sharedPreferenceUtil.pref.getString(getString(R.string.PREF_LEAGUE), getString(R.string.league_id))

        initPresenterRepo()

        initList()

        favPresenter.loadFavoriteEvents(leagueId)
    }

    private fun initList() {
        fixtureAdapter = FixtureAdapter(true, events) {
            startActivity(intentFor<FixtureDetailActivity>(getString(R.string.EXTRA_FIXTURE) to it, getString(R.string.KEY_IS_PAST_EVENT) to true))
        }
        listFixture.adapter = fixtureAdapter
        swipeRefreshFixture.onRefresh {
            favPresenter.loadFavoriteEvents(leagueId)
        }

        teamListAdapter = TeamListAdapter(teams) {
            startActivity(intentFor<TeamDetailActivity>(getString(R.string.EXTRA_TEAM) to it))
        }
        listTeam.adapter = teamListAdapter
        listTeam.addItemDecoration(DividerItemDecoration(listTeam.getContext(), DividerItemDecoration.VERTICAL))
        swipeRefreshTeam.onRefresh {
            favPresenter.loadFavoriteTeams()
        }
    }

    private fun initPresenterRepo() {
        //Repo init
        footballRepo = FootballRepo(activity!!.applicationContext)

        //Presenter init
        favPresenter = FavPresenter(footballRepo, this)
    }

    override fun showLoading() {
        progressBar.visible()
        listFixture.gone()
        listTeam.gone()
    }

    override fun hideLoading() {
        progressBar.gone()
        listFixture.visible()
        listTeam.visible()
    }

    override fun showFixtureList(events: List<Event>) {
        swipeRefreshFixture.isRefreshing = false
        this.events.clear()
        this.events.addAll(events)
        fixtureAdapter.refresh(true, this.events)
    }

    override fun showTeamList(teams: List<Team>) {
        swipeRefreshTeam.isRefreshing = false
        this.teams.clear()
        this.teams.addAll(teams)
        teamListAdapter.notifyDataSetChanged()
    }

    override fun setPresenter(presenter: FavContract.Presenter) {
        favPresenter = presenter
    }

    override fun showError(message: String) {
        toast(message)
    }

}
