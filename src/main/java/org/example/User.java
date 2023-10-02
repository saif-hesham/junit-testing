package org.example;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class User {
    private final String fullName;
    private final String email;
    private final String password;
    private final long id;
    private List<Account> accountList;


    public User(String fullName, String email, String password, long id) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public User(String fullName, String email, String password, long id, List<Account> accountList) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.id = id;
        this.accountList = accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public void addAccount(Account acc) {
        this.accountList.add(acc);
    }

    @Override
    public String toString() {
        return "User{" +
                "accountList=" + accountList +
                '}';
    }



}
