package com.abdymalikmulky.fooball.footballclubwiki.ui.team

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.data.Team
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_team.view.*


class TeamListAdapter(private val context: Context, private val teams: List<Team>, private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_team, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    override fun getItemCount() = teams.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bindItem(team: Team, onItemClickListener: (Team) -> Unit) {
            itemView.list_team_name.text = team.teamName
            Picasso.get().load(team.teamLogo).into(itemView.list_team_logo)

            itemView.setOnClickListener {
                onItemClickListener(team)
            }

        }
    }
}