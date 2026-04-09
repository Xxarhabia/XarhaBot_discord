package org.bot.commands;

import org.bot.domain.dto.BalanceDto;

public class WalletCommand {

    public String getBalanceCommand(BalanceDto balance, String user) {
        return  "El balance de " + user + " en sus cuentas es:" +
                "\n\t- Billetera: $" + balance.getWallet() + " chikidolares" +
                "\n\t- Banco: $" + balance.getBank() + " chikidolares" +
                ((balance.getBank() < 1000) ? " (Que pedazo de pobre colega)" : "");
    }

    public String depositCommand(int currentBankMoney, String user) {
        return "Tu dinero esta seguro con nosotros... Por ahora" +
                "\n\t - Usuario: " + user +
                "\n\t Cuanta de banco: " + currentBankMoney;
    }

    public String getWithdrawCommand(int currentBankMoney, int currentWalletMoney, String user) {
        return "Retiro realizado" +
                "\n\t - Usuario: " + user +
                "\n\t Cuanta de banco: " + currentBankMoney +
                "\n\t Billetera: " + currentWalletMoney;
    }
}
