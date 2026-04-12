package org.bot.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.bot.commands.WalletCommand;
import org.bot.commands.WorkCommand;
import org.bot.config.DatabaseConfig;
import org.bot.config.NsfwActionsConfig;
import org.bot.config.PropertiesConfig;
import org.bot.domain.dto.BalanceDto;
import org.bot.domain.models.UserEconomyModel;
import org.bot.domain.repositories.UserEconomyRepository;
import org.bot.services.DanbooruService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

public class SlashCommandListener extends ListenerAdapter {

    DatabaseConfig db = new DatabaseConfig();
    PropertiesConfig props = new PropertiesConfig();
    UserEconomyRepository userEconomyRepository = new UserEconomyRepository(db, props);
    DanbooruService danbooruService = new DanbooruService();

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
                    event.reply("El monto es insuficiente, pedazo de pobre " +
                            "\nMonto proporcionado: " + amount).queue();
                }
            }
            case "withdrawal" -> {
                int amount = event.getOption("monto").getAsInt();
                String discordId = event.getUser().getId();
                int currentBankMoney = userEconomyRepository.getUserBankQuery(discordId);
                if (amount <= 0) {
                    event.reply("La cantidad solicitada a retirar es insuficiente").queue();
                } else if (amount < currentBankMoney) {
                    String user = event.getUser().getAsMention();
                    WalletCommand walletCommand = new WalletCommand();
                    int currentWalletMoney = userEconomyRepository.getUserWalletQuery(discordId);
                    int newBankMoney = currentBankMoney - amount;
                    int newWalletMoney = currentWalletMoney + amount;
                    userEconomyRepository.updateUserBankQuery(newBankMoney, discordId);
                    userEconomyRepository.updateUserWalletQuery(newWalletMoney, discordId);
                    String depositText = walletCommand.getWithdrawCommand(newBankMoney, newWalletMoney, user);
                    event.reply(depositText).queue();
                } else {
                    event.reply("El monto supera la miserable cantidad de tu cuenta" +
                            "\nMonto proporcionado: " + amount +
                            "\nCuanta de banco: " + currentBankMoney).queue();
                }
            }
            case "nsfw_action" -> {
                if (!event.isFromAttachedGuild() || !event.getChannel().asTextChannel().isNSFW()) {
                    event.reply("Este comando solo funciona en canales NSFW. Pajiso").queue();
                    return;
                }
                String action = event.getOption("action").getAsString();
                var targetUser = event.getOption("user").getAsUser();

                String author = event.getUser().getAsMention();
                String target = targetUser.getAsMention();

                if (event.getUser().equals(targetUser)) {
                    event.reply("Andaaaaaaa, cacorrooooo").queue();
                    return;
                }

                Map<String, String> actionTags = Map.of(
                        "kiss", "kiss",
                        "slap", "slap",
                        "sucking_dick ", "sucking_dick",
                        "sex", "sex"
                );

                String tag = actionTags.getOrDefault(action, action);

                String imageUrl;
                try {
                    imageUrl = danbooruService.getRandomImage(tag, true, true); //esto cambio
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                System.out.println(imageUrl);
                if (imageUrl == null) {
                    event.reply("No hay polnito lico causa que abusoommmm.").queue();
                    return;
                }

                String response = target + NsfwActionsConfig.nsfwActions(action) + author;

                event.replyEmbeds(
                        new EmbedBuilder()
                                .setDescription(response)
                                .setImage(imageUrl)
                                .build()
                ).queue();
            }
            default -> {
                event.reply("El comando ingresado no existe").queue();
            }
        }
    }
}
