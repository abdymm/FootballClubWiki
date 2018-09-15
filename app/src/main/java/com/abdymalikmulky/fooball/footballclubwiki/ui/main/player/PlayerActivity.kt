package com.abdymalikmulky.fooball.footballclubwiki.ui.main.player

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballRepo
import com.abdymalikmulky.fooball.footballclubwiki.data.player.Player
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_player.*
import kotlinx.android.synthetic.main.activity_team_detail.*
import kotlinx.android.synthetic.main.content_player.*
import org.jetbrains.anko.toast
import java.lang.Exception

class PlayerActivity : AppCompatActivity() {

    internal lateinit var player: Player

    internal  var expandedActionBar = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        setSupportActionBar(toolbar_player)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        player = intent.extras.get(getString(R.string.EXTRA_PLAYER)) as Player


        app_bar_player.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if(Math.abs(verticalOffset) > 200) {
                expandedActionBar = false
                toolbar_layout_player.title = player.name
                invalidateOptionsMenu()
            } else {
                expandedActionBar = true
                toolbar_layout_player.title = ""
                invalidateOptionsMenu()
            }
        }

        renderPlayerData(player)
    }

    private fun renderPlayerData(player: Player) {
        Picasso.get().load(player.banner).into(object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }
            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                toast(e.toString())
            }
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                player_banner.setBackground(BitmapDrawable(applicationContext.resources, bitmap))
            }

        })
        Picasso.get().load(player.photo).into(player_photo)
        player_name.text = player.name
        player_position.text = player.position
        player_description.text = player.description
        Log.d("PlayerData", player.description)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
