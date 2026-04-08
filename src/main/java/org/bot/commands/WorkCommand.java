package org.bot.commands;

import org.bot.domain.models.UserEconomyModel;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class WorkCommand {

    public String getWorkCommand(String user, UserEconomyModel userEconomy, int currentMoneyWallet) {
        final double PAYMENT_PROBABILITY = 0.7;
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        userEconomy.setLastWork(timestamp);

        String[] works = {
                "Trabajo chupandolo en wposs",
                "Se conviritó en pragmaboy y ahora no deja pene sin chupar",
                "Empezó a ejercer el trabajo mas viejo del mundo",
                "Prefirió pedir plata en la casa, que pedazo de parasito"
        };

        String[] paymentAnswers = {
                ". Pero prefirieron echarlo sin darle una puta mierda",
                ". Pero le robaron todo el dinero en el prostibulo",
                ". Le pegaron un tiro pero revivira en la siguiente ronda (lastimosamente)"
        };

        int randomWork = (int) (Math.random() * works.length);
        int randomPayment = (int) (Math.random() * paymentAnswers.length);

        if (Math.random() < PAYMENT_PROBABILITY) {
            int money = (int) (Math.random() * 100) + 50;
            userEconomy.setWallet(money + currentMoneyWallet);
            return user + " " + works[randomWork] + ". Ganó $" + money + " chikydolares";
        } else {
            userEconomy.setWallet(currentMoneyWallet);
            return user + " " + works[randomWork] + paymentAnswers[randomPayment];
        }
    }
}
