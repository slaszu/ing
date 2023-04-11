package pl.slaszu.ing.onlinegame.infrastructure;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import pl.slaszu.ing.onlinegame.application.createClansGroupList.ClansGroup;
import pl.slaszu.ing.onlinegame.application.createClansGroupList.CreateClansGroupListService;
import pl.slaszu.ing.onlinegame.domain.Clan;

import java.util.List;

public class OnlinegameVertxHandler {

    public JsonArray handle(int maxGroupCount, JsonArray clanJsonArray) {

        // prepare domain data
        List<Clan> clanList = clanJsonArray.stream().parallel().map(o ->
            ((JsonObject) o).mapTo(Clan.class)
        ).toList();

        // business logic
        List<ClansGroup> clansGroupList = (new CreateClansGroupListService()).handle(maxGroupCount, clanList);

        // prepare output data
        JsonArray resultJsonArray = new JsonArray();
        clansGroupList.forEach(clansGroup -> {
            List<JsonObject> clanJsonList = clansGroup.getClanList().stream().map(JsonObject::mapFrom).toList();
            resultJsonArray.add(clanJsonList);
        });

        return resultJsonArray;
    }
}
