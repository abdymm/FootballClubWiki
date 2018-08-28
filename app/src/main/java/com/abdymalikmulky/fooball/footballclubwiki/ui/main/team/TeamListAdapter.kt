package com.abdymalikmulky.fooball.footballclubwiki.ui.main.team

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.R.id.team_badge
import com.abdymalikmulky.fooball.footballclubwiki.R.id.team_name
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*


class TeamListAdapter(private val teams: List<Team>, private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamListAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            TeamViewHolder(ListTeamUI().createView(AnkoContext.create(parent.context, parent)))


    override fun onBindViewHolder(holderTeam: TeamViewHolder, position: Int) {
        holderTeam.bindItem(teams[position], listener)
    }

    override fun getItemCount() = teams.size

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val teamBadge: ImageView = view.find(team_badge)
        private val teamName: TextView = view.find(team_name)



        fun bindItem(team: Team, onItemClickListener: (Team) -> Unit) {
            teamName.text = team.teamName
            Picasso.get().load(team.teamBadge).into(teamBadge)

            itemView.setOnClickListener {
                onItemClickListener(team)
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

                    imageView {
                        id = R.id.team_badge
                    }.lparams{
                        height = dip(50)
                        width = dip(50)
                    }

                    textView {
                        id = R.id.team_name
                        textSize = 16f
                    }.lparams{
                        margin = dip(15)
                    }

                }
            }
        }

    }
}