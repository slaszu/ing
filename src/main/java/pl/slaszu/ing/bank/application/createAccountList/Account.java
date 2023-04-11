package pl.slaszu.ing.bank.application.createAccountList;

import lombok.Getter;
import pl.slaszu.ing.bank.domain.Transaction;

import java.math.BigDecimal;

@Getter
public class Account {
    private String account;
    private int debitCount = 0;
    private int creditCount = 0;
    private BigDecimal balance = new BigDecimal("0");

    public Account(String account) {
        this.account = account;
    }

    public boolean computeTransaction(Transaction transaction) {
        if (transaction.getCreditAccount().equals(this.account)) {
            this.creditCount++;
            this.balance = this.balance.add(transaction.getAmount());
            return true;
        }
        if (transaction.getDebitAccount().equals(this.account)) {
            this.debitCount++;
            this.balance = this.balance.subtract(transaction.getAmount());
            return true;
        }
        return false;
    }
}
