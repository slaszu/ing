package pl.slaszu.ing.atm.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

import static java.util.Map.entry;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AtmNotification {

    private static final Map<String, Integer> requestTypePriorityMap = Map.ofEntries(
        entry("FAILURE_RESTART", 1),
        entry("PRIORITY", 2),
        entry("SIGNAL_LOW", 3),
        entry("STANDARD", 4)
    );


    private int region;
    private int requestTypePriority;
    private int atmId;

    @JsonCreator
    public static AtmNotification fromParams(
        @JsonProperty("requestType") String requestType,
        @JsonProperty("region") int region,
        @JsonProperty("atmId") int atmId
    ) {

        int requestTypePriority = requestTypePriorityMap.get(requestType);

        return new AtmNotification(region, requestTypePriority, atmId);
    }
}
