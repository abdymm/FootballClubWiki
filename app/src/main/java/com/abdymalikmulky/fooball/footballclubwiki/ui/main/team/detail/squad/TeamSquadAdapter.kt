package com.abdymalikmulky.fooball.footballclubwiki.ui.main.team.detail.squad

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.R.id.*
import com.abdymalikmulky.fooball.footballclubwiki.data.player.Player
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*


class TeamSquadAdapter(private var players: List<Player>, private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<TeamSquadAdapter.PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            PlayerViewHolder(PlayerTeamUI().createView(AnkoContext.create(parent.context, parent)))


    override fun onBindViewHolder(holderPlayer: PlayerViewHolder, position: Int) {
        holderPlayer.bindItem(players[position], listener)
    }

    override fun getItemCount() = players.size

    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val playerPhoto: ImageView = view.find(player_photo)
        private val playerName: TextView = view.find(player_name)
        private val playerPos: TextView = view.find(player_pos)

        fun bindItem(player: Player, onItemClickListener: (Player) -> Unit) {
            playerName.text = player.name
            playerPos.text = player.position
            Log.d("DataPlayer", player.name)
            Picasso.get().load(player.photo).into(playerPhoto)

            itemView.setOnClickListener {
                onItemClickListener(player)
            }

        }
    }

    class PlayerTeamUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui) {
                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(16)
                    orientation = LinearLayout.HORIZONTAL

                    imageView {
                        id = R.id.player_photo
                        alpha = 0.8.toFloat()
                    }.lparams{
                        height = dip(50)
                        width = dip(50)
                    }

                    linearLayout {
                        lparams(width = matchParent, height = wrapContent)
                        orientation = LinearLayout.VERTICAL
                        textView {
                            id = R.id.player_name
                            textSize = 16f
                        }.lparams{
                            leftMargin = dip(6)
                            topMargin = dip(12)
                        }
                        textView {
                            id = R.id.player_pos
                            textSize = 12f
                        }.lparams{
                            leftMargin = dip(6)
                        }



                    }


                }
            }
        }

    }

    fun refresh(players: List<Player>) {
        this.players = players
        notifyDataSetChanged()
    }
}