package com.abdymalikmulky.fooball.footballclubwiki.ui.main.team.detail


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.match.FixtureFragment
import kotlinx.android.synthetic.main.fragment_team_overview.*

class TeamOverviewFragment : Fragment() {

    internal lateinit var team: Team

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_overview, container, false)
    }


    companion object {

        fun newInstance(team: Team): TeamOverviewFragment {
            val fragment = TeamOverviewFragment()
            val args = Bundle()
            args.putSerializable("TEAM", team)
            fragment.setArguments(args)
            return fragment
        }

    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        team = Team()
        arguments?.getSerializable(getString(R.string.KEY_TEAM))?.let {
            team = it as Team
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        team_overview.text = team.teamDescription
        team_formedby.text = "Formed from : " + team.formedYear
    }

}
