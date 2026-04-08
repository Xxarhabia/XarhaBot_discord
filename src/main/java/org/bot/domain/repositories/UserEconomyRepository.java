package org.bot.domain.repositories;

import org.bot.config.DatabaseConfig;
import org.bot.config.PropertiesConfig;
import org.bot.domain.models.UserEconomyModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserEconomyRepository {

    private final DatabaseConfig db;
    private final PropertiesConfig props;

    public UserEconomyRepository(DatabaseConfig db, PropertiesConfig props) {
        this.db = db;
        this.props = props;
    }

    public void createUserEconomy(String discordId) {

        try (Connection conn = db.connectDatabase();
             PreparedStatement stmt = conn.prepareStatement(props.getSqlCreateUserEconomy())) {

            stmt.setString(1, discordId);
            stmt.executeUpdate();

            System.out.println(props.getSqlCreateUserEconomy().replace("?", discordId));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateWalletWork(UserEconomyModel userEconomyModel) {
        try (Connection conn = db.connectDatabase();
             PreparedStatement stmt = conn.prepareStatement(props.getUpdateUserWalletWork())) {

            stmt.setInt(1, userEconomyModel.getWallet());
            stmt.setTimestamp(2, userEconomyModel.getLastWork());
            stmt.setString(3, userEconomyModel.getDiscordId());
            stmt.executeUpdate();

            String sqlExecuted = props.getUpdateUserWalletWork().replaceFirst("\\?", String.valueOf(userEconomyModel.getWallet()));
            sqlExecuted = sqlExecuted.replaceFirst("\\?", String.valueOf(userEconomyModel.getLastWork()));
            sqlExecuted = sqlExecuted.replaceFirst("\\?", userEconomyModel.getDiscordId());

            System.out.println(sqlExecuted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getUserWallet(String discordId) {
        try (Connection conn = db.connectDatabase();
             PreparedStatement stmt = conn.prepareStatement(props.getUserWallet())) {

            stmt.setString(1, discordId);

            ResultSet rs = stmt.executeQuery();
            rs.next();

            System.out.println(props.getUserWallet().replace("?", discordId));

            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
