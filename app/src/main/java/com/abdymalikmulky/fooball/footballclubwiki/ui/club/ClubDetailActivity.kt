package com.abdymalikmulky.fooball.footballclubwiki.ui.club

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.abdymalikmulky.fooball.footballclubwiki.data.Club
import com.abdymalikmulky.fooball.footballclubwiki.R
import org.jetbrains.anko.*

class ClubDetailActivity : AppCompatActivity() {

    internal lateinit var club: Club

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        club = intent.extras.get(getString(R.string.EXTRA_CLUB)) as Club

        ClubDetailActivityUI(club).setContentView(this)
    }
}

