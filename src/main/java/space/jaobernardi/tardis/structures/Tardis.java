package space.jaobernardi.tardis.structures;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import space.jaobernardi.tardis.database.Database;
import space.jaobernardi.tardis.exceptions.DatabaseError;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

// Placeholder Tardis
public class Tardis {
    public Player player;
    public UUID tardisUUID;
    private Location exteriorLocation;
    private HashMap<Location, TardisBlockFunction> tardisControls;
    private TardisFlightState flightState;
    private double energyAmount;

    public enum TardisFlightState {
        LANDED,
        IN_FLIGHT
    };

    public enum TardisBlockFunction {
        ENGINE_ENGAGE("ENGINE_ENGAGE"),
        HANDBRAKES("HANDBRAKES"),
        DOOR_LOCK_TOGGLE("DOOR_LOCK_TOGGLE"),
        REALTIME_SEAL("REALTIME_SEAL"),
        SAFEGUARDS_TOGGLE("SAFEGUARDS_TOGGLE"),
        POWER_TOGGLE("POWER_TOGGLE"),
        LOCKDOWN_TOGGLE("LOCKDOWN_TOGGLE"),
        HADS_TOGGLE("HADS_TOGGLE");

        private final String type;

        TardisBlockFunction(String i) {
            this.type = i;
        }

        public String getType() {
            return type;
        }
    };

    public Tardis(Player player, Location location, UUID uuid, TardisFlightState flightState, HashMap<Location, TardisBlockFunction> tardisControls, double energyAmount) throws SQLException, IOException {
        // Placeholder
        this.tardisControls = tardisControls;
        this.flightState = flightState;
        this.exteriorLocation = location;
        this.tardisUUID = uuid;
        this.player = player;
        this.energyAmount = energyAmount;
        this.syncDatabase();
    }

    public Tardis(Player player, Location location) throws DatabaseError, SQLException, IOException {
        this(
            player,
            location,
            UUID.randomUUID(),
            TardisFlightState.LANDED,
            new HashMap<Location, TardisBlockFunction>(),
            0.0
        );
    }

    public void syncDatabase() throws SQLException, IOException {
        Database database = new Database();
        database.updateTardis(this);
    }

    public double getEnergyAmount() {
        return energyAmount;
    }

    public Location getExteriorLocation() {
        return exteriorLocation;
    }

    public void setFlightState() throws SQLException, IOException {
        this.syncDatabase();
    }

    public TardisFlightState getFlightState() {
        return flightState;
    }

    public HashMap<Location, TardisBlockFunction> getTardisControls() {
        return tardisControls;
    }

    public boolean addControl(Location location, TardisBlockFunction function) throws SQLException, IOException {
        if (location.getBlock().isEmpty()) {
            return false;
        }
        this.tardisControls.put(location, function);
        this.syncDatabase();
        return true;
    }
}
