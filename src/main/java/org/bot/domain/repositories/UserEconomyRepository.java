package org.bot.domain.repositories;

import org.bot.config.DatabaseConfig;
import org.bot.config.PropertiesConfig;
import org.bot.domain.dto.BalanceDto;
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

    public void createUserEconomyQuery(String discordId) {
        try (Connection conn = db.connectDatabase();
             PreparedStatement stmt = conn.prepareStatement(props.getSqlCreateUserEconomy())) {

            stmt.setString(1, discordId);
            stmt.executeUpdate();

            System.out.println(props.getSqlCreateUserEconomy().replace("?", discordId));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateWalletWorkQuery(UserEconomyModel userEconomyModel) {
        try (Connection conn = db.connectDatabase();
             PreparedStatement stmt = conn.prepareStatement(props.updateUserWalletWork())) {

            stmt.setInt(1, userEconomyModel.getWallet());
            stmt.setTimestamp(2, userEconomyModel.getLastWork());
            stmt.setString(3, userEconomyModel.getDiscordId());
            stmt.executeUpdate();

            String sqlExecuted = props.updateUserWalletWork().replaceFirst("\\?", String.valueOf(userEconomyModel.getWallet()));
            sqlExecuted = sqlExecuted.replaceFirst("\\?", String.valueOf(userEconomyModel.getLastWork()));
            sqlExecuted = sqlExecuted.replaceFirst("\\?", userEconomyModel.getDiscordId());

            System.out.println(sqlExecuted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getUserWalletQuery(String discordId) {
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

    public int getUserBankQuery(String discordId) {
        try (Connection conn = db.connectDatabase();
             PreparedStatement stmt = conn.prepareStatement(props.getUserBank())) {
            stmt.setString(1, discordId);

            ResultSet rs = stmt.executeQuery();
            rs.next();

            System.out.println(props.getUserBank().replace("?", discordId));

            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUserBankQuery(int bank, String discordId) {
        try (Connection conn = db.connectDatabase();
             PreparedStatement stmt = conn.prepareStatement(props.updateUserBank())) {

            stmt.setInt(1, bank);
            stmt.setString(2, discordId);
            stmt.executeUpdate();

            String sqlExecuted = props.updateUserWalletWork().replaceFirst("\\?", String.valueOf(bank));
            sqlExecuted = sqlExecuted.replaceFirst("\\?", discordId);

            System.out.println(sqlExecuted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUserWalletQuery(int wallet, String discordId) {
        try (Connection conn = db.connectDatabase();
             PreparedStatement stmt = conn.prepareStatement(props.updateUserWallet())) {

            stmt.setInt(1, wallet);
            stmt.setString(2, discordId);
            stmt.executeUpdate();

            String sqlExecuted = props.updateUserWallet().replaceFirst("\\?", String.valueOf(wallet));
            sqlExecuted = sqlExecuted.replaceFirst("\\?", discordId);

            System.out.println(sqlExecuted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BalanceDto getUserBalanceQuery(String discordId) {
        try (Connection conn = db.connectDatabase();
             PreparedStatement stmt = conn.prepareStatement(props.getUserBalance())) {

            stmt.setString(1, discordId);
            ResultSet rs = stmt.executeQuery();
            rs.next();

            System.out.println(props.getUserBalance().replace("?", discordId));

            int wallet = rs.getInt(1);
            int bank = rs.getInt(2);

            return new BalanceDto(wallet, bank);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
