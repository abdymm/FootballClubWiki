package com.abdymalikmulky.fooball.footballclubwiki.ui.main.match


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar

import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballRepo
import com.abdymalikmulky.fooball.footballclubwiki.data.event.Event
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.match.detail.FixtureDetailActivity
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.team.TeamDetailActivity
import com.abdymalikmulky.fooball.footballclubwiki.util.gone
import com.abdymalikmulky.fooball.footballclubwiki.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.*

class FixtureFragment : Fragment(), FixtureContract.View {

    var isPastEvent = false
    var isFavorite = 0

    //Presenter
    private lateinit var fixturePresenter: FixtureContract.Presenter

    //Repo
    private lateinit var footballRepo: FootballRepo

    private var events: MutableList<Event> = mutableListOf()

    private lateinit var listFixture: RecyclerView
    private lateinit var fixtureAdapter: FixtureAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout


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

                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(R.color.colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light)

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        listFixture = recyclerView {
                            lparams(width = matchParent, height = wrapContent)
                            layoutManager = LinearLayoutManager(ctx)
                        }

                        progressBar = progressBar {}.lparams {
                            centerHorizontally()
                        }
                    }
                }
            }
        }.view

    }

    companion object {

        fun newInstance(isPastEvent: Boolean): FixtureFragment {
            val fragment = FixtureFragment()
            val args = Bundle()
            args.putBoolean("IS_PAST_EVENT", isPastEvent)
            fragment.setArguments(args)
            return fragment
        }
        fun newInstance(isFavorite: Int): FixtureFragment {
            val fragment = FixtureFragment()
            val args = Bundle()
            args.putInt("IS_FAVORITE", isFavorite)
            fragment.setArguments(args)
            return fragment
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        arguments?.getBoolean(getString(R.string.KEY_IS_PAST_EVENT))?.let {
            isPastEvent = it
        }
        arguments?.getInt(getString(R.string.KEY_IS_FAVORITE))?.let {
            isFavorite = it
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPresenterRepo()

        loadEvents()

        fixtureAdapter = FixtureAdapter(isPastEvent, events) {

            startActivity(intentFor<FixtureDetailActivity>(getString(R.string.EXTRA_FIXTURE) to it, getString(R.string.KEY_IS_PAST_EVENT) to isPastEvent))

        }
        listFixture.adapter = fixtureAdapter
        swipeRefresh.onRefresh {
            loadEvents()
        }
    }

    private fun initPresenterRepo() {
        //Repo init
        footballRepo = FootballRepo(activity!!.applicationContext)

        //Presenter init
        fixturePresenter = FixturePresenter(footballRepo, this)
    }

    override fun showLoading() {
        progressBar.visible()
        listFixture.gone()
    }

    override fun hideLoading() {
        progressBar.gone()
        listFixture.visible()
    }

    override fun showFixtureList(events: List<Event>) {
        Log.d("DataEvent %s", events.toString())
        swipeRefresh.isRefreshing = false
        this.events.clear()
        this.events.addAll(events)
        fixtureAdapter.notifyDataSetChanged()
    }

    override fun setPresenter(presenter: FixtureContract.Presenter) {
        fixturePresenter = presenter
    }

    override fun showError(message: String) {
        toast(message)
    }

    internal fun loadEvents() {
        if(isFavorite==1) {
            fixturePresenter.loadFavoriteEvent(getString(R.string.league_id))
        } else {
            fixturePresenter.loadEvent(isPastEvent, getString(R.string.league_id))
        }
    }
}
