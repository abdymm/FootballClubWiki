package com.abdymalikmulky.fooball.footballclubwiki.ui.team

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Spinner
import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.R.color.colorAccent
import com.abdymalikmulky.fooball.footballclubwiki.data.Team
import com.abdymalikmulky.fooball.footballclubwiki.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import java.io.Serializable

class TeamListActivity : AppCompatActivity() {

    private var teams: MutableList<Team> = mutableListOf()

    private lateinit var spinnerLeague: Spinner
    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        linearLayout {
            val padding = 16
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(padding)
            leftPadding = dip(padding)
            rightPadding = dip(padding)

            spinnerLeague = spinner()
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listTeam = recyclerView {
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {}.lparams {
                        centerHorizontally()
                    }
                }
            }
        }



        /*initData()
        initListLayout()*/
    }
    private fun initListLayout() {
        rv_club_list.layoutManager = LinearLayoutManager(this)
        rv_club_list.adapter = TeamListAdapter(this, teams) {
            val detailIntent = Intent(this, TeamDetailActivity::class.java)
            detailIntent.putExtra(getString(R.string.EXTRA_TEAM), it as Serializable)
            startActivity(detailIntent)

            startActivity(intentFor<TeamDetailActivity>(getString(R.string.EXTRA_TEAM) to it))
        }
    }
    private fun initData(){
        val name = resources.getStringArray(R.array.club_name)
        val desc = resources.getStringArray(R.array.club_desc)
        val image = resources.obtainTypedArray(R.array.club_image)
        teams.clear()
        for (i in name.indices) {
            teams.add(Team(name[i], desc[i], image.getResourceId(i, 0)))
        }
        image.recycle()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_login -> {
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }


    override fun onBackPressed() {
        alert(getString(R.string.alert_exit)) {
            yesButton {
                super.onBackPressed()
            }
            noButton {  }
        }.show()
    }

}
