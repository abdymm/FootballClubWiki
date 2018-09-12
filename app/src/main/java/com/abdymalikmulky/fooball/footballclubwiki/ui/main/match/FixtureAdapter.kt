package com.abdymalikmulky.fooball.footballclubwiki.ui.main.match

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.R.id.team_badge
import com.abdymalikmulky.fooball.footballclubwiki.R.id.team_name
import com.abdymalikmulky.fooball.footballclubwiki.data.event.Event
import com.abdymalikmulky.fooball.footballclubwiki.util.DateTimeUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_event.view.*
import org.jetbrains.anko.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*


class FixtureAdapter(private val isPastEvent: Boolean,private val events: List<Event>, private val listener: (Event) -> Unit)
    : RecyclerView.Adapter<FixtureAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            EventViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_event, parent, false))



    override fun onBindViewHolder(holderEvent: EventViewHolder, position: Int) {
        holderEvent.bindItem(isPastEvent, events[position], listener)
    }

    override fun getItemCount() = events.size

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bindItem(isPastEvent: Boolean, event: Event, onItemClickListener: (Event) -> Unit) {

            itemView.list_event_date.text = DateTimeUtil.convertToReadableDate(event.dateEvent)
            itemView.list_event_time.text = event.timeEvent
            itemView.list_event_home_name.text = event.homeTeam
            itemView.list_event_away_name.text = event.awayTeam
            itemView.list_event_score.text = "${event.homeScore} : ${event.awayScore}"
            if(isPastEvent) {
                itemView.list_event_time_status.text = "FULL TIME"
            } else {
                itemView.list_event_time_status.text = "UPCOMING"
            }

            if(!event.homeTeamBadge.equals("")) {
                Picasso.get().load(event.homeTeamBadge)
                        .error(R.drawable.badge_dumm)
                        .into(itemView.list_event_home_badge)
            }
            if(!event.awayTeamBadge.equals("")) {
                Picasso.get().load(event.awayTeamBadge)
                        .error(R.drawable.badge_dumm)
                        .into(itemView.list_event_away_badge)
            }




            itemView.setOnClickListener {
                onItemClickListener(event)
            }

        }
    }
}