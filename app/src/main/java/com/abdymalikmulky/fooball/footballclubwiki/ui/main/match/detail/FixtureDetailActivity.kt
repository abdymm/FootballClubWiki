package com.abdymalikmulky.fooball.footballclubwiki.ui.main.match.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballRepo
import com.abdymalikmulky.fooball.footballclubwiki.data.event.Event
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.match.FixtureContract
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.match.FixturePresenter
import com.abdymalikmulky.fooball.footballclubwiki.util.DateTimeUtil
import com.abdymalikmulky.fooball.footballclubwiki.util.gone
import com.abdymalikmulky.fooball.footballclubwiki.util.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_fixture_detail.*
import kotlinx.android.synthetic.main.list_event.view.*
import org.jetbrains.anko.toast

class FixtureDetailActivity : AppCompatActivity(), FixtureDetailContract.View {



    //Presenter
    private lateinit var fixtureDetailPresenter: FixtureDetailContract.Presenter

    //Repo
    private lateinit var footballRepo: FootballRepo

    internal lateinit var menu: Menu

    private var isPastEvent: Boolean = false
    private lateinit var event: Event

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fixture_detail)

        initPresenterRepo()

        isPastEvent = intent.getBooleanExtra(getString(R.string.KEY_IS_PAST_EVENT), true)
        event = intent.extras.get(getString(R.string.EXTRA_FIXTURE)) as Event

        showFixture(event)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        this.menu = menu
        inflater.inflate(R.menu.team, menu)

        fixtureDetailPresenter.checkIsEventFavorited(event.idEvent)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.menu_fav -> {
                val unfaveMenu = this.menu.findItem(R.id.menu_unfav)
                unfaveMenu.isVisible = true
                item.isVisible = false

                fixtureDetailPresenter.favoriteMatch(true, eventId = event.idEvent)

                return true
            }
            R.id.menu_unfav -> {
                val faveMenu = this.menu.findItem(R.id.menu_fav)
                faveMenu.isVisible = true
                item.isVisible = false

                fixtureDetailPresenter.favoriteMatch(false, eventId = event.idEvent)

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    private fun initPresenterRepo() {
        //Repo init
        footballRepo = FootballRepo(applicationContext)

        //Presenter init
        fixtureDetailPresenter = FixtureDetailPresenter(footballRepo, this)
    }

    override fun showLoading() {
        //progressBar.visible()
    }

    override fun hideLoading() {
        //progressBar.gone()
    }

    override fun showTeam(team: Team) {
        if(team.teamName!!.equals(event.homeTeam)) {
            Picasso.get().load(team.teamBadge).into(event_home_badge)
        } else {
            Picasso.get().load(team.teamBadge).into(event_away_badge)
        }
    }

    override fun showFixture(event: Event) {
        Log.d("EVENTDETAIL ", event.toString())
        event_date.text = DateTimeUtil.convertToReadableDate(event.dateEvent)
        event_time.text = event.timeEvent
        event_home_name.text = event.homeTeam
        event_away_name.text = event.awayTeam
        event_score.text = "${event.homeScore} : ${event.awayScore}"


        if(isPastEvent) {
            event_time_status.text = "FULL TIME"


            event_home_goal.text = convertStringGoalDetailsToList(event.homeGoalDetails)
            event_away_goal.text = convertStringGoalDetailsToList(event.awayGoalDetails)

            if(event.homeLineUpGK != null) {
                event_home_lineup.text = convertStringLineUpToList(event.homeLineUpGK, event.homeLineUpDEF, event.homeLineUpMD, event.homeLineUpFW)
            }
            if(event.awayLineUpGK != null) {
                event_away_lineup.text = convertStringLineUpToList(event.awayLineUpGK, event.awayLineUpDEF, event.awayLineUpMD, event.awayLineUpFW)
            }
        } else {
            event_time_status.text = "UPCOMING"
        }

        fixtureDetailPresenter.loadTeam(event.idHomeTeam)
        fixtureDetailPresenter.loadTeam(event.idAwayTeam)


    }

    override fun setPresenter(presenter: FixtureDetailContract.Presenter) {
        fixtureDetailPresenter = presenter
    }

    override fun showError(message: String) {
        toast(message)
    }

    fun convertStringGoalDetailsToList(goalScorer: String?): String {
        val splitScorer = goalScorer?.split(';')
        var convertedString = "-"
        if(splitScorer != null) {
            for (i in splitScorer.indices) {
                convertedString += splitScorer[i]
                if(i<splitScorer.size) {
                    convertedString += "\n"
                }
            }
        }
        return convertedString
    }

    fun convertStringLineUpToList(gk: String,def: String,md: String,fw: String): String {
        var lineUpString = ""
        //GK
        lineUpString += "(GK) " + gk + "\n\n"

        //DEF
        lineUpString += convertStringLineUptoList(def, "DEF")

        //MD
        lineUpString += convertStringLineUptoList(md, "MDF")

        //FW
        lineUpString += convertStringLineUptoList(fw, "FW")

        return lineUpString
    }

    fun convertStringLineUptoList(lineUpString: String, position: String): String {
        var lineUpList = ""
        val arrayPlayer = lineUpString.split(";")
        for (i in arrayPlayer.indices) {
            if(i<=arrayPlayer.size-2) {
                lineUpList += "("+ position + ") " + arrayPlayer[i] + "\n"
            } else {
                lineUpList += "\n"
            }
        }
        return lineUpList
    }

    override fun showFavoriteMatchResponse(isFavorite: Boolean, eventId: String) {
        if(isFavorite) {
            toast(getString(R.string.message_match_saved))
        } else {
            toast(getString(R.string.message_match_unsaved))
        }
    }

    override fun showEventFav(isFavorite: Boolean) {
        val unfaveMenu = this.menu.findItem(R.id.menu_unfav)
        val faveMenu = this.menu.findItem(R.id.menu_fav)

        faveMenu.isVisible = true
        unfaveMenu.isVisible = false

        if(isFavorite) {
            faveMenu.isVisible = false
            unfaveMenu.isVisible = true
        }
    }
}
