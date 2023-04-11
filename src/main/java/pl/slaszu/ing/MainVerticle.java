package pl.slaszu.ing;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.validation.RequestParameters;
import io.vertx.ext.web.validation.ValidationHandler;
import pl.slaszu.ing.atm.infrastructure.AtmVertxHandler;
import pl.slaszu.ing.bank.infrastructure.BankVertxHandler;
import pl.slaszu.ing.onlinegame.infrastructure.OnlinegameVertxHandler;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {

        Router mainRouter = this.getMainRouter();

        CompositeFuture.all(
                this.getAtmRouter(),
                this.getOnlinegameRouter(),
                this.getBankRouter()
            )
            .onSuccess(compositeFuture -> {
                RouterBuilder atmRouterBuilder = compositeFuture.resultAt(0);
                RouterBuilder onlinegameRouterBuilder = compositeFuture.resultAt(1);
                RouterBuilder bankRouterBuilder = compositeFuture.resultAt(2);

                mainRouter.route("/*").subRouter(atmRouterBuilder.createRouter());
                mainRouter.route("/*").subRouter(onlinegameRouterBuilder.createRouter());
                mainRouter.route("/*").subRouter(bankRouterBuilder.createRouter());

                // Create the HTTP server
                vertx.createHttpServer()
                    .requestHandler(mainRouter)
                    .listen(Integer.parseInt(System.getProperty("port", "8080")))
                    .onSuccess(server ->
                        System.out.println(
                            "HTTP server started on port " + server.actualPort()
                        )
                    );
            })
            .onFailure(startPromise::fail);
    }

    private Router getMainRouter() {
        Router mainRouter = Router.router(this.vertx);

        mainRouter.route("/*").handler(LoggerHandler.create(LoggerFormat.SHORT));

        // Add error handlers
        mainRouter.errorHandler(404, routingContext -> {
            JsonObject errorObject = new JsonObject()
                .put("code", 404)
                .put("message",
                    (routingContext.failure() != null) ?
                        routingContext.failure().getMessage() :
                        "Not Found"
                );
            routingContext
                .response()
                .setStatusCode(404)
                .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .end(errorObject.encode());
        });
        mainRouter.errorHandler(400, routingContext -> {
            JsonObject errorObject = new JsonObject()
                .put("code", 400)
                .put("message",
                    (routingContext.failure() != null) ?
                        routingContext.failure().getMessage() :
                        "Validation Exception"
                );
            routingContext
                .response()
                .setStatusCode(400)
                .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .end(errorObject.encode());
        });
        mainRouter.errorHandler(500, routingContext -> {
            JsonObject errorObject = new JsonObject()
                .put("code", 500)
                .put("message",
                    (routingContext.failure() != null) ?
                        routingContext.failure().getMessage() :
                        "Internal Server Error"
                );
            routingContext
                .response()
                .setStatusCode(500)
                .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .end(errorObject.encode());
        });

        return mainRouter;
    }

    private Future<RouterBuilder> getAtmRouter() {
        return RouterBuilder.create(this.vertx, "zadania/atmservice/atmservice.json")
            .onSuccess(routerBuilder -> {

                routerBuilder.operation("calculate").handler(routingContext -> {

                    RequestParameters params = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);
                    JsonArray inputArray = params.body().getJsonArray();

                    // application use case
                    JsonArray outputArray = (new AtmVertxHandler()).process(inputArray);

                    routingContext
                        .response()
                        .setStatusCode(200)
                        .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                        .end(outputArray.encode());
                });
            });
    }

    private Future<RouterBuilder> getOnlinegameRouter() {
        return RouterBuilder.create(this.vertx, "zadania/onlinegame/onlinegame.json")
            .onSuccess(routerBuilder -> {
                routerBuilder.operation("calculate").handler(routingContext -> {

                    RequestParameters params = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);
                    JsonObject jsonObject = params.body().getJsonObject();

                    int maxGroupCount = jsonObject.getInteger("groupCount");
                    JsonArray clans = jsonObject.getJsonArray("clans");

                    JsonArray outputArray = (new OnlinegameVertxHandler()).handle(maxGroupCount, clans);

                    routingContext
                        .response()
                        .setStatusCode(200)
                        .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                        .end(outputArray.encode());
                });

            });
    }

    private Future<RouterBuilder> getBankRouter() {
        return RouterBuilder.create(this.vertx, "zadania/transactions/transactions.json")
            .onSuccess(routerBuilder -> {

                routerBuilder.operation("report").handler(routingContext -> {

                    RequestParameters params = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);
                    JsonArray inputArray = params.body().getJsonArray();

                    // application use case

                    JsonArray outputArray = (new BankVertxHandler()).handle(inputArray);

                    routingContext
                        .response()
                        .setStatusCode(200)
                        .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                        .end(outputArray.encode());
                });
            });
    }
}
