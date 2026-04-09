package org.bot.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesConfig {

    private Properties readProperties() {
        Properties props = new Properties();

        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props;
    }

    public String getToken(){
        Properties props = readProperties();
        return props.getProperty("application.bot.token");
    }

    /*
    Lectura de los parametros de la conexion a la base de datos
     */
    public String getUrlDatabase() {
        Properties props = readProperties();
        return props.getProperty("application.database.url");
    }

    public String getUserDatabase() {
        Properties props = readProperties();
        return props.getProperty("application.database.user");

    }

    public String getPasswordDatabase() {
        Properties props = readProperties();
        return props.getProperty("application.database.password");
    }

    /*
    Lectura de sql
     */
    public String getSqlCreateUserEconomy() {
        Properties props = readProperties();
        return props.getProperty("sql.create.user_economy");
    }

    public String updateUserWalletWork() {
        Properties props = readProperties();
        return props.getProperty("sql.update.user_wallet_work");
    }

    public String getUserWallet() {
        Properties props = readProperties();
        return props.getProperty("sql.get.user_wallet");
    }

    public String getUserBank() {
        Properties props = readProperties();
        return props.getProperty("sql.get.user_bank");
    }

    public String getUserBalance() {
        Properties props = readProperties();
        return props.getProperty("sql.get.user_balance");
    }

    public String updateUserBank() {
        Properties props = readProperties();
        return props.getProperty("sql.update.user_bank");
    }

    public String updateUserWallet() {
        Properties props = readProperties();
        return props.getProperty("sql.update.user_wallet");
    }

    public String updateDepositWithdrawal() {
        Properties props = readProperties();
        return props.getProperty("sql.update.deposit_withdrawal");
    }


}
