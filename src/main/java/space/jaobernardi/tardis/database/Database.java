package space.jaobernardi.tardis.database;

import space.jaobernardi.tardis.BernardTardis;
import space.jaobernardi.tardis.structures.Tardis;

import java.io.File;
import java.io.IOException;
import java.sql.*;


public class Database {
    private static Connection conn;

    public Database () throws SQLException, IOException {
        conn = getConn();
    }

    private static void createConn(File path) throws IOException {
        if (!path.exists()) {
            path.createNewFile();
        }
        String url = "jdbc:sqlite:"+path.getAbsolutePath();
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection getConn() throws SQLException, IOException {
        File dbPath = new File(BernardTardis.get().getDataFolder(), "database.db");
        boolean exists = dbPath.exists();

        if (conn == null || conn.isClosed()) {
            createConn(dbPath);
        }

        if (!exists) {
            setupTables();
        }

        return conn;
    }

    public void updateTardis(Tardis tardis) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT OR REPLACE INTO Tardis(UUID, PlayerUUID, FlightState, EnergyAmount, ExteriorX, ExteriorY, ExteriorZ, ExteriorWorld) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(0, tardis.tardisUUID.toString());
            statement.setString(1, tardis.player.getUniqueId().toString());
            statement.setString(2, tardis.getFlightState().toString());
            statement.setDouble(3, tardis.getEnergyAmount());
            statement.setDouble(4, tardis.getExteriorLocation().getX());
            statement.setDouble(5, tardis.getExteriorLocation().getY());
            statement.setDouble(6, tardis.getExteriorLocation().getZ());
            statement.setString(7, tardis.getExteriorLocation().getWorld().toString());
            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setupTables() throws SQLException {
        try {
            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE Tardis(UUID TEXT(36),PlayerUUID TEXT(36),FlightState TEXT,EnergyAmount REAL,ExteriorX REAL,ExteriorY REAL,ExteriorZ REAL,ExteriorWorld TEXT, PRIMARY KEY(UUID))");

            statement.execute("CREATE TABLE TardisControls(TardisUUID TEXT(36),ControlType TEXT,LocationX REAL,LocationY REAL,LocationZ REAL,LocationWorld TEXT, FOREIGN KEY(TardisUUID) REFERENCES Tardis(UUID) ON DELETE CASCADE)");
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
