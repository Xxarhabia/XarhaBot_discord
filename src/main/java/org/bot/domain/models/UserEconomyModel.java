package org.bot.domain.models;

import java.sql.Timestamp;

public class UserEconomyModel {
    private Long id;
    private String discordId;
    private int wallet;
    private int bank;
    private Timestamp lastWork;

    public UserEconomyModel() {
    }

    public UserEconomyModel(Long id, String discordId, int wallet, int bank, Timestamp lastWork) {
        this.id = id;
        this.discordId = discordId;
        this.wallet = wallet;
        this.bank = bank;
        this.lastWork = lastWork;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiscordId() {
        return discordId;
    }

    public void setDiscordId(String discordId) {
        this.discordId = discordId;
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

    public Timestamp getLastWork() {
        return lastWork;
    }

    public void setLastWork(Timestamp lastWork) {
        this.lastWork = lastWork;
    }

    @Override
    public String toString() {
        return "UserEconomyModel{" +
                "id=" + id +
                ", discordId='" + discordId + '\'' +
                ", wallet=" + wallet +
                ", bank=" + bank +
                ", lastWork=" + lastWork +
                '}';
    }
}
