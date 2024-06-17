package fr.lumi.CommandPatternObject;
import fr.lumi.Main;
import fr.lumi.Util.autocommandDataPattern;

public abstract class Command extends autocommandDataPattern {
    Main plugin;
    public Command(Main plg) {
        plugin = plg;
    }

    public abstract void execute();


}
