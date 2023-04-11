package pl.slaszu.ing.atm.application.createAtmToVisitList;

import pl.slaszu.ing.atm.domain.AtmNotification;

import java.util.Comparator;
import java.util.List;

public class SortManager {
    public List<AtmNotification> sortAtmsByRegionAndRequestPriority(List<AtmNotification> atmNotificationList) {

        return atmNotificationList.parallelStream()
            .sorted(
                Comparator.comparingInt(AtmNotification::getRegion)
                    .thenComparingInt(AtmNotification::getRequestTypePriority)
            ).toList();
    }
}
