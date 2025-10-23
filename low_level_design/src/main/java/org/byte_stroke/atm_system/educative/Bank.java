package org.byte_stroke.atm_system.educative;

import java.util.StringJoiner;

public class Bank {
    private String name;
    private String bankCode;
    public Bank() {
    }

    public Bank(String name, String bankCode) {
        this.name = name;
        this.bankCode = bankCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Bank.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("bankCode='" + bankCode + "'")
                .toString();
    }
}
