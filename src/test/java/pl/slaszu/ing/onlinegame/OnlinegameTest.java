package pl.slaszu.ing.onlinegame;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.slaszu.ing.onlinegame.infrastructure.OnlinegameVertxHandler;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class OnlinegameTest {

    @ParameterizedTest
    @MethodSource("readExamplesFromFiles")
    public void givenExamplesWorks(JsonObject request, JsonArray response) {

        int maxGroupCount = request.getInteger("groupCount");
        JsonArray clans = request.getJsonArray("clans");

        JsonArray result = (new OnlinegameVertxHandler()).handle(maxGroupCount, clans);

        assertEquals(response.encode(), result.encode(), "Result must be equals to given response !");
    }

    @SneakyThrows
    public static Stream<Arguments> readExamplesFromFiles() {

        ClassLoader loader = Test.class.getClassLoader();

        String request_1 = new String(Files.readAllBytes(
            Paths.get(loader.getResource("zadania/onlinegame/example_request.json").toURI())
        ));
        String response_1 = new String(Files.readAllBytes(
            Paths.get(loader.getResource("zadania/onlinegame/example_response.json").toURI())
        ));

        return Stream.of(
            arguments(new JsonObject(request_1), new JsonArray(response_1))
        );
    }
}
