package com.abdymalikmulky.fooball.footballclubwiki.ui.main.team.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import org.jetbrains.anko.*
import android.view.MenuItem
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballDataSource
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballRepo
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_team_detail.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.Bitmap
import android.support.design.widget.TabLayout
import android.util.Log
import java.lang.Exception


class TeamDetailActivity : AppCompatActivity(), TeamDetailContract.View {


    internal lateinit var menu: Menu
    internal var isFavorite = false

    internal lateinit var team: Team

    internal lateinit var footballRepo: FootballRepo

    internal var expandedActionBar = true

    internal lateinit var teamPagerAdapter: TeamPagerAdapter

    //Presenter
    private lateinit var teamDetailPresenter: TeamDetailContract.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        footballRepo = FootballRepo(this)
        teamDetailPresenter = TeamDetailPresenter(footballRepo, this)

        team = intent.extras.get(getString(R.string.EXTRA_TEAM)) as Team

        app_bar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if(Math.abs(verticalOffset) > 200) {
                expandedActionBar = false
                toolbar_layout.title = team.teamName
                invalidateOptionsMenu()
            } else {
                expandedActionBar = true
                toolbar_layout.title = ""
                invalidateOptionsMenu()
            }
        }

        teamDetailPresenter.loadTeam(team.teamId)
        teamDetailPresenter.isTeamHasFavorite(team.teamId)

        fab_fav.setOnClickListener {
            isFavorite = !isFavorite
            if(isFavorite) {
                fab_fav.setImageResource(R.drawable.ic_favorite_black_24dp)
            } else {
                fab_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            }
            setFavoriteTeam(isFavorite, team.teamId)
        }
    }

    private fun initTab(team: Team) {
        teamPagerAdapter = TeamPagerAdapter(team, supportFragmentManager)
        view_pager.adapter = teamPagerAdapter
        tab_layout.setupWithViewPager(view_pager)
        tab_layout.tabMode = TabLayout.MODE_FIXED

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun renderTeamData(team: Team) {
        Picasso.get().load(team.stadiumThumb).into(object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }
            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                toast(e.toString())
            }
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                team_header_layout.setBackground(BitmapDrawable(applicationContext.resources, bitmap))
            }

        })

        Picasso.get().load(team.teamBadge).into(team_header_logo)
        team_header_name.text = team.teamName
        team_header_nick.text = if(team.teamCountry!="") team.teamCountry else team.teamNick
    }

    fun setFavoriteTeam(favorite: Boolean, teamId: String) {
        footballRepo.setFavoriteTeam(favorite, teamId, object: FootballDataSource.SetFavoriteTeamCallback{
            override fun onSavedTeam(teamId: String) {

            }
            override fun onFailed(errorMsg: String) {
                toast(errorMsg)
            }

        })
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showTeam(team: Team) {
        this.team = team

        initTab(team)
        renderTeamData(team)
    }

    override fun setPresenter(presenter: TeamDetailContract.Presenter) {
        teamDetailPresenter = presenter
    }

    override fun showError(message: String) {
        toast(message)
    }

    override fun isTeamFavorit(isFavorite: Boolean) {
        this.isFavorite = isFavorite
        if(isFavorite) {
            fab_fav.setImageResource(R.drawable.ic_favorite_black_24dp)
        } else {
            fab_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

