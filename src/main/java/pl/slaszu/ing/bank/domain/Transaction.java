package pl.slaszu.ing.bank.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Transaction {
    private String debitAccount;
    private String creditAccount;
    private BigDecimal amount;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Transaction(
        @JsonProperty("debitAccount") String debitAccount,
        @JsonProperty("creditAccount") String creditAccount,
        @JsonProperty("amount") BigDecimal amount
    ) {
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.amount = amount;
    }
}
