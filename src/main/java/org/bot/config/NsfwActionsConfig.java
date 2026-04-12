package org.bot.config;

public class NsfwActionsConfig {

    public static String nsfwActions(String action) {
        switch (action) {
            case "kiss" -> {
                return " le esta comiendo la boca a ";
            }
            case "slap" -> {
                return " esta recibiendo lo suyo por ";
            }
            case "sucking_dick" -> {
                return " le esta haciendo un trabajito a ";
            }
            case "sex" -> {
                return " esta siendo detonado por ";
            }
        }
        return "";
    }
}
