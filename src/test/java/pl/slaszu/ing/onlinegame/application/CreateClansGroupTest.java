package pl.slaszu.ing.onlinegame.application;

import org.junit.jupiter.api.Test;
import pl.slaszu.ing.onlinegame.application.createClansGroupList.ClansGroup;
import pl.slaszu.ing.onlinegame.domain.Clan;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateClansGroupTest {

    @Test
    public void clansGroup() {
        ClansGroup clansGroup = new ClansGroup(4);

        Clan clan1 = new Clan(3, 40);
        assertTrue(clansGroup.addIfPossible(clan1));
        assertTrue(clansGroup.isOpen());

        Clan clan2 = new Clan(1, 56);
        assertTrue(clansGroup.addIfPossible(clan2));
        assertFalse(clansGroup.isOpen());

        Clan clan3 = new Clan(3, 16);
        assertFalse(clansGroup.addIfPossible(clan3));

    }

}
