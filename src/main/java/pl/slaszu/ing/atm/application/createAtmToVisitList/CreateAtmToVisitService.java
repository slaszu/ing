package pl.slaszu.ing.atm.application.createAtmToVisitList;

import pl.slaszu.ing.atm.domain.AtmNotification;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

public class CreateAtmToVisitService {

    private final SortManager sortManager = new SortManager();

    public List<AtmToVisit> handle(List<AtmNotification> atmNotificationList) {
        List<AtmNotification> sortedByVisitOrder = this.sortManager.sortAtmsByRegionAndRequestPriority(atmNotificationList);
        return this.toAtmToVisitList(sortedByVisitOrder);
    }

    private List<AtmToVisit> toAtmToVisitList(List<AtmNotification> atmNotificationList) {

        // transform atmNotification to AtmToVisit
        List<AtmToVisit> atmToVisitList = atmNotificationList.parallelStream()
            .map(AtmToVisit::fromAtmNotification)
            .toList();

        // remove duplicates
        return new ArrayList<>(new LinkedHashSet<>(atmToVisitList));
    }
}
