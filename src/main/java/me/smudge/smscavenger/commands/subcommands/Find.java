/*
 *   _____            _____
 *  / ____|          / ____|
 * | (___  _ __ ___ | (___   ___ __ ___   _____ _ __   __ _  ___ _ __
 *  \___ \| '_ ` _ \ \___ \ / __/ _` \ \ / / _ \ '_ \ / _` |/ _ \ '__|
 *  ____) | | | | | |____) | (_| (_| |\ V /  __/ | | | (_| |  __/ |
 * |_____/|_| |_| |_|_____/ \___\__,_| \_/ \___|_| |_|\__, |\___|_|
 *                                                     __/ |
 *                                                    |___/
 * SmScavenger is a treasure hunt game where players look for treasure
 * you hide to get rewards.
 *
 * Author : Smudge
 */

package me.smudge.smscavenger.commands.subcommands;

import me.smudge.smscavenger.commands.SubCommand;
import me.smudge.smscavenger.configs.CLocations;
import me.smudge.smscavenger.utility.Send;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;

public class Find extends SubCommand {
    @Override
    public String getName() {
        return "find";
    }

    @Override
    public String getDescription() {
        return "Find treasure";
    }

    @Override
    public HashMap<Integer, ArrayList<String>> getTabComplete() {
        return null;
    }

    @Override
    public String getPermission() {
        return "admin";
    }

    @Override
    public int getRequiredArguments() {
        return 0;
    }

    @Override
    public boolean preform(Player player, String[] args, Plugin plugin) {
        ArrayList<Location> locations = CLocations.getTreasuresLeft(5);

        // Send the player a list of treasure yet to be found
        Send.player(player, "&8&m&l--------]&r &6&lFind &8&m&l[--------\n");

        for (Location location : locations) {
            Send.player(player, "&aTreasure ID &e" + CLocations.getTreasureID(location));

            String teleportCommand = "/tp " +
                    location.getBlockX() + " " +
                    location.getBlockY() + " " +
                    location.getBlockZ();

            TextComponent message = new TextComponent(Send.convert("&7&l> &7" + teleportCommand + " &f&lClick to Teleport!"));
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, teleportCommand));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Send.convert("&7" + teleportCommand)).create()));
            player.spigot().sendMessage(message);
        }

        Send.player(player, "&8&m&l----------------------\n");
        Send.player(player, "&7Max locations &f5 &8| &7Total &f" + CLocations.getAmountOfLocationsToSpawn());
        return true;
    }
}
