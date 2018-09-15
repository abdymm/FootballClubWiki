package com.abdymalikmulky.fooball.footballclubwiki.ui.main.team


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballRepo
import com.abdymalikmulky.fooball.footballclubwiki.data.league.League
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.MainActivity
import com.abdymalikmulky.fooball.footballclubwiki.util.gone
import com.abdymalikmulky.fooball.footballclubwiki.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.*

class TeamListFragment : Fragment(), TeamContract.View {

    //Presenter
    private lateinit var teamPresenter: TeamContract.Presenter

    //Repo
    private lateinit var footballRepo: FootballRepo

    private lateinit var leagueId: String
    private var teams: MutableList<Team> = mutableListOf()

    private lateinit var listTeam: RecyclerView
    private lateinit var teamListTeamAdapter: TeamListAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    companion object {
        fun newInstance(leagueId: String): TeamListFragment {
            val fragment = TeamListFragment()
            val args = Bundle()
            args.putString("LEAGUE_ID", leagueId)
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        arguments?.getString(getString(R.string.KEY_LEAGUE_ID))?.let {
            leagueId = it
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return UI {
            linearLayout {
                val padding = 20
                val paddingTop = 26
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL
                topPadding = dip(paddingTop)
                leftPadding = dip(padding)
                rightPadding = dip(padding)
                bottomPadding = dip(paddingTop)
                backgroundColor = resources.getColor(R.color.colorBackground)

                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(R.color.colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light)

                    cardView {
                        lparams(width = matchParent, height = wrapContent)

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
            }
        }.view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenterRepo()

        loadTeamByLeagueId(leagueId)

        teamListTeamAdapter = TeamListAdapter(teams) {
            teamPresenter.setFavoriteTeam(it.teamId)
        }
        listTeam.adapter = teamListTeamAdapter
        listTeam.addItemDecoration(DividerItemDecoration(listTeam.getContext(), DividerItemDecoration.VERTICAL))
        swipeRefresh.onRefresh {
            teamPresenter.loadTeam(leagueId)
        }
    }





    private fun initPresenterRepo() {
        //Repo init
        footballRepo = FootballRepo(activity!!.applicationContext)

        //Presenter init
        teamPresenter = TeamPresenter(footballRepo, this)
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

    override fun showTeamList(teams: List<Team>) {
        swipeRefresh.isRefreshing = false
        this.teams.clear()
        this.teams.addAll(teams)
        teamListTeamAdapter.notifyDataSetChanged()
    }

    override fun teamFavorited(teamId: String) {
        toast(teamId)
        startActivity(intentFor<MainActivity>())
    }

    override fun setPresenter(presenter: TeamContract.Presenter) {
        teamPresenter = presenter
    }


    override fun showError(message: String) {
        toast(message)
    }
}

