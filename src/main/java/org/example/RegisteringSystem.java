package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisteringSystem {
    private Scanner scanner;


     List<User> users =
            new ArrayList<>(List.of(new User("Saif Hesham", "saif@gmail.com", "12345678", 5454,
                            new ArrayList<>(List.of(new Account(5454, 1212, AccountType.current, 15440),
                                    new Account(5454, 1313, AccountType.saving, 15550)))),
                    new User("Ahmed Anwar", "ahmed@gmail.com", "87654321", 4433, new ArrayList<>(List.of(new Account(4433, 2323, AccountType.current, 15440),
                            new Account(4433, 3434, AccountType.saving, 15550)))),
                    new User("Ziad Ahmed", "zyad@gmail", "1234567", 6632, new ArrayList<>(List.of(new Account(6632, 4545, AccountType.current, 15440),
                            new Account(6632, 5656, AccountType.saving, 15550)))),
                    new User("Rowan Ahmed", "rowan@gmail.com", "12345678", 3212, new ArrayList<>(List.of(new Account(3212, 6767, AccountType.current, 15440),
                            new Account(3212, 7878, AccountType.saving, 15550))))));

    public void initiateSystem () throws NotValidMailException {
        Scanner reader = new Scanner(System.in);
        System.out.println("Type r for register or l for login");

        String signOption = reader.nextLine();
        if(Objects.equals(signOption, "r")){
            System.out.println("Navigating to register");
            register();
        }
        else if(Objects.equals(signOption, "l")){
            logIn();
        } else {
            System.out.println("You can only enter l or r, please try again later.");
        }

    }

    public void register () throws NotValidMailException {
        Scanner reader = new Scanner(System.in);

        System.out.println("Please enter your email address");
        String email = reader.nextLine();

        //checking email validity
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()) {
            //check if the user is in our list of users
            boolean foundUser = false;
            for (User user: users) {
                if (user.getEmail().equals(email)) {
                    foundUser = true;
                    break;
                }
            }

            if (foundUser){
                System.out.println("user already exists");
                System.out.println("Type r to try again, l to login or any other character to exit");
                String registerOption = reader.nextLine();
                if (registerOption.equals("r")) {
                    register();
                } else if (registerOption.equals("l")) {
                    logIn();
                } else {
                    return;
                }

            }
            System.out.println("Please enter your fullName");
            String fullName = reader.nextLine();
            System.out.println("Please enter your password");
            String password = reader.nextLine();
            this.users.add(new User(fullName, email, password, 2322));
            System.out.println("You have registered successfully.");
        } else {
            throw new NotValidMailException("Please enter a valid email address");
        }
    }

    public void logIn() throws NotValidMailException {
        Scanner reader = new Scanner(System.in);

        System.out.println("Please enter your email address");
        String email = reader.nextLine();

        boolean foundUser = false;
        User currentUser = null;
        for (User user: users) {
            if (user.getEmail().equals(email)) {
                foundUser = true;
                currentUser = user;
                break;
            }
        }
        if (foundUser) {
            System.out.println("Type in your password");
            String password = reader.nextLine();
            if (currentUser.getPassword().equals(password)) {
                System.out.println("Logged In successfully");
                System.out.println("Type w to withdraw, d to deposit or o to open a new account");
                String accountOption = reader.nextLine();


                switch (accountOption) {
                    case "d" -> {
                        System.out.println("Type 0 to choose your current account or one to choose your savings");
                        int accountType = Integer.parseInt(reader.nextLine());
                        System.out.println("Type how much you want to add");
                        int amount = Integer.parseInt(reader.nextLine());
                        Account chosenAccount = currentUser.getAccountList().get(accountType);
                        chosenAccount.deposite(amount);
                        System.out.println("Your new balance on this account is" + chosenAccount.getBalance());

                    }
                    case "w" -> {
                        System.out.println("Type 0 to choose your current account or one to choose your savings");
                        int accountType = Integer.parseInt(reader.nextLine());
                        System.out.println("Type how much you want to withdraw");
                        int amount = Integer.parseInt(reader.nextLine());
                        Account chosenAccount = currentUser.getAccountList().get(accountType);
                        if (amount < chosenAccount.getBalance()){
                            chosenAccount.withdraw(amount);
                            System.out.println("Your new balance on this account is" + chosenAccount.getBalance());
                        } else {
                            System.out.println("Amount requested is higher than the current balance");
                            tryAgain();
                        }

                    }
                    case "o" -> {
                        System.out.println("Type c to open a current account or s for a saving account");
                        String openAccountType = reader.nextLine();
                        AccountType newAccountType = openAccountType.equals("s")? AccountType.saving: AccountType.current;
                        Account newAccount = new Account(currentUser.getId(), 1123, newAccountType);
                        currentUser.addAccount(newAccount);
                        System.out.println(currentUser.toString());
                    }
                }

            } else {
              tryAgain();
            }

        } else {
           tryAgain();
        }

    }

    public void tryAgain() throws NotValidMailException {
        Scanner reader = new Scanner(System.in);
        System.out.println("User not found, type l to try again, r to register or any other character to exit");
        String registerOption = reader.nextLine();
        if (registerOption.equals("r")) {
            register();
        } else if (registerOption.equals("l")) {
            logIn();
        }
    }
}
