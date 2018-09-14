package com.abdymalikmulky.fooball.footballclubwiki.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.league.LeagueListFragment
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.match.FixtureFragment
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.team.TeamListActivity
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.team.TeamListFragment
import kotlinx.android.synthetic.main.activity_main_bottom.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_bottom)

        toolbar = supportActionBar!!
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


        val fixtureFragment = FixtureFragment.newInstance()
        openFragment(fixtureFragment)

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_fixture -> {
                val fixtureFragment = FixtureFragment.newInstance()
                openFragment(fixtureFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_league -> {
                val leagueFragment = LeagueListFragment.newInstance()
                openFragment(leagueFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_fav_match -> {
                val fixtureFragment = FixtureFragment.newInstance(1)
                openFragment(fixtureFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.menu_team -> {
                startActivity(intentFor<TeamListActivity>())
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


}
