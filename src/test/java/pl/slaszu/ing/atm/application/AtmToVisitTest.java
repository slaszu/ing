package pl.slaszu.ing.atm.application;

import org.junit.jupiter.api.Test;
import pl.slaszu.ing.atm.application.createAtmToVisitList.AtmToVisit;
import pl.slaszu.ing.atm.domain.AtmNotification;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AtmToVisitTest {

    @Test
    public void fromAtmNotification() {
        AtmNotification notification = AtmNotification.fromParams("STANDARD", 11, 12345);

        AtmToVisit atmToVisit = AtmToVisit.fromAtmNotification(notification);

        assertEquals(11, atmToVisit.getRegion());
        assertEquals(12345, atmToVisit.getAtmId());
    }

}
