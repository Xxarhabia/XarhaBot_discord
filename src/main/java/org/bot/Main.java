package org.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.bot.config.PropertiesConfig;
import org.bot.listeners.SlashCommandListener;

import java.util.Collections;


public class Main {
    public static void main(String[] args) {
        PropertiesConfig prop = new PropertiesConfig();

        JDA jda = JDABuilder.createLight(prop.getToken(), Collections.emptyList())
                .addEventListeners(new SlashCommandListener())
                .build();

//        CommandListUpdateAction commands = jda.updateCommands();
//        commands.addCommands(
//            Commands.slash("work", "Se encarga de asignarle un trabajo al usuario para que gane dinero"),
//            Commands.slash("balance", "Muesta el balance de tu cuenta de banco y billetera"),
//            Commands.slash("deposit", "Se encarga de depositar en el banco un monto dado")
//                    .addOption(OptionType.INTEGER, "monto", "Monto a usar", true),
//            Commands.slash("withdrawal", "Se engarga de retirar un monto del banco a la billetera")
//                    .addOption(OptionType.INTEGER, "monto", "Monto a usar", true)
//        );

//        commands.queue();

        /* modo dev */
        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        OptionData actionOption = new OptionData(OptionType.STRING, "action", "Accion a realizar");

        actionOption.addChoice("kiss", "kiss");
        actionOption.addChoice("slap", "slap");
        actionOption.addChoice("sucking_dick ", "sucking_dick");
        actionOption.addChoice("sex", "sex");

        actionOption.setRequired(true);

        jda.getGuildById("796152295757709312")
            .updateCommands()
            .addCommands(
                Commands.slash("work", "Se encarga de asignarle un trabajo al usuario para que gane dinero"),
                Commands.slash("balance", "Muesta el balance de tu cuenta de banco y billetera"),
                Commands.slash("deposit", "Se encarga de depositar en el banco un monto dado")
                        .addOption(OptionType.INTEGER, "monto", "Monto a usar", true),
                Commands.slash("withdrawal", "Se engarga de retirar un monto del banco a la billetera")
                        .addOption(OptionType.INTEGER, "monto", "Monto a usar", true),
                Commands.slash("nsfw_action", "Acciones NSFW entre usuarios")
                        .addOptions(actionOption)
                        .addOption(OptionType.USER, "user", "Usuario objetivo", true)
            ).queue();

    }
}