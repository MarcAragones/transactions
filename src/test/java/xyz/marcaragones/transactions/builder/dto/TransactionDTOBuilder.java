package xyz.marcaragones.transactions.builder.dto;

import xyz.marcaragones.transactions.dto.TransactionDTO;

public class TransactionDTOBuilder {

    private double amount;
    private long timestamp;

    private TransactionDTOBuilder() {}

    public static TransactionDTOBuilder aTransactionDTOBuilder() {
        return new TransactionDTOBuilder();
    }

    public TransactionDTOBuilder withAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public TransactionDTOBuilder withTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public TransactionDTO build() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(amount);
        transactionDTO.setTimestamp(timestamp);
        return transactionDTO;
    }
}
