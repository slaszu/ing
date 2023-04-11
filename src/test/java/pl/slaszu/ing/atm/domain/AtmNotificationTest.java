package pl.slaszu.ing.atm.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AtmNotificationTest {

    @Test
    public void checkRequestTypePriority() {
        AtmNotification notification;

        notification = AtmNotification.fromParams("FAILURE_RESTART", 1, 2);
        assertEquals(1, notification.getRequestTypePriority());

        notification = AtmNotification.fromParams("PRIORITY", 1, 2);
        assertEquals(2, notification.getRequestTypePriority());

        notification = AtmNotification.fromParams("SIGNAL_LOW", 1, 2);
        assertEquals(3, notification.getRequestTypePriority());

        notification = AtmNotification.fromParams("STANDARD", 1, 2);
        assertEquals(4, notification.getRequestTypePriority());
    }
}
