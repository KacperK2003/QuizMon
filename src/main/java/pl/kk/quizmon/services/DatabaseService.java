package pl.kk.quizmon.services;

import pl.kk.quizmon.models.Pokemon;
import pl.kk.quizmon.models.PokemonDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DatabaseService {
    private static final String connetionString = "jdbc:sqlite:database.db";
    private static DatabaseService instance;

    private DatabaseService() {
        initializeDatabase();
    }

    public static DatabaseService getInstance() {
        if (instance == null)
            instance = new DatabaseService();
        return instance;
    }

    private Connection connect() {
        try {
            return DriverManager.getConnection(connetionString);
        } catch (SQLException e) {
            Logger.getGlobal().severe(e.getMessage());
            Logger.getGlobal().severe(e.getSQLState());
        }
        return null;
    }

    private void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS pokemon (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "sprite BLOB, " +
                "icon BLOB" +
                ");";

        try (Connection conn = connect()) {
            if (conn == null)
                return;
            Statement statement = conn.createStatement();
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            Logger.getGlobal().severe(e.getMessage());
            Logger.getGlobal().severe(e.getSQLState());
        }
    }

    public void addPokemon(Pokemon pokemon) {
        String sql = "INSERT OR REPLACE INTO pokemon (id, name, sprite, icon) VALUES (?, ?, ?, ?);";

        if (pokemon == Pokemon.getUnknown())
            return;

        PokemonDAO dao = new PokemonDAO(pokemon);

        try (Connection conn = connect()) {
            if (conn == null)
                return;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, dao.getId());
            preparedStatement.setString(2, dao.getName());
            preparedStatement.setBytes(3, dao.getSprite());
            preparedStatement.setBytes(4, dao.getIcon());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.getGlobal().severe(e.getMessage());
            Logger.getGlobal().severe(e.getSQLState());
        }
    }

    public List<Pokemon> getPokemonList() {
        String sql = "SELECT * FROM pokemon ORDER BY id;";
        List<Pokemon> pokemonArrayList = new ArrayList<>();

        try (Connection conn = connect()) {
            if (conn == null)
                return null;

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                byte[] sprite = rs.getBytes("sprite");
                byte[] icon = rs.getBytes("icon");

                PokemonDAO dao = new PokemonDAO(id, name, sprite, icon);
                pokemonArrayList.add(dao.getPokemon());
            }

            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.getGlobal().severe(e.getMessage());
            Logger.getGlobal().severe(e.getSQLState());
        }

        return pokemonArrayList;
    }
}
