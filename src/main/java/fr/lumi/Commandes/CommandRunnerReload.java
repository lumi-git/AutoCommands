package fr.lumi.Commandes;

import fr.lumi.Main;
import fr.lumi.Util.autocommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class CommandRunnerReload implements CommandExecutor {

    Main plugin;

    public CommandRunnerReload(Main plg) {

        plugin = plg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        plugin.Load();
        Bukkit.getConsoleSender().sendMessage(plugin.getUt().replacePlaceHoldersPluginVars(plugin.getLangConfig().getString("OnReload")));
        sender.sendMessage(plugin.getUt().replacePlaceHoldersPluginVars(plugin.getLangConfig().getString("OnReload")));

        return true;
    }
}