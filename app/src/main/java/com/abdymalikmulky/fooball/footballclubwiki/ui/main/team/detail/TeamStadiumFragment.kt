package com.abdymalikmulky.fooball.footballclubwiki.ui.main.team.detail

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team_detail.*
import kotlinx.android.synthetic.main.fragment_team_stadium.*

class TeamStadiumFragment : Fragment() {

    internal lateinit var team: Team


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_stadium, container, false)
    }


    companion object {

        fun newInstance(team: Team): TeamStadiumFragment {
            val fragment = TeamStadiumFragment()
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

        team_stadium_title.text = team.stadiumName
        team_stadium_location.text = "Location : " + team.stadiumLocation
        team_stadium_capacity.text = "Capacity : " + team.stadiumCapacity
        team_stadium_description.text = team.stadiumDesc
        Picasso.get().load(team.stadiumThumb).into(team_stadium_photo)
    }

}
