package space.jaobernardi.tardis.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import org.bukkit.entity.Player;
import space.jaobernardi.tardis.utils.Chat;

@CommandAlias("tardis|bernardtardis")
public class Tardis extends BaseCommand {
    @Default
    public void onTardis(Player player) {
        Chat.sendPrefixMsg(player, "Olá, mundo!");
    }

    @Subcommand("create")
    @CommandPermission("bernardtardis.tardis.create")
    public void onCreate(Player player, String name) {
        Chat.sendPrefixMsg(player, "Olá, tardis!");
    }
}
