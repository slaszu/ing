package pl.slaszu.ing.bank.application;

import org.junit.jupiter.api.Test;
import pl.slaszu.ing.bank.application.createAccountList.Account;
import pl.slaszu.ing.bank.domain.Transaction;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {

    @Test
    public void computeTransaction() {
        String accountNumber = "0123";
        Account account = new Account(accountNumber);

        assertEquals(accountNumber, account.getAccount());
        assertEquals(BigDecimal.valueOf(0), account.getBalance());
        assertEquals(0, account.getCreditCount());
        assertEquals(0, account.getDebitCount());

        Transaction transaction1 = new Transaction(accountNumber, "3212", new BigDecimal("250.37"));
        account.computeTransaction(transaction1);

        assertEquals(new BigDecimal("-250.37"), account.getBalance());
        assertEquals(0, account.getCreditCount());
        assertEquals(1, account.getDebitCount());

        Transaction transaction2 = new Transaction("3212", accountNumber, new BigDecimal("150.37"));
        account.computeTransaction(transaction2);

        assertEquals(new BigDecimal("-100.00"), account.getBalance());
        assertEquals(1, account.getCreditCount());
        assertEquals(1, account.getDebitCount());
    }
}
