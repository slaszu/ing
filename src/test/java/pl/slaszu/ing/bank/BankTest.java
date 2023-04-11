package pl.slaszu.ing.bank;

import io.vertx.core.json.JsonArray;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.slaszu.ing.bank.infrastructure.BankVertxHandler;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class BankTest {

    @ParameterizedTest
    @MethodSource("readExamplesFromFiles")
    public void givenExamplesWorks(JsonArray request, JsonArray response) {


        JsonArray result = (new BankVertxHandler()).handle(request);

        assertEquals(response.encode(), result.encode(), "Result must be equals to given response !");
    }

    @SneakyThrows
    public static Stream<Arguments> readExamplesFromFiles() {

        ClassLoader loader = Test.class.getClassLoader();

        String request_1 = new String(Files.readAllBytes(
            Paths.get(loader.getResource("zadania/transactions/example_request.json").toURI())
        ));
        String response_1 = new String(Files.readAllBytes(
            Paths.get(loader.getResource("zadania/transactions/example_response.json").toURI())
        ));

        return Stream.of(
            arguments(new JsonArray(request_1), new JsonArray(response_1))
        );
    }
}
