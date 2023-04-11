package pl.slaszu.ing.atm.infrastructure;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import pl.slaszu.ing.atm.application.createAtmToVisitList.AtmToVisit;
import pl.slaszu.ing.atm.application.createAtmToVisitList.CreateAtmToVisitService;
import pl.slaszu.ing.atm.domain.AtmNotification;

import java.util.List;

public class AtmVertxHandler {

    public JsonArray process(JsonArray atmNotificationJsonArray) {

        // prepare input data
        List<AtmNotification> atmNotificationList = atmNotificationJsonArray.stream().parallel().map(o ->
            ((JsonObject) o).mapTo(AtmNotification.class)
        ).toList();

        // business logic
        List<AtmToVisit> viewModelList = (new CreateAtmToVisitService()).handle(atmNotificationList);

        // prepare output data
        JsonArray resultJsonArray = new JsonArray();
        viewModelList.forEach(atmToVisit ->
            resultJsonArray.add(JsonObject.mapFrom(atmToVisit))
        );

        return resultJsonArray;
    }
}
