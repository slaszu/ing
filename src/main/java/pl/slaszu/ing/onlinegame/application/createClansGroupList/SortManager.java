package pl.slaszu.ing.onlinegame.application.createClansGroupList;

import pl.slaszu.ing.onlinegame.domain.Clan;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortManager {
    public List<Clan> sortClansByPointsAndPlayersNumber(List<Clan> clanList) {
        return clanList.parallelStream()
            .sorted(
                Comparator
                    .comparingInt(Clan::getPoints).reversed()
                    .thenComparingInt(Clan::getNumberOfPlayers)
            ).collect(Collectors.toList());
    }
}
