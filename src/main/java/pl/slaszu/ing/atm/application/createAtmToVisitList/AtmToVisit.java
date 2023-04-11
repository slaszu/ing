package pl.slaszu.ing.atm.application.createAtmToVisitList;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.slaszu.ing.atm.domain.AtmNotification;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class AtmToVisit {
    private int region;
    private int atmId;

    public static AtmToVisit fromAtmNotification(AtmNotification atmNotification) {
        return new AtmToVisit(atmNotification.getRegion(), atmNotification.getAtmId());
    }
}
