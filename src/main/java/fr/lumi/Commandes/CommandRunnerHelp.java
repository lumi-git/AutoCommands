package fr.lumi.Commandes;

import fr.lumi.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandRunnerHelp implements CommandExecutor, TabCompleter {


    Main plugin;

    public CommandRunnerHelp(Main plg) {

        plugin = plg;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        List<String> l = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("acmdhelp")) {
            if (sender instanceof Player) {
                List<String> list = new ArrayList<>();

            }
        }
        return l;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            String message =
                    "§e-----------§aAutoCommands-Help§e--------------\n"
                            + "§6" + ChatColor.translateAlternateColorCodes('&', plugin.VerifyPluginVersion()) + "\n"
                            + "§6ACMD Version : " + plugin.getDescription().getVersion() + "\n"
                            + "§6  /acmd <params> §e-> §7The main command of the plugin\n"
                            + "§6  /acmd list [page] §e-> §7Displays the list of the AutoCommands\n"
                            + "§6  /acmdreload §e-> §7reload the plugin and the Autocomands\n"
                            + "§6  /acmdeditor §e-> §7The §aUI§7 of autocomands, try it now!\n"
                            + "§6Link to our discord : §3https://discord.gg/EQHknuSTP8 \n"
                            + "§6The plugin's wiki  :§a https://github.com/AutoPluginsDev/Documentation/wiki/AutoCommands-%5BACMD%5D \n"
                            + "§e------------------------------------------\n";

            player.sendMessage(message);
        }
        return true;
    }
}