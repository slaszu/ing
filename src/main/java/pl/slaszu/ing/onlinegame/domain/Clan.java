package pl.slaszu.ing.onlinegame.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Clan {
    private int numberOfPlayers;
    private int points;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Clan(
        @JsonProperty("numberOfPlayers") int numberOfPlayers,
        @JsonProperty("points") int points
    ) {
        this.numberOfPlayers = numberOfPlayers;
        this.points = points;
    }
}
