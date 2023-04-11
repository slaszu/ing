package pl.slaszu.ing.onlinegame.application.createClansGroupList;

import io.vertx.core.json.JsonObject;
import pl.slaszu.ing.onlinegame.domain.Clan;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class CreateClansGroupListService {

    private final SortManager sortManager = new SortManager();

    public List<ClansGroup> handle(int maxGroupCount, List<Clan> clanList) {

        List<Clan> sortedClanList = this.sortManager.sortClansByPointsAndPlayersNumber(clanList);

        List<ClansGroup> clansGroupList = new ArrayList<>();

        // for each clan from input
        while (sortedClanList.size() > 0) {

            // get first clan and remove it from list
            Clan clanToProcess = sortedClanList.remove(0);

            // flag
            AtomicBoolean createNewGroupFlag = new AtomicBoolean(true);

            // try to add clan to some clansGroup
            for (ClansGroup clansGroup : clansGroupList) {
                if (!clansGroup.isOpen()) continue;

                // add if possible to first group and break loop
                if (clansGroup.addIfPossible(clanToProcess)) {
                    createNewGroupFlag.set(false);
                    break;
                }
            }


            // clan was not fit to any clansGroup
            // create new clansGroup and add clan to it
            if (createNewGroupFlag.get()) {
                ClansGroup newClansGroup = new ClansGroup(maxGroupCount);
                if (newClansGroup.addIfPossible(clanToProcess)) {
                    clansGroupList.add(newClansGroup);
                } else {
                    throw new RuntimeException(
                        "Clan \"%s\" has more players then max group count %d".formatted(
                            JsonObject.mapFrom(clanToProcess).encode(),
                            maxGroupCount
                        )
                    );
                }
            }
        }

        return clansGroupList;
    }

    private List<Clan> sortClansByPointsAndPlayersNumber(List<Clan> clanList) {
        return clanList.parallelStream()
            .sorted(
                Comparator
                    .comparingInt(Clan::getPoints).reversed()
                    .thenComparingInt(Clan::getNumberOfPlayers)
            ).collect(Collectors.toList());
    }
}
