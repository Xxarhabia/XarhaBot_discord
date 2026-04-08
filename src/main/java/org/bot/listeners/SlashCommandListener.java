package org.bot.listeners;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bot.commands.WorkCommand;
import org.bot.config.DatabaseConfig;
import org.bot.config.PropertiesConfig;
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
                userEconomyRepository.createUserEconomy(discordId);
                int currentWalletMoney = userEconomyRepository.getUserWallet(discordId);

                String workText = workCommand.getWorkCommand(user, userEconomyModel, currentWalletMoney);

                userEconomyRepository.updateWalletWork(userEconomyModel);
                event.reply(workText).queue();
            }
            case "balance" -> {

            }
            case "deposit" -> {

            }
            default -> {
                event.reply("El comando ingresado no existe").queue();
            }
        }
    }
}
