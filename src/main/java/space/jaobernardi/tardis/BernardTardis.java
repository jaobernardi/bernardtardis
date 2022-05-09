package space.jaobernardi.tardis;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import space.jaobernardi.tardis.commands.Tardis;
import space.jaobernardi.tardis.database.Database;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class BernardTardis extends JavaPlugin {
    public static BernardTardis INSTANCE;

    @Override
    public void onLoad() {
        INSTANCE = this;

        // Run setups
        setupDataFolder();
        setupConfig();
        try {
            Database db = new Database();
        } catch (SQLException e) {
            this.severe("Failed to load database: "+e.getStackTrace()[0].toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        info("Finished loading.");
    }

    @Override
    public void onEnable() {
        PaperCommandManager commandManager;
        // Connect commands
        info("Registering commands.");
        commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new Tardis());

        // Done
        info("Done.");
    }

    public static BernardTardis get() {
        if (INSTANCE == null)
            throw new Error("Plugin was not instanciated.");
        else
            return INSTANCE;
    }

    public void setupDataFolder() {
        File path = getDataFolder();
        if (!path.exists()) {
            info("Creating plugin's folder.");
            path.mkdir();
        }
    }

    private void setupConfig () {
        if (!new File(getDataFolder(), "config.yml").exists()) {
            this.saveDefaultConfig();
        } else {
            this.info("Loaded config");
        }
    }

    // Logging stuff
    public void info (String msg) {
        this.getLogger().info(msg);
    }

    public void severe (String msg) {
        this.getLogger().severe(msg);
    }

    public void warn (String msg) {
        this.getLogger().warning(msg);
    }
}
