package pl.slaszu.ing.onlinegame.application;

import org.junit.jupiter.api.Test;
import pl.slaszu.ing.onlinegame.application.createClansGroupList.SortManager;
import pl.slaszu.ing.onlinegame.domain.Clan;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortManagerTest {

    @Test
    public void sortInCorrectOrderAndMutable() {
        Clan c1 = new Clan(3, 50);
        Clan c2 = new Clan(4, 50);
        Clan c3 = new Clan(3, 40);

        List<Clan> clanList = List.of(c3, c2, c1);
        List<Clan> clanListSorted = (new SortManager()).sortClansByPointsAndPlayersNumber(clanList);

        // sort order check
        assertEquals(c1, clanListSorted.get(0));
        assertEquals(c2, clanListSorted.get(1));
        assertEquals(c3, clanListSorted.get(2));
        assertEquals(3, clanListSorted.size());

        // list must be mutable
        assertEquals(c1, clanListSorted.remove(0));
        assertEquals(c2, clanListSorted.get(0));
        assertEquals(c3, clanListSorted.get(1));
        assertEquals(2, clanListSorted.size());

    }

}
