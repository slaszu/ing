package pl.slaszu.ing.onlinegame.application.createClansGroupList;

import pl.slaszu.ing.onlinegame.domain.Clan;

import java.util.ArrayList;
import java.util.List;

public class ClansGroup {

    private List<Clan> clanList = new ArrayList<>();

    private int maxGroupCount;

    private int groupCount = 0;

    public ClansGroup(int maxGroupCount) {
        this.maxGroupCount = maxGroupCount;
    }

    public boolean addIfPossible(Clan clan) {

        if ((this.groupCount + clan.getNumberOfPlayers()) > this.maxGroupCount) {
            return false;
        }

        this.clanList.add(clan);

        this.groupCount += clan.getNumberOfPlayers();

        return true;
    }

    public boolean isOpen() {
        return this.groupCount < this.maxGroupCount;
    }

    public List<Clan> getClanList() {
        return clanList;
    }
}
