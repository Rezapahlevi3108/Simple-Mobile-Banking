package com.example.simplemobilebanking;

public class HelperClass {

    String name,email,username,password;
    float balance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public HelperClass(String name, String email, String username, String password, float balance) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public HelperClass() {
    }
}
