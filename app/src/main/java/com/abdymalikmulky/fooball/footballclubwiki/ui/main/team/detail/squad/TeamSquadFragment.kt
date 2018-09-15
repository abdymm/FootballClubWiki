package com.abdymalikmulky.fooball.footballclubwiki.ui.main.team.detail.squad

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballDataSource
import com.abdymalikmulky.fooball.footballclubwiki.data.FootballRepo
import com.abdymalikmulky.fooball.footballclubwiki.data.player.Player
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.player.PlayerActivity
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.team.detail.TeamDetailActivity
import kotlinx.android.synthetic.main.fragment_team_squad.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast

class TeamSquadFragment : Fragment() {

    internal lateinit var team: Team

    internal lateinit var footballRepo: FootballRepo

    internal lateinit var teamSquadAdapter: TeamSquadAdapter
    private var players: MutableList<Player> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_squad, container, false)
    }

    companion object {

        fun newInstance(team: Team): TeamSquadFragment {
            val fragment = TeamSquadFragment()
            val args = Bundle()
            args.putSerializable("TEAM", team)
            fragment.setArguments(args)
            return fragment
        }

    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        footballRepo = FootballRepo(context!!)
        team = Team()
        arguments?.getSerializable(getString(R.string.KEY_TEAM))?.let {
            team = it as Team
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        players = mutableListOf()
        teamSquadAdapter = TeamSquadAdapter(players) {
            Log.d("PlayerData ", it.name)
            startActivity(intentFor<PlayerActivity>(getString(R.string.EXTRA_PLAYER) to it))
        }
        player_list.adapter = teamSquadAdapter
        player_list.layoutManager = LinearLayoutManager(activity?.applicationContext)

        player_list.addItemDecoration(DividerItemDecoration(player_list.getContext(), DividerItemDecoration.VERTICAL))

        footballRepo.loadPlayersByTeam(team.teamId, object : FootballDataSource.LoadPlayersCallback{
            override fun onLoaded(players: List<Player>) {
                teamSquadAdapter.refresh(players)
            }

            override fun onFailed(errorMsg: String) {
                toast(errorMsg)
            }

        })
    }
}
