package com.abdymalikmulky.fooball.footballclubwiki.ui.club

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.abdymalikmulky.fooball.footballclubwiki.data.Club
import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton
import java.io.Serializable

class ClubListActivity : AppCompatActivity() {

    private var clubs: MutableList<Club> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        initListLayout()
    }
    private fun initListLayout() {
        rv_club_list.layoutManager = LinearLayoutManager(this)
        rv_club_list.adapter = ClubRVAdapter(this, clubs) {
            val detailIntent = Intent(this, ClubDetailActivity::class.java)
            detailIntent.putExtra(getString(R.string.EXTRA_CLUB), it as Serializable)
            startActivity(detailIntent)

            startActivity(intentFor<ClubDetailActivity>(getString(R.string.EXTRA_CLUB) to it))
        }
    }

    private fun initData(){
        val name = resources.getStringArray(R.array.club_name)
        val desc = resources.getStringArray(R.array.club_desc)
        val image = resources.obtainTypedArray(R.array.club_image)
        clubs.clear()
        for (i in name.indices) {
            clubs.add(Club(name[i], desc[i], image.getResourceId(i, 0)))
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
