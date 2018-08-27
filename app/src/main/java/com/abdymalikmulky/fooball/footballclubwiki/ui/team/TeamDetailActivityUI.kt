package com.abdymalikmulky.fooball.footballclubwiki.ui.team

import com.abdymalikmulky.fooball.footballclubwiki.data.Team
import org.jetbrains.anko.*

class ClubDetailActivityUI(team: Team) : AnkoComponent<TeamDetailActivity> {

    internal var team: Team

    init {
        this.team = team
    }

    override fun createView(ui: AnkoContext<TeamDetailActivity>) = with(ui) {
        toast(team.teamName!!)
        relativeLayout {
            imageView(team.teamLogo) {
                id = Ids.iv_team_logo
            }.lparams(width = dip(150), height = dip(150)) {
                alignParentTop()
                centerHorizontally()
            }
            textView(team.teamName) {
                id = Ids.tv_team_title
                textSize = 28f
            }.lparams(width = wrapContent, height = wrapContent) {
                below(Ids.iv_team_logo)
                centerHorizontally()
                margin = dip(10)
            }
            textView(team.teamDescription) {
                id = Ids.tv_team_desc
                textSize = 18f
            }.lparams(width = wrapContent, height = wrapContent) {
                below(Ids.tv_team_title)
                centerHorizontally()
                margin = dip(10)
            }
        }
    }

}

private object Ids {
    val iv_team_logo = 1
    val tv_team_desc = 2
    val tv_team_title = 3
}
