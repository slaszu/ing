package pl.slaszu.ing.bank.infrastructure;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import pl.slaszu.ing.bank.application.createAccountList.Account;
import pl.slaszu.ing.bank.application.createAccountList.CreateAccountListService;
import pl.slaszu.ing.bank.domain.Transaction;

import java.util.List;

public class BankVertxHandler {

    public JsonArray handle(JsonArray transactionJsonArray) {

        // prepare input
        List<Transaction> transactionList = transactionJsonArray.stream().parallel().map(o ->
            ((JsonObject) o).mapTo(Transaction.class)
        ).toList();

        // business logic
        List<Account> accountList = (new CreateAccountListService()).handle(transactionList);

        // prepare output
        JsonArray resultJsonArray = new JsonArray();
        accountList.forEach(account ->
            resultJsonArray.add(JsonObject.mapFrom(account))
        );

        return resultJsonArray;
    }
}
