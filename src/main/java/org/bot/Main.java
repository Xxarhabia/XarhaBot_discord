package org.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.bot.config.PropertiesConfig;
import org.bot.listeners.SlashCommandListener;

import java.util.Collections;


public class Main {
    public static void main(String[] args) {
        PropertiesConfig prop = new PropertiesConfig();

        JDA jda = JDABuilder.createLight(prop.getToken(), Collections.emptyList())
                .addEventListeners(new SlashCommandListener())
                .build();

        CommandListUpdateAction commands = jda.updateCommands();
        commands.addCommands(
            Commands.slash("work", "Se encarga de asignarle un trabajo al usuario para que gane dinero")
        );

        commands.queue();
    }
}