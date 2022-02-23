package fr.lumi.Commandes;

import fr.lumi.Main;
import fr.lumi.Util.ListAfficher;
import fr.lumi.Util.StringNumberVerif;
import fr.lumi.Util.autocommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandRunnerCommand implements CommandExecutor, TabCompleter {

    private Main plugin;
    private ListAfficher listAfficher;

    public CommandRunnerCommand(Main plg) {
        plugin = plg;
        listAfficher = new ListAfficher(plugin);
    }



    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        List<String> l = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("acmd")) {

            if (sender instanceof Player) {
                List<String> list = new ArrayList<>();
                if (args.length == 1) {
                    l.add("list");
                    l.add("new");
                    l.add("enable");
                    l.add("disable");
                    l.add("run");
                    l.add("stop");
                    l.add("edit");
                    l.add("delete");
                }

                if (args.length == 2) {
                    if(Objects.equals(args[0], "list")){

                        for(int i =1;i< (plugin.getcommandList().size()+1)/listAfficher.getMaxCommand();i++) l.add((i)+"");

                    }
                }


                if (args.length == 2) {
                    if (Objects.equals(args[0], "delete") || (Objects.equals(args[0], "enable") || Objects.equals(args[0], "disable") || Objects.equals(args[0], "run") || Objects.equals(args[0], "stop"))) {
                        for (autocommand acmd : plugin.getcommandList()) {
                            l.add(acmd.getID());
                        }
                    }
                }

                if (Objects.equals(args[0], "edit")) {
                    if (args.length == 2) {
                        for (autocommand acmd : plugin.getcommandList()) {
                            l.add(acmd.getID());
                        }
                    }
                    if (args.length == 3) l.add("setMessage");
                    if (args.length == 3) l.add("addCommand");
                    if (args.length == 3) l.add("removeCommand");
                    if (args.length == 3) l.add("setDailyExecutionTime");

                    if (args.length == 4) {
                        if (Objects.equals(args[2], "setMessage")) {
                            l.add("message");
                        }
                        if (Objects.equals(args[2], "addCommand"))
                            l.add("command");
                        if (Objects.equals(args[2], "removeCommand"))
                            if (plugin.acmdIdExist(args[1])) {
                                autocommand acmd = plugin.getacmdInList(args[1]);
                                int i = 0;
                                for (String command : acmd.getCommands()) {
                                    l.add(i + "");
                                    i++;
                                }
                            }
                        if (Objects.equals(args[2], "setDailyExecutionTime")) {
                            l.add("18H42");
                        }
                    }


                }


                if (Objects.equals(args[0], "new")) {
                    if (args.length == 2) l.add("NAME");
                    if (args.length == 3) l.add("Loop Time (tick) 20tick->1second");
                    if (args.length == 4) l.add("Delay At Start (tick) 20tick->1second");
                    if (args.length == 5) l.add("Repetitions(-1 = no limit)");
                    if (args.length == 6) l.add("YOUR COMMAND");
                    if (args.length == 6) l.add("If you need to display a message, use /acmd setMessage ID [message]");
                }
            }
        }
        return l;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CommandSender player = sender;
        if (args.length == 0) return false;

        if (args.length >= 1) {

            if (Objects.equals(args[0], "list")) {

                if(args.length == 1 )
                    return false;

                if(!StringNumberVerif.isDigit(args[1]))
                    return false;
                int index = Integer.parseInt(args[1]);


                if(index <= 0 || index > listAfficher.getPageNumber()+1 ){ //need more args
                    if(listAfficher.getPageNumber() <= 0)
                        player.sendMessage("§4No command to show !");
                    else
                        player.sendMessage(plugin.getUt().replacePlaceHoldersPluginVars("&4Try a page between 1 and "+(listAfficher.getPageNumber() + 1)));
                    return false;
                }
                listAfficher.printListToSender(index-1,player);

            }
        }
        if (args.length == 2) {
            if (Objects.equals(args[0], "run")) {
                String id = args[1];
                if (!plugin.acmdIdExist(id)) return false;

                autocommand acmd;
                acmd = plugin.getacmdInList(id);
                if(acmd == null) return false;

                if(!acmd.isActive()){
                    player.sendMessage(plugin.getUt().replacePlaceHoldersPluginVars("&4Try to enable the command "+acmd.getName()+" first. "));
                    return false;
                }
                acmd.setRunning(true, plugin.getCommandsConfig(), plugin);


                player.sendMessage(plugin.getUt().replacePlaceHoldersForPlayer(plugin.getLangConfig().getString("onRunAcmd"), acmd));
            }

            if (Objects.equals(args[0], "stop")) {
                String id = args[1];
                if (!plugin.acmdIdExist(id)) return false;

                autocommand acmd;
                acmd = plugin.getacmdInList(id);
                if(acmd == null) return false;
                acmd.setRunning(false, plugin.getCommandsConfig(), plugin);

                player.sendMessage(plugin.getUt().replacePlaceHoldersForPlayer(plugin.getLangConfig().getString("onStopAcmd"), acmd));
                //player.sendMessage(plugin.getConfig().getString("Prefix")+" please reload the plugin with /acmdreload to make this change effective");
            }
            if (Objects.equals(args[0], "enable")) {
                String id = args[1];
                if (!plugin.acmdIdExist(id)) return false;

                autocommand acmd;
                acmd = plugin.getacmdInList(id);
                if(acmd == null) return false;

                acmd.setActive(true);

                player.sendMessage(plugin.getUt().replacePlaceHoldersForPlayer(plugin.getLangConfig().getString("onEnablAcmd"), acmd));
            }

            if (Objects.equals(args[0], "disable")) {
                String id = args[1];
                if (!plugin.acmdIdExist(id)) return false;

                autocommand acmd;
                acmd = plugin.getacmdInList(id);
                if(acmd == null) return false;
                acmd.setRunning(false, plugin.getCommandsConfig(), plugin);
                acmd.setActive(false);
                player.sendMessage(plugin.getUt().replacePlaceHoldersForPlayer(plugin.getLangConfig().getString("onDisableAcmd"), acmd));
                //player.sendMessage(plugin.getConfig().getString("Prefix")+" please reload the plugin with /acmdreload to make this change effective");
            }






        }
        if (Objects.equals(args[0], "edit") && args.length >= 2) {

            String id = args[1];
            if (!plugin.acmdIdExist(id)) return false;
            //initializing the new command
            autocommand acmd;
            acmd = plugin.getacmdInList(id);
            if(acmd == null) return false;
            if (Objects.equals(args[2], "setMessage")) {

                StringBuilder s = new StringBuilder();
                for (int i = 3; i <= args.length - 1; i++) {
                    s.append(args[i] + " ");
                }
                if (args.length == 3) s = new StringBuilder();
                acmd.setmessage(s.toString());

                acmd.saveInConfig(plugin.getCommandsConfig(), plugin);

            }
            if (Objects.equals(args[2], "addCommand")) {


                StringBuilder s = new StringBuilder();
                for (int i = 3; i <= args.length - 1; i++) {
                    s.append(args[i] + " ");
                }
                if (args.length == 3) return false;
                acmd.addCommand(s.toString());

                acmd.saveInConfig(plugin.getCommandsConfig(), plugin);

            }

            if (Objects.equals(args[2], "removeCommand")) {


                if(!StringNumberVerif.isDigit(args[3]))
                    return false;

                int index = Integer.parseInt(args[3]);
                if (index >= 0 && index < acmd.getCommandCount())
                    acmd.removeCommand(index);

                acmd.saveInConfig(plugin.getCommandsConfig(), plugin);

            }

            if (Objects.equals(args[2], "setDai" +
                    "lyExecutionTime")) {

                if (args.length == 3) {
                    acmd.setTime("");
                } else
                    acmd.setTime(args[3]);

                acmd.saveInConfig(plugin.getCommandsConfig(), plugin);

                player.sendMessage(plugin.getUt().replacePlaceHoldersForPlayer(plugin.getLangConfig().getString("CommandEdited"), acmd));
            }


        }

        if (args.length >= 2) {
            if (Objects.equals(args[0], "delete")) {

                String id = args[1];
                if (!plugin.acmdIdExist(id)) return false;
                //initializing the new command
                autocommand cmd;
                cmd = plugin.getacmdInList(id);

                //desactivation of the command

                cmd.setRunning(false, plugin.getCommandsConfig(), plugin);



                cmd.delete(plugin.getCommandsConfig());

                player.sendMessage(plugin.getUt().replacePlaceHoldersForPlayer(plugin.getLangConfig().getString("onDeleteAcmd"), cmd));


            }
        }


        if (args.length >= 4) {
            if (Objects.equals(args[0], "new")) {

                long cycle;
                long delay;
                int repetitions;

                autocommand cmd = new autocommand(plugin);
                cmd.setName(args[1]);
                if(!StringNumberVerif.isDigit(args[2]))
                    return false;

                cycle = (long) Float.parseFloat(args[2]);


                cmd.setCycle(cycle);
                if (cmd.getCycle() < 200) {
                    player.sendMessage(plugin.getUt().replacePlaceHoldersForPlayer(plugin.getLangConfig().getString("AlertShortCycle"), cmd));

                }

                if(!StringNumberVerif.isDigit(args[3]))
                    return false;
                delay = (long) Float.parseFloat(args[3]);
                cmd.setDelay(delay);

                if(!StringNumberVerif.isDigit(args[4]))
                    return false;
                repetitions = Integer.parseInt(args[4]);

                cmd.setRepetition(repetitions);

                StringBuilder s = new StringBuilder();
                for (int i = 5; i <= args.length - 1; i++) {
                    s.append(args[i] + " ");
                }

                cmd.addCommand(s.toString());

                cmd.setID("acmd" + plugin.getcommandList().size());

                player.sendMessage(plugin.getUt().replacePlaceHoldersForPlayer(plugin.getLangConfig().getString("onAddingANewCommand"), cmd));



                cmd.saveInConfig(plugin.getCommandsConfig(), plugin);//sauvegarde de la commande dans le fichier de commands


                cmd.setRunning(cmd.isRunning(), plugin.getCommandsConfig(), plugin);
                plugin.getcommandList().add(cmd);

            }
        }

        return true;
    }
}