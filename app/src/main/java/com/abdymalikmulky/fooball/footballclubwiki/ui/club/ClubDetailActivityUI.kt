package com.abdymalikmulky.fooball.footballclubwiki.ui.club

import com.abdymalikmulky.fooball.footballclubwiki.data.Club
import org.jetbrains.anko.*

class ClubDetailActivityUI(club: Club) : AnkoComponent<ClubDetailActivity> {

    internal var club: Club

    init {
        this.club = club
    }

    override fun createView(ui: AnkoContext<ClubDetailActivity>) = with(ui) {
        toast(club.clubName!!)
        relativeLayout {
            imageView(club.clubLogo) {
                id = Ids.iv_club_logo
            }.lparams(width = dip(150), height = dip(150)) {
                alignParentTop()
                centerHorizontally()
            }
            textView(club.clubName) {
                id = Ids.tv_club_title
                textSize = 28f
            }.lparams(width = wrapContent, height = wrapContent) {
                below(Ids.iv_club_logo)
                centerHorizontally()
                margin = dip(10)
            }
            textView(club.clubDescription) {
                id = Ids.tv_club_desc
                textSize = 18f
            }.lparams(width = wrapContent, height = wrapContent) {
                below(Ids.tv_club_title)
                centerHorizontally()
                margin = dip(10)
            }
        }
    }

}

private object Ids {
    val iv_club_logo = 1
    val tv_club_desc = 2
    val tv_club_title = 3
}
