package com.abdymalikmulky.fooball.footballclubwiki.ui.main.match


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar

import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballRepo
import com.abdymalikmulky.fooball.footballclubwiki.data.event.Event
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.team.TeamDetailActivity
import com.abdymalikmulky.fooball.footballclubwiki.util.gone
import com.abdymalikmulky.fooball.footballclubwiki.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.*

class FixtureFragment : Fragment(), FixtureContract.View {

    var isPastEvent = false

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

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        arguments?.getBoolean(getString(R.string.KEY_IS_PAST_EVENT))?.let {
            isPastEvent = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPresenterRepo()

        fixturePresenter.loadEvent(isPastEvent, "4328")

        fixtureAdapter = FixtureAdapter(isPastEvent, events) {
            startActivity(intentFor<TeamDetailActivity>(getString(R.string.EXTRA_FIXTURE) to it))
        }
        listFixture.adapter = fixtureAdapter
        swipeRefresh.onRefresh {
            fixturePresenter.loadEvent(isPastEvent, "4328")
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
}
