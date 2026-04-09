package org.bot.domain.dto;

public class BalanceDto {

    private int wallet;
    private int bank;

    public BalanceDto() {
    }

    public BalanceDto(int wallet, int bank) {
        this.wallet = wallet;
        this.bank = bank;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }
}
