package pl.slaszu.ing.bank.application.createAccountList;

import pl.slaszu.ing.bank.domain.Transaction;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateAccountListService {

    public List<Account> handle(List<Transaction> transactionList) {

        Map<String, Account> accounts = new HashMap<>();

        transactionList.forEach(transaction -> {

            // compute for credit transaction account number
            String creditAccount = transaction.getCreditAccount();
            accounts.computeIfAbsent(creditAccount, Account::new);
            accounts.get(creditAccount).computeTransaction(transaction);

            // compute for debit transaction account number
            String debitAccount = transaction.getDebitAccount();
            accounts.computeIfAbsent(debitAccount, Account::new);
            accounts.get(debitAccount).computeTransaction(transaction);

        });

        // sort by account number
        return accounts.values().parallelStream().sorted(Comparator.comparing(Account::getAccount)).toList();
    }

}
