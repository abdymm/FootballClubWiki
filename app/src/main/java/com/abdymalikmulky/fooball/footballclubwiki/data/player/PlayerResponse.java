
package com.abdymalikmulky.fooball.footballclubwiki.data.player;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayerResponse {

    @SerializedName("player")
    @Expose
    private List<Player> player = null;

    public List<Player> getPlayer() {
        return player;
    }

    public void setPlayer(List<Player> player) {
        this.player = player;
    }

}
