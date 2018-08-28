package com.abdymalikmulky.fooball.footballclubwiki.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.match.FixtureFragment
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.team.TeamListFragment
import kotlinx.android.synthetic.main.activity_main_bottom.*

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_bottom)

        toolbar = supportActionBar!!
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val fixtureFragment = FixtureFragment.newInstance(true)
        openFragment(fixtureFragment)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_scores -> {
                val fixtureFragment = FixtureFragment.newInstance(true)
                openFragment(fixtureFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_fixture -> {
                val fixtureFragment = FixtureFragment.newInstance(false)
                openFragment(fixtureFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_team_dir -> {
                val teamFragment = TeamListFragment.newInstance()
                openFragment(teamFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }



    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}