package org.bot.listeners;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.bot.commands.WalletCommand;
import org.bot.commands.WorkCommand;
import org.bot.config.DatabaseConfig;
import org.bot.config.PropertiesConfig;
import org.bot.domain.dto.BalanceDto;
import org.bot.domain.models.UserEconomyModel;
import org.bot.domain.repositories.UserEconomyRepository;
import org.jetbrains.annotations.NotNull;

public class SlashCommandListener extends ListenerAdapter {

    DatabaseConfig db = new DatabaseConfig();
    PropertiesConfig props = new PropertiesConfig();
    UserEconomyRepository userEconomyRepository = new UserEconomyRepository(db, props);

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        switch (event.getName()) {
            case "work" -> {
                UserEconomyModel userEconomyModel = new UserEconomyModel();
                WorkCommand workCommand = new WorkCommand();
                String user = event.getUser().getAsMention();
                String discordId = event.getUser().getId();
                userEconomyModel.setDiscordId(discordId);
                userEconomyRepository.createUserEconomyQuery(discordId);
                int currentWalletMoney = userEconomyRepository.getUserWalletQuery(discordId);

                String workText = workCommand.getWorkCommand(user, userEconomyModel, currentWalletMoney);

                userEconomyRepository.updateWalletWorkQuery(userEconomyModel);
                event.reply(workText).queue();
            }
            case "balance" -> {
                String discordId = event.getUser().getId();
                String user = event.getUser().getAsMention();
                WalletCommand walletCommand = new WalletCommand();
                BalanceDto balance = userEconomyRepository.getUserBalanceQuery(discordId);
                String balanceText = walletCommand.getBalanceCommand(balance, user);
                event.reply(balanceText).queue();
            }
            case "deposit" -> {
                int amount = event.getOption("monto").getAsInt();
                if (amount > 0) {
                    String user = event.getUser().getAsMention();
                    String discordId = event.getUser().getId();
                    WalletCommand walletCommand = new WalletCommand();
                    int currentBankMoney = userEconomyRepository.getUserBankQuery(discordId);
                    int currentWalletMoney = userEconomyRepository.getUserWalletQuery(discordId);
                    int newBankMoney = currentBankMoney + amount;
                    int newWalletMoney = currentWalletMoney - amount;
                    userEconomyRepository.updateUserBankQuery(newBankMoney, discordId);
                    userEconomyRepository.updateUserWalletQuery(newWalletMoney, discordId);
                    String depositText = walletCommand.depositCommand(newBankMoney, user);
                    event.reply(depositText).queue();
                } else {
                    event.reply("El monto es insuficiente, pedazo de pobre").queue();
                }
            }
            case "withdrawal" -> {

            }
            default -> {
                event.reply("El comando ingresado no existe").queue();
            }
        }
    }
}
