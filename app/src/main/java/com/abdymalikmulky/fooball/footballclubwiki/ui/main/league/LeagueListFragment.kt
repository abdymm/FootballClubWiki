package com.abdymalikmulky.fooball.footballclubwiki.ui.main.league


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
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
import com.abdymalikmulky.fooball.footballclubwiki.util.gone
import com.abdymalikmulky.fooball.footballclubwiki.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.*
import android.support.v7.widget.DividerItemDecoration
import android.util.TypedValue
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.team.TeamListFragment
import com.abdymalikmulky.fooball.footballclubwiki.util.openFragment
import org.jetbrains.anko.cardview.v7.cardView


class LeagueListFragment : Fragment(), LeagueContract.View {


    //Presenter
    private lateinit var leaguePresenter: LeagueContract.Presenter

    //Repo
    private lateinit var footballRepo: FootballRepo

    private var leagues: MutableList<League> = mutableListOf()

    private lateinit var listLeague: RecyclerView
    private lateinit var leagueListAdapter: LeagueListAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout


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

                            listLeague = recyclerView {
                                id = R.id.league_list
                                lparams(width = matchParent, height = wrapContent)
                                layoutManager = LinearLayoutManager(ctx)

                                val outValue = TypedValue()
                                context!!.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
                                backgroundResource = outValue.resourceId
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

        leaguePresenter.loadLeague()


        leagueListAdapter = LeagueListAdapter(leagues) {

            leaguePresenter.setFavoriteLeague(it.leagueId)
            showTeamPage(leagueId = it.leagueId)

        }
        listLeague.adapter = leagueListAdapter
        listLeague.addItemDecoration(DividerItemDecoration(listLeague.getContext(), DividerItemDecoration.VERTICAL))
        swipeRefresh.onRefresh {
            leaguePresenter.loadLeague()
        }
    }

    companion object {
        fun newInstance(): LeagueListFragment = LeagueListFragment()
    }



    private fun initPresenterRepo() {
        //Repo init
        footballRepo = FootballRepo(activity!!.applicationContext)

        //Presenter init
        leaguePresenter = LeaguePresenter(footballRepo, this)
    }


    override fun showLoading() {
        progressBar.visible()
        listLeague.gone()
    }

    override fun hideLoading() {
        progressBar.gone()
        listLeague.visible()
    }

    override fun showLeagueList(leagues: List<League>) {
        swipeRefresh.isRefreshing = false
        this.leagues.clear()
        this.leagues.addAll(leagues)
        leagueListAdapter.notifyDataSetChanged()
    }

    override fun showTeamPage(leagueId: String) {
        //openfragment team
        val teamFragment = TeamListFragment.newInstance(leagueId)
        openFragment(this.activity, teamFragment, R.id.content_team_league)
    }

    override fun setPresenter(presenter: LeagueContract.Presenter) {
        leaguePresenter = presenter
    }


    override fun showError(message: String) {
        toast(message)
    }

}

