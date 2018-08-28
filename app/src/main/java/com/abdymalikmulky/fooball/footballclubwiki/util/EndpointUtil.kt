package id.or.ypt.agendaapp.util

/**
 * Created by abdymalikmulky on 11/3/17.
 */
class EndpointUtil {
    companion object {
        const val ENDPOINT_PATH = "api/v1/json/1/"

        //LEAGUE
        const val GET_LEAGUES = ENDPOINT_PATH + "all_leagues.php"

        //TEAM
        const val GET_TEAMS = ENDPOINT_PATH + "lookup_all_teams.php"

        //PLAYER
        const val GET_PLAYERS = ENDPOINT_PATH + "lookup_all_players.php"

        //EVENT or SCHEDULE
        const val GET_EVENT_PAST = ENDPOINT_PATH + "eventspastleague.php"
        const val GET_EVENT_NEXT = ENDPOINT_PATH + "eventsnextleague.php"

        //Endpoint Key
        const val KEY_ID = "id"


    }
}