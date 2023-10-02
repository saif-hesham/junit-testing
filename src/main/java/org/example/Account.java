package org.example;

import lombok.Getter;

public class Account {
    private final long userId;
    private final long accountId;
    private final AccountType accountType;
    @Getter
    private int balance;
    public Account(long userId, long accountId, AccountType accountType, int balance) {
        this.userId = userId;
        this.accountId = accountId;
        this.accountType = accountType;
        this.balance = balance;
    }
    public Account(long userId, long accountId, AccountType accountType) {
        this.userId = userId;
        this.accountId = accountId;
        this.accountType = accountType;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void deposite(int balance) {
        this.balance += balance;
    }

    public void withdraw(int balance) {
        this.balance -= balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userId=" + userId +
                ", accountId=" + accountId +
                ", accountType=" + accountType +
                '}';
    }
}
