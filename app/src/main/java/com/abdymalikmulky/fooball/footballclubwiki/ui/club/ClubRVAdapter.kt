package com.abdymalikmulky.fooball.footballclubwiki.ui.club

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abdymalikmulky.fooball.footballclubwiki.data.Club
import com.abdymalikmulky.fooball.footballclubwiki.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_club.view.*

class ClubRVAdapter(private val context: Context, private val clubs: List<Club>, private val listener: (Club) -> Unit)
    : RecyclerView.Adapter<ClubRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_club, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(clubs[position], listener)
    }

    override fun getItemCount() = clubs.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bindItem(club: Club, onItemClickListener: (Club) -> Unit) {
            itemView.list_club_name.text = club.clubName
            Picasso.get().load(club.clubLogo).into(itemView.list_club_logo)

            itemView.setOnClickListener {
                onItemClickListener(club)
            }

        }
    }
}