package com.abdymalikmulky.fooball.footballclubwiki.ui.main.league

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.R.id.*
import com.abdymalikmulky.fooball.footballclubwiki.data.league.League
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*


class LeagueListAdapter(private val leagues: List<League>, private val listener: (League) -> Unit)
    : RecyclerView.Adapter<LeagueListAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            TeamViewHolder(ListTeamUI().createView(AnkoContext.create(parent.context, parent)))


    override fun onBindViewHolder(holderTeam: TeamViewHolder, position: Int) {
        holderTeam.bindItem(leagues[position], listener)
    }

    override fun getItemCount() = leagues.size

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val leagueName: TextView = view.find(league_name)



        fun bindItem(league: League, onItemClickListener: (League) -> Unit) {
            leagueName.text = league.leagueName

            itemView.setOnClickListener {
                onItemClickListener(league)
            }

        }
    }

    class ListTeamUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui) {
                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(16)
                    orientation = LinearLayout.HORIZONTAL


                    textView {
                        id = R.id.league_name
                        textSize = 16f
                    }.lparams{
                        margin = dip(4)
                    }
                }
            }
        }

    }
}