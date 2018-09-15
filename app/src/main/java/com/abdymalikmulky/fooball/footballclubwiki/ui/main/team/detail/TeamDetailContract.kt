package com.abdymalikmulky.fooball.footballclubwiki.ui.main.team.detail

import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.polri.sipp.app.ui.BasePresenter
import com.polri.sipp.app.ui.BaseView


interface TeamDetailContract {

    interface View : BaseView<Presenter> {
        fun showLoading()
        fun hideLoading()

        fun showTeam(team: Team)

        fun isTeamFavorit(isFavorite: Boolean)

    }
    interface Presenter : BasePresenter {
        fun loadTeam(teamId: String)
        fun isTeamHasFavorite(teamId: String)
    }

}